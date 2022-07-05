package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.TypeTransaction;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoInteractions;

public class OperationTest {

    public Operation anOperation(User anUser, TransactionIntention transactionIntention){
        return Operation
                .builder()
                .withAmount(10d)
                .withIntention(transactionIntention)
                .withUserInitOperation(anUser)
                .build();
    }

    @Test
    public void anOperationCreatedHasAllIts() {
        User transactionUser = Mockito.mock(User.class);
        User operationUser = Mockito.mock(User.class);
        TransactionIntention aTransactionIntention = Mockito.mock(TransactionIntention.class);

        Mockito.when(aTransactionIntention.getUser()).thenReturn(transactionUser);

        Operation operation = anOperation(operationUser, aTransactionIntention);

        assertEquals(transactionUser, operation.getUserTransactionOperation());
    }

    @Test
    public void theIntentionUserCanCancelTheOperation() throws OperationException {
        User transactionUser = Mockito.mock(User.class);
        User operationUser = Mockito.mock(User.class);
        TransactionIntention aTransactionIntention = Mockito.mock(TransactionIntention.class);

        Mockito.when(aTransactionIntention.getUser()).thenReturn(transactionUser);
        Mockito.when(transactionUser.getId()).thenReturn(1L);
        Mockito.when(operationUser.getId()).thenReturn(2L);

        Operation operation = anOperation(operationUser, aTransactionIntention);

        operation.cancelOperation(transactionUser);

        assertEquals(CANCELED, operation.getStatus());
        Mockito.verify(transactionUser).cancelledTransaction();
    }

    @Test
    public void theOperationUserCanCancelTheOperation() throws OperationException {
        User transactionUser = Mockito.mock(User.class);
        User operationUser = Mockito.mock(User.class);
        TransactionIntention aTransactionIntention = Mockito.mock(TransactionIntention.class);

        Mockito.when(aTransactionIntention.getUser()).thenReturn(transactionUser);
        Mockito.when(transactionUser.getId()).thenReturn(1L);
        Mockito.when(operationUser.getId()).thenReturn(2l);

        Operation operation = anOperation(operationUser, aTransactionIntention);

        operation.cancelOperation(operationUser);

        assertEquals(CANCELED, operation.getStatus());
        Mockito.verify(operationUser).cancelledTransaction();
    }

    @Test
    public void aRandomUserCanNotCancelTheOperation() {
        User transactionUser = Mockito.mock(User.class);
        User operationUser = Mockito.mock(User.class);
        User aRandomUser = Mockito.mock(User.class);
        TransactionIntention aTransactionIntention = Mockito.mock(TransactionIntention.class);

        Mockito.when(aTransactionIntention.getUser()).thenReturn(transactionUser);
        Mockito.when(transactionUser.getId()).thenReturn(1L);
        Mockito.when(operationUser.getId()).thenReturn(2L);
        Mockito.when(aRandomUser.getId()).thenReturn(3L);

        Operation operation = anOperation(operationUser, aTransactionIntention);

        Exception exception = assertThrows(OperationException.class, () -> operation.cancelOperation(aRandomUser));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Cannot cancel operation";

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void testOnSuccessfulCompleteOperation() throws OperationException {
        User operationUser = Mockito.mock(User.class);
        User transactionUser = Mockito.mock(User.class);
        TransactionIntention aTransactionIntention = Mockito.mock(TransactionIntention.class);
        CryptoCurrency aCryptoCurrency = Mockito.mock(CryptoCurrency.class);

        Mockito.when(transactionUser.getId()).thenReturn(1L);
        Mockito.when(operationUser.getId()).thenReturn(2L);
        Mockito.when(aTransactionIntention.getUser()).thenReturn(transactionUser);
        Mockito.when(aTransactionIntention.getPrice()).thenReturn(1f);
        Mockito.when(aCryptoCurrency.compareQuotation(1f)).thenReturn(true);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);
        anOperation.setStatus(WAITING);
        anOperation.completeOperation(operationUser, aCryptoCurrency);

        assertEquals(DONE, anOperation.getStatus());
        assertNotNull(anOperation.getDateCompleted());
        Mockito.verify(operationUser, times(1)).completedTransaction(10);
        Mockito.verify(transactionUser, times(1)).completedTransaction(10);
        Mockito.verify(aCryptoCurrency, times(1)).compareQuotation(1f);
        Mockito.verify(aTransactionIntention, times(1)).reduceAvailableAmount(10);
    }

    @Test
    public void testFailCompleteOperationOnPriceRange() {
        User operationUser = Mockito.mock(User.class);
        TransactionIntention aTransactionIntention = Mockito.mock(TransactionIntention.class);
        CryptoCurrency aCryptoCurrency = Mockito.mock(CryptoCurrency.class);


        Mockito.when(operationUser.getId()).thenReturn(2L); //
        Mockito.when(aTransactionIntention.getPrice()).thenReturn(1f);
        Mockito.when(aCryptoCurrency.compareQuotation(1f)).thenReturn(false); //

        Operation anOperation = anOperation(operationUser, aTransactionIntention);
        anOperation.setStatus(WAITING);

        Exception exception = assertThrows(OperationException.class, () -> anOperation.completeOperation(operationUser, aCryptoCurrency));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Cannot complete Operation, price out of range";

        assertEquals(CANCELED, anOperation.getStatus());
        assertNotNull(anOperation.getDateCompleted());

        Mockito.verify(aTransactionIntention, times(1)).getCryptoName();
        Mockito.verify(aTransactionIntention, times(1)).getPrice();
        Mockito.verify(operationUser, times(2)).getId();
        Mockito.verify(aCryptoCurrency, times(1)).compareQuotation(1f);

        Mockito.verifyNoMoreInteractions(operationUser, aCryptoCurrency, aTransactionIntention);
    }

    @Test
    public void testFailCompleteOperationOnUserNotBeingInOperation() throws OperationException {
        User operationUser = Mockito.mock(User.class);
        User randomUser = Mockito.mock(User.class);
        TransactionIntention aTransactionIntention = Mockito.mock(TransactionIntention.class);
        CryptoCurrency aCryptoCurrency = Mockito.mock(CryptoCurrency.class);

        Mockito.when(operationUser.getId()).thenReturn(2L);
        Mockito.when(randomUser.getId()).thenReturn(3L);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);
        anOperation.setStatus(WAITING);

        Exception exception = assertThrows(OperationException.class, () -> anOperation.completeOperation(randomUser, aCryptoCurrency));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Cannot confirm reception";

        Mockito.verifyNoInteractions(aCryptoCurrency);
        Mockito.verify(operationUser, times(1)).getId();
        Mockito.verify(randomUser, times(1)).getId();
        Mockito.verify(aTransactionIntention, times(1)).getCryptoName();
        Mockito.verifyNoMoreInteractions(operationUser, randomUser, aTransactionIntention);

        assertEquals(WAITING, anOperation.getStatus());
        assertNull(anOperation.getDateCompleted());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testFailCompleteOperationOnStatus() {
        User operationUser = Mockito.mock(User.class);
        TransactionIntention aTransactionIntention = Mockito.mock(TransactionIntention.class);
        CryptoCurrency aCryptoCurrency = Mockito.mock(CryptoCurrency.class);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);

        Exception exception = assertThrows(OperationException.class, () -> anOperation.completeOperation(operationUser, aCryptoCurrency));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Cannot confirm reception";

        Mockito.verifyNoInteractions(operationUser, aCryptoCurrency);
        Mockito.verify(aTransactionIntention, times(1)).getCryptoName();
        Mockito.verifyNoMoreInteractions(aTransactionIntention);

        assertNotEquals(CANCELED, anOperation.getStatus());
        assertNull(anOperation.getDateCompleted());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testOnAwaitsConfirmationSuccessful() throws OperationException {
        User operationUser = Mockito.mock(User.class);
        TransactionIntention aTransactionIntention = Mockito.mock(TransactionIntention.class);

        Mockito.when(operationUser.getId()).thenReturn(2L);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);

        assertEquals(ONGOING, anOperation.getStatus());

        Operation methodReturn = anOperation.awaitsConfirmation(operationUser);

        assertEquals(WAITING, anOperation.getStatus());
        assertEquals(anOperation, methodReturn);

        Mockito.verify(operationUser, times(2)).getId();
        Mockito.verifyNoMoreInteractions(operationUser);
        Mockito.verify(aTransactionIntention, times(1)).getCryptoName();
        Mockito.verifyNoMoreInteractions(aTransactionIntention);
    }

    @Test
    public void testOnAwaitsConfirmationFailsOnUser(){
        User operationUser = Mockito.mock(User.class);
        User randomUser = Mockito.mock(User.class);
        TransactionIntention aTransactionIntention = Mockito.mock(TransactionIntention.class);

        Mockito.when(operationUser.getId()).thenReturn(2L);
        Mockito.when(randomUser.getId()).thenReturn(3L);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);

        assertEquals(ONGOING, anOperation.getStatus());

        Exception exception = assertThrows(OperationException.class, () -> anOperation.awaitsConfirmation(randomUser));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Cannot realize transfer";

        assertEquals(actualMessage, expectedMessage);
        assertEquals(ONGOING, anOperation.getStatus());

        Mockito.verify(operationUser, times(1)).getId();
        Mockito.verify(randomUser, times(1)).getId();
        Mockito.verify(aTransactionIntention, times(1)).getCryptoName();
        Mockito.verifyNoMoreInteractions(operationUser, randomUser, aTransactionIntention);
    }

    @Test
    public void testOnAwaitsConfirmationFailsOnStatus(){
        User operationUser = Mockito.mock(User.class);
        TransactionIntention aTransactionIntention = Mockito.mock(TransactionIntention.class);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);

        anOperation.setStatus(CANCELED);

        Exception exception = assertThrows(OperationException.class, () -> anOperation.awaitsConfirmation(operationUser));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Cannot realize transfer";

        assertEquals(actualMessage, expectedMessage);
        assertNotEquals(WAITING, anOperation.getStatus());

        verifyNoInteractions(operationUser);
        Mockito.verify(aTransactionIntention, times(1)).getCryptoName();
        Mockito.verifyNoMoreInteractions(aTransactionIntention);
    }

    @Test
    public void testOnIsCompleteFalse(){
        User operationUser = Mockito.mock(User.class);
        TransactionIntention aTransactionIntention = Mockito.mock(TransactionIntention.class);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);

        assertFalse(anOperation.isComplete());
    }

    @Test
    public void testOnIsCompleteTrue(){
        User operationUser = Mockito.mock(User.class);
        TransactionIntention aTransactionIntention = Mockito.mock(TransactionIntention.class);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);
        anOperation.setStatus(DONE);

        assertTrue(anOperation.isComplete());
    }




}
