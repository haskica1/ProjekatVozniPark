package ba.unsa.etf.rpr.projekat;

import java.util.ArrayList;
import java.util.List;

public class ServiceManagement {

    private List<Service> serviceList;
    private Vehicle vehicle;


    public ServiceManagement(List<Service> service, Vehicle vehicle) {
        this.serviceList = service;
        this.vehicle = vehicle;
    }

    public ServiceManagement() {
    }

    public List<Service> getService() {
        return serviceList;
    }

    public void setService(List<Service> service) {
        this.serviceList = service;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void addService(Service service){
        serviceList.add(service);
    }
}
