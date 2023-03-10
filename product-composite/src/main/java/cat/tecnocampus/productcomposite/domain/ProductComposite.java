package cat.tecnocampus.productcomposite.domain;

import java.util.List;

public class ProductComposite {
    private Product product;
    private List<Review> reviews;
    private List<Recommendation> recommendations;

    public ProductComposite(Product product, List<Review> reviews, List<Recommendation> recommendations) {
        this.product = product;
        this.reviews = reviews;
        this.recommendations = recommendations;
    }
    public ProductComposite() {
        this.product = null;
        this.reviews = null;
        this.recommendations = null;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
