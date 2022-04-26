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

    public static void walletMatches(String wallet) throws UserException{
        if (!(Pattern.compile("[0-9]{8}").matcher(wallet).matches())){
            throw new UserException("Wallet not valid");
        }
    }

    public static void cvuMatches(String cvu) throws UserException{
        if (!(Pattern.compile("[0-9]{22}").matcher(cvu).matches())){
            throw new UserException("Cvu not valid");
        }
    }

}
