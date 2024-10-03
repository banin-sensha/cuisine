package com.uow.sose.cuisine.Controller;

import com.uow.sose.cuisine.Entity.Customer;
import com.uow.sose.cuisine.Entity.MenuItem;
import com.uow.sose.cuisine.Entity.Order;
import com.uow.sose.cuisine.Entity.OrderedItem;
import com.uow.sose.cuisine.Generic.ResponseUtil;
import com.uow.sose.cuisine.Service.CustomerService;
import com.uow.sose.cuisine.Service.OrderService;
import com.uow.sose.cuisine.Service.OrderedItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderedItemService orderedItemService;

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

            List<HashMap<String, Object>> itemIdList = orderParam.getMenuItems();
            for (HashMap<String, Object> map: itemIdList) {
                OrderedItem orderedItem = new OrderedItem();
                orderedItem.setOrder_id(newOrder.getOrder_id());
                orderedItem.setItem_id(Integer.parseInt(map.get("id").toString()));
                orderedItemService.add(orderedItem);
            }
            List<HashMap<String, Object>> menuItems = orderService.findMenuItemsByOrderId(newOrder.getOrder_id());
            if (!menuItems.isEmpty()) {
                order.setMenuItems(menuItems);
            }
            else {
                order.setMenuItems(Collections.emptyList());
            }
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
            for(Order order: allOrders) {
                List<HashMap<String, Object>> menuItems = orderService.findMenuItemsByOrderId(order.getOrder_id());
                if (!menuItems.isEmpty()) {
                    order.setMenuItems(menuItems);
                }
                else {
                    order.setMenuItems(Collections.emptyList());
                }
            }
            return ResponseUtil.generateSuccessResponseWithData(allOrders);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable int id) {

        Order order = orderService.getOrderById(id);

        if (order != null) {
            List<HashMap<String, Object>> menuItems = orderService.findMenuItemsByOrderId(id);
            if (!menuItems.isEmpty()) {
                order.setMenuItems(menuItems);
            }
            else {
                order.setMenuItems(Collections.emptyList());
            }
            return ResponseUtil.generateSuccessResponseWithData(order);
        }
        else {
            return ResponseUtil.generateErrorResponse("Error while fetching specific order details", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateCustomer(@RequestBody Order orderParam) {
        Order order = orderService.updateOrder(orderParam);

        if (order != null) {
            return ResponseUtil.generateSuccessResponseWithData(order);
        }
        else {
            return ResponseUtil.generateErrorResponse("Order details to be updated not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable int id) {

        if (orderService.deleteOrder(id) == 0) {
            return ResponseUtil.generateErrorResponse("Order details to be deleted not found", HttpStatus.NOT_FOUND);
        }
        else {
            return ResponseUtil.generateSuccessResponseWithoutData("Successfully deleted Order details");
        }
    }

    @GetMapping("/menu/{id}")
    public ResponseEntity<Object> getMenu(@PathVariable int id) {
        List<HashMap<String, Object>> menuItemsByOrderId = orderService.findMenuItemsByOrderId(id);
        return ResponseUtil.generateSuccessResponseWithData(menuItemsByOrderId);

    }
}
