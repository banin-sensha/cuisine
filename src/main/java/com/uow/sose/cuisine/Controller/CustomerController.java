package com.uow.sose.cuisine.Controller;

import com.uow.sose.cuisine.Entity.Customer;
import com.uow.sose.cuisine.Generic.ResponseUtil;
import com.uow.sose.cuisine.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Add a new customer
    @PostMapping("/add")
    public ResponseEntity<Object> addCustomer(@RequestBody Customer customerParam) {
        Customer customer = customerService.addCustomer(customerParam);

        if (customer != null) {
            return ResponseUtil.generateSuccessResponseWithData(customer);
        }
        else {
            return ResponseUtil.generateErrorResponse("Error while adding new customer", HttpStatus.BAD_REQUEST);
        }
    }

    // Get all customers
    @GetMapping("/all")
    public ResponseEntity<Object> getAllCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();

        if (allCustomers.isEmpty()) {
            return ResponseUtil.generateErrorResponse("Customer is not registered yet", HttpStatus.NOT_FOUND);
        }
        else {
            return ResponseUtil.generateSuccessResponseWithData(allCustomers);
        }

    }

    // Get a customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable int id) {
        Customer customer = customerService.getCustomerById(id);

        if (customer != null) {
            return ResponseUtil.generateSuccessResponseWithData(customer);
        }
        else {
            return ResponseUtil.generateErrorResponse("Error while fetching customer by Id", HttpStatus.BAD_REQUEST);
        }

    }

    // Update a customer
    @PostMapping("/update")
    public ResponseEntity<Object> updateCustomer(@RequestBody Customer customerParam) {
        Customer customer = customerService.updateCustomer(customerParam);

        if (customer != null) {
            return ResponseUtil.generateSuccessResponseWithData(customer);
        }
        else {
            return ResponseUtil.generateErrorResponse("Customer details not found to update.", HttpStatus.NOT_FOUND);
        }
    }

    // Delete a customer by ID
    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable int id) {
        if (customerService.deleteCustomer(id) == 0) {
            return ResponseUtil.generateErrorResponse("Customer details to be deleted not found", HttpStatus.NOT_FOUND);
        }
        else {
            return ResponseUtil.generateSuccessResponseWithoutData("Successfully deleted Customer details");
        }
    }
}
