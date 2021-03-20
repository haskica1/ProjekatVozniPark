package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MotorFleetTest {

    @Test
    public void constructorTest(){
        MotorFleet motorFleet = new MotorFleet("Vozni park", null,null,null,null,null);
        assertEquals(motorFleet.getNameOfMotorFleet(),"Vozni park");
        assertNull(motorFleet.getEmployeeList());
        assertNull(motorFleet.getManager());
        assertNull(motorFleet.getServiceList());
        assertNull(motorFleet.getStorageList());
        assertNull(motorFleet.getVehicleList());
    }

    @Test
    public void setterMethodTest(){
        MotorFleet motorFleet = new MotorFleet("Vozni park", null,null,null,null,null);
        User asim = new User("Asim","Haskic","000/000-000","Fakultet br.1",LevelOfResponsibility.ADMINISTRATOR);
        motorFleet.setManager(asim);
        assertEquals(motorFleet.getNameOfMotorFleet(),"Vozni park");
        assertNull(motorFleet.getEmployeeList());
        assertNotNull(motorFleet.getManager());
        assertNull(motorFleet.getServiceList());
        assertNull(motorFleet.getStorageList());
        assertNull(motorFleet.getVehicleList());
    }

    @Test
    public void addingMethodsTest(){
        MotorFleet motorFleet = new MotorFleet("Vozni park", null,null,null,null,null);
        User asim = new User("Asim","Haskic","000/000-000","Fakultet br.1",LevelOfResponsibility.ADMINISTRATOR);
        motorFleet.setManager(asim);
        assertEquals(motorFleet.getNameOfMotorFleet(),"Vozni park");

        List<User> userList = new ArrayList<>();
        userList.add(asim);
        motorFleet.setEmployeeList(userList);

        Vehicle vehicle = new MotorVehicle("VW","000-0-000", "Blue", null);
        TrailerVehicle vehicle2 = new TrailerVehicle("Krone","000-0-001", "Blue", null,50);
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle);
        vehicleList.add(vehicle2);
        motorFleet.setVehicleList(vehicleList);

        Storage storage = new Storage("Skladiste",asim,null);
        List<Storage> storageList = new ArrayList<>();
        storageList.add(storage);
        motorFleet.setStorageList(storageList);

        ServiceManagement serviceManagement = new ServiceManagement(null,vehicle2);
        List<ServiceManagement> serviceManagementList = new ArrayList<>();
        serviceManagementList.add(serviceManagement);
        motorFleet.setServiceList(serviceManagementList);

        assertEquals(motorFleet.getEmployeeList().size(),1);
        assertEquals(motorFleet.getServiceList().size(),1);
        assertEquals(motorFleet.getStorageList().size(),1);
        assertEquals(motorFleet.getVehicleList().size(),2);

        HeavyVehicle vehicle3 = new HeavyVehicle("Daf","000-0-002", "White", null,55);
        motorFleet.addVehicle(vehicle3);
        assertEquals(motorFleet.getVehicleList().size(),3);

        Storage storage2 = new Storage("Skladiste2",asim,null);
        motorFleet.addStorage(storage2);
        assertEquals(motorFleet.getStorageList().size(),2);

        ServiceManagement serviceManagemen2 = new ServiceManagement(null,vehicle);
        motorFleet.addServiceManagment(serviceManagemen2);
        assertEquals(motorFleet.getServiceList().size(),2);

        User vedran = new User("Vedran","Ljubovic","000/000-001","Fakultet br.2",LevelOfResponsibility.ADMINISTRATOR);
        motorFleet.addUser(vedran);
        assertEquals(motorFleet.getEmployeeList().size(),2);
    }
}
