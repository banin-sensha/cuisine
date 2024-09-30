package com.uow.sose.cuisine.Controller;

import com.uow.sose.cuisine.Entity.Customer;
import com.uow.sose.cuisine.Entity.Order;
import com.uow.sose.cuisine.Generic.ResponseUtil;
import com.uow.sose.cuisine.Service.CustomerService;
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

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<Object> addOrder(@RequestBody Order orderParam) {

        Customer existingCustomer = customerService.getCustomerById(orderParam.getCustomer().getCustomer_id());
        Order order = new Order();

        if (existingCustomer == null) {
            return ResponseUtil.generateErrorResponse("Customer does not exist. Please provide correct customer_id", HttpStatus.BAD_REQUEST);
        }
        else {
            order.setCustomer(existingCustomer);
            order.setStatus(orderParam.getStatus());
            order.setTotal_amount(orderParam.getTotal_amount());
            order.setPromo_code(orderParam.getPromo_code());
        }
        Order newOrder = orderService.addOrder(order);

        if (newOrder != null) {
            return ResponseUtil.generateSuccessResponseWithData(newOrder);
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
    public ResponseEntity<Object> deleteOrder(@PathVariable int id) {

        if (orderService.deleteOrder(id) == 0) {
            return ResponseUtil.generateErrorResponse("Order details to be deleted not found", HttpStatus.NOT_FOUND);
        }
        else {
            return ResponseUtil.generateSuccessResponseWithoutData("Successfully deleted Order details");
        }
    }
}
