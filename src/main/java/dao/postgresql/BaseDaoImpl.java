package dao.postgresql;

import java.sql.Connection;

abstract public class BaseDaoImpl {
    private Connection connection;

    public BaseDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
