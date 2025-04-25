package vehicles;

public class Motorcycle extends Vehicle {
    private String chainCondition;
    private String chainReplacementCost;

    /**
     * Constructs a Motorcycle object with both general vehicle info and motorcycle-specific details.
     */
    public Motorcycle(String vin, String make, String model, String year, String type, String vehicle_type, String costEstimate,
                      String chainCondition, String chainReplacementCost) {
        super(vin, make, model, year, type, vehicle_type, costEstimate);
        this.chainCondition = chainCondition;
        this.chainReplacementCost = chainReplacementCost;
    }

    // Getters for chainConditions and chainReplacementCosts
    public String getChainCondition() {
        return chainCondition;
    }

    public String getChainReplacementCost() {
        return chainReplacementCost;
    }

    /**
     * Overrides the Vehicle display method to show motorcycle-specific maintenance details.
     */
    @Override
    public void displayMaintenanceDetails() {
        System.out.println("Motorcycle: " + super.getMake() + " " + super.getModel() +
                " | VIN: " + super.getVin() +
                " | Maintenance: $" + super.getCostEstimate() +
                " | Chain Condition: " + chainCondition +
                " | Chain Replacement Cost: " + chainReplacementCost);
    }
}