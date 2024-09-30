package com.uow.sose.cuisine.Repository;

import com.uow.sose.cuisine.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Integer> {
}
