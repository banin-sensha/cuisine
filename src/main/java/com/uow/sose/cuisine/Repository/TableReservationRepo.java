package com.uow.sose.cuisine.Repository;

import com.uow.sose.cuisine.Entity.TableReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface TableReservationRepo extends JpaRepository<TableReservation, Integer> {

    @Query("SELECT R FROM TableReservation R WHERE R.number_of_guests >= :noOfGuests")
    List<TableReservation> getTableReservations(@Param("noOfGuests") int noOfGuests);

    @Query("SELECT R FROM TableReservation R, Customer C WHERE C.customer_id = R.customer_id AND R.customer_id = :customerId")
    List<TableReservation> getTableReservationByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT R FROM TableReservation R WHERE R.table_number = :tableNumber")
    List<TableReservation> getTableReservationDetailsByTableNumber(@Param("tableNumber") int tableNumber);
}
