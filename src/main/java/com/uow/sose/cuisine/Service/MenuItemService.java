package com.uow.sose.cuisine.Service;

import com.uow.sose.cuisine.Entity.Customer;
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

    public MenuItem getMenuItemById(int id) {
        return menuItemRepo.findById(id).orElse(null);
    }

    public MenuItem updateMenuItem(MenuItem menuItem) {
        MenuItem existingMenuItem = menuItemRepo.findById(menuItem.getItem_id())
                .orElse(null);

        if (existingMenuItem == null) {
            return null;
        }
        else {
            // Modify the entity
            existingMenuItem.setName(menuItem.getName());
            existingMenuItem.setPrice(menuItem.getPrice());
            existingMenuItem.setAvailability(menuItem.isAvailability());

            // Save the updated entity
            return menuItemRepo.save(existingMenuItem);
        }
    }

    public int deleteMenuItem(int id) {
        MenuItem menuItem = menuItemRepo.findById(id).orElse(null);

        if (menuItem == null) {
            return 0;
        }
        else {
            menuItemRepo.deleteById(id);
            return 1;
        }
    }
}
