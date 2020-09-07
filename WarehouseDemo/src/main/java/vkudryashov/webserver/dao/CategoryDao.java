package vkudryashov.webserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vkudryashov.webserver.model.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {
}
