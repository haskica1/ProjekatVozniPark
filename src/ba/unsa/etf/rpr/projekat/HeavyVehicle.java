package ba.unsa.etf.rpr.projekat;

import java.util.List;

public class HeavyVehicle extends Vehicle {
    private int transportCapacity;

    public HeavyVehicle(){

    }

    public HeavyVehicle(int transportCapacity) {
        this.transportCapacity = transportCapacity;
    }

    public HeavyVehicle(String vehicleName, String licencePlate, String color, List<Equipment> equipmentList, int transportCapacity) {
        super(vehicleName, licencePlate, color, equipmentList);
        this.transportCapacity = transportCapacity;
    }

    public int getTransportCapacity() {
        return transportCapacity;
    }

    public void setTransportCapacity(int transportCapacity) {
        this.transportCapacity = transportCapacity;
    }
}
