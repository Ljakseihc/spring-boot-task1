package epam.springboot.advanced.demo.models.exceptions;

public class ConflictWithExistingResourceException extends RuntimeException {
    public ConflictWithExistingResourceException(String message) {
        super(message);
    }
}