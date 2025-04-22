package util;

import database.DatabaseConnector;
import vehicles.VehicleManager;

import java.sql.Connection;

public class PrintMenu {

    private byte userChoice;
    public PrintMenu() {
        InputValidation validate = new InputValidation();
        DatabaseConnector dbc = new DatabaseConnector();
        Connection connection = dbc.getConnection();
        VehicleManager vm = new VehicleManager(connection);

        boolean run = true;
        while (run) {
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

            if (userChoice == 0) {
                run = false;
            }
            else if (userChoice == 1) {
                vm.printVehicleSummaries();
            }
            else if (userChoice == 2) {
                vm.updateVehicle();
            }
            else if (userChoice == 3) {
                vm.addVehicle();
            }
            else if (userChoice == 4) {
                vm.deleteVehicle();
            }
            else if (userChoice == 5) {
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
