package vehicles;

import database.DatabaseManager;
import util.InputValidation;

import java.sql.Connection;
import java.util.ArrayList;

public class VehicleManager {
    private final InputValidation valide = new InputValidation();
    private DatabaseManager dbmanager;
    private Connection connection;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Car> cars;
    private ArrayList<Motorcycle> motorcycles;

    private ArrayList<Truck> trucks;
    public VehicleManager(Connection connection) {
        this.connection = connection;
        dbmanager = new DatabaseManager(this.connection);

        vehicles = dbmanager.getVehicles();
        cars = dbmanager.getCars();
        motorcycles = dbmanager.getMotorcycles();
        trucks = dbmanager.getTrucks();
    }

    public void printVehicleSummaries() {
        for (int i = 0; i < cars.size(); i++) {
            cars.get(i).displayMaintenanceDetails();
        }
        for (int i = 0; i < motorcycles.size(); i++) {
            motorcycles.get(i).displayMaintenanceDetails();
        }
        for (int i = 0; i < trucks.size(); i++) {
            trucks.get(i).displayMaintenanceDetails();
        }
    }

    public void addVehicle() {
        System.out.print("Please enter a vehicle id: ");
        String vin = valide.line();

        System.out.print("Please enter a vehicle make: ");
        String make = valide.line();

        System.out.print("Please enter a vehicle model: ");
        String model = valide.line();

        System.out.print("Please enter the year produced: ");
        String year = valide.line();

        boolean run = true;
        String type = "";
        while (run) {
            System.out.print("Please enter the type of vehicle (Car/Truck/Motorcycle): ");
            type = valide.line();
            if (type.equalsIgnoreCase("Car")) {
                run = false;
            }
            else if (type.equalsIgnoreCase("Motorcycle")) {
                run = false;
            }
            else if (type.equalsIgnoreCase("Truck")) {
                run = false;
            }
            else {
                System.out.println("Please enter a valid vehicle type");
            }
        }

        System.out.print("Please enter the vehicle brand descriptor (Silverado, Corvette, etc.): ");
        String vehicle_type = valide.line();

        System.out.print("Please enter a vehicle cost estimate: ");
        String costEstimate = valide.line();

        if (type.equalsIgnoreCase("Car")) {
            System.out.print("How many doors does your car have? (numerical value): ");
            String numberOfDoors = valide.line();

            System.out.println("How expensive is it to change the car's oil? : $");
            String oilChangeCost = valide.line();

            dbmanager.addCar(vin, make, model, year, type, vehicle_type, costEstimate, numberOfDoors, oilChangeCost);

        }
        if (type.equalsIgnoreCase("Motorcycle")) {
            System.out.println("What is the condition of your chain? ");
            System.out.println("1.) Poor");
            System.out.println("2.) Fair");
            System.out.println("3.) Good");
            System.out.println("4.) Excellent");

            boolean chainConditionRun = true;
            byte userChoice = 0;
            String chainCondition = "";

            while (chainConditionRun) {
                System.out.print("Please pick a choice (1-4): ");
                userChoice = valide.validateByte();

                if (userChoice == 1) {
                    chainCondition = "Poor";
                    run = false;
                }
                else if (userChoice == 2) {
                    chainCondition = "Fair";
                    run = false;
                }
                else if (userChoice == 3) {
                    chainCondition = "Good";
                    run = false;
                }
                else if (userChoice == 4) {
                    chainCondition = "Excellent";
                    run = false;
                }
                else {
                    System.out.printf("Invalid choice %d\n", userChoice);
                }
            }

            System.out.println("How expensive is it to replace the motorcycle's chain? $");
            String chainReplacementCost = valide.line();

        }
        if (type.equalsIgnoreCase("Truck")) {
            System.out.println("What is the max load of the truck's bed? ");
            String maxLoad = valide.line();

            System.out.println("How expensive is it to inspect the truck's  cargo? ");
            String cargoInspectionCost = valide.line();
        }
    }

    public void deleteVehicle() {
        for (int i = 0; i < vehicles.size(); i++) {
            String vin = vehicles.get(i).getVin();
            String make = vehicles.get(i).getMake();
            String model = vehicles.get(i).getModel();
            String year = vehicles.get(i).getYear();

            System.out.printf("%d: Vin: %s, Make: %s, Model: %s, Year: %s\n", (i + 1), vin, make, model, year);
        }

        System.out.print("Please enter a desired VIN #: ");
        String userVin = valide.line();

        boolean run = true;
        String type = "";
        while (run) {
            System.out.print("Please enter the type of vehicle (Car/Truck/Motorcycle): ");
            type = valide.line();
            if (type.equalsIgnoreCase("Car")) {
                run = false;
            }
            else if (type.equalsIgnoreCase("Motorcycle")) {
                run = false;
            }
            else if (type.equalsIgnoreCase("Truck")) {
                run = false;
            }
            else {
                System.out.println("Please enter a valid vehicle type");
            }
        }

        if (type.equalsIgnoreCase("Car")) {
            for (int i = 0; i < cars.size(); i++) {
                if (cars.get(i).getVin().equalsIgnoreCase(userVin)) {
                    cars.remove(i);
                }
            }
        }

        if (type.equalsIgnoreCase("Motorcycle")) {
            for (int i = 0; i < motorcycles.size(); i++) {
                if (motorcycles.get(i).getVin().equalsIgnoreCase(userVin)) {
                    motorcycles.remove(i);
                }
            }
        }

        if (type.equalsIgnoreCase("Truck")) {
            for (int i = 0; i < trucks.size(); i++) {
                if (trucks.get(i).getVin().equalsIgnoreCase(userVin)) {
                    trucks.remove(i);
                }
            }
        }

        dbmanager.deleteVehicle(userVin, type);
    }

    public double maintenanceAverageVehicle() {
        double average = 0.0;
        for (Vehicle vehicle : vehicles) {
            average += Double.parseDouble(vehicle.getCostEstimate());
        }
        average /= vehicles.size();

        return average;
    }

    public double maintenanceAverageCar() {
        double average = 0.0;
        for (Car car : cars) {
            average += Double.parseDouble(car.getCostEstimate());
        }
        average /= cars.size();

        return average;
    }

    public double maintenanceAverageMotorcycle() {
        double average = 0.0;
        for (Motorcycle motorcycle : motorcycles) {
            average += Double.parseDouble(motorcycle.getCostEstimate());
        }
        average /= motorcycles.size();

        return average;
    }

    public double maintenanceAverageTruck() {
        double average = 0.0;
        for (Truck truck : trucks) {
            average += Double.parseDouble(truck.getCostEstimate());
        }
        average /= trucks.size();

        return average;
    }

}
