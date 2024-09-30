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
    public ResponseEntity<Object> addCustomer(@RequestBody Customer customer) {
        Customer cust = customerService.addCustomer(customer);

        if (cust != null) {
            return ResponseUtil.generateSuccessResponseWithData(cust);
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
    public Optional<Customer> getCustomerById(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }

    // Update a customer
    @PutMapping("/update")
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    // Delete a customer by ID
    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
    }
}
