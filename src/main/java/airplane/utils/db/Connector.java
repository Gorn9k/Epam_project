package airplane.utils.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static java.sql.DriverManager.getConnection;
import static java.util.ResourceBundle.getBundle;

public class Connector {
    private static final ResourceBundle RESOURCE_BUNDLE;
    private static final String BUNDLE_NAME = "db_config";
    private static final String DRIVER;
    private static final String URL;
    private static final String USER;
    private static final String PASS;

    static {
        RESOURCE_BUNDLE = getBundle(BUNDLE_NAME);
        DRIVER = RESOURCE_BUNDLE.getString("DATABASE_DRIVER_NAME");
        URL = RESOURCE_BUNDLE.getString("DATABASE_URL");
        USER = RESOURCE_BUNDLE.getString("DB_USER");
        PASS = RESOURCE_BUNDLE.getString("DB_PASS");
    }

    public static void init() throws ConnectorException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException c) {
            throw new ConnectorException("\nDriver for DBMS not found");
        }
    }

    public static Connection getConnecting() throws ConnectorException {
        try {
            return getConnection(URL, USER, PASS);
        } catch (SQLException s) {
            throw new ConnectorException("\nCheck your database connection");
        }
    }
}
