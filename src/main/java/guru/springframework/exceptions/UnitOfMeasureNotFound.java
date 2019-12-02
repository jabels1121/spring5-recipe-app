package guru.springframework.exceptions;

public class UnitOfMeasureNotFound extends ApplicationException {

    public UnitOfMeasureNotFound(String message) {
        super(message);
    }

    public UnitOfMeasureNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
