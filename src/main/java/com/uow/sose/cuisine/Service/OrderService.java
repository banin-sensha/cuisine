package com.uow.sose.cuisine.Service;

import com.uow.sose.cuisine.Entity.Customer;
import com.uow.sose.cuisine.Entity.Order;
import com.uow.sose.cuisine.Repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public Order addOrder(Order order) {
        return orderRepo.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Order getOrderById(int id) {
        return orderRepo.findById(id).orElse(null);
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
}
