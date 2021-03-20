package ba.unsa.etf.rpr.projekat;

import java.util.List;

public class MotorVehicle extends Vehicle {

    public MotorVehicle() {
    }

    public MotorVehicle(String vehicleName, String licencePlate, String color, List<Equipment> equipmentList) {
        super(vehicleName, licencePlate, color, equipmentList);
    }
}
