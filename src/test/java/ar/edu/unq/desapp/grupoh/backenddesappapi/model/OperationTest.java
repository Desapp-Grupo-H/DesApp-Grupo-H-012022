package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;
import org.junit.jupiter.api.Test;

import static ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        User transactionUser = mock(User.class);
        User operationUser = mock(User.class);
        TransactionIntention aTransactionIntention = mock(TransactionIntention.class);

        when(aTransactionIntention.getUser()).thenReturn(transactionUser);

        Operation operation = anOperation(operationUser, aTransactionIntention);

        assertEquals(transactionUser, operation.getUserTransactionOperation());
    }

    @Test
    public void theIntentionUserCanCancelTheOperation() throws OperationException {
        User transactionUser = mock(User.class);
        User operationUser = mock(User.class);
        TransactionIntention aTransactionIntention = mock(TransactionIntention.class);

        when(aTransactionIntention.getUser()).thenReturn(transactionUser);
        when(transactionUser.getId()).thenReturn(1L);
        when(operationUser.getId()).thenReturn(2L);

        Operation operation = anOperation(operationUser, aTransactionIntention);

        operation.cancelOperation(transactionUser);

        assertEquals(CANCELED, operation.getStatus());
        verify(transactionUser).cancelledTransaction();
    }

    @Test
    public void theOperationUserCanCancelTheOperation() throws OperationException {
        User transactionUser = mock(User.class);
        User operationUser = mock(User.class);
        TransactionIntention aTransactionIntention = mock(TransactionIntention.class);

        when(aTransactionIntention.getUser()).thenReturn(transactionUser);
        when(transactionUser.getId()).thenReturn(1L);
        when(operationUser.getId()).thenReturn(2L);

        Operation operation = anOperation(operationUser, aTransactionIntention);

        operation.cancelOperation(operationUser);

        assertEquals(CANCELED, operation.getStatus());
        verify(operationUser).cancelledTransaction();
    }

    @Test
    public void aRandomUserCanNotCancelTheOperation() {
        User transactionUser = mock(User.class);
        User operationUser = mock(User.class);
        User aRandomUser = mock(User.class);
        TransactionIntention aTransactionIntention = mock(TransactionIntention.class);

        when(aTransactionIntention.getUser()).thenReturn(transactionUser);
        when(transactionUser.getId()).thenReturn(1L);
        when(operationUser.getId()).thenReturn(2L);
        when(aRandomUser.getId()).thenReturn(3L);

        Operation operation = anOperation(operationUser, aTransactionIntention);

        Exception exception = assertThrows(OperationException.class, () -> operation.cancelOperation(aRandomUser));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Cannot cancel operation";

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void testOnSuccessfulCompleteOperation() throws OperationException {
        User operationUser = mock(User.class);
        User transactionUser = mock(User.class);
        TransactionIntention aTransactionIntention = mock(TransactionIntention.class);
        CryptoCurrency aCryptoCurrency = mock(CryptoCurrency.class);

        when(transactionUser.getId()).thenReturn(1L);
        when(operationUser.getId()).thenReturn(2L);
        when(aTransactionIntention.getUser()).thenReturn(transactionUser);
        when(aTransactionIntention.getPrice()).thenReturn(1f);
        when(aCryptoCurrency.compareQuotation(1f)).thenReturn(true);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);
        anOperation.setStatus(WAITING);
        anOperation.completeOperation(operationUser, aCryptoCurrency);

        assertEquals(DONE, anOperation.getStatus());
        assertNotNull(anOperation.getDateCompleted());
        verify(operationUser, times(1)).completedTransaction(10);
        verify(transactionUser, times(1)).completedTransaction(10);
        verify(aCryptoCurrency, times(1)).compareQuotation(1f);
        verify(aTransactionIntention, times(1)).reduceAvailableAmount(10);
    }

    @Test
    public void testFailCompleteOperationOnPriceRange() {
        User operationUser = mock(User.class);
        TransactionIntention aTransactionIntention = mock(TransactionIntention.class);
        CryptoCurrency aCryptoCurrency = mock(CryptoCurrency.class);


        when(operationUser.getId()).thenReturn(2L); //
        when(aTransactionIntention.getPrice()).thenReturn(1f);
        when(aCryptoCurrency.compareQuotation(1f)).thenReturn(false); //

        Operation anOperation = anOperation(operationUser, aTransactionIntention);
        anOperation.setStatus(WAITING);

        Exception exception = assertThrows(OperationException.class, () -> anOperation.completeOperation(operationUser, aCryptoCurrency));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Cannot complete Operation, price out of range";

        assertEquals(CANCELED, anOperation.getStatus());
        assertEquals(actualMessage, expectedMessage);
        assertNotNull(anOperation.getDateCompleted());

        verify(aTransactionIntention, times(1)).getCryptoName();
        verify(aTransactionIntention, times(1)).getPrice();
        verify(operationUser, times(2)).getId();
        verify(aCryptoCurrency, times(1)).compareQuotation(1f);

        verifyNoMoreInteractions(operationUser, aCryptoCurrency, aTransactionIntention);
    }

    @Test
    public void testFailCompleteOperationOnUserNotBeingInOperation() {
        User operationUser = mock(User.class);
        User randomUser = mock(User.class);
        TransactionIntention aTransactionIntention = mock(TransactionIntention.class);
        CryptoCurrency aCryptoCurrency = mock(CryptoCurrency.class);

        when(operationUser.getId()).thenReturn(2L);
        when(randomUser.getId()).thenReturn(3L);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);
        anOperation.setStatus(WAITING);

        Exception exception = assertThrows(OperationException.class, () -> anOperation.completeOperation(randomUser, aCryptoCurrency));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Cannot confirm reception";

        verifyNoInteractions(aCryptoCurrency);
        verify(operationUser, times(1)).getId();
        verify(randomUser, times(1)).getId();
        verify(aTransactionIntention, times(1)).getCryptoName();
        verifyNoMoreInteractions(operationUser, randomUser, aTransactionIntention);

        assertEquals(WAITING, anOperation.getStatus());
        assertNull(anOperation.getDateCompleted());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testFailCompleteOperationOnStatus() {
        User operationUser = mock(User.class);
        TransactionIntention aTransactionIntention = mock(TransactionIntention.class);
        CryptoCurrency aCryptoCurrency = mock(CryptoCurrency.class);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);

        Exception exception = assertThrows(OperationException.class, () -> anOperation.completeOperation(operationUser, aCryptoCurrency));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Cannot confirm reception";

        verifyNoInteractions(operationUser, aCryptoCurrency);
        verify(aTransactionIntention, times(1)).getCryptoName();
        verifyNoMoreInteractions(aTransactionIntention);

        assertNotEquals(CANCELED, anOperation.getStatus());
        assertNull(anOperation.getDateCompleted());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testOnAwaitsConfirmationSuccessful() throws OperationException {
        User operationUser = mock(User.class);
        TransactionIntention aTransactionIntention = mock(TransactionIntention.class);

        when(operationUser.getId()).thenReturn(2L);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);

        assertEquals(ONGOING, anOperation.getStatus());

        Operation methodReturn = anOperation.awaitsConfirmation(operationUser);

        assertEquals(WAITING, anOperation.getStatus());
        assertEquals(anOperation, methodReturn);

        verify(operationUser, times(2)).getId();
        verifyNoMoreInteractions(operationUser);
        verify(aTransactionIntention, times(1)).getCryptoName();
        verifyNoMoreInteractions(aTransactionIntention);
    }

    @Test
    public void testOnAwaitsConfirmationFailsOnUser(){
        User operationUser = mock(User.class);
        User randomUser = mock(User.class);
        TransactionIntention aTransactionIntention = mock(TransactionIntention.class);

        when(operationUser.getId()).thenReturn(2L);
        when(randomUser.getId()).thenReturn(3L);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);

        assertEquals(ONGOING, anOperation.getStatus());

        Exception exception = assertThrows(OperationException.class, () -> anOperation.awaitsConfirmation(randomUser));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Cannot realize transfer";

        assertEquals(actualMessage, expectedMessage);
        assertEquals(ONGOING, anOperation.getStatus());

        verify(operationUser, times(1)).getId();
        verify(randomUser, times(1)).getId();
        verify(aTransactionIntention, times(1)).getCryptoName();
        verifyNoMoreInteractions(operationUser, randomUser, aTransactionIntention);
    }

    @Test
    public void testOnAwaitsConfirmationFailsOnStatus(){
        User operationUser = mock(User.class);
        TransactionIntention aTransactionIntention = mock(TransactionIntention.class);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);

        anOperation.setStatus(CANCELED);

        Exception exception = assertThrows(OperationException.class, () -> anOperation.awaitsConfirmation(operationUser));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Cannot realize transfer";

        assertEquals(actualMessage, expectedMessage);
        assertNotEquals(WAITING, anOperation.getStatus());

        verifyNoInteractions(operationUser);
        verify(aTransactionIntention, times(1)).getCryptoName();
        verifyNoMoreInteractions(aTransactionIntention);
    }

    @Test
    public void testOnIsCompleteFalse(){
        User operationUser = mock(User.class);
        TransactionIntention aTransactionIntention = mock(TransactionIntention.class);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);

        assertFalse(anOperation.isComplete());
    }

    @Test
    public void testOnIsCompleteTrue(){
        User operationUser = mock(User.class);
        TransactionIntention aTransactionIntention = mock(TransactionIntention.class);

        Operation anOperation = anOperation(operationUser, aTransactionIntention);
        anOperation.setStatus(DONE);

        assertTrue(anOperation.isComplete());
    }




}
