package ar.edu.unq.desapp.grupoh.backenddesappapi.model.validators;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;

import java.util.regex.Pattern;

public class EmailValidator {
    public static void patternMatches(String emailAddress) throws UserException {
        if (!(Pattern.compile("^(.+)@(\\S+)$").matcher(emailAddress).matches())){
            throw new UserException("Email not valid");
        }
    }
}
