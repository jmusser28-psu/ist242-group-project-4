package vehicles;

public class Car extends Vehicle {
    private String numDoors;
    private String oilChangeCost;

    /**
     * Constructs a Car object with both general vehicle info and car details.
     */
    public Car(String vin, String make, String model, String year, String type, String vehicle_type, String costEstimate,
               String numDoors, String oilChangeCost) {
        super(vin, make, model, year, type, vehicle_type, costEstimate);
        this.numDoors = numDoors;
        this.oilChangeCost = oilChangeCost;
    }
    // Getter for the number of doors
    public String getNumDoors() {
        return numDoors;
    }

    // Getter for the oil change cost
    public String getOilChangeCost() {
        return oilChangeCost;
    }

    /**
     * Overrides the vehicle display method to include car-specific maintenance details.
     */
    @Override
    public void displayMaintenanceDetails() {
        System.out.println("Car: " + super.getMake() + " " + super.getModel() +
                " | VIN: " + super.getVin() +
                " | Maintenance: $" + super.getCostEstimate() +
                " | Oil Change Cost: " + (oilChangeCost));
    }
}
