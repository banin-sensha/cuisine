package com.uow.sose.cuisine.Repository;

import com.uow.sose.cuisine.Entity.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedItemRepo extends JpaRepository<OrderedItem, Integer> {
}
