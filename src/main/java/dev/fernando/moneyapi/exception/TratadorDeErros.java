package dev.fernando.moneyapi.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroApi> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroApi(ex.getMessage()));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroApi> handleConstraintViolationException(ConstraintViolationException ex) {
        var msg = ex.getConstraintViolations()
        .stream()
        .map(violation->violation.getPropertyPath()+" "+violation.getMessage())
        .collect(Collectors.joining("\n"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroApi(msg));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroApi> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var msg = ex.getFieldErrors()
        .stream()
        .map(fieldError->fieldError.getField() + " " + fieldError.getDefaultMessage())
        .collect(Collectors.joining("\n"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroApi(msg));
    }
}
