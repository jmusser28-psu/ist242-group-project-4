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
                                motorcycles.add(new Motorcycle(vin, make, model, year, type, vehicle_type, costEstimate, chainCondition, chainReplacementCost));
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

    public void addVehicle(String vin, String make, String model, String year, String type, String vehicle_type, String costEstimate) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO vehicles (vin, make, model, " +
                "year, type, vehicle_type, costEstimate) VALUES ? ? ? ? ? ? ?")) {
            ps.setString(1, vin);
            ps.setString(2, make);
            ps.setString(3, model);
            ps.setString(4, year);
            ps.setString(5, type);
            ps.setString(6, vehicle_type);
            ps.setString(7, costEstimate);
            ps.execute();
        }

        catch (SQLException e) {
            System.err.println("Error occurred " + e.getMessage());
        }
    }


    public void addCar(String vin, String make, String model, String year, String type, String vehicle_type,
                       String costEstimate, String numberOfDoors, String oilChangeCost) {
        addVehicle(vin, make, model, year, type, vehicle_type, costEstimate);

        String insertVehicle = "INSERT INTO car_details (vin, numberOfDoors, oilChangeCost) VALUES ? ? ?";
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO car_details (vin, numberOfDoors, oilChangeCost) VALUES ? ? ?")) {
            ps.setString(1, vin);
            ps.setString(2, numberOfDoors);
            ps.setString(3, oilChangeCost);
            ps.execute();
        }

        catch (SQLException e) {
            System.err.println("Error occurred " + e.getMessage());
        }
    }

    public void addMotorcycle(String vin, String make, String model, String year, String type, String vehicle_type,
                       String costEstimate, String chainCondition, String chainReplacementCost) {
        addVehicle(vin, make, model, year, type, vehicle_type, costEstimate);

        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO motorcycle_details (vin, numberOfDoors, oilChangeCost) VALUES ? ? ?")) {
            ps.setString(1, vin);
            ps.setString(2, chainCondition);
            ps.setString(3, chainReplacementCost);
            ps.execute();
        }

        catch (SQLException e) {
            System.err.println("Error occurred " + e.getMessage());
        }
    }

    public void addTruck(String vin, String make, String model, String year, String type, String vehicle_type,
                       String costEstimate, String maxLoad, String cargoInspectionCost) {
            addVehicle(vin, make, model, year, type, vehicle_type, costEstimate);

        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO truck_details (vin, maxLoad, cargoInspectionCost) VALUES ? ? ?")){
            ps.setString(1, vin);
            ps.setString(2, maxLoad);
            ps.setString(3, cargoInspectionCost);
            ps.execute();
        }
        catch (SQLException e){
            System.err.println("Error occurred " + e.getMessage());
        }
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

    public void updateVehicle(String vin, String make, String model, String year, String type, String vehicle_type, String costEstimate) {
        try(PreparedStatement ps = connection.prepareStatement("UPDATE VEHICLES SET make = ?, model = ?, year = ?, type = ?, vehicle_type = ?, costEstimate = ?" +
                " WHERE vin = ?")) {
            ps.setString(1, make);
            ps.setString(2,model);
            ps.setString(3,year);
            ps.setString(4,type);
            ps.setString(5,vehicle_type);
            ps.setString(6,costEstimate);
            ps.setString(7,vin);
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
