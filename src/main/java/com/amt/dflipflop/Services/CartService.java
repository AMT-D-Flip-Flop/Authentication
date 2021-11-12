package com.amt.dflipflop.Services;

import com.amt.dflipflop.Entities.Cart;
import com.amt.dflipflop.Repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart get(Integer id) {
        Optional<Cart> cart = cartRepository.findById(id);
        return cart.orElse(null);
    }

    public Cart insert(Cart category){
        return cartRepository.save(category);
    }

    public void remove(Integer id){
        cartRepository.deleteById(id);
    }

    public Long count() {
        return categoryRepository.count();
    }
}