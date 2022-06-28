package ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions;

public class TransactionException extends Exception implements IModelException{
    public TransactionException(String message) {super(message);}
}
