package com.uow.sose.cuisine.Controller;

import com.uow.sose.cuisine.Entity.Customer;
import com.uow.sose.cuisine.Entity.MenuItem;
import com.uow.sose.cuisine.Generic.ResponseUtil;
import com.uow.sose.cuisine.Service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/menu-item")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody MenuItem menuItemParam) {
        MenuItem menuItem = menuItemService.addMenuItem(menuItemParam);

        if (menuItem != null) {
            return ResponseUtil.generateSuccessResponseWithData(menuItem);
        }
        else {
            return ResponseUtil.generateErrorResponse("Error while adding new menu item", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody MenuItem menuItemParam) {
        MenuItem menuItem = menuItemService.updateMenuItem(menuItemParam);

        if (menuItem != null) {
            return ResponseUtil.generateSuccessResponseWithData(menuItem);
        }
        else {
            return ResponseUtil.generateErrorResponse("Error while updating menu item", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        List<MenuItem> menuItemList = menuItemService.getAllMenuItems();

        if (menuItemList.isEmpty()) {
            return ResponseUtil.generateErrorResponse("Error while fetching all menu item details", HttpStatus.BAD_REQUEST);
        }
        else {
            return ResponseUtil.generateSuccessResponseWithData(menuItemList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        MenuItem menuItem = menuItemService.getMenuItemById(id);

        if (menuItem == null) {
            return ResponseUtil.generateErrorResponse("Error while fetching menu item details", HttpStatus.BAD_REQUEST);
        }
        else {
            return ResponseUtil.generateSuccessResponseWithData(menuItem);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable int id) {
        if (menuItemService.deleteMenuItem(id) == 0) {
            return ResponseUtil.generateErrorResponse("Menu item details to be deleted not found", HttpStatus.NOT_FOUND);
        }
        else {
            return ResponseUtil.generateSuccessResponseWithoutData("Successfully deleted Menu item");
        }
    }
}
