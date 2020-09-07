package vkudryashov.webserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vkudryashov.webserver.dao.CategoryDao;
import vkudryashov.webserver.exceptions.ProductNotFoundException;
import vkudryashov.webserver.model.Category;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    public List<Category> getCategoriesByIds(List<Long> ids) {
        return categoryDao.findAllById(ids);
    }

    public Category saveOrUpdate(Category category) {
        return categoryDao.save(category);
    }

    public Category findById(Long id) {
        return categoryDao.findById(id).orElseThrow(() -> new ProductNotFoundException("Can't found category with id = " + id));
    }

    public void deleteById(Long id) {
        categoryDao.deleteById(id);
    }
}
