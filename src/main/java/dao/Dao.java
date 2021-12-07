package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static utils.db.Connector.getConnection;

public interface Dao<T> {
    T read(Integer id) throws DaoException;

    void create(T entity) throws DaoException;

    void update(T entity) throws DaoException;

    void delete(Integer id) throws DaoException;

    List<T> readAll() throws DaoException;

    default Integer getMaxIdFromTable(String tableName) throws DaoException {
        StringBuilder sql = new StringBuilder("select max(id) from ");
        try (PreparedStatement statement = getConnection().prepareStatement(String.valueOf(sql.append(tableName)));
             ResultSet resultSet = statement.executeQuery()){
            int id = 0;
            if(resultSet.next()) {
                id = resultSet.getInt(1);
            }
            return ++id;
        } catch(SQLException e) {
            throw new DaoException(e);
        }
    }
}
