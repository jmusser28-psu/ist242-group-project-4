package util;

import database.DatabaseConnector;
import vehicles.VehicleManager;

import java.sql.Connection;

/**
 * Handles the command-line menu for the Vehicle Maintenance Management System.
 * Connects to the database and routes user input to the appropriate VehicleManager methods.
 */
public class PrintMenu {

    private byte userChoice;

    /**
     * Constructor launches the interactive menu loop.
     */
    public PrintMenu() {
        InputValidation validate = new InputValidation();
        DatabaseConnector dbc = new DatabaseConnector();
        Connection connection = dbc.getConnection();
        VehicleManager vm = new VehicleManager(connection);

        boolean run = true;
        while (run) {
            // Display main menu options
            System.out.println("---IST Vehicle Care Solutions---");
            System.out.println("Vehicle Maintenance Management System");
            System.out.println("0.) Exit Program");
            System.out.println("1.) View vehicle list & summaries");
            System.out.println("2.) Update Vehicle");
            System.out.println("3.) Add Vehicle");
            System.out.println("4.) Remove Vehicle");
            System.out.println("5.) Display Average Maintenance Cost");
            System.out.print("What would you like to do (0-5): ");
            userChoice = validate.validateByte();

            //Handle user choices

            if (userChoice == 0) {
                System.out.println("Exiting...");
                run = false; // Exit program
            }
            else if (userChoice == 1) {
                vm.printVehicleSummaries();
            }
            else if (userChoice == 2) {
                // Prompt for vehicle type to update
                boolean valid = false;
                System.out.print("What would you like to update (Car/Motorcycle/Truck): ");
                String typeToUpdate = "";
                while (!valid) {
                    typeToUpdate = validate.line();
                    if (typeToUpdate.equalsIgnoreCase("Car")) {
                        valid = true;
                    }
                    else if (typeToUpdate.equalsIgnoreCase("Motorcycle")) {
                        valid = true;
                    }
                    else if (typeToUpdate.equalsIgnoreCase("Truck")) {
                        valid = true;
                    }
                    else {
                        System.out.println("Invalid input " + typeToUpdate);
                    }
                }

                // Call the correct update method based on vehicle
                if (typeToUpdate.equalsIgnoreCase("Car")) {
                    vm.updateCar();
                }
                if (typeToUpdate.equalsIgnoreCase("Motorcycle")) {
                    vm.updateMotorcycle();
                }
                if (typeToUpdate.equalsIgnoreCase("Truck")) {
                    vm.updateTruck();
                }
                // Refresh vehicle data after update
                vm = new VehicleManager(connection);

            }
            else if (userChoice == 3) {
                // Add new vehicle
                vm.addVehicle();
                vm = new VehicleManager(connection);
            }
            else if (userChoice == 4) {
                // Delete vehicle by VIN
                vm.deleteVehicle();
            }
            else if (userChoice == 5) {
                // Display average maintenance costs submenu
                boolean runMaintenance = true;
                while(runMaintenance) {
                    System.out.println("Average Maintenance Cost");
                    System.out.println("0.) Return to main menu");
                    System.out.println("1.) View average for all vehicles");
                    System.out.println("2.) View average for cars");
                    System.out.println("3.) View average for motorcycles");
                    System.out.println("4.) View average for trucks");
                    System.out.print("What would you like to do (0-4): ");
                    userChoice = validate.validateByte();

                    if (userChoice == 0) {
                        runMaintenance = false;
                    }
                    else if (userChoice == 1) {
                        System.out.printf("$%.2f\n", vm.maintenanceAverageVehicle());
                    }
                    else if (userChoice == 2) {
                        System.out.printf("$%.2f\n", vm.maintenanceAverageCar());
                    }
                    else if (userChoice == 3) {
                        System.out.printf("$%.2f\n", vm.maintenanceAverageMotorcycle());
                    }
                    else if (userChoice == 4) {
                        System.out.printf("$%.2f\n", vm.maintenanceAverageTruck());
                    }
                    else {
                        System.out.printf("Invalid choice %d\n", userChoice);
                    }
                }

            }
            else {
                System.out.printf("Invalid choice %d\n", userChoice);
            }
        }
    }

}
