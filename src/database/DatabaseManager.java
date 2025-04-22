package database;

import vehicles.Car;
import vehicles.Motorcycle;
import vehicles.Truck;
import vehicles.Vehicle;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    Connection connection;

    public DatabaseManager(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Vehicle> getVehicles() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        final String query = "SELECT * FROM VEHICLES";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String vin = rs.getString("vin");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String year = rs.getString("year");
                String type = rs.getString("type");
                String vehicle_type = rs.getString("vehicle_type");
                String costEstimate = rs.getString("costEstimate");

                vehicles.add(new Vehicle(vin, make, model, year, type, vehicle_type, costEstimate));
            }
        }
        catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }

        return vehicles;
    }

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

                try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(carQuery)) {
                    while (resultSet.next()) {
                        String vehicleID = resultSet.getString("vin");
                        String numberOfDoors = resultSet.getString("numberOfDoors");
                        String oilChangeCost = resultSet.getString("oilChangeCost");

                        if (type.equalsIgnoreCase("Car")) {
                            if (vin.equals(vehicleID)) {
                                cars.add(new Car(vin, make, model, year, type, vehicle_type, costEstimate, numberOfDoors, oilChangeCost));
                            }
                        }
                    }
                }
                catch (SQLException e) {
                    System.err.println("Error occurred " + e.getMessage());
                }

            }
        }
        catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }

        return cars;
    }

    public ArrayList<Motorcycle> getMotorcycles() {
        ArrayList<Motorcycle> motorcycles = new ArrayList<>();

        final String query = "SELECT * FROM VEHICLES";
        final String motorcycleQuery = "SELECT * FROM motorcycle_details";
        final boolean hasSideCar = true; // May need to fix later

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String vin = rs.getString("vin");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String year = rs.getString("year");
                String type = rs.getString("type");
                String vehicle_type = rs.getString("vehicle_type");
                String costEstimate = rs.getString("costEstimate");

                try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(motorcycleQuery)) {
                    while (resultSet.next()) {
                        String vehicleID = resultSet.getString("vin");
                        String chainCondition = resultSet.getString("chainCondition");
                        String chainReplacementCost = resultSet.getString("chainReplacementCost");

                        if (type.equalsIgnoreCase("Motorcycle")) {
                            if (vin.equals(vehicleID)) {
                                motorcycles.add(new Motorcycle(vin, make, model, year, type, vehicle_type, costEstimate, hasSideCar, chainCondition, chainReplacementCost));
                            }
                        }
                    }
                }
                catch (SQLException e) {
                    System.err.println("Error occurred " + e.getMessage());
                }
            }
        }
        catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }

        return motorcycles;
    }

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

                        if (type.equalsIgnoreCase("Truck")) {
                            if (vin.equals(vehicleID)) {
                                trucks.add(new Truck(vin, make, model, year, type, vehicle_type, costEstimate, maxLoad, cargoInspectionCost));
                            }
                        }
                    }
                }
                catch (SQLException e) {
                    System.err.println("Error occurred " + e.getMessage());
                }
            }
        }
        catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }

        return trucks;
    }

    public void deleteVehicle(String vin, String type) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM vehicles WHERE vin = ?")) {
            ps.setString(1, vin);
            ps.execute();
            if (type.equalsIgnoreCase("Car")) {
                try (PreparedStatement pstat = connection.prepareStatement("DELETE FROM car_details WHERE vin = ?")) {
                    pstat.setString(1, vin);
                    pstat.execute();
                }
            }
        }
        catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }

}
