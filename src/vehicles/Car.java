package vehicles;

public class Car extends Vehicle {
    private String numDoors;
    private String oilChangeCost;
    public Car(String vin, String make, String model, String year, String type, String vehicle_type, String costEstimate,
               String numDoors, String oilChangeCost) {
        super(vin, make, model, year, type, vehicle_type, costEstimate);
        this.numDoors = numDoors;
        this.oilChangeCost = oilChangeCost;
    }

    @Override
    public void displayMaintenanceDetails() {
        System.out.println("Car: " + super.getMake() + " " + super.getModel() +
                " | VIN: " + super.getVin() +
                " | Maintenance: $" + super.getCostEstimate() +
                " | Oil Change Cost: " + (oilChangeCost));
    }
}
