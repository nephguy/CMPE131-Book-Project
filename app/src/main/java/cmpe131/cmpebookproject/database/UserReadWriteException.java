package cmpe131.cmpebookproject.database;

public class UserReadWriteException extends RuntimeException {

    public UserReadWriteException (String message) {
        super(message);
    }
}
