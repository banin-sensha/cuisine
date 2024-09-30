package com.uow.sose.cuisine.Generic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ResponseUtil {
    public static <T> ResponseEntity<Object> generateSuccessResponseWithData(T data) {
        HashMap<String, Object> response = new HashMap<>();
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setMessage("Success");
        genericResponse.setStatusCode(HttpStatus.OK.value());

        response.put("status", genericResponse);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<Object> generateSuccessResponseWithoutData(String message) {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setMessage(message);
        genericResponse.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    public static ResponseEntity<Object> generateErrorResponse(String message, HttpStatus status) {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setMessage(message);
        genericResponse.setStatusCode(status.value());

        return new ResponseEntity<>(genericResponse, status);
    }
}
