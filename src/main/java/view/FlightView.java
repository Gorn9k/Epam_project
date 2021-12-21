package view;

import entity.Brigade;
import entity.Flight;
import entity.PersonType;
import service.ServiceException;
import service.logic.FlightServiceImpl;
import java.sql.SQLException;
import java.util.*;

public class FlightView {
    private Scanner scanner;
    private FlightServiceImpl flightService;

    public FlightView() throws SQLException {
        scanner = new Scanner(System.in);
        flightService = new FlightServiceImpl();
    }


    private void showAllFlights() {
        try {
            List<Flight> flights = flightService.findAll();
            if (!flights.isEmpty()) {
                System.out.println("\nList of flights:\n");
                flights.forEach(System.out::println);
            } else {
                System.out.println("\nNo flight found in the database!");
            }
        } catch (ServiceException serviceException) {
            System.out.println(serviceException.getMessage());
        }
    }

    private void showFlightById() {
        System.out.println("\nEnter the id of the flight you want to find:");
        try {
            Flight flight = flightService.findById(scanner.nextLong());
            if (flight != null) {
                System.out.println("\n" + flight);
            } else {
                System.out.println("\nNo flight with this id found in the database!");
            }
        } catch (ServiceException serviceException) {
            System.out.println(serviceException.getMessage());
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("\nIncorrectly entered Id.");
            scanner.nextLine();
        }
    }

    private void createFlight() {
        try {
            Flight flight = new Flight();
            System.out.println("\nEnter flight's name:");
            flight.setFlightName(scanner.next());
            System.out.println("Do you want to immediately add a crew to your flight? Enter yes or no:");
            switch (scanner.next()) {
                case "yes":
                    System.out.println("Enter the brigade id:");
                    Brigade brigade = new Brigade();
                    brigade.setId(scanner.nextLong());
                    flight.setBrigade(brigade);
                    break;
                case "no":
                    break;
                default:
                    System.out.println("Incorrect input.");
                    return;
            }
            List<Flight> flightList = new ArrayList<Flight>(){{add(flight);}};
            flightService.create(flightList);
            System.out.println("\nSaving to the database was successful!");
        } catch (ServiceException serviceException) {
            System.out.println("\n" + serviceException.getMessage());
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("\nIncorrectly entered Id.");
            scanner.nextLine();
        }
    }

    private void editFlight() {
        try {
            System.out.println("\nEnter the id of the person you want to change:");
            Flight flight = flightService.findById(scanner.nextLong());
            if (flight != null) {
                System.out.println("\nSelect an option for the operation (enter a number):\n" +
                        "1. Complete change of data about a person\n" +
                        "2. Partial change of data about a person\n" +
                        "3. Abort the change operation");
                switch (scanner.nextInt()) {
                    case 1:
                        System.out.println("\nEnter flight's name:");
                        flight.setFlightName(scanner.next());
                        System.out.println("Enter the brigade id:");
                        Brigade brigade = new Brigade();
                        brigade.setId(scanner.nextLong());
                        flight.setBrigade(brigade);
                        break;
                    case 2:
                        System.out.println("\nSelect an option for the operation (enter a number):\n" +
                                "1. Change of flight's name\n" +
                                "2. Change brigade\n" +
                                "3. Abort the change operation");
                        switch (scanner.nextInt()) {
                            case 1:
                                System.out.println("\nEnter flight's name:");
                                flight.setFlightName(scanner.next());
                                break;
                            case 2:
                                System.out.println("Enter the brigade id:");
                                Brigade brigade1 = new Brigade();
                                brigade1.setId(scanner.nextLong());
                                flight.setBrigade(brigade1);
                                break;
                            case 3:
                                return;
                            default:
                                System.out.println("\nInvalid number! Enter a number between 1 and 3.");
                        }
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("\nInvalid number! Enter a number between 1 and 3.");
                }
                flightService.edit(flight);
                System.out.println("\nThe flight change was successful!");
            } else {
                System.out.println("\nNo flight with this id found in the database!");
            }
        } catch (ServiceException serviceException) {
            System.out.println("\n" + serviceException.getMessage());
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("\nIncorrectly entered Id.");
            scanner.nextLine();
        }
    }

    private void deleteFlight() {
        System.out.println("\nEnter the id of the flight you want to remove:");
        try {
            flightService.delete(scanner.nextLong());
            System.out.println("\nRemoval was successful!");
        } catch (ServiceException serviceException) {
            System.out.println(serviceException.getMessage());
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("\nIncorrectly entered Id.");
            scanner.nextLine();
        }
    }

    public void run() {
        boolean proceed = true;
        while (proceed) {
            System.out.println("\nSelect the action you want to take:\n" +
                    "1. Display flight list\n" +
                    "2. Display flight by id\n" +
                    "3. Create flight\n" +
                    "4. Edit flight\n" +
                    "5. Delete flight\n" +
                    "6. Return to main menu");
            try {
                switch (scanner.nextInt()) {
                    case 1:
                        showAllFlights();
                        break;
                    case 2:
                        showFlightById();
                        break;
                    case 3:
                        createFlight();
                        break;
                    case 4:
                        editFlight();
                        break;
                    case 5:
                        deleteFlight();
                        break;
                    case 6:
                        proceed = false;
                        break;
                    default:
                        System.out.println("\nInvalid number! Enter a number between 1 and 6.");
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
