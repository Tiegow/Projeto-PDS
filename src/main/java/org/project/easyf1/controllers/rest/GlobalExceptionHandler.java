package org.project.easyf1.controllers.rest;

import java.util.HashMap;
import java.util.Map;

import org.project.easyf1.exception.DriversNotFoundException;
import org.project.easyf1.exception.EventsNotFountException;
import org.project.easyf1.exception.NoSessionTodayException;
import org.project.easyf1.exception.PositionsUpdateException;
import org.project.easyf1.exception.WeatherNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Erro interno do servidor: " + ex.getMessage());
    }

    @ExceptionHandler(NoSessionTodayException.class)
    public ResponseEntity<Map<String, String>> handleNoSessionToday(NoSessionTodayException ex) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @ExceptionHandler(DriversNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDriversNotFound(DriversNotFoundException ex) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @ExceptionHandler(WeatherNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleWeatherNotFound(WeatherNotFoundException ex) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @ExceptionHandler(PositionsUpdateException.class)
    public ResponseEntity<Map<String, String>> handlePositionsError(PositionsUpdateException ex) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
    }

    @ExceptionHandler(EventsNotFountException.class)
    public ResponseEntity<Map<String, String>> handleEventsNotFound(EventsNotFountException ex) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }
}
