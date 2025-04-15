package vehicles;

public class Truck extends Vehicle {

    private String maxLoad;
    private String cargoInspectionCost;

    public Truck(String vin, String make, String model, String year, String type, String vehicle_type, String costEstimate,
                 String maxLoad, String cargoInspectionCost) {
        super(vin, make, model, year, type, vehicle_type, costEstimate);
        this.maxLoad = maxLoad;
        this.cargoInspectionCost = cargoInspectionCost;
    }


}
