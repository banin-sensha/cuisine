package com.uow.sose.cuisine.Controller;

import com.uow.sose.cuisine.Entity.Customer;
import com.uow.sose.cuisine.Entity.Order;
import com.uow.sose.cuisine.Generic.ResponseUtil;
import com.uow.sose.cuisine.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<Object> addOrder(@RequestBody Order orderParam) {

        Order order = orderService.addOrder(orderParam);

        if (order != null) {
            return ResponseUtil.generateSuccessResponseWithData(order);
        }
        else {
            return ResponseUtil.generateErrorResponse("Error while adding new order", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllOrders() {
        List<Order> allOrders = orderService.getAllOrders();

        if (allOrders.isEmpty()) {
            return ResponseUtil.generateErrorResponse("Order is not placed yet", HttpStatus.NOT_FOUND);
        }
        else {
            return ResponseUtil.generateSuccessResponseWithData(allOrders);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable int id) {

        Order order = orderService.getOrderById(id);

        if (order != null) {
            return ResponseUtil.generateSuccessResponseWithData(order);
        }
        else {
            return ResponseUtil.generateErrorResponse("Error while fetching order by Id", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }
}
