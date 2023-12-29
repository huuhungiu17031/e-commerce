package group6.ecommerce.controller;

import group6.ecommerce.model.Product;
import group6.ecommerce.payload.response.PageProductRespone;
import group6.ecommerce.payload.response.ProductRespone;
import group6.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping ("/{id}")
    public ResponseEntity<PageProductRespone> findByPage (@PathVariable("id")Optional<Integer> p){
        Pageable page = PageRequest.of(p.orElse(0),12);
        Page<Product> pageRespone = productService.findAllQuantityLarger0(page);
        return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
    }

    @GetMapping ("/details/{id}")
    public ResponseEntity<ProductRespone> DetailsProduct (@PathVariable("id")Optional<Integer> id){
        Product Product = productService.findById(id.get());
        ProductRespone productRespone = new ProductRespone(Product);
        return ResponseEntity.status(HttpStatus.OK).body(productRespone);
    }
}
