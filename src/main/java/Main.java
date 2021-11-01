import entity.Person;
import entity.PersonType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connector.init("org.postgresql.Driver", "jdbc:postgresql://localhost/airplane",
                "postgres", "635756");
        Connection c = Connector.getConnection();
        Statement statement = c.createStatement();
        statement.execute("INSERT INTO Flights (FlightName, Date_comp,  Travel_time) " +
                "Values('3rdfdfd', 'rgfgrgrgrgrgd', 'gfgfdhthdbngnnttr')");
        System.out.println("done");
        ResultSet resultSet = statement.executeQuery("select * from flights;");
        while(resultSet.next()){
            System.out.println(resultSet.getString(1)+"\t"+resultSet.getString(2)
                    +"\t"+resultSet.getString(3) +"\t"+resultSet.getString(4));
        }
        statement.execute("update flights set FlightName = '12345678' where FlightName = '12' and \n" +
                "id = '382ff7be-3901-11ec-903b-145afc00fdab'; ");
        resultSet = statement.executeQuery("select * from flights;");
        System.out.println("done");
        while(resultSet.next()){
            System.out.println(resultSet.getString(1)+"\t"+resultSet.getString(2)
                    +"\t"+resultSet.getString(3) +"\t"+resultSet.getString(4));
        }
        statement.execute("delete from flights where id = '382ff7be-3901-11ec-903b-145afc00fdab' ");
        System.out.println("done");
        resultSet = statement.executeQuery("select * from flights;");
        while(resultSet.next()){
            System.out.println(resultSet.getString(1)+"\t"+resultSet.getString(2)
                    +"\t"+resultSet.getString(3) +"\t"+resultSet.getString(4));
        }
        c.close();
        statement.close();
        resultSet.close();
    }
}
