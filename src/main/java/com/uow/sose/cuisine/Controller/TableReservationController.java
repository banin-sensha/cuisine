package com.uow.sose.cuisine.Controller;

import com.uow.sose.cuisine.Entity.TableReservation;
import com.uow.sose.cuisine.Generic.ResponseUtil;
import com.uow.sose.cuisine.Service.TableReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/table-reservation")
public class TableReservationController {

    @Autowired
    private TableReservationService tableReservationService;

    @PostMapping("/check/availability")
    public ResponseEntity<Object> checkAvailability(@RequestBody TableReservation tableReservation) {
        if (tableReservation.getNumber_of_guests() > 10 ) {
            return ResponseUtil.generateSuccessResponseWithoutData("No availability: Guest count exceeds the limit.");
        }
        else {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            LocalDateTime localDateTime = LocalDateTime.parse(tableReservation.getReservation_time(), formatter);
            return ResponseUtil.generateSuccessResponseWithoutData(tableReservationService.checkAvailability(tableReservation.getReservation_time()));
        }
    }

    @PostMapping("/add")
    public TableReservation add(@RequestBody TableReservation tableReservation) {
        return tableReservationService.add(tableReservation);
    }

    @GetMapping("/all")
    public List<TableReservation> getAll() {
        return tableReservationService.getAll();
    }

    @GetMapping("/{id}")
    public TableReservation getById(@PathVariable int id) {
        return tableReservationService.getById(id).orElse(null);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable int id) {
        tableReservationService.deleteById(id);
    }
}
