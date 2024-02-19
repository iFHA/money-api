package dev.fernando.moneyapi.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroApi> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroApi(ex.getMessage()));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroApi> handleConstraintViolationException(ConstraintViolationException ex) {
        var msg = ex.getConstraintViolations()
        .stream()
        .map(violation->violation.getPropertyPath()+" "+violation.getMessageTemplate())
        .collect(Collectors.joining("\n"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroApi(msg));
    }
}
