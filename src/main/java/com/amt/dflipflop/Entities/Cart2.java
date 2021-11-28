package com.amt.dflipflop.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart2 {

    // needed for SpringBoot Hibernate to create objects
    public Cart2() {}

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    @OneToMany
    private List<ItemCart> cartList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public ItemCart getProductById(Integer id){
        return cartList.get(id);
    }
    public void addProduct(ItemCart product){
        cartList.add(product);
    }
    public Integer nbProduct(){
        return cartList.size();
    }
    public List<ItemCart> getItemsCart(){
        List<ItemCart> itemCart = new ArrayList<>();
        for(int i = 0; i < cartList.size(); ++i){
            itemCart.add(this.getProductById(i));
        }
        return itemCart;
    }

}