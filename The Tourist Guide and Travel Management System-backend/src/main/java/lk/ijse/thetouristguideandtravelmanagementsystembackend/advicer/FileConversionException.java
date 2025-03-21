package lk.ijse.thetouristguideandtravelmanagementsystembackend.advicer;

/**
 * Exception thrown when file conversion operations fail in the Tourist Guide and Travel Management System.
 */
public class FileConversionException extends RuntimeException {

    /**
     * Constructs a new FileConversionException with the specified detail message.
     *
     * @param message The detail message
     */
    public FileConversionException(String message) {
        super(message);
    }

    /**
     * Constructs a new FileConversionException with the specified detail message and cause.
     *
     * @param message The detail message
     * @param cause The cause of the exception
     */
    public FileConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}