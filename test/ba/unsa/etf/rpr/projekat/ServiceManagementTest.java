package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceManagementTest {

    @Test
    public void constructorTest(){
        ServiceManagement serviceManagement = new ServiceManagement(null,null);
        assertNull(serviceManagement.getService());
        assertNull(serviceManagement.getVehicle());
    }

    @Test
    public void setterMethodesTest(){
        ServiceManagement serviceManagement = new ServiceManagement(null,null);
        assertNull(serviceManagement.getService());
        assertNull(serviceManagement.getVehicle());
        Vehicle vehicle = new MotorVehicle("VW","000-0-000", "Blue", null);
        serviceManagement.setVehicle(vehicle);
        assertNotNull(serviceManagement.getVehicle());
        LocalDate date = LocalDate.of(2000,1,1);
        Service service = new Service("Mali servis",date,"Asim Haskic","Uradjeno sve sto se tice malog servisa");
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(service);
        serviceManagement.setService(serviceList);
        assertNotNull(serviceManagement.getService());
    }

    @Test
    public void addingServiceTest(){
        ServiceManagement serviceManagement = new ServiceManagement(null,null);
        Vehicle vehicle = new MotorVehicle("VW","000-0-000", "Blue", null);
        serviceManagement.setVehicle(vehicle);
        LocalDate date = LocalDate.of(2000,1,1);
        Service service = new Service("Mali servis",date,"Asim Haskic","Uradjeno sve sto se tice malog servisa");
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(service);
        serviceManagement.setService(serviceList);
        assertEquals(serviceManagement.getService().size(),1);
        LocalDate date2 = LocalDate.of(2000,1,2);
        Service service2 = new Service("Veliki servis",date2,"Vedran Ljubovic","Uradjeno sve sto se tice velikog servisa");
        serviceManagement.addService(service2);
        assertEquals(serviceManagement.getService().size(),2);
    }
}
