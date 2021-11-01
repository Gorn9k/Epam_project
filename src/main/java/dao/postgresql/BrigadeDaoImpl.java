package dao.postgresql;

import dao.BrigadeDao;
import dao.DaoException;
import entity.Brigade;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class BrigadeDaoImpl extends BaseDaoImpl implements BrigadeDao {
    @Override
    public List<Brigade> readAll() {
        return null;
    }

    @Override
    public Brigade read(UUID id) throws DaoException {
        String sql = "select id, dateFormation, id_firstPilot, id_secondPilot, id_navigator, id_radioman, id_firstSteward, " +
                "id_secondSteward, id_thirdSteward, id_flight from brigades where id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setString(1, String.valueOf(id));
            resultSet = statement.executeQuery();
            Brigade brigade = null;
            if(resultSet.next()) {
                brigade = new Brigade();
                brigade.setId(id);
                brigade.setDateFormation(resultSet.getString("dateFormation"));
                brigade.setPersons(resultSet.getArray(2));
            }
            return account;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    @Override
    public Long create(Brigade entity) throws DaoException {
        return null;
    }

    @Override
    public void update(Brigade entity) throws DaoException {

    }

    @Override
    public void delete(UUID id) throws DaoException {

    }
}
