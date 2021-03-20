package ba.unsa.etf.rpr.projekat;

import java.util.List;

public class MotorFleet {

    private Integer motorFleetId;
    private String nameOfMotorFleet;
    private List<User>  employeeList;
    private User manager;
    private List<Vehicle> vehicleList;
    private List<Storage> storageList;
    private List<ServiceManagement> serviceList;

    public MotorFleet(String nameOfMotorFleet, List<User> employeeList, User manager, List<Vehicle> vehicleList, List<Storage> storageList, List<ServiceManagement> serviceList) {
        this.nameOfMotorFleet = nameOfMotorFleet;
        this.employeeList = employeeList;
        this.manager = manager;
        this.vehicleList = vehicleList;
        this.storageList = storageList;
        this.serviceList = serviceList;
    }

    public String getNameOfMotorFleet() {
        return nameOfMotorFleet;
    }

    public void setNameOfMotorFleet(String nameOfMotorFleet) {
        this.nameOfMotorFleet = nameOfMotorFleet;
    }

    public List<User> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<User> employeeList) {
        this.employeeList = employeeList;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<Storage> getStorageList() {
        return storageList;
    }

    public void setStorageList(List<Storage> storageList) {
        this.storageList = storageList;
    }

    public List<ServiceManagement> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServiceManagement> serviceList) {
        this.serviceList = serviceList;
    }

    public void addUser(User user){
        employeeList.add(user);
    }

    public void addVehicle(Vehicle vehicle){
        vehicleList.add(vehicle);
    }

    public void addStorage(Storage storage){
        storageList.add(storage);
    }

    public void addServiceManagment(ServiceManagement serviceManagment){
        serviceList.add(serviceManagment);
    }

    public Integer getMotorFleetId() {
        return motorFleetId;
    }

    public void setMotorFleetId(Integer motorFleetId) {
        this.motorFleetId = motorFleetId;
    }
}
