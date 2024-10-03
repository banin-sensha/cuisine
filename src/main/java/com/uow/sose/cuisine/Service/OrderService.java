package com.uow.sose.cuisine.Service;

import com.uow.sose.cuisine.Entity.Customer;
import com.uow.sose.cuisine.Entity.MenuItem;
import com.uow.sose.cuisine.Entity.Order;
import com.uow.sose.cuisine.Generic.ResponseUtil;
import com.uow.sose.cuisine.Repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CustomerService customerService;

    public Order addOrder(Order order) {
        return orderRepo.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Order getOrderById(int id) {
        return orderRepo.findById(id).orElse(null);
    }

    // Update a customer
    public Order updateOrder(Order order) {
        Order existingOrder = orderRepo.findById(order.getOrder_id())
                .orElse(null);

        if (existingOrder == null) {
            return null;
        }
        else {
            // Modify the entity
            Customer existingCustomer = customerService.getCustomerById(order.getCustomer().getCustomer_id());
            existingOrder.setCustomer(existingCustomer);
            existingOrder.setTotal_amount(order.getTotal_amount());
            existingOrder.setStatus(order.getStatus());
            existingOrder.setPromo_code(order.getPromo_code());

            // Save the updated entity
            return orderRepo.save(existingOrder);
        }
    }

    public int deleteOrder(int id) {
        Order existingOrder = orderRepo.findById(id)
                .orElse(null);

        if (existingOrder == null) {
            return 0;
        }
        else {
            orderRepo.deleteById(id);
            return 1;
        }

    }

    public List<HashMap<String, Object>> findMenuItemsByOrderId(int orderId) {
        return orderRepo.findMenuItemsByOrderId(orderId);
    }

}
