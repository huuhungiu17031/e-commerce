package group6.ecommerce.controller;

import group6.ecommerce.model.Cart_Details;
import group6.ecommerce.model.Users;
import group6.ecommerce.payload.request.CartDetailsRequest;
import group6.ecommerce.payload.response.CartRespone;
import group6.ecommerce.service.CartService;
import group6.ecommerce.service.ProductDetailsService;
import group6.ecommerce.service.ProductService;
import group6.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductDetailsService productDetailsService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<CartRespone> cart (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users principal = (Users) authentication.getPrincipal();
        System.out.println(principal.getId());
        Users userLogin = userService.findById(principal.getId());
        CartRespone cartRespone = new CartRespone(userLogin.getCart());
        return ResponseEntity.status(HttpStatus.OK).body(cartRespone);
    }
    @PostMapping
    public ResponseEntity<String> addToCart (@RequestBody CartDetailsRequest cartDetailsRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users principal = (Users) authentication.getPrincipal();
        Users userLogin = userService.findById(principal.getId());
        cartDetailsRequest.setUser(userLogin);
        cartDetailsRequest.setProduct(productService.findById(cartDetailsRequest.getProductId()));
        Cart_Details item = cartDetailsRequest.getCartDetails();
        String staus = cartService.addTocart(item,userLogin.getId());
        if (staus.equalsIgnoreCase("Thêm Vào Giỏ Hàng Thành Công")) {
            return ResponseEntity.status(HttpStatus.OK).body(staus);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(staus);
        }
    }

    @DeleteMapping
    ResponseEntity<String> removeToCart (@RequestBody CartDetailsRequest cartDetailsRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users principal = (Users) authentication.getPrincipal();
        cartDetailsRequest.setUser(principal);
        cartDetailsRequest.setProduct(productService.findById(cartDetailsRequest.getProductId()));
        String status = cartService.removeToCart(cartDetailsRequest.getCartDetails(), principal.getId());
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }
}

