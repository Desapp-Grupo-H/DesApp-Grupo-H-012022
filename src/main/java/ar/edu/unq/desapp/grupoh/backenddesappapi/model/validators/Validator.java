package ar.edu.unq.desapp.grupoh.backenddesappapi.model.validators;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;

import java.util.regex.Pattern;

public class Validator {
    public static void patternMatches(String emailAddress) throws UserException {
        if (!(Pattern.compile("^(.+)@(\\S+)$").matcher(emailAddress).matches())){
            throw new UserException("Email not valid");
        }
    }

    public static void nameMatches(String name) throws UserException{
        if (!(Pattern.compile("^.{3,30}").matcher(name).matches())){
            throw new UserException("Name or Lastname not valid");
        }
    }

    public static void adressMatches(String adress) throws UserException{
        if (!(Pattern.compile("^.{10,30}").matcher(adress).matches())){
            throw new UserException("Adress not valid");
        }
    }

    public static void walletMatches(Long wallet) throws UserException{
        if (!(wallet.toString().length() == 8)){
            throw new UserException("Wallet not valid");
        }
    }

}
