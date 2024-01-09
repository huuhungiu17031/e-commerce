package group6.ecommerce.controller;

import group6.ecommerce.configuration.VnpayConfig;
import group6.ecommerce.model.Order;
import group6.ecommerce.model.Users;
import group6.ecommerce.payload.request.OrderRequest;
import group6.ecommerce.payload.response.HttpResponse;
import group6.ecommerce.payload.response.CheckOutRespone;
import group6.ecommerce.payload.response.OrderResponse;
import group6.ecommerce.payload.response.addCartRespone;
import group6.ecommerce.service.OrderService;
import group6.ecommerce.service.ProductDetailsService;
import group6.ecommerce.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping ("order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final ProductDetailsService productDetails;
    @PostMapping ("checkout")
    public ResponseEntity<CheckOutRespone> CheckOut (@RequestBody OrderRequest order){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users principal = (Users) authentication.getPrincipal();
        Users userLogin = userService.findById(principal.getId());
        order.setUserOrder(userLogin);
        CheckOutRespone status = orderService.CheckOut(order.getOrder());
        if (status.getStatus().equalsIgnoreCase("Đặt Hàng Thành Công") || status.getStatus().equalsIgnoreCase("waitPayVnpay")) {
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

    @GetMapping("/paying/{id}")
    public ResponseEntity<addCartRespone> paying(@PathVariable("id") int id,
                                                 @RequestParam("vnp_Amount") String amount,
                                                 @RequestParam("vnp_BankCode") String bankcode,
                                                 @RequestParam("vnp_CardType") String cardtype,
                                                 @RequestParam("vnp_OrderInfo") String orderInfo,
                                                 @RequestParam("vnp_PayDate") String date,
                                                 @RequestParam("vnp_ResponseCode") String code,
                                                 @RequestParam("vnp_TmnCode") String tmncode,
                                                 @RequestParam("vnp_TransactionNo") String transactionno,
                                                 @RequestParam("vnp_TransactionStatus") String status,
                                                 @RequestParam("vnp_TxnRef") String txnref,
                                                 @RequestParam("vnp_SecureHash") String hash,
                                                 @RequestParam(name = "vnp_BankTranNo", defaultValue = "") String tranno,
                                                 Model model,
                                                 HttpSession session) throws UnsupportedEncodingException, MessagingException {
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Amount", amount);
        vnp_Params.put("vnp_BankCode", bankcode);
        vnp_Params.put("vnp_CardType", cardtype);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_PayDate", date);
        vnp_Params.put("vnp_ResponseCode", code);
        vnp_Params.put("vnp_TmnCode", tmncode);
        vnp_Params.put("vnp_TransactionNo", transactionno);
        vnp_Params.put("vnp_TransactionStatus", status);
        vnp_Params.put("vnp_TxnRef", txnref);
        if (!tranno.equalsIgnoreCase("")) {
            vnp_Params.put("vnp_BankTranNo", tranno);
        }
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.vnp_HashSecret, hashData.toString());
        System.out.println(vnp_SecureHash);
        System.out.println(hash);
        Order order = orderService.findById(id);
        if (vnp_SecureHash.equalsIgnoreCase(hash) && order.getId() == Integer.parseInt(txnref)) {
            if (status.equalsIgnoreCase("00")) {

                order.setPayment("paid");
                order.setStatus("pending");
                orderService.save(order);


                return ResponseEntity.status(HttpStatus.OK).body(new addCartRespone("Thanh Toán Thành Công"));
            } else {
                order.setPayment("unpaid");
                orderService.cancel(order);
                return ResponseEntity.status(HttpStatus.OK).body(new addCartRespone("Đơn Hàng Đã Hủy"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new addCartRespone("https://sandbox.vnpayment.vn/paymentv2/Payment/Error.html?code=70"));
        }
    }

    @GetMapping("{userid}")
    public List<OrderResponse> findOrdersByUserId(@PathVariable("userid") int userid){
        return orderService.findOrderByUserId(userid);
    }
}
