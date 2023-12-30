package group6.ecommerce.controller;

import group6.ecommerce.model.Cart_Details;
import group6.ecommerce.model.ProductDetails;
import group6.ecommerce.model.Users;
import group6.ecommerce.payload.request.CartDetailsRequest;
import group6.ecommerce.service.CartService;
import group6.ecommerce.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/user/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductDetailsService productDetailsService;
    @PostMapping ("/addtocart")
    public ResponseEntity<String> addToCart (@RequestBody CartDetailsRequest cartDetailsRequest){
        Users userLogin = new Users();
        Cart_Details item = cartDetailsRequest.getCartDetails();
        String staus = cartService.addTocart(item,userLogin.getId());
        if (staus.equalsIgnoreCase("Thêm Vào Giỏ Hàng Thành Công")) {
            return ResponseEntity.status(HttpStatus.OK).body(staus);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(staus);
        }
    }
    @PostMapping ("/removetocart")
    ResponseEntity<String> removeToCart (@RequestBody CartDetailsRequest cartDetailsRequest){
        Users userLogin = new Users();
        cartService.removeToCart(cartDetailsRequest.getCartDetails(),userLogin.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Xóa Giỏ Hàng Thành Công");
    }
}

