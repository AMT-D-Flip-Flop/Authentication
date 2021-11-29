package com.amt.dflipflop.Services;

import com.amt.dflipflop.Entities.Category;
import com.amt.dflipflop.Entities.Product;
import com.amt.dflipflop.Repositories.CategoryRepository;
import com.amt.dflipflop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    public ArrayList<Category> getAll() {

        Iterable<Category> it = categoryRepository.findAll();

        ArrayList<Category> categories = new ArrayList<Category>();
        it.forEach(categories::add);

        return categories;
    }

    public ArrayList<Category> getNonEmpty() {
        ArrayList<Category> categories = new ArrayList<>();
        ArrayList<Product> products = productService.getAll();
        for(Product product : products){
            Category cat = product.getCategory();
            if(cat  != null && !categories.contains(cat)){
                categories.add(cat);
            }
        }

        return categories;
    }

    public Category get(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }

    public Category insert(Category category){
        return categoryRepository.save(category);
    }

    public void remove(Integer id){
        categoryRepository.deleteById(id);
    }

    public boolean categoryExists(String name) {
        ArrayList<Category> categories = getAll();
        for( Category cat : categories){
            if (cat.getName().equals(name))
                return true;
        }
        return false;
    }

    public Long count() {
        return categoryRepository.count();
    }
}