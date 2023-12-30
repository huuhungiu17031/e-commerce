package group6.ecommerce.payload.response;

import group6.ecommerce.model.Product;
import lombok.Getter;
import org.springframework.data.domain.Page;


import java.util.ArrayList;
import java.util.List;

@Getter
public class PageProductRespone {
    private List<ProductRespone> listProduct;
    private int pageNumber;
    private int totalPage;
    public PageProductRespone(Page<Product> page) {
        this.totalPage = page.getTotalPages();
        this.pageNumber = page.getNumber();
        List<ProductRespone> listP = new ArrayList<>();
        page.getContent().stream().forEach(pro -> listP.add(new ProductRespone(pro)));
        this.listProduct = listP;
    }
}
