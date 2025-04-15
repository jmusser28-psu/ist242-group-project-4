package util;

import database.DatabaseConnector;

import java.sql.Connection;

public class PrintMenu {

    private byte userChoice;
    public PrintMenu() {
        InputValidation validate = new InputValidation();
        DatabaseConnector dbc = new DatabaseConnector();
        Connection connection = dbc.getConnection();

        boolean run = true;
        while (run) {
            System.out.println("vehicle Maintenance Management System");
            System.out.println("0.) Exit Program");
            System.out.println("1.) View vehicle summaries");
            System.out.println("2.) Update Vehicle");
            System.out.println("3.) Remove Vehicle");
            System.out.println("4.) Display Average Maintenance Cost");
            System.out.print("What would you like to do (1-4): ");
            userChoice = validate.validateByte();

            if (userChoice == 0) {
                run = false;
            }
            else if (userChoice == 1) {

            }
            else if (userChoice == 2) {

            }
            else if (userChoice == 3) {

            }
            else if (userChoice == 4) {

            }
            else {
                System.out.printf("Invalid choice %d\n\n", userChoice);
            }
        }
    }

}
