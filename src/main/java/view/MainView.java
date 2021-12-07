package view;

import java.util.Scanner;

public class MainView {

    public void runMainView() {
        Scanner scanner = new Scanner(System.in);
        boolean proceed = true;
        System.out.println("Hello!");
        while (proceed) {
            System.out.println("\nSelect an option for the operation (enter a number):\n" +
                    "1. Database management\n" +
                    "2. Personnel management\n" +
                    "3. Brigades management\n" +
                    "4. Flights management\n" +
                    "5. Quit the application");
            switch (scanner.nextInt()) {
                case 1:
                    new UtilView().run();
                    break;
                case 2:
                    new PersonViеw().run();
                    break;
                case 3:
                    new BrigadeView().run();
                    break;
                case 4:
                    new FlightView().run();
                    break;
                case 5:
                    proceed = false;
                    break;
                default:
                    System.out.println("\nInvalid number! Enter a number between 1 and 5.");
            }
        }
    }
}