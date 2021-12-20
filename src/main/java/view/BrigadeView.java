package view;

import entity.Person;
import entity.PersonType;
import service.ServiceException;
import service.logic.PersonServiceImpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BrigadeView {
    Scanner scanner = new Scanner(System.in);
/*
    public void showAllPersons() {
        try {
            List<Person> persons = new PersonServiceImpl().readAll();
            if (!persons.isEmpty()) {
                System.out.println("\nList of persons:\n");
                persons.forEach(System.out::println);
            } else {
                System.out.println("\nNo person found in the database!");
            }
        } catch (ServiceException serviceException) {
            System.out.println(serviceException.getMessage());
        }
    }

    public void showPersonById() {
        System.out.println("\nEnter the id of the person you want to find:");
        try {
            Person person = new PersonServiceImpl().read(scanner.nextInt());
            if (person != null) {
                System.out.println("\n" + person);
            } else {
                System.out.println("\nNo person with this id found in the database!");
            }
        } catch (ServiceException serviceException) {
            System.out.println(serviceException.getMessage());
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("\nIncorrectly entered Id.");
            scanner.nextLine();
        }
    }

    public void createPerson() {
        try {
            Person person = new Person();
            System.out.println("\nEnter person's name:");
            person.setPersonName(scanner.next());
            System.out.println("Enter the position of the person:");
            person.setPersonType(PersonType.valueOf(scanner.next().toUpperCase()));
            new PersonServiceImpl().save(person);
            System.out.println("\nSaving to the database was successful!");
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("\nThere is no such position in the system.");
        } catch (ServiceException serviceException) {
            System.out.println("\n" + serviceException.getMessage());
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("\nIncorrectly entered Id.");
            scanner.nextLine();
        }
    }

    public void editPerson() {
        try {
            System.out.println("\nEnter the id of the person you want to change:");
            Person person = new PersonServiceImpl().read(scanner.nextInt());
            if (person != null) {
                System.out.println("\nSelect an option for the operation (enter a number):\n" +
                        "1. Complete change of data about a person\n" +
                        "2. Partial change of data about a person\n" +
                        "3. Abort the change operation");
                switch (scanner.nextInt()) {
                    case 1:
                        System.out.println("\nEnter person's name:");
                        person.setPersonName(scanner.next());
                        System.out.println("Enter the position of the person:");
                        person.setPersonType(PersonType.valueOf(scanner.next().toUpperCase()));
                        System.out.println("Enter the person's employment (yes or no):");
                        String isFree1 = scanner.next();
                        if (isFree1.equals("yes")) {
                            person.setFree(false);
                        } else if (isFree1.equals("no")) {
                            person.setFree(true);
                        } else {
                            System.out.println("\nThe value of the person's employment has been entered incorrectly! Canceling the change to the object.");
                            return;
                        }
                        break;
                    case 2:
                        System.out.println("\nSelect an option for the operation (enter a number):\n" +
                                "1. Change of person's name\n" +
                                "2. Changing the position of a person\n" +
                                "3. Person's employment change\n" +
                                "4. Abort the change operation");
                        switch (scanner.nextInt()) {
                            case 1:
                                System.out.println("\nEnter person's name:");
                                person.setPersonName(scanner.next());
                                break;
                            case 2:
                                System.out.println("Enter the position of the person:");
                                person.setPersonType(PersonType.valueOf(scanner.next().toUpperCase()));
                                break;
                            case 3:
                                System.out.println("Enter the person's employment (yes or no):");
                                String isFree2 = scanner.next();
                                if (isFree2.equals("yes")) {
                                    person.setFree(false);
                                } else if (isFree2.equals("no")) {
                                    person.setFree(true);
                                } else {
                                    System.out.println("\nThe value of the person's employment has been entered incorrectly! Canceling the change to the object.");
                                    return;
                                }
                                break;
                            case 4:
                                return;
                            default:
                                System.out.println("\nInvalid number! Enter a number between 1 and 4.");
                        }
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("\nInvalid number! Enter a number between 1 and 3.");
                }
                new PersonServiceImpl().edit(person);
                System.out.println("\nThe persona change was successful!");
            } else {
                System.out.println("\nNo person with this id found in the database!");
            }
        } catch (ServiceException serviceException) {
            System.out.println("\n" + serviceException.getMessage());
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("\nIncorrectly entered Id.");
            scanner.nextLine();
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("\nThere is no such position in the system.");
        }
    }

    public void deletePerson() {
        System.out.println("\nEnter the id of the person you want to remove:");
        try {
            new PersonServiceImpl().delete(scanner.nextInt());
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
                    "1. Display personnel list\n" +
                    "2. Display person by id\n" +
                    "3. Create person\n" +
                    "4. Edit person\n" +
                    "5. Delete person\n" +
                    "6. Return to main menu");
            try {
                switch (scanner.nextInt()) {
                    case 1:
                        showAllPersons();
                        break;
                    case 2:
                        showPersonById();
                        break;
                    case 3:
                        createPerson();
                        break;
                    case 4:
                        editPerson();
                        break;
                    case 5:
                        deletePerson();
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
    }*/
}
