package com.heroku.java;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private int order_id;
    private Date order_date;
    private Float total_price;
    private String menu_order;

    public Order(int order_id, Date order_date, Float total_price, String menu_order) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.total_price = total_price;
        this.menu_order = menu_order;
    }

    public Order(String orderId, ArrayList<Menu> cartItems, float totalPrice, java.util.Date orderDate) {
        // Default constructor
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }


    public java.util.Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Float totalPrice) {
        this.total_price = totalPrice;
    }

    public String getMenu_order() {
        return menu_order;
    }

    public void setMenu_order(String menu_order) {
        this.menu_order = menu_order;
    }

    public String getMenu_Order() {
        return null;
    }

    public void addMenu_Order(String menu_Order) {
    }

    public String getMenuOrder() {
        return null;
    }
}
