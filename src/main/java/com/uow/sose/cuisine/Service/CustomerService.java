package com.uow.sose.cuisine.Service;

import com.uow.sose.cuisine.Entity.Customer;
import com.uow.sose.cuisine.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    // Create or add a new customer
    public Customer addCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    // Retrieve all customers
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    // Retrieve customer by ID
    public Optional<Customer> getCustomerById(int id) {
        return customerRepo.findById(id);
    }

    // Update a customer
    public Customer updateCustomer(Customer customer) {
        Customer existingCustomer = customerRepo.findById(customer.getCustomer_id())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Modify the entity
        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());

        // Save the updated entity
        return customerRepo.save(existingCustomer);
    }

    // Delete customer by ID
    public void deleteCustomer(int id) {
        customerRepo.deleteById(id);
    }
}
