public class DoorControlException extends Exception {
    public DoorControlException(String message) {
        super(message);
    }

    public DoorControlException(String message, Throwable cause) {
        super(message, cause);
    }
}