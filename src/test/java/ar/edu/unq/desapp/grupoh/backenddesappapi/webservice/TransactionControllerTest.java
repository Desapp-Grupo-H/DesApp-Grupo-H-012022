package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction.ITransactionService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction.TransactionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private ITransactionService transactionServiceMock;
    @InjectMocks
    private TransactionController transactionController;

    @Test
    public void getAllTransactions_returnTransactions() {
        // arrange
        TransactionIntention transaction1 = TransactionIntention.builder()
                .build();
        TransactionIntention transaction2 = TransactionIntention.builder()
                .build();

        List<TransactionIntention> expectedTransactionList = new ArrayList<>();
        expectedTransactionList.add(transaction1);
        expectedTransactionList.add(transaction2);

        when(transactionServiceMock.findAll()).thenReturn(expectedTransactionList);

        // act
        List<TransactionIntention> actualTransactionList = (List<TransactionIntention>) transactionController.getAllTransactions().getBody();

        // assert
        verify(transactionServiceMock, atLeastOnce()).findAll();

        Assertions.assertEquals(2, actualTransactionList.size());
        Assertions.assertTrue(actualTransactionList.contains(transaction1));
        Assertions.assertTrue(actualTransactionList.contains(transaction2));
    }

    @Test
    public void getTransactionById() throws TransactionException {
        // arrange
        Long expectedId = 1L;
        TransactionIntention expectedTransaction = TransactionIntention.builder()
                .withId(expectedId)
                .build();

        // act
        when(transactionServiceMock.findById(expectedId)).thenReturn(expectedTransaction);

        // assert
        ResponseEntity<?> response = transactionController.findById(expectedId);

        verify(transactionServiceMock, atLeastOnce()).findById(expectedId);

        Assertions.assertEquals(expectedTransaction, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
/*
    @Test
    public void whenCreateTransaction_returnCreated() {
        // arrange
        TransactionDTO payload = TransactionDTO.TransactionDTOBuilder
                .withId(1L)
                .userId(1L)
                .build();
        TransactionIntention expectedTransaction = TransactionIntention.builder()
                .withId(1L)
                .build();

        // act
        when(transactionServiceMock.saveTransaction(payload)).thenReturn(expectedTransaction);

        // assert
        ResponseEntity<?> response = transactionController.create(payload);

        verify(transactionServiceMock, atLeastOnce()).save(payload);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void acceptTransaction() {
        Long id = 1L;
        transactionController.accept(id);
        verify(transactionServiceMock, atLeastOnce()).acceptTransaction(id);
    }

    @Test
    public void cancelTransaction() {
        Long id = 1L;
        transactionController.cancel(id);
        verify(transactionServiceMock, atLeastOnce()).cancel(id);
    }

    @Test
    public void getAllActiveTransactions_returnTransactions() {
        // arrange
        TransactionIntention transaction1 = TransactionIntention.builder()
                .withId(1L)
                .state(TransactionState.PENDING)
                .build();
        TransactionIntention transaction2 = TransactionIntention.builder()
                .withId(1L)
                .state(TransactionState.PENDING)
                .build();

        List<TransactionIntention> expectedTransactionList = new ArrayList<>();
        expectedTransactionList.add(transaction1);
        expectedTransactionList.add(transaction2);

        when(transactionServiceMock.findActiveTransactions()).thenReturn(expectedTransactionList);

        // act
        List<TransactionIntention> actualUserList = transactionController.getAllActive();

        // assert
        verify(transactionServiceMock, atLeastOnce()).findActiveTransactions();

        Assertions.assertEquals(2, actualUserList.size());
        Assertions.assertTrue(actualUserList.contains(transaction1));
        Assertions.assertTrue(actualUserList.contains(transaction2));
    }*/
}