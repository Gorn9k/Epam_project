package view;

import entity.Brigade;
import entity.Flight;
import entity.Person;
import entity.PersonType;
import service.ServiceException;
import service.logic.BrigadeServiceImpl;
import service.logic.PersonServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BrigadeView {
    private Scanner scanner;
    private BrigadeServiceImpl brigadeService;
    private PersonServiceImpl personService;

    public BrigadeView() throws SQLException {
        scanner = new Scanner(System.in);
        brigadeService = new BrigadeServiceImpl();
        personService = new PersonServiceImpl();
    }

    private void showAllBrigades() {
        try {
            List<Brigade> brigades = brigadeService.findAll();
            if (!brigades.isEmpty()) {
                System.out.println("\nList of brigades:\n");
                brigades.forEach(System.out::println);
            } else {
                System.out.println("\nNo brigade found in the database!");
            }
        } catch (ServiceException serviceException) {
            System.out.println(serviceException.getMessage());
        }
    }

    private void showBrigadeById() {
        System.out.println("\nEnter the id of the brigade you want to find:");
        try {
            Brigade brigade = brigadeService.findById(scanner.nextLong());
            if (brigade != null) {
                System.out.println("\n" + brigade);
            } else {
                System.out.println("\nNo brigade with this id found in the database!");
            }
        } catch (ServiceException serviceException) {
            System.out.println(serviceException.getMessage());
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("\nIncorrectly entered Id.");
            scanner.nextLine();
        }
    }

    private void createBrigade() {
        try {
            Brigade brigade = new Brigade();
            List<Person> persons = new ArrayList<>();
            System.out.println("\nEnter the number of people in the brigade (3-5):");
            switch (scanner.nextInt()) {
                case 3:
                    System.out.println("Enter pilot id:");
                    persons.add(personService.findById(scanner.nextLong()));
                    System.out.println("Enter navigator id:");
                    persons.add(personService.findById(scanner.nextLong()));
                    System.out.println("Enter radioman id:");
                    persons.add(personService.findById(scanner.nextLong()));
                    break;
                case 4:
                    System.out.println("Enter pilot id:");
                    persons.add(personService.findById(scanner.nextLong()));
                    System.out.println("Enter navigator id:");
                    persons.add(personService.findById(scanner.nextLong()));
                    System.out.println("Enter radioman id:");
                    persons.add(personService.findById(scanner.nextLong()));
                    System.out.println("Enter first steward id:");
                    persons.add(personService.findById(scanner.nextLong()));
                    break;
                case 5:
                    System.out.println("Enter pilot id:");
                    persons.add(personService.findById(scanner.nextLong()));
                    System.out.println("Enter navigator id:");
                    persons.add(personService.findById(scanner.nextLong()));
                    System.out.println("Enter radioman id:");
                    persons.add(personService.findById(scanner.nextLong()));
                    System.out.println("Enter first steward id:");
                    persons.add(personService.findById(scanner.nextLong()));
                    System.out.println("Enter second steward id:");
                    persons.add(personService.findById(scanner.nextLong()));
                    break;
                default:
                    System.out.println("\nInvalid number! Enter a number between 3 and 5.");
                    return;
            }
            brigade.setPersons(persons);
            List<Brigade> brigadeList = new ArrayList<Brigade>(){{add(brigade);}};
            brigadeService.create(brigadeList);
        } catch (ServiceException serviceException) {
            System.out.println("\n" + serviceException.getMessage());
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("\nIncorrectly entered Id.");
            scanner.nextLine();
        }
    }

    private void editBrigade() {
        try {
            System.out.println("\nEnter the id of the brigade you want to change:");
            Brigade brigade = brigadeService.findById(scanner.nextLong());
            if (brigade != null) {
                System.out.println("\nSelect an option for the operation (enter a number):\n" +
                        "1. Complete change of data about a person\n" +
                        "2. Partial change of data about a person\n" +
                        "3. Abort the change operation");
                switch (scanner.nextInt()) {
                    case 1:
                        List<Person> persons = new ArrayList<>();
                        switch (scanner.nextInt()) {
                            case 3:
                                System.out.println("Enter pilot id:");
                                persons.add(personService.findById(scanner.nextLong()));
                                System.out.println("Enter navigator id:");
                                persons.add(personService.findById(scanner.nextLong()));
                                System.out.println("Enter radioman id:");
                                persons.add(personService.findById(scanner.nextLong()));
                                break;
                            case 4:
                                System.out.println("Enter pilot id:");
                                persons.add(personService.findById(scanner.nextLong()));
                                System.out.println("Enter navigator id:");
                                persons.add(personService.findById(scanner.nextLong()));
                                System.out.println("Enter radioman id:");
                                persons.add(personService.findById(scanner.nextLong()));
                                System.out.println("Enter first steward id:");
                                persons.add(personService.findById(scanner.nextLong()));
                                break;
                            case 5:
                                System.out.println("Enter pilot id:");
                                persons.add(personService.findById(scanner.nextLong()));
                                System.out.println("Enter navigator id:");
                                persons.add(personService.findById(scanner.nextLong()));
                                System.out.println("Enter radioman id:");
                                persons.add(personService.findById(scanner.nextLong()));
                                System.out.println("Enter first steward id:");
                                persons.add(personService.findById(scanner.nextLong()));
                                System.out.println("Enter second steward id:");
                                persons.add(personService.findById(scanner.nextLong()));
                                break;
                            default:
                                System.out.println("\nInvalid number! Enter a number between 3 and 5.");
                                return;
                        }
                        brigade.setPersons(persons);
                        break;
                    case 2:
                        System.out.println("\nSelect an option for the operation (enter a number):\n" +
                                "1. Change pilot\n" +
                                "2. Change navigator\n" +
                                "3. Change radioman\n" +
                                "4. Change first steward\n" +
                                "5. Change second steward\n" +
                                "6. Abort the change operation");
                        switch (scanner.nextInt()) {
                            case 1:
                                System.out.println("Enter pilot id:");
                                brigade.getPersons().set(0, personService.findById(scanner.nextLong()));
                                break;
                            case 2:
                                System.out.println("Enter navigator id:");
                                brigade.getPersons().set(1, personService.findById(scanner.nextLong()));
                                break;
                            case 3:
                                System.out.println("Enter radioman id:");
                                brigade.getPersons().set(2, personService.findById(scanner.nextLong()));
                                break;
                            case 4:
                                System.out.println("Enter first steward id:");
                                brigade.getPersons().set(3, personService.findById(scanner.nextLong()));
                                break;
                            case 5:
                                System.out.println("Enter second steward id:");
                                brigade.getPersons().set(4, personService.findById(scanner.nextLong()));
                                break;
                            case 6:
                                return;
                            default:
                                System.out.println("\nInvalid number! Enter a number between 1 and 6.");
                        }
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("\nInvalid number! Enter a number between 1 and 3.");
                }
                brigadeService.edit(brigade);
                System.out.println("\nThe brigade change was successful!");
            } else {
                System.out.println("\nNo brigade with this id found in the database!");
            }
        } catch (ServiceException serviceException) {
            System.out.println("\n" + serviceException.getMessage());
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("\nIncorrectly entered Id.");
            scanner.nextLine();
        }
    }

    private void deleteBrigade() {
        System.out.println("\nEnter the id of the brigade you want to remove:");
        try {
            brigadeService.delete(scanner.nextLong());
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
                    "1. Display brigades list\n" +
                    "2. Display brigade by id\n" +
                    "3. Create brigade\n" +
                    "4. Edit brigade\n" +
                    "5. Delete brigade\n" +
                    "6. Return to main menu");
            try {
                switch (scanner.nextInt()) {
                    case 1:
                        showAllBrigades();
                        break;
                    case 2:
                        showBrigadeById();
                        break;
                    case 3:
                        createBrigade();
                        break;
                    case 4:
                        editBrigade();
                        break;
                    case 5:
                        deleteBrigade();
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
