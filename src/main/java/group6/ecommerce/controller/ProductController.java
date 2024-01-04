package group6.ecommerce.controller;

import group6.ecommerce.model.Category;
import group6.ecommerce.model.Product;
import group6.ecommerce.model.Type;
import group6.ecommerce.payload.request.ProductRequest;
import group6.ecommerce.payload.response.HttpResponse;
import group6.ecommerce.payload.response.ProductRespone;
import group6.ecommerce.service.CategoryService;
import group6.ecommerce.service.ProductService;
import group6.ecommerce.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final TypeService typeService;
    private final CategoryService categoryService;
    @GetMapping
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

    @GetMapping (value = "search")
    public ResponseEntity<HttpResponse> getProductByName(
            @RequestParam(required = false, defaultValue = "12", value = "pageSize") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0", value = "pageNum") Integer pageNum,
            @RequestParam(required = false, defaultValue = "id", value = "fields") String fields,
            @RequestParam(required = false, defaultValue = "asc", value = "orderBy") String orderBy,
            @RequestParam(required = false, defaultValue = "false", value = "getAll") Boolean getAll,
            @RequestParam(required = false, value = "value") String search) {
        HttpResponse httpResponse = new HttpResponse(
                HttpStatus.OK.value(),
                null,
                productService.listProductByName(pageSize, pageNum, fields, orderBy, getAll, search));
        return ResponseEntity.status(HttpStatus.OK).body(httpResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductRespone> DetailsProduct (@PathVariable("id") Optional<Integer> id){
        Product Product = productService.findById(id.get());
        ProductRespone productRespone = new ProductRespone(Product);
        return ResponseEntity.status(HttpStatus.OK).body(productRespone);
    }

    @PostMapping
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

    @GetMapping("topRepurchase/{year}/{month}")
    public ResonseEntity<List<ProductRespone>> getTopRepurchaseProduct(@PathVariable int year, @PathVariable int month) {
        // Get list id of top 10 repurchase product
        List<Integer> list = productService.getTopRepurchaseProduct(year, month);

        // Convert list of product ids to list of ProductResponses
        List<ProductRespone> list1 = list.stream()
                .map(productId -> productService.findById(productId))
                .filter(product -> product != null)
                .map(ProductRespone::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(list1, HttpStatus.OK);
    }
}


