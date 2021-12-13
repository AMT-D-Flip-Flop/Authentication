package com.amt.dflipflop.Services;

import com.amt.dflipflop.Entities.Product;
import com.amt.dflipflop.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

        @Autowired
        private ProductRepository productRepository;

        public ArrayList<Product> getAll() {

            Iterable<Product> it = productRepository.findAll();

            ArrayList<Product> products = new ArrayList<Product>();
            it.forEach(products::add);

            return products;
        }

        public Product get(Integer id) {
            Optional<Product> product = productRepository.findById(id);
            return product.orElse(null);
        }

        public Product insert(Product product){
            return productRepository.save(product);
        }

        public Long count() {
            return productRepository.count();
        }
  
        public Product update(Product product){
            return productRepository.save(product);
        }

    /**
     * Returns a list of products related to the given category
     * @param cat id of category
     * @return the list of products filtered with the category
     * @implNote It is not optimal, but I couldn't find a better way to implement it with hibernate
     */
    public ArrayList<Product> getProductsByCategory(Integer cat){
            ArrayList<Product> products = getAll();
            ArrayList<Product> filtered = new ArrayList<>();

            for(Product product : products){
                ArrayList<Integer> categories = product.getCategoriesId();

                if(!categories.isEmpty() && categories.contains(cat)){
                    filtered.add(product);
                }
            }

            return filtered;
        }
}