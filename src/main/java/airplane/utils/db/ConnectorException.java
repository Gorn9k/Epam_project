package airplane.utils.db;

public class ConnectorException extends Exception {
    public ConnectorException() {}

    public ConnectorException(String message) {
        super(message);
    }

    public ConnectorException(Throwable cause) {
        super(cause);
    }
}