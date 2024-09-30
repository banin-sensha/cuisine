package com.uow.sose.cuisine.Controller;

import com.uow.sose.cuisine.Entity.MenuItem;
import com.uow.sose.cuisine.Service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-item")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/add")
    public MenuItem add(@RequestBody MenuItem menuItem) {
        return menuItemService.addMenuItem(menuItem);
    }

    @PostMapping("/update")
    public MenuItem update(@RequestBody MenuItem menuItem) {
        return menuItemService.updateMenuItem(menuItem);
    }

    @GetMapping("/all")
    public List<MenuItem> getAll() {
        return menuItemService.getAllMenuItems();
    }

    @GetMapping("/{id}")
    public MenuItem getById(@PathVariable int id) {
        return menuItemService.getMenuItemById(id).orElse(null);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable int id) {
        menuItemService.deleteMenuItem(id);
    }
}
