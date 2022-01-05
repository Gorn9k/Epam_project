package airplane.dao;

import airplane.entity.Entity;
import airplane.utils.db.StatementSetter;
import java.sql.*;
import java.util.List;

public interface Dao<T extends Entity> {
    T read(Long id) throws DaoException;

    void save(List<T> entities) throws DaoException;

    void update(T entity) throws DaoException;

    void delete(Long id) throws DaoException;

    List<T> readAll() throws DaoException;

    Long getMaxId() throws DaoException;

    default void create(String sql, List<T> entities, Connection connection, StatementSetter<T> statementSetter) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (T entity : entities) {
                statementSetter.setStatement(statement, entity);
                statement.addBatch();
            }
            statement.clearParameters();
            statement.executeBatch();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    default void update(String sql, T entity, Connection connection, StatementSetter<T> statementSetter) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statementSetter.setStatement(statement, entity);
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        }
    }

    default void delete(String sql, Connection connection) throws DaoException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch(SQLException e) {
            throw new DaoException(e);
        }
    }

    default Long getMaxId(String sql, Connection connection) throws DaoException {
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getLong(1) + 1;
            } else {
                throw new DaoException();
            }
        } catch(SQLException e) {
            throw new DaoException(e);
        }
    }
}
