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

    @PostMapping("/place")
    public ResponseEntity<Object> placeOrder(@RequestBody Order orderParam) {

        Customer existingCustomer = customerService.getCustomerById(orderParam.getCustomer().getCustomer_id());
        Order order = new Order();

        if (existingCustomer == null) {
            return ResponseUtil.generateErrorResponse("Customer does not exist. Please provide correct customer_id", HttpStatus.BAD_REQUEST);
        }
        else {
            order.setCustomer(existingCustomer);
            order.setStatus(orderParam.getStatus());
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
                double totalAmount = 0.0;
                for (HashMap<String, Object> item: menuItems) {
                    totalAmount = totalAmount + Double.parseDouble(item.get("price").toString());
                }
                order.setTotal_amount(Double.parseDouble(String.format("%.2f", totalAmount)));
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
            return ResponseUtil.generateErrorResponse("Error while fetching all orders", HttpStatus.BAD_REQUEST);
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

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Object> getOrderByCustomerId(@PathVariable int customerId) {

        List<Order> orderList = orderService.getOrdersByCustomerId(customerId);

        if (!orderList.isEmpty()) {
            for (Order order: orderList) {
                List<HashMap<String, Object>> menuItems = orderService.findMenuItemsByOrderId(order.getOrder_id());
                if (!menuItems.isEmpty()) {
                    order.setMenuItems(menuItems);
                    double totalAmount = 0.0;
                    for (HashMap<String, Object> item: menuItems) {
                        totalAmount = totalAmount + Double.parseDouble(item.get("price").toString());
                    }
                    order.setTotal_amount(Double.parseDouble(String.format("%.2f", totalAmount)));
                }
                else {
                    order.setMenuItems(Collections.emptyList());
                }
            }

            return ResponseUtil.generateSuccessResponseWithData(orderList);
        }
        else {
            return ResponseUtil.generateErrorResponse("Error while fetching specific order details", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateOrder(@RequestBody Order orderParam) {
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
}
