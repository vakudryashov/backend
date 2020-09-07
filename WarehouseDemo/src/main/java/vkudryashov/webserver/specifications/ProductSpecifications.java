package vkudryashov.webserver.specifications;

import org.springframework.data.jpa.domain.Specification;
import vkudryashov.webserver.model.Category;
import vkudryashov.webserver.model.Product;

public class ProductSpecifications {
    public static Specification<Product> titleLike(String title) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title));
    }

    public static Specification<Product> categoryIs(Category category) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> {
//            Join join = root.join("categories");
//            return criteriaBuilder.equal(join.get("id"), category.getId());
             return criteriaBuilder.isMember(category, root.get("categories"));
        };
    }
}
