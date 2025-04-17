package org.project.easyf1.controllers.rest;

import org.project.easyf1.exception.NoSessionTodayException;
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
    public ResponseEntity<?> handleNoSessionToday(NoSessionTodayException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
    }
}
