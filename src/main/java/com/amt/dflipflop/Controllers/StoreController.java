package com.amt.dflipflop.Controllers;
import com.amt.dflipflop.Entities.Category;
import com.amt.dflipflop.Entities.Product;
import com.amt.dflipflop.Entities.ProductSelection;
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

@Controller
public class StoreController {

    @GetMapping("/insert_items") // Uncomment if needed
    public String insertItems(Model model) {
        productService.insert(new Product("Produit 1", "Super produit 1", 3.5f, "shoes-img3.png"));
        productService.insert(new Product("Produit 2", "Super produit 2", 6.7f, "shoes-img9.png"));
        productService.insert(new Product("Produit 3", "Super produit 3", 33f, "shoes-img4.png"));
        productService.insert(new Product("Produit 4", "Super produit 4", 11.4f, "shoes-img9.png"));
        productService.insert(new Product("Produit 5", "Super produit 5", 42f, "shoes-img2.png"));
        productService.insert(new Product("Produit 6", "Super produit 6", 12f, "shoes-img9.png"));

        return "redirect:store";
    }


    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/store")
    public String getStorePage(@RequestParam(value = "cat", required = false) Integer catId, Model model) {

        ArrayList<Product> products;
        ArrayList<Category> categories = categoryService.getNonEmpty();

        if(catId != null){
            products = productService.getProductsByCategory(catId);
            model.addAttribute("selected", catId);
        }
        else {
            products = productService.getAll();
        }


        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "store";
    }

    @GetMapping("/store/product/{id}")
    public String getStoreProduct(@PathVariable("id") Integer productId, Model model) {

        Product product = productService.get(productId);

        if (product == null) {
            return "redirect:/store/product";
        }

        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/store/add-product")
    public String getAddProductPage(Model model) {
        ArrayList<Category> categories = categoryService.getAll();
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        return "add-product";
    }

    /**
     *
     * @param product The product get from the form (front-end)
     * @param multipartFile The stream for the picture
     * @param result State of the request
     * @return The redirection to a page
     * @throws IOException If write fail
     */
    @PostMapping(path="/store/add-product") // Map ONLY POST Requests
    public String addNewProduct (@ModelAttribute("product") Product product, @RequestParam("image") MultipartFile multipartFile, BindingResult result) throws IOException {
        String uploadDir = "src/main/resources/static/images";
        String fileName;

        // Process if an image has been selected
        if (!multipartFile.isEmpty()) {
            //Get name of the img uploaded
            fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            //Import name of image
            product.setImageName(fileName);

            //Upload and write img
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = Paths.get(uploadDir).resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }

        // Add the product via a product service
        productService.insert(product);

        if(result.hasErrors()){
            return "add-product";
        }

        return "redirect:/store";
    }

}
