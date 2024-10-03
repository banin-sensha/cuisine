package com.uow.sose.cuisine.Repository;

import com.uow.sose.cuisine.Entity.MenuItem;
import com.uow.sose.cuisine.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Integer> {

    @Query("SELECT new map(MI.name as name, MI.price as price) " +
            "FROM MenuItem MI, OrderedItem OI, Order O " +
            "WHERE O.order_id = :orderId " +
            "AND O.order_id = OI.order_id " +
            "AND OI.item_id = MI.item_id")
    List<HashMap<String, Object>> findMenuItemsByOrderId(@Param("orderId") int orderId);

}
