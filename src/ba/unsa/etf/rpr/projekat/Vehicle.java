package ba.unsa.etf.rpr.projekat;

import java.util.List;

public abstract class Vehicle {

    private Integer vehicleId;
    private String vehicleName;
    private String licencePlate;
    private String color;
    private List<Equipment> equipmentList;
    private String vehicleType;


    public Vehicle() {
    }

    public Vehicle(String vehicleName, String licencePlate, String color, List<Equipment> equipmentList) {
        this.vehicleName = vehicleName;
        this.licencePlate = licencePlate;
        this.color = color;
        this.equipmentList = equipmentList;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public void addEquipment(Equipment equipment){
        equipmentList.add(equipment);
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
