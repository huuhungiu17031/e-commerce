package group6.ecommerce.payload.response;
import group6.ecommerce.model.Product;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductRespone {
    private int id;
    private String name;
    private int price;
    private int dimension;
    private int weight;
    private String material;
    private String category;
    private String imageUrls;
    private String type;
    private String description;

    private List<ProductDetailsRespone> productDetails;

    public ProductRespone(Product p) {
        this.id = p.getId();
        this.name =p.getName();
        this.price = p.getPrice();
        this.dimension = p.getDimension();
        this.weight = p.getWeight();
        this.category = p.getCategory().getCategoryName();
        this.imageUrls = p.getImageUrls();
        this.type = p.getType().getTypeName();
        this.description = p.getDescription();
        List<ProductDetailsRespone> listProductDetails = new ArrayList<>();
        p.getListProductDetails().stream().forEach(e -> listProductDetails.add(new ProductDetailsRespone(e)));
        this.productDetails = listProductDetails;
        this.material = p.getMaterial();
    }

    public boolean isOutOfStock (){
        return productDetails.isEmpty();
    }
}
