package vehicles;

public class Vehicle {

    private String vin;
    private String make;
    private String model;
    private String year;
    private String type;
    private String vehicle_type;
    private String costEstimate;


    public Vehicle(String vin, String make, String model, String year, String type, String vehicle_type, String costEstimate) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.type = type;
        this.vehicle_type = vehicle_type;
        this.costEstimate = costEstimate;
        this.year = year;
    }

    public String getVin(){
        return vin;
    }
    public String getMake(){
        return make;
    }

    public String getModel(){
        return model;
    }
    public String getYear(){
        return year;
    }

    public String getType(){
        return type;
    }

    public String getVehicleType(){
        return vehicle_type;
    }

    public String getCostEstimate(){
        return costEstimate;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public void setCostEstimate(String costEstimate) {
        this.costEstimate = costEstimate;
    }



    public void displayMaintenanceDetails() {
        System.out.println("Vehicle: " + vehicle_type + " " + model +
                " | VIN: " + vin +
                " | Maintenance: $" + costEstimate);
    }

}
