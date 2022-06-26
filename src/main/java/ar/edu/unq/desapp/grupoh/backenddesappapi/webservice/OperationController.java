package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationAction;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.operation.OperationDTO;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.operation.OperationService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects.LogExecutionTime;
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

    @Transactional
    @GetMapping("/operations")
    @LogExecutionTime
    public ResponseEntity<?> getAllOperations(){
        return ResponseEntity.ok(operationService.findAll());
    }

    @Transactional
    @GetMapping("/operations/{operationId}")
    @LogExecutionTime
    public ResponseEntity<?> findById(@PathVariable Long operationId) throws OperationException {
        /*
        try {
            Operation operation = this.operationService.findById(operationId);
        }catch(OperationException exception){

        }
        return ResponseEntity.status(HttpStatus.OK).body(operation);*/
        Operation operation = this.operationService.findById(operationId);
        return ResponseEntity.status(HttpStatus.OK).body(operation);
    }

    @Transactional
    @GetMapping("/operations/{cryptoName}")
    @LogExecutionTime
    public ResponseEntity<?> volumeOfOperations(@PathVariable String crypto){
        int volumeOfOperations = this.operationService.volumeOfOperations(crypto);
        return ResponseEntity.status(HttpStatus.OK).body(volumeOfOperations);
    }

    @Transactional
    @PostMapping("/operations")
    @LogExecutionTime
    public ResponseEntity<?> createOperation(@Valid @RequestBody OperationDTO operationDTO) throws OperationException{
        Operation operation = operationService.saveOperation(operationDTO.createOperation());
        return ResponseEntity.status(HttpStatus.CREATED).body(operation);
    }

    @Transactional
    @PutMapping("/operations/{operationId}")
    @LogExecutionTime
    public ResponseEntity<?> forwardOperation(@PathVariable long operationId, @Valid @RequestBody OperationAction action, @RequestHeader long userId) throws OperationException {
        operationService.actionOperation(operationId, action, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
