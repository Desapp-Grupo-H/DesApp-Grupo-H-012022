package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationAction;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.operation.IOperationService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.operation.OperationDTO;
import ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OperationController {
    @Autowired
    private IOperationService operationService;

    @GetMapping("/operations")
    @LogExecutionTime
    public ResponseEntity<List<Operation>> getAllOperations(){
        try {
            List<Operation> operations = operationService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(operations);

        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/operations/{operationId}")
    @LogExecutionTime
    public ResponseEntity<Operation> findById(@PathVariable Long operationId) throws OperationException {
        try {
            Operation operation = this.operationService.findById(operationId);
            return ResponseEntity.status(HttpStatus.OK).body(operation);
        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/operations/crypto/{cryptoName}")
    @LogExecutionTime
    public ResponseEntity<?> volumeOfOperations(@PathVariable("cryptoName") CryptoName cryptoName){
        try {
            int volumeOfOperations = this.operationService.volumeOfOperations(cryptoName);
            return ResponseEntity.status(HttpStatus.OK).body(volumeOfOperations);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/operations")
    @LogExecutionTime
    public ResponseEntity<Operation> createOperation(@Valid @RequestBody OperationDTO operationDTO){
        try {
            Operation operation = operationDTO.createOperation();
            operation = operationService.saveOperation(operation);
            return ResponseEntity.status(HttpStatus.CREATED).body(operation);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/operations/{operationId}")
    @LogExecutionTime
    public ResponseEntity<Operation> forwardOperation(@PathVariable("operationId") long operationId, @Valid @RequestBody OperationAction action, @RequestHeader long userId) {
        try {
            Operation operation = operationService.actionOperation(operationId, action, userId);
            return ResponseEntity.status(HttpStatus.OK).body(operation);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()  ;
        }
    }
}
