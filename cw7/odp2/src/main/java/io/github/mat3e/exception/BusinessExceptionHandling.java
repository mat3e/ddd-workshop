package io.github.mat3e.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class BusinessExceptionHandling {
    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Void> handleNotFound(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BusinessException.class)
    ResponseEntity<String> handleAll(BusinessException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
