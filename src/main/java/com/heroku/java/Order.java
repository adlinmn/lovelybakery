package com.heroku.java;

import java.util.Date;

public class Order {
    private String order_id;
    private String order_status;
    private Date order_date;
    private Float total_price;
    private String menu_name;
    private String menu_id;

    public Order(String order_id, String order_status, Date order_date, Float total_price, String menu_name, String menu_id) {
        this.order_id = order_id;
        this.order_status = order_status;
        this.order_date = order_date;
        this.total_price = total_price;
        this.menu_name = menu_name;
        this.menu_id = menu_id;
    }

    public Order() {
        // Default constructor
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public Float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Float totalPrice) {
        this.total_price = totalPrice;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public CharSequence getMenuOrder() {
        return null;
    }

    public void addMenu_Order(String menuName) {
    }
}
