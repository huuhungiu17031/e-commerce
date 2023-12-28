package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "Review")
@Data
@Getter
@Setter
public class Review {
    @Id
    @Column (name = "ReviewId")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int Id;
    @ManyToOne
    @JoinColumn (name = "UserId")
    private Users userReview;
    @ManyToOne
    @JoinColumn (name = "ProductId")
    private Product productReview;
    @Column (name = "Review")
    private String review;
    @Column (name = "Rating")
    private int rating;
}
