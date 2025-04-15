package vehicles;

public class Motorcycle extends Vehicle {
    private boolean hasSidecar;
    private String chainCondition;
    private String chainReplacementCost;

    public Motorcycle(String vin, String make, String model, String year, String type, String vehicle_type, String costEstimate,
                      boolean hasSidecar, String chainCondition, String chainReplacementCost) {
        super(vin, make, model, year, type, vehicle_type, costEstimate);
        this.hasSidecar = hasSidecar;
        this.chainCondition = chainCondition;
        this.chainReplacementCost = chainReplacementCost;
    }
    @Override
    public void displayMaintenanceDetails() {
        System.out.println("Motorcycle: " + super.getMake() + " " + super.getModel() +
                           " | VIN: " + super.getVin() +
                           " | Maintenance: $" + super.getCostEstimate() +
                           " | Sidecar: " + (hasSidecar ? "Yes" : "No"));
    }

    public String getServiceAdvice() {
        return hasSidecar
                ? "Motorcycle with sidecar: CHeck alignment and tire balance more frequently."
                : "Standard motorcycle: Chain and oil maintenance every 3,000 miles.";
    }

    public boolean hasSidecar() {
        return hasSidecar;
    }
}
