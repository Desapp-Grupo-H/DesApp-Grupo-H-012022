package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationAction;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.operation.IOperationService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.operation.OperationDTO;
import ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    public ResponseEntity<?> getAllOperations(){
        try {
            List<Operation> operations = operationService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(operations);

        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/operations/{operationId}")
    @LogExecutionTime
    public ResponseEntity<?> findById(@PathVariable Long operationId) throws OperationException {
        try {
            Operation operation = this.operationService.findById(operationId);
            return ResponseEntity.status(HttpStatus.OK).body(operation);
        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/operations/{cryptoName}")
    @LogExecutionTime
    public ResponseEntity<?> volumeOfOperations(@PathVariable String crypto){
        try {
            int volumeOfOperations = this.operationService.volumeOfOperations(crypto);
            return ResponseEntity.status(HttpStatus.OK).body(volumeOfOperations);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/operations")
    @LogExecutionTime
    public ResponseEntity<?> createOperation(@Valid @RequestBody OperationDTO operationDTO) throws OperationException{
        try {
            Operation operation = operationService.saveOperation(operationDTO.createOperation());
            return ResponseEntity.status(HttpStatus.CREATED).body(operation);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/operations/{operationId}")
    @LogExecutionTime
    public ResponseEntity<?> forwardOperation(@PathVariable long operationId, @Valid @RequestBody OperationAction action, @RequestHeader long userId) throws OperationException {
        try {
            operationService.actionOperation(operationId, action, userId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
