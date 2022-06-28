package ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions;

public class UserException extends Exception implements IModelException{
    public UserException(String message) {super(message);}
}
