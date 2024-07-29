/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Cart {
    private List<Item> listCart = new ArrayList<>();
   
    
    public Cart() {
        listCart = new ArrayList<>(); // Initialize the ArrayList
    }

    public List<Item> getListCart() {
        return listCart;
    }

    public void setListCart(List<Item> listCart) {
        this.listCart = listCart;
    }
    
    public int getCount() {
        return listCart.size();
    }

    public void addItem(Item item) {
        listCart.add(item);
    }

    public void removeItem(Item item) {
        listCart.remove(item);
    }
    
    public float getTotalAmount(){
        float result = 0;
        
        for(Item item : listCart){
            result += item.getTotal();
        }
        
        return result;
    }
    
    public Item getItemByProductId(int productId, int sizeId) {
    for (Item item : listCart) {
        if (item.getProduct().getId() == productId && item.getSize().getSize_id() == sizeId) {
            return item;
        }
    }
    return null; 
}

}
