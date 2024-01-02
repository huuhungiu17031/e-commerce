package group6.ecommerce.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginationResponse {
    private int pageNum;
    private int pageSize;
    private long totalElements;
    private boolean isLast;
    private int totalPage;
    private Object content;
}