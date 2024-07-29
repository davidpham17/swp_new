/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

public class Order {
    private int orderID;
    private Date orderDate;
    private double total;
    
    public Order() {
        this.orderID = -1;
        this.orderDate = new Date();
        this.total = -1;
    }

    // Constructor
    public Order(int orderID, Date orderDate, double total) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.total = total;
    }

    // Getter and setter methods
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return ""   + " orderDate:" + orderDate + ", total:" + total+"$" ;
    }
    
    
}