package utils.db;

import entity.Entity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementSetter<T extends Entity> {
    void setStatement(PreparedStatement statement, T entity) throws SQLException;
}
