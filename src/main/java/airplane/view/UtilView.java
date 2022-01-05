package airplane.view;

import airplane.dao.DaoException;
import airplane.utils.db.ConnectorException;
import airplane.utils.json.read.JsonReader;
import airplane.utils.json.write.BrigadeWriter;
import airplane.utils.json.write.FlightWriter;
import airplane.utils.json.write.JsonWriter;
import airplane.utils.json.write.PersonWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UtilView {
    private Scanner scanner;
    private JsonWriter personWriter;
    private JsonWriter flightWriter;
    private JsonWriter brigadeWriter;
    private JsonReader jsonReader;

    public UtilView() throws ConnectorException {
        scanner = new Scanner(System.in);
        personWriter = new PersonWriter();
        flightWriter = new FlightWriter();
        brigadeWriter = new BrigadeWriter();
        jsonReader = new JsonReader();
    }

    private void importToDB() {
        System.out.println("\nEnter the path to the input file:");
        try {
            jsonReader.importToDBFromJson(scanner.next(), "persons");
        } catch (DaoException e) {
            System.out.println("\nError while writing to database!");
        } catch (IOException e) {
            System.out.println("\nCould not find input file!");
        } catch (ConnectorException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Import of data into the database was successful!");
    }

    private void exportToJson() {
        System.out.println("\nEnter the name of the output file for persons:");
        String personsOutputFile = scanner.next();
        System.out.println("Enter the name of the output file for flights:");
        String flightsOutputFile = scanner.next();
        System.out.println("Enter the name of the output file for brigades:");
        String brigadesOutputFile = scanner.next();
        try {
            personWriter.exportToJsonFromDB(personsOutputFile);
            flightWriter.exportToJsonFromDB(flightsOutputFile);
            brigadeWriter.exportToJsonFromDB(brigadesOutputFile);
        } catch (DaoException e) {
            System.out.println("\nFailed to export data from database to json!");
        } catch (IOException e) {
            System.out.println("Could not create an output file at the specified path!");
        }
        System.out.println("Exporting data from database to json file was successful!");
    }


    public void run() {
        boolean proceed = true;
        while (proceed) {
            System.out.println("\nSelect the action you want to take:\n" +
                    "1. Go to import to DB\n" +
                    "2. Go to export to Json\n" +
                    "3. Return to main menu");
            try {
                switch (scanner.nextInt()) {
                    case 1:
                        importToDB();
                        break;
                    case 2:
                        exportToJson();
                        break;
                    case 3:
                        proceed = false;
                        break;
                    default:
                        System.out.println("\nInvalid number! Enter a number between 1 and 3.");
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("\nError! You must enter a number.");
                scanner.nextLine();
            }
            if (proceed) {
                System.out.println("\nDo you want to continue?\n1. Yes\n2. No");
                try {
                    switch (scanner.nextInt()) {
                        case 1:
                            break;
                        case 2:
                            proceed = false;
                            break;
                        default:
                            System.out.println("\nInvalid number! Enter a number between 1 and 2.");
                    }
                } catch (InputMismatchException inputMismatchException) {
                    System.out.println("\nError! You must enter a number.");
                    scanner.nextLine();
                    return;
                }
            }
        }
    }
}
