package com.geekbrains.july.warehouse.controllers.web;

import com.geekbrains.july.warehouse.entities.Category;
import com.geekbrains.july.warehouse.entities.Product;
import com.geekbrains.july.warehouse.services.CategoriesService;
import com.geekbrains.july.warehouse.services.ProductsService;
import com.geekbrains.july.warehouse.utils.ProductFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private ProductsService productsService;
    private CategoriesService categoriesService;

    @Autowired
    public ProductsController(ProductsService productsService, CategoriesService categoriesService) {
        this.productsService = productsService;
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public String showAll(Model model,
                          @RequestParam Map<String, String> requestParams,
                          @RequestParam(name = "categories", required = false) List<Long> categoriesIds) {
        Integer pageNumber = Integer.parseInt(requestParams.getOrDefault("p", "1"));

        List<Category> categoriesFilter = null;
        if (categoriesIds != null) {
            categoriesFilter = categoriesService.getCategoriesByIds(categoriesIds);
        }
        ProductFilter productFilter = new ProductFilter(requestParams, categoriesFilter);
        Page<Product> products = productsService.findAll(productFilter.getSpec(), pageNumber);
        model.addAttribute("products", products);
        model.addAttribute("filterDef", productFilter.getFilterDefinition().toString());
        return "/products/all_products";
    }

    @GetMapping("/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productsService.findById(id));
        return "/products/product_form";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "/products/add_product_form";
    }

    @PostMapping("/add")
    public String saveNewProduct(@RequestParam(name = "title") String title,
                                 @RequestParam(name = "description", required = false) String description,
                                 @RequestParam(name = "quantity") int quantity,
                                 @RequestParam(name = "categories", required = false) List<Long> categoriesIds) {
        Product product = new Product(0L, title, description, quantity, "шт.", null, null);
        if (categoriesIds != null) {
            productsService.setCategories(product, categoriesIds);
        }
        productsService.saveOrUpdate(product);
        return "redirect:/products/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productsService.findById(id));
        return "/products/edit_product_form";
    }

    @PostMapping("/edit")
    public String modifyProduct(@RequestParam(name = "id") Long id,
                                @RequestParam(name = "title") String title,
                                @RequestParam(name = "description", required = false) String description,
                                @RequestParam(name = "quantity") int quantity,
                                @RequestParam(name = "categories", required = false) List<Long> categoriesIds) {
        Product product = new Product(id, title, description, quantity, "шт.", null, null);
        if (categoriesIds != null) {
            productsService.setCategories(product, categoriesIds);
        }
        productsService.saveOrUpdate(product);
        return "redirect:/products/";
    }

    @GetMapping("/del/{id}")
    public String delProduct(@PathVariable Long id) {
        productsService.deleteById(id);
        return "redirect:/products/";
    }
}