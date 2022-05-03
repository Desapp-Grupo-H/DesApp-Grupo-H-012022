package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTests {

    public User anUser() throws UserException {
        return User.builder().withName("Pepe").withLastname("Argento").withEmail("pepeArg@yahoo.com").withCvu("1312313123131231312322").withWallet("12345678").withAdress("1234567891").withPassword("123123").build();
    }


    @Test
    public void anUserHasNameLastNameAndEmail() throws UserException{
        User user = anUser();

        assertEquals("Pepe", user.getName());
        assertEquals("Argento", user.getLastname());
        assertEquals("pepeArg@yahoo.com", user.getEmail());
    }

    @Test
    public void anUserHasInvalidName() {
        UserException error  = assertThrows(UserException.class, () -> {
            User.builder().withName("Pe").withLastname("Argento").withEmail("pepeArg@yahoo.com").build();
        });

        String actualMessage = error.getMessage();

        assertTrue(actualMessage.contains("Name or Lastname not valid"));

    }

    @Test
    public void anUserHasInvalidLastname() {
        UserException error  = assertThrows(UserException.class, () -> {
            User.builder().withName("Pepe").withLastname("Ar").withEmail("pepeArg@yahoo.com").build();
        });

        String actualMessage = error.getMessage();

        assertTrue(actualMessage.contains("Name or Lastname not valid"));

    }

    @Test
    public void anUserHasInvalidCvu() {
        UserException error  = assertThrows(UserException.class, () -> {
            User.builder().withName("Pepe").withLastname("Argento").withEmail("pepeArg@yahoo.com").withCvu("1312313123131231312322").withWallet("12345678").withAdress("1234567891").withPassword("123123").withCvu("1312131231312322").build();
        });

        String actualMessage = error.getMessage();

        assertTrue(actualMessage.contains("Cvu not valid"));

    }


    @Test
    public void anUserHasInvalidWallet() {
        UserException error  = assertThrows(UserException.class, () -> {
            User.builder().withName("Pepe").withLastname("Argento").withEmail("pepeArg@yahoo.com").withWallet("123456").build();
        });

        String actualMessage = error.getMessage();

        assertTrue(actualMessage.contains("Wallet not valid"));

    }

    @Test
    public void anUserHasInvalidEmail() {
        UserException error  = assertThrows(UserException.class, () -> {
            User.builder().withName("Pepe").withLastname("Argento").withEmail("pepeArgyahoo.com").build();
        });

        String actualMessage = error.getMessage();

        assertTrue(actualMessage.contains("Email not valid"));

    }


}
