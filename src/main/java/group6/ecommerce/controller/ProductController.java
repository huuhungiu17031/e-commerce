package group6.ecommerce.controller;

import group6.ecommerce.model.Category;
import group6.ecommerce.model.Product;
import group6.ecommerce.model.Type;
import group6.ecommerce.payload.request.ProductRequest;
import group6.ecommerce.payload.response.PageProductRespone;
import group6.ecommerce.payload.response.ProductRespone;
import group6.ecommerce.service.CategoryService;
import group6.ecommerce.service.ProductService;
import group6.ecommerce.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final TypeService typeService;
    private final CategoryService categoryService;
    @GetMapping ("/user/product/{page}")
    public ResponseEntity<PageProductRespone> findByPage (@PathVariable(value = "page")Optional<Integer> p,
                                                          @RequestParam (value = "sort", defaultValue = "")String sort,
                                                          @RequestParam (value = "category", defaultValue = "")String cateogory){
        if (cateogory.equalsIgnoreCase("") || categoryService.findCategoryByName(cateogory)==null){
        if (sort.equalsIgnoreCase("nameaz")){
            Pageable page = PageRequest.of(p.orElse(0),12, Sort.by("product_name").ascending());
            Page<Product> pageRespone = productService.findAllQuantityLarger0(page);
            return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
        }else if (sort.equalsIgnoreCase("nameza")){
            Pageable page = PageRequest.of(p.orElse(0),12, Sort.by("product_name").descending());
            Page<Product> pageRespone = productService.findAllQuantityLarger0(page);
            return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
        }else if (sort.equalsIgnoreCase("pricelowtohight")){
            Pageable page = PageRequest.of(p.orElse(0),12, Sort.by("product_price").ascending());
            Page<Product> pageRespone = productService.findAllQuantityLarger0(page);
            return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
        }else if (sort.equalsIgnoreCase("pricehighttolow")){
            Pageable page = PageRequest.of(p.orElse(0),12, Sort.by("product_price").descending());
            Page<Product> pageRespone = productService.findAllQuantityLarger0(page);
            return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
        }else{
            Pageable page = PageRequest.of(p.orElse(0),12);
            Page<Product> pageRespone = productService.findAllQuantityLarger0(page);
            return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
        }
        }else{
            if (sort.equalsIgnoreCase("nameaz")){
                Pageable page = PageRequest.of(p.orElse(0),12, Sort.by("product_name").ascending());
                Page<Product> pageRespone = productService.findByCategoryNameQuantityLarger0(cateogory,page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            }else if (sort.equalsIgnoreCase("nameza")){
                Pageable page = PageRequest.of(p.orElse(0),12, Sort.by("product_name").descending());
                Page<Product> pageRespone = productService.findByCategoryNameQuantityLarger0(cateogory,page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            }else if (sort.equalsIgnoreCase("pricelowtohight")){
                Pageable page = PageRequest.of(p.orElse(0),12, Sort.by("product_price").ascending());
                Page<Product> pageRespone = productService.findByCategoryNameQuantityLarger0(cateogory,page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            }else if (sort.equalsIgnoreCase("pricehighttolow")){
                Pageable page = PageRequest.of(p.orElse(0),12, Sort.by("product_price").descending());
                Page<Product> pageRespone = productService.findByCategoryNameQuantityLarger0(cateogory,page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            }else{
                Pageable page = PageRequest.of(p.orElse(0),12);
                Page<Product> pageRespone = productService.findByCategoryNameQuantityLarger0(cateogory,page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            }
        }
    }

    @GetMapping ("/user/product/details/{id}")
    public ResponseEntity<ProductRespone> DetailsProduct (@PathVariable("id")Optional<Integer> id){
        Product Product = productService.findById(id.get());
        ProductRespone productRespone = new ProductRespone(Product);
        return ResponseEntity.status(HttpStatus.OK).body(productRespone);
    }

    @PostMapping("/admin/product/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest){
        // Get type object in DB
        Type type = typeService.getTypeById(productRequest.getTypeName());

        // Get category object in DB
        Category category = categoryService.findCategoryByName(productRequest.getCategoryName());

        Product product = new Product();
        product.setDescription(productRequest.getDescription());
        product.setDimension(productRequest.getDimension());
        product.setImageUrls(productRequest.getImageUrls());
        product.setMaterial(productRequest.getMaterial());
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setWeight(productRequest.getWeight());

        product.setType(type);
        product.setCategory(category);
        productService.addNewProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
    @GetMapping("/admin/product/topRepurchase/{year}/{month}")
    public ResponseEntity<List<ProductRespone>> getTop10RepurchaseProduct(@PathVariable int year, @PathVariable int month) {
        // Get list id of top 10 repurchase product
        List<Integer> list = productService.getTop10RepurchaseProduct(year, month);

        // Convert list of product ids to list of ProductResponses
        List<ProductRespone> list1 = list.stream()
                .map(productId -> productService.findById(productId))
                .filter(product -> product != null)
                .map(ProductRespone::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(list1, HttpStatus.OK);
    }
}
