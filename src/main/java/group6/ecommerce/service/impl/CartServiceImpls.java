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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpls implements CartService {

    private final CartRepository cartRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final UserService userService;
    private final ProductDetailsService productDetailsService;

    @Override
    public String addTocart(Cart_Details item, int userId) {
        ProductDetails productDetails = productDetailsService
                .findProductDetailsByProductIdAndColornameAndSizename(item.getProduct().getId()
                        ,item.getColor(),item.getSize());
        String valueItem = item.getProduct().getId()+item.getSize()+item.getColor();
        Users userLogin = userService.findById(userId);
        Cart cart = userLogin.getCart();
        if (cart.getListItems().get(valueItem)==null){
                if (item.getAmount() <= productDetails.getQuantity()) {
                    cart.getListItems().put(valueItem, item);
                }else{
                    return "Số Lượng Sản Phẩm Không Đủ";
                }
        }else{
            Cart_Details itemCart = cart.getListItems().get(valueItem);
            itemCart.setAmount(itemCart.getAmount()+itemCart.getAmount());
            if (item.getAmount() <= productDetails.getQuantity()) {
                cart.getListItems().put(valueItem, itemCart);
            }else{
                return "Số Lượng Sản Phẩm Không Đủ";
            }
        }

        cartRepository.save(cart);
        return "Thêm Vào Giỏ Hàng Thành Công";
    }

    @Override
    public void removeToCart(Cart_Details item, int userId) {
        Users userLogin = userService.findById(userId);
        String itemValue = item.getProduct().getId()+item.getSize()+item.getColor();
        Cart cart = userLogin.getCart();
        if (cart.getListItems().get(itemValue)!=null){
            cart.getListItems().remove(itemValue);
        }
        cartRepository.save(cart);
    }
}
