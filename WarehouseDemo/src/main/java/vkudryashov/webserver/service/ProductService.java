package vkudryashov.webserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vkudryashov.webserver.dao.ProductDao;
import vkudryashov.webserver.exceptions.ProductNotFoundException;
import vkudryashov.webserver.model.Product;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public Product saveOrUpdate(Product product) {
        return productDao.save(product);
    }

    public Product findById(Long id) {
        return productDao.findById(id).orElseThrow(() -> new ProductNotFoundException("Can't found product with id = " + id));
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public Page<Product> findAll(Specification<Product> spec, Integer page) {
        if (page < 1L) {
            page = 1;
        }
        return productDao.findAll(spec, PageRequest.of(page - 1, 10));
    }

    public void deleteAll() {
        productDao.deleteAll();
    }

    public void deleteById(Long id) {
        productDao.deleteById(id);
    }

    public boolean existsById(Long id) {
        return productDao.existsById(id);
    }

//    public List<ProductDto> getDtoData() {
//        return productDao.findAllBy();
//    }
}
