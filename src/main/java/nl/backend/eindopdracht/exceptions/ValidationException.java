package nl.backend.eindopdracht.exceptions;

public class ValidationException extends RuntimeException{
    public ValidationException() {
        super();
    }


    public ValidationException(String message) {
        super(message);
    }

}