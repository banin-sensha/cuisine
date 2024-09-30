package com.uow.sose.cuisine.Controller;

import com.uow.sose.cuisine.Entity.OrderedItem;
import com.uow.sose.cuisine.Service.OrderedItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordered-item")
public class OrderedItemController {

    @Autowired
    private OrderedItemService orderedItemService;

    @PostMapping("/add")
    public OrderedItem add(@RequestBody OrderedItem orderedItem) {
        return orderedItemService.add(orderedItem);
    }

    @GetMapping("/all")
    public List<OrderedItem> getAll() {
        return orderedItemService.getAll();
    }

    @GetMapping("/{id}")
    public OrderedItem getById(@PathVariable int id) {
        return orderedItemService.getById(id).orElse(null);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable int id) {
        orderedItemService.deleteById(id);
    }
}
