package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.OperationDTO;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.OperationService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class OperationController {
    @Autowired
    private  OperationService operationService;

    @GetMapping("/operations")
    public ResponseEntity<?> getAllOperations(){
        return ResponseEntity.ok(operationService.findAll());
    }

    @Transactional
    @GetMapping("/operations/{operationId}")
    public ResponseEntity<?> findById(@PathVariable Long operationId) throws OperationException {
        Operation operation = this.operationService.findById(operationId);
        return ResponseEntity.status(HttpStatus.OK).body(operation);
    }

    @PostMapping("/operations")
    public ResponseEntity<?> createOperation(@Valid @RequestBody OperationDTO operationDTO) throws OperationException{
        Operation operation = operationDTO.createOperation();
        operationService.saveOperation(operation);
        return ResponseEntity.status(HttpStatus.CREATED).body(operation);
    }

    @PutMapping("/operations/accept/{operationId}")
    public ResponseEntity<?> acceptOperation(@PathVariable long operationId) throws OperationException {
        operationService.acceptOperation(operationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/operations/cancel/{operationId}")
    public ResponseEntity<?> cancelOperation(@PathVariable long operationId) throws OperationException {
        operationService.declineOperation(operationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
