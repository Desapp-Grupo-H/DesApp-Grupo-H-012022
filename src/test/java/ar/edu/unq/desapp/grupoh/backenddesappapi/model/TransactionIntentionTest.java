package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.TypeTransaction;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import org.junit.jupiter.api.Test;

import static ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.TransactionStatus.INACTIVE;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionIntentionTest {


    public User anUser() throws UserException {
        return User
            .builder()
            .withName("Pepe")
            .withLastname("Argento")
            .withAddress("1234567891")
            .withEmail("pepeArg@yahoo.com")
            .withPassword("Pa55w0?d!")
            .withCvu("1312313123131231312322")
            .withWallet("12345678")
            .build();
    }

    public TransactionIntention aTransactionIntention(User anUser, TypeTransaction type) throws TransactionException{
        return TransactionIntention
                .builder()
                .withAmount(20d)
                .withPrice(2.1f)
                .withUser(anUser)
                .withCryptoCurrency(CryptoName.ALICEUSDT)
                .withTypeTransaction(type)
                .build();
    }

    @Test
    public void aBuyTransactionIntentionCreatedHasAllItsAtributes() throws UserException, TransactionException {
        User anUser = anUser();
        TransactionIntention transactionIntention = aTransactionIntention(anUser, TypeTransaction.BUY);

        assertTrue(transactionIntention.isBuy());
        assertFalse(transactionIntention.isSell());
    }

    @Test
    public void aSellTransactionIntentionCreatedHasAllItsAtributes() throws UserException, TransactionException {
        User anUser = anUser();
        TransactionIntention transactionIntention = aTransactionIntention(anUser, TypeTransaction.SELL);

        assertFalse(transactionIntention.isBuy());
        assertTrue(transactionIntention.isSell());
    }

    @Test
    public void aBuyTransactionIsTerminated() throws UserException, TransactionException {
        User anUser = anUser();
        TransactionIntention transactionIntention = aTransactionIntention(anUser, TypeTransaction.BUY);

        transactionIntention.endIntention();

        assertEquals(INACTIVE, transactionIntention.getStatus());

    }

    @Test
    public void aBuyTransactionAmountIsReduced() throws UserException, TransactionException {
        User anUser = anUser();
        TransactionIntention transactionIntention = aTransactionIntention(anUser, TypeTransaction.BUY);

        assertEquals(20d, transactionIntention.getAmount());

        transactionIntention.reduceAvailableAmount(10d);
        assertEquals(10d, transactionIntention.getAmount());

    }

}
