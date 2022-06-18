package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.TransactionDto;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.TransactionService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*" ,methods = {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.POST})
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> getAllTransactions(){
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) throws TransactionException {
        return ResponseEntity.status(HttpStatus.OK).body(this.transactionService.findById(id));
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> register(@Valid @RequestBody TransactionDto transactionDto) throws TransactionException {
        TransactionIntention transaction = transactionDto.createTransaction();
        transactionService.saveTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionDto);
    }
}
