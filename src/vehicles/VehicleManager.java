package vehicles;

import database.DatabaseManager;
import util.InputValidation;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * VehicleManager is responsible for going through all of the vehicle related functions. It
 * includes adding, updating and deleting the vehicles, while also being able to calculate
 * the maintenance costs.
 */
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

    /**
     * Prints the maintenance details for all of the vehicles.
     */
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

    /**
     * Adds a new vehicle, while also asking the user for the vehicle type and
     * vehicle characteristics.
     */
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
            if (type.equals("Car")) {
                run = false;
            }
            else if (type.equals("Motorcycle")) {
                run = false;
            }
            else if (type.equals("Truck")) {
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

            System.out.print("How expensive is it to change the car's oil? : $");
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
                    chainConditionRun = false;
                }
                else if (userChoice == 2) {
                    chainCondition = "Fair";
                    chainConditionRun = false;
                }
                else if (userChoice == 3) {
                    chainCondition = "Good";
                    chainConditionRun = false;
                }
                else if (userChoice == 4) {
                    chainCondition = "Excellent";
                    chainConditionRun = false;
                }
                else {
                    System.out.printf("Invalid choice %d\n", userChoice);
                }
            }

            System.out.println("How expensive is it to replace the motorcycle's chain? $");
            String chainReplacementCost = valide.line();

            dbmanager.addMotorcycle(vin, make, model, year, type, vehicle_type, costEstimate, chainCondition, chainReplacementCost);
        }
        if (type.equalsIgnoreCase("Truck")) {
            System.out.println("What is the max load of the truck's bed? ");
            String maxLoad = valide.line();

            System.out.println("How expensive is it to inspect the truck's  cargo? ");
            String cargoInspectionCost = valide.line();
            dbmanager.addTruck(vin, make, model, year, type, vehicle_type, costEstimate, maxLoad, cargoInspectionCost);
        }
    }


    /**
     * Deletes a vehicle based off of its VIN, Make, Model, amd Year
     */
    public void deleteVehicle() {
        for (int i = 0; i < vehicles.size(); i++) {
            String vin = vehicles.get(i).getVin();
            String make = vehicles.get(i).getMake();
            String model = vehicles.get(i).getModel();
            String year = vehicles.get(i).getYear();
            String type = vehicles.get(i).getType();

            System.out.printf("%d: Vin: %s, Make: %s, Model: %s, Year: %s, Type: %s\n", (i + 1), vin, make, model, year, type);
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


    /**
     * Prompts the user to update the field and returns the updated information.
     * @return
     */
    public String[] updateVehicle(String type) {
        String vin = "";
        String make = "";
        String model = "";
        String year = "";
        String vehicleType = "";
        String costEstimate = "";

        if (type.equalsIgnoreCase("Car")) {
            for (int i = 0; i < cars.size(); i++) {
                System.out.printf("VIN: %s, Make: %s, Type: %s\n", cars.get(i).getVin(),
                        cars.get(i).getMake(), cars.get(i).getType());
            }
        }

        if (type.equalsIgnoreCase("Motorcycle")) {
            for (int i = 0; i < motorcycles.size(); i++) {
                System.out.printf("VIN: %s, Make: %s, Type: %s\n", motorcycles.get(i).getVin(),
                        motorcycles.get(i).getMake(), motorcycles.get(i).getType());
            }
        }

        if (type.equalsIgnoreCase("Truck")) {
            for (int i = 0; i < trucks.size(); i++) {
                System.out.printf("VIN: %s, Make: %s, Type: %s\n", trucks.get(i).getVin(),
                        trucks.get(i).getMake(), trucks.get(i).getType());
            }
        }

        System.out.print("Please enter the VIN of the vehicle you would like to modify: ");
        String userVin = valide.line();

        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getVin().equalsIgnoreCase(userVin)) {
                vin = vehicles.get(i).getVin();
                make = vehicles.get(i).getMake();
                model = vehicles.get(i).getModel();
                year = vehicles.get(i).getYear();
                type = vehicles.get(i).getType();
                vehicleType = vehicles.get(i).getVehicleType();
                costEstimate = vehicles.get(i).getCostEstimate();
            }
        }

        dbmanager.deleteVehicle(vin, type);

        // Updates most information of a vehicle, however assumes the type and VIN will be constant.
        boolean loopDone = false;
        byte choice = 0;
        while (!loopDone) {
            System.out.println("Modify Vehicle Information");
            System.out.println("0.) Exit");
            System.out.println("1.) Make");
            System.out.println("2.) Model");
            System.out.println("3.) Year");
            System.out.println("4.) Vehicle Brand Type");
            System.out.println("5.) Cost Estimate");
            System.out.print("What would you like to modify (0-5): ");
            choice = valide.validateByte();

            if (choice == 0) {
                loopDone = true;
            }
            else if (choice == 1) {
                System.out.print("Please enter a new make: ");
                make = valide.line();
            }
            else if (choice == 2) {
                System.out.print("Please enter a new model: ");
                model = valide.line();
            }
            else if (choice == 3) {
                System.out.print("Please enter a new year: ");
                year = valide.line();
            }
            else if (choice == 4) {
                System.out.print("Please enter a new brand type: ");
                vehicleType = valide.line();
            }
            else if (choice == 5) {
                System.out.print("Please enter a new cost estimate: ");
                costEstimate = valide.line();
            }
        }

        String[] info = {vin, make, model, year, vehicleType, costEstimate};

        return info;
    }

    public void updateCar() {
        String type = "Car";
        String[] info = updateVehicle(type);

        String vin = info[0];
        String make = info[1];
        String model = info[2];
        String year = info[3];
        String vehicleType = info[4];
        String costEstimate = info[5];

        boolean loopDone = false;
        byte choice = 0;

        String numberOfDoors = "";
        String oilChangeCost = "";

        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getVin().equals(vin)) {
                numberOfDoors = cars.get(i).getNumDoors();
                oilChangeCost = cars.get(i).getOilChangeCost();
            }
        }

        while (!loopDone) {
            System.out.println("Modify Car Information");
            System.out.println("0.) Exit");
            System.out.println("1.) Number of Doors");
            System.out.println("2.) Oil Change Cost");
            System.out.print("What would you like to modify (0-2): ");
            choice = valide.validateByte();

            if (choice == 0) {
                loopDone = true;
            }
            else if (choice == 1) {
                System.out.print("Please enter a new number of doors: ");
                numberOfDoors = valide.line();
            }
            else if (choice == 2) {
                System.out.print("Please enter a new oil change cost: ");
                oilChangeCost = valide.line();
            }
            else {
                System.out.printf("Invalid input %d", choice);
            }

        }

        dbmanager.addCar(vin, make, model, year, type, vehicleType, costEstimate, numberOfDoors, oilChangeCost);

    }

    public void updateMotorcycle() {
        String type = "Motorcycle";
        String[] info = updateVehicle(type);

        String vin = info[0];
        String make = info[1];
        String model = info[2];
        String year = info[3];
        String vehicleType = info[4];
        String costEstimate = info[5];

        boolean loopDone = false;
        byte choice = 0;

        String chainCondition = "";
        String chainReplacementCost = "";

        for (int i = 0; i < motorcycles.size(); i++) {
            if (motorcycles.get(i).getVin().equals(vin)) {
                chainCondition = motorcycles.get(i).getChainCondition();
                chainReplacementCost = motorcycles.get(i).getChainReplacementCost();
            }
        }

        while (!loopDone) {
            System.out.println("Modify Motorcycle Information");
            System.out.println("0.) Exit");
            System.out.println("1.) Chain Condition");
            System.out.println("2.) Chain Replacement Cost");
            System.out.print("What would you like to modify (0-3): ");
            choice = valide.validateByte();

            if (choice == 0) {
                loopDone = true;
            }
            else if (choice == 1) {
                System.out.println("What is the condition of your chain? ");
                System.out.println("1.) Poor");
                System.out.println("2.) Fair");
                System.out.println("3.) Good");
                System.out.println("4.) Excellent");

                boolean chainConditionRun = true;
                byte userChoice = 0;

                while (chainConditionRun) {
                    System.out.print("Please pick a choice (1-4): ");
                    userChoice = valide.validateByte();

                    if (userChoice == 1) {
                        chainCondition = "Poor";
                        chainConditionRun = false;
                    }
                    else if (userChoice == 2) {
                        chainCondition = "Fair";
                        chainConditionRun = false;
                    }
                    else if (userChoice == 3) {
                        chainCondition = "Good";
                        chainConditionRun = false;
                    }
                    else if (userChoice == 4) {
                        chainCondition = "Excellent";
                        chainConditionRun = false;
                    }
                    else {
                        System.out.printf("Invalid choice %d\n", userChoice);
                    }
                }
            }
            else if (choice == 2) {
                System.out.print("Please enter a new chain replacement cost: ");
                chainReplacementCost = valide.line();
            }
            else {
                System.out.printf("Invalid input %d", choice);
            }

        }

        dbmanager.addMotorcycle(vin, make, model, year, type, vehicleType, costEstimate, chainCondition, chainReplacementCost);

    }

    public void updateTruck() {
        String type = "Truck";
        String[] info = updateVehicle(type);

        String vin = info[0];
        String make = info[1];
        String model = info[2];
        String year = info[3];
        String vehicleType = info[4];
        String costEstimate = info[5];

        boolean loopDone = false;
        byte choice = 0;

        String maxLoad = "";
        String cargoInspectionCost = "";

        for (int i = 0; i < trucks.size(); i++) {
            if (trucks.get(i).getVin().equals(vin)) {
                maxLoad = trucks.get(i).getMaxLoad();
                cargoInspectionCost = trucks.get(i).getCargoInspectionCost();
            }
        }

        while (!loopDone) {
            System.out.println("Modify Car Information");
            System.out.println("0.) Exit");
            System.out.println("1.) Max Load");
            System.out.println("2.) Cargo Inspection Cost");
            System.out.print("What would you like to modify (0-3): ");
            choice = valide.validateByte();

            if (choice == 0) {
                loopDone = true;
            }
            else if (choice == 1) {
                System.out.print("Please enter a new max load capacity: ");
                maxLoad = valide.line();
            }
            else if (choice == 2) {
                System.out.print("Please enter a new cargo inspection cost: ");
                cargoInspectionCost = valide.line();
            }
            else {
                System.out.printf("Invalid input %d", choice);
            }

        }

        dbmanager.addTruck(vin, make, model, year, type, vehicleType, costEstimate, maxLoad, cargoInspectionCost);

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
