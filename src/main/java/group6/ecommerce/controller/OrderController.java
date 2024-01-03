package group6.ecommerce.controller;

import group6.ecommerce.model.Users;
import group6.ecommerce.payload.request.OrderRequest;
import group6.ecommerce.service.OrderService;
import group6.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping ("order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping ("checkout")
    public ResponseEntity<String> CheckOut (@RequestBody OrderRequest order){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users principal = (Users) authentication.getPrincipal();
        Users userLogin = userService.findById(principal.getId());
        order.setUserOrder(userLogin);
        String status = orderService.CheckOut(order.getOrder());
        if (status.equalsIgnoreCase("Đặt Hàng Thành Công") || status.equalsIgnoreCase("waitPay")) {
            return ResponseEntity.status(HttpStatus.OK).body(status);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status);
        }
    }
}
