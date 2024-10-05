package com.uow.sose.cuisine.Service;

import com.uow.sose.cuisine.Entity.TableReservation;
import com.uow.sose.cuisine.Generic.ResponseUtil;
import com.uow.sose.cuisine.Repository.TableReservationRepo;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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

    public TableReservation getById(int id) {
        return tableReservationRepo.findById(id).orElse(null);
    }

    public List<TableReservation> getTableReservationDetailsByCustomerId(int customerId) {
        List<TableReservation> tableReservationList = tableReservationRepo.getTableReservationByCustomerId(customerId);

        if (tableReservationList == null || tableReservationList.isEmpty()) {
            return null;
        }
        else {
            return tableReservationList;
        }
    }

    public List<TableReservation> getTableReservationDetailsByTableNumber(int tableNumber) {
        List<TableReservation> tableReservationList = tableReservationRepo.getTableReservationDetailsByTableNumber(tableNumber);

        if (tableReservationList == null || tableReservationList.isEmpty()) {
            return null;
        }
        else {
            return tableReservationList;
        }
    }

    public HashMap<String, Object> checkAvailability(int noOfGuests, String dateTime) {

        HashMap<String, Object> result = new HashMap<>();
        if (noOfGuests> 10 ) {
            result.put("key", -1);
            result.put("data", Collections.EMPTY_LIST);
        }
        else {
            List<TableReservation> tables = tableReservationRepo.getTableReservations(noOfGuests);
            List<TableReservation> availableTableList = new ArrayList<>();

            for (TableReservation table: tables) {
                if (table.getStatus().equals("Confirmed") && dateTime.equals(table.getReservation_time())) {
                    //do nothing
                }
                else {
                    availableTableList.add(table);
                }
            }

            if (availableTableList.isEmpty()) {
                result.put("key", 0);
                result.put("data", Collections.EMPTY_LIST);
            }
            else {
                result.put("key", 1);
                result.put("data", availableTableList);
            }
        }

        return result;
    }
}
