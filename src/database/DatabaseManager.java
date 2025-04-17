package database;

import vehicles.Car;
import vehicles.Vehicle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseManager {
    Connection connection;

    public DatabaseManager(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Vehicle> getVehicles(Connection connection) {
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

    public ArrayList<Car> getCars(Connection connection) {
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

                try (Statement statement = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(carQuery)) {
                    while (rs.next()) {
                        String vehicleID = rs.getString("vin");
                        String numberOfDoors = rs.getString("numberOfDoors");
                        String oilChangeCost = rs.getString("oilChangeCost");

                        if (type.equals("Car")) {
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

    public void closeConnection() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }

}
