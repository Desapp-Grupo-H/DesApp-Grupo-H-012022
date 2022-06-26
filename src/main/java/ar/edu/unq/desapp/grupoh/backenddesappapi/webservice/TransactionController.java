package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction.TransactionDTO;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction.TransactionService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*" ,methods = {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.POST})
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public ResponseEntity<?> getActiveTransactions(){
        return ResponseEntity.ok(transactionService.findAllActive());
    }

    @GetMapping("/transactions/all")
    public ResponseEntity<?> getAllTransactions(){
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) throws TransactionException {
        TransactionIntention transaction = this.transactionService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(transaction);
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> register(@Valid @RequestBody TransactionDTO transactionDto) throws TransactionException {
        TransactionIntention transaction =transactionService.saveTransaction(transactionDto.createTransaction());
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}
