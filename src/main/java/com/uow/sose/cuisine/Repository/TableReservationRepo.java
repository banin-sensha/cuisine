package com.uow.sose.cuisine.Repository;

import com.uow.sose.cuisine.Entity.TableReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableReservationRepo extends JpaRepository<TableReservation, Integer> {
}
