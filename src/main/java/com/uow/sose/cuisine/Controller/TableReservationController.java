package com.uow.sose.cuisine.Controller;

import com.uow.sose.cuisine.Entity.TableReservation;
import com.uow.sose.cuisine.Service.TableReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/table-reservation")
public class TableReservationController {

    @Autowired
    private TableReservationService tableReservationService;

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
