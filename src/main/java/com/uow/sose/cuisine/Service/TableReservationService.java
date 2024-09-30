package com.uow.sose.cuisine.Service;

import com.uow.sose.cuisine.Entity.TableReservation;
import com.uow.sose.cuisine.Repository.TableReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableReservationService {

    @Autowired
    private TableReservationRepo tableReservationRepo;

    public TableReservation add(TableReservation tableReservation) {
        return tableReservationRepo.save(tableReservation);
    }

    public List<TableReservation> getAll() {
        return tableReservationRepo.findAll();
    }

    public Optional<TableReservation> getById(int id) {
        return tableReservationRepo.findById(id);
    }

    public void deleteById(int id) {
        tableReservationRepo.deleteById(id);
    }
}
