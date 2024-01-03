package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.CartDetailsRepository;
import group6.ecommerce.Repository.OrderDetailsRepository;
import group6.ecommerce.Repository.OrderRepository;
import group6.ecommerce.model.Order;
import group6.ecommerce.model.Order_Details;
import group6.ecommerce.model.ProductDetails;
import group6.ecommerce.service.CouponService;
import group6.ecommerce.service.ProductDetailsService;
import group6.ecommerce.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
    public String CheckOut(Order order) {
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
                orderRepository.save(order);
                return "Đặt Hàng Thành Công";
            }else{
                order.setStatus("waitPay");
                orderRepository.save(order);
                return "waitPay";
            }
        }else{
            return validate;
        }
    }
}
