package group6.ecommerce.controller;

import group6.ecommerce.model.*;
import group6.ecommerce.payload.request.ProductDetailRequest;
import group6.ecommerce.payload.request.ProductRequest;
import group6.ecommerce.payload.response.HttpResponse;
import group6.ecommerce.payload.response.ProductRespone;
import group6.ecommerce.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final TypeService typeService;
    private final CategoryService categoryService;
    private final ProductDetailsService productDetailsService;
    private final ColorService colorService;
    private final SizeService sizeService;
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

        Product productSaved = productService.addNewProduct(product);

        List<ProductDetails> productDetailsList = new ArrayList<>();
        if(!productRequest.getProductDetailRequestList().isEmpty()){
            for (ProductDetailRequest productDetailRequest : productRequest.getProductDetailRequestList()){
                ProductDetails productDetails = new ProductDetails();
                // Get size object in DB
                Size size = sizeService.findSizeByName(productDetailRequest.getSizeName());

                // Get color object in DB
                Color color = colorService.findColorByName(productDetailRequest.getColor());

                productDetails.setSize(size);
                productDetails.setColor(color);
                productDetails.setQuantity(productDetailRequest.getQuantity());
                productDetails.setOutOfStock(productDetailRequest.isOutOfStock());
                productDetails.setProducts(productSaved);

                ProductDetails productDetailsSaved = productDetailsService.addNewProductDetail(productDetails);
                productDetailsList.add(productDetailsSaved);
            }
        }

        productSaved.setListProductDetails(productDetailsList);
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}