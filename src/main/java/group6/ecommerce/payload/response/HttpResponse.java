package group6.ecommerce.payload.response;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HttpResponse {
    private int status;
    private String message;
    private String timestamp;
    private Object data;

    public HttpResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date().toString();
        this.data = data;
    }
}