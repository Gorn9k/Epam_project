package utils.db;

import entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityCreator<T extends Entity> {
    T createEntity(ResultSet resultSet) throws SQLException;
}
