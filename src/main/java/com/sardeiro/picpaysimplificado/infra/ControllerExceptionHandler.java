package com.sardeiro.picpaysimplificado.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.sardeiro.picpaysimplificado.dtos.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDTO> threatResponseEntity(DataIntegrityViolationException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO("Usuário ja cadastrado", "400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    } 

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> threa404(EntityNotFoundException exception){
        return ResponseEntity.notFound().build();
    } 

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> threageneralException(Exception exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "500");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    } 
    
}
