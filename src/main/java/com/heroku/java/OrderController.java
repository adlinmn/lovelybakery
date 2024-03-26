package com.heroku.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;

@Controller
public class OrderController {
     private DataSource dataSource;
     private JdbcTemplate jdbcTemplate;


    @Autowired
    public OrderController(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostMapping("/custViewOrder")
public String showCustViewOrderPage(Model model, @RequestParam("order_list") String orderList) {
    Date orderDate = new Date();

    try (Connection connection = dataSource.getConnection()) {
        String sql = "INSERT INTO orders (order_date, order_list) VALUES (?, ?) RETURNING order_id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, new java.sql.Timestamp(orderDate.getTime()));
            statement.setString(2, orderList);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String orderId = resultSet.getString("order_id");

                    model.addAttribute("order_id", orderId);
                    model.addAttribute("order_date", orderDate);
                    model.addAttribute("order_list", orderList);

                    return "custViewOrder";
                } else {
                    return "redirect:/";
                }
            }
        }
    } catch (SQLException sqe) {
        sqe.printStackTrace();
        return "redirect:/";
    } catch (Exception e) {
        e.printStackTrace();
        return "redirect:/";
    }
}



    @GetMapping("/AdminViewOrder")
    public String showAdminViewOrder(Model model) {
        List<Order> orders = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
        String sql = "SELECT * FROM orders";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        System.out.println("connected");

        while (rs.next()) {
            int order_id = rs.getInt("order_id");
            Date order_date = rs.getDate("order_date");
            String order_list = rs.getString("order_list");
            Order order = new Order(order_id, order_date, order_list);
            orders.add(order);
        }

        model.addAttribute("orders", orders);  // Add orders to the model
        return "AdminViewOrder";
    } catch (SQLException sqe) {
        sqe.printStackTrace();
        return "error";
    } catch (Exception e) {
        e.printStackTrace();
        return "error";
    }
}

    @PostMapping("/deleteOrder")
    public String deleteOrder(@RequestParam("orderId") int orderId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM orders WHERE order_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, orderId);
            statement.executeUpdate();
            connection.close();
            return "redirect:/AdminViewOrder";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


}


