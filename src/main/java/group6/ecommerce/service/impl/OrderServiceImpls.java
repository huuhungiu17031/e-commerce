package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.CartDetailsRepository;
import group6.ecommerce.Repository.OrderDetailsRepository;
import group6.ecommerce.Repository.OrderRepository;
import group6.ecommerce.model.Order;
import group6.ecommerce.model.Order_Details;
import group6.ecommerce.model.Product;
import group6.ecommerce.model.ProductDetails;

import group6.ecommerce.payload.response.OrderResponse;
import group6.ecommerce.payload.response.PaginationResponse;
import group6.ecommerce.service.CouponService;
import group6.ecommerce.service.ProductDetailsService;
import group6.ecommerce.service.OrderService;
import group6.ecommerce.utils.HandleSort;

import group6.ecommerce.payload.response.CheckOutRespone;

import group6.ecommerce.service.ProductService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpls implements OrderService {
    private final ProductDetailsService productDetailsService;
    private final CouponService couponService;
    private final OrderRepository orderRepository;
    private final CartDetailsRepository cartDetailsRepository;

    private final OrderDetailsRepository orderDetailsRepository;

    private final ProductService productService;
    public String validate (Order order){
        AtomicBoolean checkQuantity = new AtomicBoolean(true);
        if (order.getUserOrder().getCart().getListItems().size() <=0) {
            return "Vui Lòng Thêm Sản Phẩm Vào Giỏ Hàng Trước Khí Đặt Hàng";
        }
        order.getUserOrder().getCart().getListItems().values().stream().forEach(item -> {
            if (productDetailsService.findProductDetailsByProductIdAndColornameAndSizename(
                    item.getProduct().getId(),
                    item.getColor(),
                    item.getSize()
            )==null || productDetailsService.findProductDetailsByProductIdAndColornameAndSizename(
                    item.getProduct().getId(),
                    item.getColor(),
                    item.getSize()
            ).getQuantity()<item.getAmount()){
                checkQuantity.set(false);
            }
        });
        if (checkQuantity.get() == false){
            return "Có Sản Phẩm Trong Giỏ Hàng Đã Hết Hàng Hoặc Không Đủ Số Lượng Bạn Đặt";
        }

        return "ok";
    }

    @Override
    public CheckOutRespone CheckOut(Order order) {
        if (!order.getCoupon().equalsIgnoreCase("")) {
            if (couponService.findByCode(order.getCoupon()) == null ||
                    couponService.findByCode(order.getCoupon()).getDiscount() <= 0) {
                order.setCoupon("");
            }
        }
        String validate = validate(order);
            orderRepository.save(order);
            if (validate.equalsIgnoreCase("ok")){
            order.getUserOrder().getCart().getListItems().values().stream().forEach(item -> {
                Order_Details order_details = new Order_Details();
                order_details.setProduct(item.getProduct());
                order_details.setSize(item.getSize());
                order_details.setColor(item.getColor());
                order_details.setAmount(item.getAmount());
                order_details.setItemOrder(order);
                String key = item.getProduct().getId()+item.getSize()+item.getColor();
                order.getListItems().put(key,order_details);
                orderDetailsRepository.save(order_details);
                cartDetailsRepository.deleteByKey(key,item.getItemCart().getCartId());
                ProductDetails productDetails = productDetailsService.findProductDetailsByProductIdAndColornameAndSizename(
                        item.getProduct().getId(),
                        item.getColor(),
                        item.getSize());
                productDetails.setQuantity(productDetails.getQuantity()-item.getAmount());
                productDetailsService.save(productDetails);
            });
            if (order.getPayment().equalsIgnoreCase("shipcod")){
                Order o = orderRepository.save(order);
                CheckOutRespone rp = new CheckOutRespone();
                rp.setOrderId(o.getId());
                rp.setStatus("Đặt Hàng Thành Công");
                return rp;
            }else{
                order.setStatus("waitPay");
                orderRepository.save(order);
                Order o = orderRepository.save(order);
                CheckOutRespone rp = new CheckOutRespone();
                rp.setOrderId(o.getId());
                rp.setStatus("waitPayVnpay");
                return rp;
            }
        }else{
                CheckOutRespone rp = new CheckOutRespone();
                rp.setOrderId(-99);
                rp.setStatus(validate);
                return rp;
        }
    }

    @Override
    public String updateStatus(int orderId, String newStatus) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null){
            order.setStatus(newStatus);
            orderRepository.save(order);
            return "Updated Successfully";
        }else{
            return "Updated Failed";
        }
    }

    @Override
    public PaginationResponse listOrder(
            Integer pageSize,
            Integer pageNum,
            String fields,
            String orderBy,
            Boolean getAll,
            String status) {
        Sort sort = HandleSort.buildSortProperties(fields, orderBy);
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Order> pageOrder = orderRepository.findByStatusAndSort(status, pageable);
        return new PaginationResponse(
                pageNum,
                pageSize,
                pageOrder.getTotalElements(),
                pageOrder.isLast(),
                pageOrder.getTotalPages(),
                pageOrder.getContent().stream().map(order -> new OrderResponse(order)).toList());
    }
  
    public Order findById(int id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public Order save(Order order) {
       return  orderRepository.save(order);
    }

    @Override
    public void cancel(Order order) {
        Map<String, Order_Details> items = order.getListItems();
        items.values().stream().forEach(item->{
            Product p = productService.findById(item.getProduct().getId());
            for (ProductDetails productdetails : p.getListProductDetails()){
                if ((productdetails.getProducts().getId()+""+productdetails.getSize()+productdetails.getColor()).
                        equalsIgnoreCase(item.getProduct().getId()+item.getSize()+item.getColor())){
                    productdetails.setQuantity(productdetails.getQuantity()+item.getAmount());
                    productDetailsService.save(productdetails);
                }
            }
        });
        order.setStatus("cancel");
        orderRepository.save(order);
    }
}
