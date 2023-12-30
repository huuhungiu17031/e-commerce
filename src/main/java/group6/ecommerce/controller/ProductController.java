package group6.ecommerce.controller;

import group6.ecommerce.model.*;
import group6.ecommerce.payload.request.ProductDetailRequest;
import group6.ecommerce.payload.request.ProductRequest;
import group6.ecommerce.payload.response.PageProductRespone;
import group6.ecommerce.payload.response.ProductRespone;
import group6.ecommerce.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductDetailsService productDetailsService;
    private final TypeService typeService;
    private final CategoryService categoryService;
    private final ColorService colorService;
    private final SizeService sizeService;

    @GetMapping ("/{id}")
    public ResponseEntity<PageProductRespone> findByPage (@PathVariable(value = "id")Optional<Integer> p){
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

    @PostMapping("/add")
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

        productSaved.setListProductDetails(productDetailsList);
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
