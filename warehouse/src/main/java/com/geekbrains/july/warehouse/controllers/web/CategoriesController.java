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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/categories")
public class CategoriesController {
    private ProductsService productsService;
    private CategoriesService categoriesService;

    @Autowired
    public CategoriesController(ProductsService productsService, CategoriesService categoriesService) {
        this.productsService = productsService;
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public String showAll(Model model) {
        List<Category> categories = categoriesService.getAllCategories();
        model.addAttribute("categories", categories);
        return "/categories/all_categories";
    }

    @GetMapping("/{id}")
    public String showCategory(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoriesService.findById(id));
        return "/categories/category_form";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "/categories/add_category_form";
    }

    @PostMapping("/add")
    public String saveNewProduct(@ModelAttribute Category category) {
        categoriesService.saveOrUpdate(category);
        return "redirect:/categories/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoriesService.findById(id));
        return "/categories/edit_category_form";
    }

    @PostMapping("/edit")
    public String modifyCategory(@ModelAttribute Category category) {
        categoriesService.saveOrUpdate(category);
        return "redirect:/categories/";
    }

    @GetMapping("/del/{id}")
    public String delCategory(@PathVariable Long id) {
        categoriesService.deleteById(id);
        return "redirect:/categories/";
    }
}
