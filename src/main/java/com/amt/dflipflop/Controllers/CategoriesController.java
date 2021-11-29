package com.amt.dflipflop.Controllers;

import com.amt.dflipflop.Entities.Category;
import com.amt.dflipflop.Entities.Product;
import com.amt.dflipflop.Services.CategoryService;
import com.amt.dflipflop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.nio.file.*;
import java.util.HashSet;
import java.util.List;

@Controller
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/categories")
    public String displayCategoriesAndForm(Model model) {
        ArrayList<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("category", new Category());
        return "categories";
    }

    /**
     * @param category The category get from the form (front-end)
     * @return The redirection to a page
     * @throws IOException If write fail
     */
    @PostMapping(path="/categories/add-category") // Map ONLY POST Requests
    public String addNewCategory (@ModelAttribute Category category) throws IOException {


        if(categoryService.categoryExists(category.getName())){
            return "redirect:/categories";
        }

        // Add the category via a category service
        categoryService.insert(category);
        return "redirect:/categories";
    }

    /**
     * Called with /categories/remove?id=N
     * @return The redirection to a page
     * @throws IOException If suppress fail
     */
    @GetMapping(path="/categories/remove")
    public String removeCategory (@RequestParam(value = "id") Integer id) throws IOException {

        // Check if any item uses this category<

        // Add the category via a category service
        categoryService.remove(id);

        return "redirect:/categories";
    }

    /**
     * Assign the categories to the product
     * @return The redirection to a page
     * @throws IOException If suppress fail
     */
    @PostMapping(path="/categories/set_category")
    public String setCategory (
            @RequestParam(value = "product") Integer productId,
            @RequestParam(value = "cat") List<Integer> categoriesId) throws IOException {

        Product product = productService.get(productId);

        if (product == null)
        {
            return "redirect:/store";
        }

        HashSet<Category> categories = new HashSet<Category>();
        for(int id: categoriesId){
            Category category = categoryService.get(id);
            if(category != null){
                categories.add(category);
            }
        }

        product.setCategories(categories);
        productService.update(product);

        return "redirect:/store/product/" + productId;
    }

}

