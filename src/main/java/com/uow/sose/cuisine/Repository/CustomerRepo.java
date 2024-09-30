package com.uow.sose.cuisine.Repository;

import com.uow.sose.cuisine.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
}
