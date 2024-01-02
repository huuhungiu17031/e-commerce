package search_by_name;

import group6.ecommerce.model.Product;
import group6.ecommerce.payload.response.HttpResponse;
import group6.ecommerce.payload.response.ProductRespone;
import group6.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


public class search {

    @RestController
    @RequestMapping("product")
    public class ProductController {

        @Autowired
        private ProductService productService;

        @GetMapping("search")
        public ResponseEntity<HttpResponse> searchProductByName(@RequestParam String name) {
            List<Product> products = productService.findProductsByName(name);

            HttpResponse httpResponse = new HttpResponse(
                    HttpStatus.OK.value(),
                    null,
                    products);

            return ResponseEntity.status(HttpStatus.OK).body(httpResponse);
        }
    }

}
