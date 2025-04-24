package database;

import vehicles.Car;
import vehicles.Motorcycle;
import vehicles.Truck;
import vehicles.Vehicle;

import java.sql.*;
import java.util.ArrayList;

/**
 * DatabaseManager handles database operations for the Vehicle Maintenance Management System.
 * It includes methods to retrieve, insert, and delete vehicle data using JDBC with SQLite.
 */
public class DatabaseManager {
    Connection connection;

    /**
     * Constructor to initialize DatabaseManager with an active database connection.
     * @param connection The JDBC connection to the SQLite database.
     */
    public DatabaseManager(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieves all vehicles from the 'VEHICLES' table.
     * @return ArrayList of Vehicle objects containing basic vehicle info.
     */
    public ArrayList<Vehicle> getVehicles() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        final String query = "SELECT * FROM VEHICLES";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                // Extracting vehicle data from each row in the result set.
                String vin = rs.getString("vin");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String year = rs.getString("year");
                String type = rs.getString("type");
                String vehicle_type = rs.getString("vehicle_type");
                String costEstimate = rs.getString("costEstimate");

                vehicles.add(new Vehicle(vin, make, model, year, type, vehicle_type, costEstimate));
            }
        } catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }

        return vehicles;
    }

    /**
     * Retrieves all cars from both the 'VEHICLES' and 'car_details' tables.
     * Joins general vehicle data with car-specific details.
     * @return ArrayList of Car objects.
     */
    public ArrayList<Car> getCars() {
        ArrayList<Car> cars = new ArrayList<>();
        final String query = "SELECT * FROM VEHICLES";
        final String carQuery = "SELECT * FROM car_details";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String vin = rs.getString("vin");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String year = rs.getString("year");
                String type = rs.getString("type");
                String vehicle_type = rs.getString("vehicle_type");
                String costEstimate = rs.getString("costEstimate");

                // Inner query to find matching car details using VIN.
                try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(carQuery)) {
                    while (resultSet.next()) {
                        String vehicleID = resultSet.getString("vin");
                        String numberOfDoors = resultSet.getString("numberOfDoors");
                        String oilChangeCost = resultSet.getString("oilChangeCost");

                        // Check if vehicle is of type 'Car' and VIN matches.
                        if (type.equalsIgnoreCase("Car") && vin.equals(vehicleID)) {
                            cars.add(new Car(vin, make, model, year, type, vehicle_type, costEstimate, numberOfDoors, oilChangeCost));
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("Error occurred " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }

        return cars;
    }

    /**
     * Retrieves all motorcycles from the database by joining 'VEHICLES' with 'motorcycle_details'.
     * @return ArrayList of Motorcycle objects.
     */
    public ArrayList<Motorcycle> getMotorcycles() {
        ArrayList<Motorcycle> motorcycles = new ArrayList<>();
        final String query = "SELECT * FROM VEHICLES";
        final String motorcycleQuery = "SELECT * FROM motorcycle_details";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String vin = rs.getString("vin");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String year = rs.getString("year");
                String type = rs.getString("type");
                String vehicle_type = rs.getString("vehicle_type");
                String costEstimate = rs.getString("costEstimate");

                // Matching motorcycles with their specific details.
                try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(motorcycleQuery)) {
                    while (resultSet.next()) {
                        String vehicleID = resultSet.getString("vin");
                        String chainCondition = resultSet.getString("chainCondition");
                        String chainReplacementCost = resultSet.getString("chainReplacementCost");

                        if (type.equalsIgnoreCase("Motorcycle") && vin.equals(vehicleID)) {
                            motorcycles.add(new Motorcycle(vin, make, model, year, type, vehicle_type, costEstimate, chainCondition, chainReplacementCost));
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("Error occurred " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }

        return motorcycles;
    }

    /**
     * Retrieves all trucks from the database by joining 'VEHICLES' with 'truck_details'.
     * @return ArrayList of Truck objects.
     */
    public ArrayList<Truck> getTrucks() {
        ArrayList<Truck> trucks = new ArrayList<>();
        final String query = "SELECT * FROM VEHICLES";
        final String truckQuery = "SELECT * FROM truck_details";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String vin = rs.getString("vin");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String year = rs.getString("year");
                String type = rs.getString("type");
                String vehicle_type = rs.getString("vehicle_type");
                String costEstimate = rs.getString("costEstimate");

                try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(truckQuery)) {
                    while (resultSet.next()) {
                        String vehicleID = resultSet.getString("vin");
                        String maxLoad = resultSet.getString("maxLoad");
                        String cargoInspectionCost = resultSet.getString("cargoInspectionCost");

                        if (type.equalsIgnoreCase("Truck") && vin.equals(vehicleID)) {
                            trucks.add(new Truck(vin, make, model, year, type, vehicle_type, costEstimate, maxLoad, cargoInspectionCost));
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("Error occurred " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }

        return trucks;
    }

    /**
     * Inserts a new general vehicle into the 'VEHICLES' table.
     * @param vin Vehicle Identification Number.
     * @param make Vehicle make (manufacturer).
     * @param model Vehicle model.
     * @param year Vehicle year.
     * @param type Vehicle type (Car, Truck, Motorcycle).
     * @param vehicle_type Vehicle category (if applicable).
     * @param costEstimate Estimated maintenance cost.
     */
    public void addVehicle(String vin, String make, String model, String year, String type, String vehicle_type, String costEstimate) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO vehicles (vin, make, model, year, type, vehicle_type, costEstimate) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, vin);
            ps.setString(2, make);
            ps.setString(3, model);
            ps.setString(4, year);
            ps.setString(5, type);
            ps.setString(6, vehicle_type);
            ps.setString(7, costEstimate);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error occurred " + e.getMessage());
        }
    }

    /**
     * Adds a new car by inserting general vehicle data and car-specific details.
     */
    public void addCar(String vin, String make, String model, String year, String type, String vehicle_type,
                       String costEstimate, String numberOfDoors, String oilChangeCost) {
        addVehicle(vin, make, model, year, type, vehicle_type, costEstimate);
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO car_details (vin, numberOfDoors, oilChangeCost) VALUES (?, ?, ?)")) {
            ps.setString(1, vin);
            ps.setString(2, numberOfDoors);
            ps.setString(3, oilChangeCost);
            ps.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("Error occurred " + exception.getMessage());
        }
    }

    /**
     * Adds a new motorcycle along with its specific maintenance details.
     */
    public void addMotorcycle(String vin, String make, String model, String year, String type, String vehicle_type,
                              String costEstimate, String chainCondition, String chainReplacementCost) {
        addVehicle(vin, make, model, year, type, vehicle_type, costEstimate);
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO motorcycle_details (vin, chainCondition, chainReplacementCost) VALUES (?, ?, ?)")) {
            ps.setString(1, vin);
            ps.setString(2, chainCondition);
            ps.setString(3, chainReplacementCost);
            ps.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("Error occurred " + exception.getMessage());
        }
    }

    /**
     * Adds a new truck along with truck-specific details.
     */
    public void addTruck(String vin, String make, String model, String year, String type, String vehicle_type,
                         String costEstimate, String maxLoad, String cargoInspectionCost) {
        addVehicle(vin, make, model, year, type, vehicle_type, costEstimate);
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO truck_details (vin, maxLoad, cargoInspectionCost) VALUES (?, ?, ?)")) {
            ps.setString(1, vin);
            ps.setString(2, maxLoad);
            ps.setString(3, cargoInspectionCost);
            ps.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("Error occurred " + exception.getMessage());
        }
    }

    /**
     * Deletes a vehicle from the 'vehicles' table (and from type-specific tables if applicable).
     * @param vin Vehicle Identification Number.
     * @param type Vehicle type (to determine which detail table to also delete from).
     */
    public void deleteVehicle(String vin, String type) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM vehicles WHERE vin = ?")) {
            ps.setString(1, vin);
            ps.execute();
            // If the vehicle is a car, delete from the car_details table as well.
            if (type.equalsIgnoreCase("Car")) {
                try (PreparedStatement pstat = connection.prepareStatement("DELETE FROM car_details WHERE vin = ?")) {
                    pstat.setString(1, vin);
                    pstat.execute();
                }
            }
            if (type.equalsIgnoreCase("Motorcycle")) {
                try (PreparedStatement pstat = connection.prepareStatement("DELETE FROM motorcycle_details WHERE vin = ?")) {
                    pstat.setString(1, vin);
                    pstat.execute();
                }
            }
            if (type.equalsIgnoreCase("Truck")) {
                try (PreparedStatement pstat = connection.prepareStatement("DELETE FROM truck_details WHERE vin = ?")) {
                    pstat.setString(1, vin);
                    pstat.execute();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }

    /**
     * Closes the database connection when done.
     * Helps prevent resource leaks.
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}
