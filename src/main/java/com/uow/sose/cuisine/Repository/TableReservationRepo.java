package com.uow.sose.cuisine.Repository;

import com.uow.sose.cuisine.Entity.TableReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TableReservationRepo extends JpaRepository<TableReservation, Integer> {

    @Query("SELECT R FROM TableReservation R WHERE R.reservation_time = :dateTime")
    List<TableReservation> findReservationsByTimeRange(@Param("dateTime") String dateTime);

}
