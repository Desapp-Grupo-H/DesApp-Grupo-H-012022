package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTests {

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

    @Test
    public void anUserCreatedHasAllItsAtributes() throws UserException{
        User user = anUser();

        assertEquals("Pepe", user.getName());
        assertEquals("Argento", user.getLastName());
        assertEquals("1234567891", user.getAddress());
        assertEquals("pepeArg@yahoo.com", user.getEmail());
        assertEquals("Pa55w0?d!", user.getPassword());
        assertEquals("1312313123131231312322", user.getCvu());
        assertEquals("12345678", user.getWallet());
        assertEquals(0, user.getTransactionsPoints());
        assertEquals(0, user.getSuccessfulOperations());
    }

    @Test
    public void anUserHasInvalidName() {
        UserException error  = assertThrows(UserException.class, () ->
            User.builder().withName("Pe").build());

        String actualMessage = error.getMessage();

        assertTrue(actualMessage.contains("Name or Lastname not valid"));

    }

    @Test
    public void anUserHasInvalidLastname() {
        UserException error  = assertThrows(UserException.class, () ->
            User.builder().withLastname("Ar").build());

        String actualMessage = error.getMessage();

        assertTrue(actualMessage.contains("Name or Lastname not valid"));

    }

    @Test
    public void anUserHasInvalidCvu() {
        UserException error  = assertThrows(UserException.class, () ->
            User.builder().withCvu("1312313122").build());
        String actualMessage = error.getMessage();
        assertTrue(actualMessage.contains("Cvu not valid"));
    }


    @Test
    public void anUserHasInvalidWallet() {
        UserException error  = assertThrows(UserException.class, () ->
            User.builder().withWallet("123456").build());

        String actualMessage = error.getMessage();

        assertTrue(actualMessage.contains("Wallet not valid"));

    }

    @Test
    public void anUserHasInvalidEmail() {
        UserException error  = assertThrows(UserException.class, () ->
            User.builder().withEmail("pepeArgyahoo.com").build());

        String actualMessage = error.getMessage();

        assertTrue(actualMessage.contains("Email not valid"));

    }

    @Test
    public void anUserGet20PointsAndIncreasesTheSuccessfulOperationsCounter() throws UserException {
        User user = anUser();
        user.completedTransaction(20);
        assertEquals(20, user.getTransactionsPoints());
        assertEquals(1, user.getSuccessfulOperations());
    }

    @Test
    public void anUserGetnegative20PointsWhenCancelAnOperation() throws UserException {
        User user = anUser();
        user.cancelledTransaction();
        assertEquals(-20, user.getTransactionsPoints());
    }


}
