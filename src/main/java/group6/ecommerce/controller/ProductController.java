package group6.ecommerce.controller;

import group6.ecommerce.model.Category;
import group6.ecommerce.model.Product;
import group6.ecommerce.model.Type;
import group6.ecommerce.payload.request.ProductRequest;
import group6.ecommerce.payload.response.HttpResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final TypeService typeService;
    private final CategoryService categoryService;

    @GetMapping("/user/product/{page}")
    public ResponseEntity<PageProductRespone> findByPage(@PathVariable(value = "page") Optional<Integer> p,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "category", defaultValue = "") String cateogory) {
        if (cateogory.equalsIgnoreCase("") || categoryService.findCategoryByName(cateogory) == null) {
            if (sort.equalsIgnoreCase("nameaz")) {
                Pageable page = PageRequest.of(p.orElse(0), 12, Sort.by("product_name").ascending());
                Page<Product> pageRespone = productService.findAllQuantityLarger0(page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            } else if (sort.equalsIgnoreCase("nameza")) {
                Pageable page = PageRequest.of(p.orElse(0), 12, Sort.by("product_name").descending());
                Page<Product> pageRespone = productService.findAllQuantityLarger0(page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            } else if (sort.equalsIgnoreCase("pricelowtohight")) {
                Pageable page = PageRequest.of(p.orElse(0), 12, Sort.by("product_price").ascending());
                Page<Product> pageRespone = productService.findAllQuantityLarger0(page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            } else if (sort.equalsIgnoreCase("pricehighttolow")) {
                Pageable page = PageRequest.of(p.orElse(0), 12, Sort.by("product_price").descending());
                Page<Product> pageRespone = productService.findAllQuantityLarger0(page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            } else {
                Pageable page = PageRequest.of(p.orElse(0), 12);
                Page<Product> pageRespone = productService.findAllQuantityLarger0(page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            }
        } else {
            if (sort.equalsIgnoreCase("nameaz")) {
                Pageable page = PageRequest.of(p.orElse(0), 12, Sort.by("product_name").ascending());
                Page<Product> pageRespone = productService.findByCategoryNameQuantityLarger0(cateogory, page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            } else if (sort.equalsIgnoreCase("nameza")) {
                Pageable page = PageRequest.of(p.orElse(0), 12, Sort.by("product_name").descending());
                Page<Product> pageRespone = productService.findByCategoryNameQuantityLarger0(cateogory, page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            } else if (sort.equalsIgnoreCase("pricelowtohight")) {
                Pageable page = PageRequest.of(p.orElse(0), 12, Sort.by("product_price").ascending());
                Page<Product> pageRespone = productService.findByCategoryNameQuantityLarger0(cateogory, page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            } else if (sort.equalsIgnoreCase("pricehighttolow")) {
                Pageable page = PageRequest.of(p.orElse(0), 12, Sort.by("product_price").descending());
                Page<Product> pageRespone = productService.findByCategoryNameQuantityLarger0(cateogory, page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            } else {
                Pageable page = PageRequest.of(p.orElse(0), 12);
                Page<Product> pageRespone = productService.findByCategoryNameQuantityLarger0(cateogory, page);
                return ResponseEntity.status(HttpStatus.OK).body(new PageProductRespone(pageRespone));
            }
        }
    }

    @GetMapping("product")
    public ResponseEntity<HttpResponse> getProduct(
            @RequestParam(required = false, defaultValue = "12", value = "pageSize") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0", value = "pageNum") Integer pageNum,
            @RequestParam(required = false, defaultValue = "id", value = "fields") String fields,
            @RequestParam(required = false, defaultValue = "asc", value = "orderBy") String orderBy,
            @RequestParam(required = false, defaultValue = "false", value = "getAll") Boolean getAll,
            @RequestParam(required = false, value = "categoryId") Integer categoryId) {
        HttpResponse httpResponse = new HttpResponse(
                HttpStatus.OK.value(),
                null,
                productService.listProduct(pageSize, pageNum, fields, orderBy, getAll, categoryId));
        return ResponseEntity.status(HttpStatus.OK).body(httpResponse);
    }

    @GetMapping("/user/product/details/{id}")
    public ResponseEntity<ProductRespone> DetailsProduct(@PathVariable("id") Optional<Integer> id) {
        Product Product = productService.findById(id.get());
        ProductRespone productRespone = new ProductRespone(Product);
        return ResponseEntity.status(HttpStatus.OK).body(productRespone);
    }

    @PostMapping("/admin/product/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest) {
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
}
