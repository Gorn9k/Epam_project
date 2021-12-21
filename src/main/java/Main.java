import dao.DaoException;
import service.ServiceException;
import utils.db.Connector;
import utils.json.read.JsonReader;
import utils.json.write.BrigadeWriter;
import utils.json.write.FlightWriter;
import utils.json.write.JsonWriter;
import utils.json.write.PersonWriter;
import view.MainView;

import java.sql.SQLException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        Connector.init("org.postgresql.Driver", "jdbc:postgresql://localhost/airplane",
                "postgres", "635756");
        try {
            new MainView().runMainView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
