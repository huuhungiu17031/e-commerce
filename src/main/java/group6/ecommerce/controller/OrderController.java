package group6.ecommerce.controller;

import group6.ecommerce.model.Users;
import group6.ecommerce.payload.request.OrderRequest;
import group6.ecommerce.payload.response.HttpResponse;
import group6.ecommerce.service.OrderService;
import group6.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("updateStatus/{orderId}/{newStatus}")
    public ResponseEntity<String> updateStatus(@PathVariable int orderId, @PathVariable String newStatus) {
        String result = orderService.updateStatus(orderId, newStatus);
        if (result.equalsIgnoreCase("Updated Successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

    }

    @GetMapping
    public ResponseEntity<HttpResponse> getOrder(
            @RequestParam(required = false, defaultValue = "12", value = "pageSize") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0", value = "pageNum") Integer pageNum,
            @RequestParam(required = false, defaultValue = "id", value = "fields") String fields,
            @RequestParam(required = false, defaultValue = "asc", value = "orderBy") String orderBy,
            @RequestParam(required = false, defaultValue = "false", value = "getAll") Boolean getAll,
            @RequestParam(required = false, value = "status") String status) {
        HttpResponse httpResponse = new HttpResponse(
                HttpStatus.OK.value(),
                null,
                orderService.listOrder(pageSize, pageNum, fields, orderBy, getAll, status));
        return ResponseEntity.status(HttpStatus.OK).body(httpResponse);
    }
}
