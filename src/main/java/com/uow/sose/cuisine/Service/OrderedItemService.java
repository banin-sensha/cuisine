package com.uow.sose.cuisine.Service;

import com.uow.sose.cuisine.Entity.OrderedItem;
import com.uow.sose.cuisine.Repository.OrderedItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderedItemService {

    @Autowired
    private OrderedItemRepo orderedItemRepo;

    public OrderedItem add(OrderedItem orderedItem) {
        return orderedItemRepo.save(orderedItem);
    }

    public List<OrderedItem> getAll() {
        return orderedItemRepo.findAll();
    }

    public Optional<OrderedItem> getById(int id) {
        return orderedItemRepo.findById(id);
    }

    public void deleteById(int id) {
        orderedItemRepo.deleteById(id);
    }
}
