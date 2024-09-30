package com.uow.sose.cuisine.Service;

import com.uow.sose.cuisine.Entity.MenuItem;
import com.uow.sose.cuisine.Repository.MenuItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepo menuItemRepo;

    public MenuItem addMenuItem(MenuItem menuItem) {
        return menuItemRepo.save(menuItem);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepo.findAll();
    }

    public Optional<MenuItem> getMenuItemById(int id) {
        return menuItemRepo.findById(id);
    }

    public MenuItem updateMenuItem(MenuItem menuItem) {
        return menuItemRepo.save(menuItem);
    }

    public void deleteMenuItem(int id) {
        menuItemRepo.deleteById(id);
    }
}
