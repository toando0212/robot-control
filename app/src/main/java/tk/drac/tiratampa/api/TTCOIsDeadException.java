package tk.drac.tiratampa.api;

public class TTCOIsDeadException extends Exception {
    public TTCOIsDeadException() {
        super();
    }

    public TTCOIsDeadException(String message) {
        super(message);
    }

    public TTCOIsDeadException(String message, Throwable cause) {
        super(message, cause);
    }

    public TTCOIsDeadException(Throwable cause) {
        super(cause);
    }
}
