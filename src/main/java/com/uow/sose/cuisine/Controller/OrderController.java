package com.uow.sose.cuisine.Controller;

import com.uow.sose.cuisine.Entity.Order;
import com.uow.sose.cuisine.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public Order addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }
}
