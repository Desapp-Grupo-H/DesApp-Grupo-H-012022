package ar.edu.unq.desapp.grupoh.backenddesappapi.model.validators;

import java.util.regex.Pattern;

public class EmailValidator {
    public static boolean patternMatches(String emailAddress) {
        return Pattern.compile("^(.+)@(\\S+)$")
                .matcher(emailAddress)
                .matches();
    }
}
