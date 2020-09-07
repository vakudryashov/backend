package vkudryashov.webserver.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vkudryashov.webserver.model.Category;
import vkudryashov.webserver.service.CategoryService;
import vkudryashov.webserver.service.ProductService;

import java.util.List;
/*

@Controller
@RequestMapping("/categories")
public class CategoriesController {
    private ProductService productsService;
    private CategoryService categoryService;

    @Autowired
    public CategoriesController(ProductService productsService, CategoryService categoryService) {
        this.productsService = productsService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showAll(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "all_categories";
    }

    @GetMapping("/{id}")
    public String showCategory(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "category_form";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "add_category_form";
    }

    @PostMapping("/add")
    public String saveNewProduct(@ModelAttribute Category category) {
        categoryService.saveOrUpdate(category);
        return "redirect:/categories/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "edit_category_form";
    }

    @PostMapping("/edit")
    public String modifyCategory(@ModelAttribute Category category) {
        categoryService.saveOrUpdate(category);
        return "redirect:/categories/";
    }

    @GetMapping("/del/{id}")
    public String delCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/categories/";
    }
}
*/
