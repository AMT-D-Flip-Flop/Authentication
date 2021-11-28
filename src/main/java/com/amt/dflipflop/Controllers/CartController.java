package com.amt.dflipflop.Controllers;

import com.amt.dflipflop.Entities.Cart;
import com.amt.dflipflop.Entities.ItemCart;
import com.amt.dflipflop.Entities.Product;
import com.amt.dflipflop.Services.CartService;
import com.amt.dflipflop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    private boolean init = true;
    private List<ItemCart> itemCarts = new ArrayList<>();


    @GetMapping("/cart")
    public String getCart(Model model) {
        if(!init){
            itemCarts.clear();
            init = true;
            Cart cart = new Cart();
            Product product1 = new Product("Produit 1", "Super produit 1", 3.5f, "shoes-img3.png");
            Product product2 = new Product("Produit 2", "Super produit 2", 6.5f, "shoes-img9.png");
            Product product3 = new Product("Produit 3", "Super produit 3", 9.5f, "shoes-img4.png");
            ItemCart item1 = new ItemCart(10, product1);
            ItemCart item2 = new ItemCart(8, product2);
            ItemCart item3 = new ItemCart(12, product3);
            cart.addProduct(item1);
            cart.addProduct(item2);
            cart.addProduct(item3);
            itemCarts = cart.getItemsCart();
        }
        double total = 0;
        for(ItemCart ic : itemCarts)
            total += (ic.getQty() * ic.getProduct().getPrice());
        model.addAttribute("itemCarts", itemCarts);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/cart/change/{id}")
    public String addQty(@ModelAttribute ("itemCart") ItemCart itemCart, @PathVariable("id") int index) {
        if(itemCart.getQty() > 0){
            itemCarts.get(index).setQty(itemCart.getQty());
        } else {
            itemCarts.remove(index);
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/empty")
    public String empty() {
        itemCarts.clear();
        return "redirect:/cart";
    }

    @PostMapping("/cart/init")
    public String init() {
        init = false;
        return "redirect:/cart";
    }

    @PostMapping("/cart/delete/{id}")
    public String deleteItem(@PathVariable("id") int index) {
        itemCarts.remove(index);
        return "redirect:/cart";
    }
}
