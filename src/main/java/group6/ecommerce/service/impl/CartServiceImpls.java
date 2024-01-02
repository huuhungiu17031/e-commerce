package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.CartDetailsRepository;
import group6.ecommerce.Repository.CartRepository;
import group6.ecommerce.model.Cart;
import group6.ecommerce.model.Cart_Details;
import group6.ecommerce.model.ProductDetails;
import group6.ecommerce.model.Users;
import group6.ecommerce.service.CartService;
import group6.ecommerce.service.ProductDetailsService;
import group6.ecommerce.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartServiceImpls implements CartService {

    private final CartRepository cartRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final UserService userService;

    private final ProductDetailsService productDetailsService;

    @Override
    public String addTocart(Cart_Details item, int userId) {
        ProductDetails productDetails = null;
        if (productDetailsService.findProductDetailsByProductIdAndColornameAndSizename(item.getProduct().getId()
                        ,item.getColor(),
                        item.getSize())!=null) {
         productDetails = productDetailsService.findProductDetailsByProductIdAndColornameAndSizename(
                 item.getProduct().getId(),
                 item.getColor(),
                 item.getSize());
        }else{
            return "Sản Phẩm Không Tồn Tại";
        }
        String valueItem = item.getProduct().getId()+item.getSize()+item.getColor();
        Users userLogin = userService.findById(userId);
        Cart cart = userLogin.getCart();
        if (cart.getListItems().get(valueItem)==null){
                if (item.getAmount() <= productDetails.getQuantity()) {
                    Map<String,Cart_Details> listitem = cart.getListItems();
                    listitem.put(valueItem,item);
                    cart.setListItems(listitem);
                    cartDetailsRepository.save(item);
                }else{
                    return "Số Lượng Sản Phẩm Không Đủ";
                }
        }else{
            Cart_Details itemCart = cart.getListItems().get(valueItem);
            itemCart.setAmount(itemCart.getAmount()+item.getAmount());
            if (itemCart.getAmount() <= productDetails.getQuantity()) {
                Map<String,Cart_Details> listitem = cart.getListItems();
                listitem.put(valueItem,itemCart);
                cart.setListItems(listitem);
                cartDetailsRepository.save(itemCart);
            }else{
                return "Số Lượng Sản Phẩm Không Đủ";
            }
        }

        cartRepository.save(cart);
        return "Thêm Vào Giỏ Hàng Thành Công";
    }

    @Transactional
    @Override
    public String removeToCart(Cart_Details item, int userId) {
        Users userLogin = userService.findById(userId);
        String itemValue = item.getProduct().getId()+item.getSize()+item.getColor();
        Cart cart = userLogin.getCart();
        if (cart.getListItems().get(itemValue)!=null){
            cart.getListItems().remove(itemValue);
            cartDetailsRepository.deleteByKey(itemValue);
            return "xóa giỏ hàng thành công";
        }
        return "Không Thấy SP Này";
    }

    public void save (Cart cart){
        cartRepository.save(cart);
    }
}
