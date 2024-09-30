package com.uow.sose.cuisine.Repository;

import com.uow.sose.cuisine.Entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepo extends JpaRepository<MenuItem, Integer> {
}