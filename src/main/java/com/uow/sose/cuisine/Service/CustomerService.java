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
    public Customer getCustomerById(int id) {
        return customerRepo.findById(id)
                .orElse(null);
    }

    // Update a customer
    public Customer updateCustomer(Customer customer) {
        Customer existingCustomer = customerRepo.findById(customer.getCustomer_id())
                .orElse(null);

        if (existingCustomer == null) {
            return null;
        }
        else {
            // Modify the entity
            existingCustomer.setName(customer.getName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPhone(customer.getPhone());

            // Save the updated entity
            return customerRepo.save(existingCustomer);
        }
    }

    // Delete customer by ID
    public int deleteCustomer(int id) {
        Optional<Customer> cust = customerRepo.findById(id);

        if (cust.isPresent()) {
            customerRepo.deleteById(id);
            return 1;
        }
        else {
            return 0;
        }
    }
}
