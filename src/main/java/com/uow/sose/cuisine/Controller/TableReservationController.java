package com.uow.sose.cuisine.Controller;

import com.uow.sose.cuisine.Entity.Customer;
import com.uow.sose.cuisine.Entity.TableReservation;
import com.uow.sose.cuisine.Generic.ResponseUtil;
import com.uow.sose.cuisine.Service.TableReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/table-reservation")
public class TableReservationController {

    @Autowired
    private TableReservationService tableReservationService;

    @PostMapping("/check/availability")
    public ResponseEntity<Object> checkAvailability(@RequestBody TableReservation tableReservation) {

        HashMap<String, Object> map = tableReservationService.checkAvailability(tableReservation.getNumber_of_guests(),
                tableReservation.getReservation_time());


        int code = Integer.parseInt(map.get("key").toString());
        List<TableReservation> availableList = (List<TableReservation>) map.get("data");


        switch (code) {
            case -1:
                return ResponseUtil.generateSuccessResponseWithoutData("No availability: Guest count exceeds the limit");

            case 0:
                return ResponseUtil.generateSuccessResponseWithoutData("No availability: All tables booked");

            case 1:
                return ResponseUtil.generateSuccessResponseWithData(availableList);

            default:
                return ResponseUtil.generateErrorResponse("Error while checking table availability", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/book")
    public ResponseEntity<Object> add(@RequestBody TableReservation tableReservation) {

        HashMap<String, Object> map = tableReservationService.checkAvailability(tableReservation.getNumber_of_guests(), tableReservation.getReservation_time());

        int code = Integer.parseInt(map.get("key").toString());

        if (code <= 0) {
            return ResponseUtil.generateErrorResponse("Error while checking table availability", HttpStatus.BAD_REQUEST);
        }
        else {
            return  ResponseUtil.generateSuccessResponseWithData(tableReservationService.add(tableReservation));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        List<TableReservation> tableReservationList = tableReservationService.getAll();;

        if (tableReservationList.isEmpty()) {
            return ResponseUtil.generateErrorResponse("Error while fetching all table reservation details", HttpStatus.BAD_REQUEST);
        }
        else {
            return ResponseUtil.generateSuccessResponseWithData(tableReservationList);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Object> getByCustomerId(@PathVariable int customerId) {
        List<TableReservation> tableReservationList = tableReservationService.getTableReservationDetailsByCustomerId(customerId);
        if (tableReservationList == null) {
            return ResponseUtil.generateErrorResponse("Error while fetching table reservation details by customer id", HttpStatus.BAD_REQUEST);
        }
        else {
            return ResponseUtil.generateSuccessResponseWithData(tableReservationList);
        }
    }

    @GetMapping("/{tableNumber}")
    public ResponseEntity<Object> getByTableNumber(@PathVariable int tableNumber) {
        List<TableReservation> tableReservationList = tableReservationService.getTableReservationDetailsByTableNumber(tableNumber);
        if (tableReservationList == null) {
            return ResponseUtil.generateErrorResponse("Error while fetching table reservation details by table number", HttpStatus.BAD_REQUEST);
        }
        else {
            return ResponseUtil.generateSuccessResponseWithData(tableReservationList);
        }
    }
}
