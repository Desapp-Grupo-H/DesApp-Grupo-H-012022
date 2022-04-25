package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.validators.EmailValidator;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTests {

    public User anUser() throws UserException {
        return User.builder().withName("Pepe").withLastname("Argento").withEmail("pepeArg@yahoo.com").build();
    }


    @Test
    public void anUserHasNameLastNameAndEmail() throws UserException{
        User user = anUser();

        assertEquals("Pepe", user.getName());
        assertEquals("Argento", user.getLastname());
        assertEquals("pepeArg@yahoo.com", user.getEmail());
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
