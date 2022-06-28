package ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions;

public class OperationException extends Exception implements IModelException{
    public OperationException(String message) {super(message);}
}
