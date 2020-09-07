package vkudryashov.webserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vkudryashov.webserver.model.Product;

public interface ProductDao extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}
