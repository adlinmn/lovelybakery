package com.heroku.java;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private int order_id;
    private Date order_date;
    private String order_list;

    public Order(int order_id, Date order_date, String order_list) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.order_list = order_list;
    }

    public Order(String orderId, java.util.Date orderDate, String orderList) {
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

    public String getOrder_list() {
        return order_list;
    }

    public void setOrder_list(String order_list) {
        this.order_list = order_list;
    }

}
