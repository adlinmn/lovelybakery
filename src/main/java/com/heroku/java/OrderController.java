package com.heroku.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.sql.DataSource;
import java.util.Date;

import javax.servlet.http.HttpSession;

@Controller
public class OrderController {
     private DataSource dataSource;

    @Autowired
    public OrderController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/custViewOrder")
public String showCustViewOrderPage(Model model, @RequestParam("order_id") String orderId, @RequestParam("menu_order") ArrayList<Menu> menuOrder, @RequestParam("total_price") float totalPrice, @RequestParam("order_date") long orderDateTimestamp) {

    // Convert timestamp to Date
    Date orderDate = new Date(orderDateTimestamp);

    model.addAttribute("order_id", orderId);
    model.addAttribute("menu_order", menuOrder);
    model.addAttribute("total_price", totalPrice);
    model.addAttribute("order_date", orderDate);

    try (Connection connection = dataSource.getConnection()) {
        String sql = "INSERT INTO orders (order_id, order_date, total_price, menu_order) VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        Order orders = new Order(sql, menuOrder, totalPrice, orderDate); // Assuming you have a default constructor for the Order class

        // Assuming getOrder_id(), getOrder_date(), getTotal_price(), and getMenuOrder() are methods in the Order class
        statement.setString(1, orders.getOrder_id());
        statement.setTimestamp(2, new java.sql.Timestamp(orderDate.getTime()));
        statement.setFloat(3, orders.getTotal_price());
        statement.setString(4, orders.getMenuOrder());

        statement.executeUpdate();

        connection.close();
        return "redirect:/custViewOrder";
    } catch (SQLException sqe) {
        sqe.printStackTrace();
        return "redirect:/";
    } catch (Exception e) {
        e.printStackTrace();
        return "redirect:/";
    }
}

@PostMapping("/createOrder")
public String createOrder(HttpSession session, RedirectAttributes redirectAttributes) {
    // Retrieve cart items from the session
    ArrayList<Menu> cartItems = (ArrayList<Menu>) session.getAttribute("cartItems");

    if (cartItems == null || cartItems.isEmpty()) {
        // Handle the case where the cart is empty (no items selected)
        // You might want to redirect the user to the cart view or show an error message
        return "redirect:/custViewCart";
    }

    // Calculate the total price of selected menu items
    float totalPrice = 0;
    for (Menu cartItem : cartItems) {
        totalPrice += cartItem.getPrice();
    }

    // Generate order ID and order date
    Date orderDate = generateOrderDate();
    String orderId = generateOrderId(orderDate);

    // Create an Order object
    Order order = new Order(orderId, cartItems, totalPrice, orderDate);

    // Save the order in the database
    saveOrder(order);

    // Store order information in the session
    session.setAttribute("order_id", orderId);
    session.setAttribute("menu_order", cartItems);
    session.setAttribute("total_price", totalPrice);
    session.setAttribute("order_date", orderDate.getTime()); // Passing timestamp for simplicity

    // Clear the cartItems from the session after successful payment
    session.removeAttribute("cartItems");

    // Redirect to custViewOrder
    return "redirect:/custViewOrder";
}

private String generateOrderId(Date orderDate) {
    return null;
}

private Date generateOrderDate() {
    return null;
}

private void saveOrder(Order order) {
    try (Connection connection = dataSource.getConnection()) {
        String sql = "INSERT INTO orders (order_id, order_date, total_price, menu_order) VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        // Assuming getOrder_id(), getOrder_date(), getTotal_price(), and getMenuOrder() are methods in the Order class
        statement.setString(1, order.getOrder_id());
        statement.setTimestamp(2, new java.sql.Timestamp(order.getOrder_date().getTime()));
        statement.setFloat(3, order.getTotal_price());
        statement.setString(4, order.getMenuOrder());

        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}





}
