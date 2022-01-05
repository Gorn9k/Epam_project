package airplane;

import airplane.utils.db.Connector;
import airplane.utils.db.ConnectorException;
import airplane.view.MainView;

public class AirplaneSystem {

    public static void main(String[] args) {
        try {
            Connector.init();
            new MainView().runMainView();
        } catch (ConnectorException e) {
            System.out.println(e.getMessage());
        }
    }
}
