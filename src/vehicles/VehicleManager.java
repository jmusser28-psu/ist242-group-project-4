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


    /**
     * Prompts the user to update the field and returns the updated information.
     * @return
     */
    public String[] updateVehicle() {
        String vin = "";
        String make = "";
        String model = "";
        String year = "";
        String type = "";
        String vehicleType = "";
        String costEstimate = "";


        System.out.println("Vehicle Information:");
        for (int i = 0; i < vehicles.size(); i++) {
            System.out.printf("VIN: %s, Make: %s, Type: %s\n", vehicles.get(i).getVin(),
                    vehicles.get(i).getMake(), vehicles.get(i).getType()
            );
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

        boolean loopDone = false;
        byte choice = 0;
        while (!loopDone) {
            System.out.println("Modify Vehicle Information");
            System.out.println("0.) Exit");
            System.out.println("1.) Vin");
            System.out.println("2.) Make");
            System.out.println("3.) Model");
            System.out.println("4.) Year");
            System.out.println("5.) Type");
            System.out.println("6.) Vehicle Brand Type");
            System.out.println("7.) Cost Estimate");
            System.out.println("What would you like to modify (numerical value): ");
            choice = valide.validateByte();

            if (choice == 0) {
                loopDone = true;
            }
            else if (choice == 1) {
                vin = valide.line();
            }
            else if (choice == 2) {
                make = valide.line();
            }
            else if (choice == 3) {
                model = valide.line();
            }
            else if (choice == 4) {
                year = valide.line();
            }
            else if (choice == 5) {
                type = valide.line();
            }
            else if (choice == 6) {
                vehicleType = valide.line();
            }
            else if (choice == 7) {
                costEstimate = valide.line();
            }
        }

        dbmanager.deleteVehicle(vin, type);
        dbmanager.addVehicle(vin, make, model, year, type, vehicleType, costEstimate);

        String[] info = {vin, make, model, year, type, vehicleType, costEstimate};

        return info;
    }

    public void updateCar() {
        String[] info = updateVehicle();
        String vin = info[0];
        String make = info[1];
        String model = info[2];
        String year = info[3];
        String type = info[4];
        String vehicleType = info[5];
        String costEstimate = info[6];

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
            choice = valide.validateByte();

            if (choice == 0) {
                loopDone = true;
            }
            else if (choice == 1) {
                numberOfDoors = valide.line();
            }
            else if (choice == 2) {
                oilChangeCost = valide.line();
            }
            else {
                System.out.printf("Invalid input %d", choice);
            }

            dbmanager.addCar(vin, make, model, year, type, vehicleType, costEstimate, numberOfDoors, oilChangeCost);

        }

    }

    public void updateMotorcycle() {
        String[] info = updateVehicle();
        String vin = info[0];
        String make = info[1];
        String model = info[2];
        String year = info[3];
        String type = info[4];
        String vehicleType = info[5];
        String costEstimate = info[6];

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
                chainReplacementCost = valide.line();
            }
            else {
                System.out.printf("Invalid input %d", choice);
            }

            dbmanager.addMotorcycle(vin, make, model, year, type, vehicleType, costEstimate, chainCondition, chainReplacementCost);

        }

    }

    public void updateTruck() {
        String[] info = updateVehicle();
        String vin = info[0];
        String make = info[1];
        String model = info[2];
        String year = info[3];
        String type = info[4];
        String vehicleType = info[5];
        String costEstimate = info[6];

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
            choice = valide.validateByte();

            if (choice == 0) {
                loopDone = true;
            }
            else if (choice == 1) {
                maxLoad = valide.line();
            }
            else if (choice == 2) {
                cargoInspectionCost = valide.line();
            }
            else {
                System.out.printf("Invalid input %d", choice);
            }

            dbmanager.addTruck(vin, make, model, year, type, vehicleType, costEstimate, maxLoad, cargoInspectionCost);

        }

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
