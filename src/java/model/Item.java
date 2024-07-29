/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class Item {
    private Product product;
    private Size size;
    private int quantity;

    public Item() {
    }

    public Item(Product product, int quantity, Size size) {
        this.product = product;
        this.quantity = quantity;
        this.size = size;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getTotal(){
        return product.getPrice() * quantity;
    }
    
    public Size getSize(){
        return this.size;
    }
    
    public void setSize(Size size){
        this.size = size;
    }

    @Override
    public String toString() {
        return "Item{" + "product=" + product + ", size=" + size + ", quantity=" + quantity + '}';
    }
    
    
    
    
}
