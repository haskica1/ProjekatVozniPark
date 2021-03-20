package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {
    @Test
    public void motorVehicleTest(){
        Vehicle vehicle = new MotorVehicle("VW","000-0-000", "Blue", null);
        assertEquals(vehicle.getVehicleName(),"VW");
        assertEquals(vehicle.getLicencePlate(),"000-0-000");
        assertEquals(vehicle.getColor(),"Blue");
    }
    @Test
    public void traileVehicleTest(){
        TrailerVehicle vehicle = new TrailerVehicle("Krone","000-0-001", "Blue", null,50);
        assertEquals(vehicle.getVehicleName(),"Krone");
        assertEquals(vehicle.getLicencePlate(),"000-0-001");
        assertEquals(vehicle.getColor(),"Blue");
        assertEquals(vehicle.getTransportCapacity(),50);
    }
    @Test
    public void heavyVehicleTest(){
        HeavyVehicle vehicle = new HeavyVehicle("Daf","000-0-002", "White", null,55);
        assertEquals(vehicle.getVehicleName(),"Daf");
        assertEquals(vehicle.getLicencePlate(),"000-0-002");
        assertEquals(vehicle.getColor(),"White");
        assertEquals(vehicle.getTransportCapacity(),55);
    }
    @Test
    public void heavyVehicleTest2(){
        Vehicle vehicle = new HeavyVehicle("Volvo","000-0-003", "Green", null,55);
        assertEquals(vehicle.getVehicleName(),"Volvo");
        assertEquals(vehicle.getLicencePlate(),"000-0-003");
        assertEquals(vehicle.getColor(),"Green");
        assertEquals(((HeavyVehicle) vehicle).getTransportCapacity(),55);
    }
    @Test
    public void equipmentVehicleTest(){
        Equipment firstAid = new Equipment("Prva pomoc","Obavezna oprema");
        Equipment jack = new Equipment("Dizalica","Obavezna oprama");
        Equipment cable = new Equipment("Sajla","Obavezna oprema");
        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(firstAid);
        equipmentList.add(jack);
        equipmentList.add(cable);
        HeavyVehicle vehicle = new HeavyVehicle("Daf","000-0-002", "White", equipmentList,55);
        assertEquals(vehicle.getVehicleName(),"Daf");
        assertEquals(vehicle.getLicencePlate(),"000-0-002");
        assertEquals(vehicle.getColor(),"White");
        assertEquals(vehicle.getTransportCapacity(),55);
        assertEquals(vehicle.getEquipmentList().size(),3);
        Equipment spareWheel = new Equipment("Rezervna guma","Obavezna oprema");
        vehicle.addEquipment(spareWheel);
        assertEquals(vehicle.getEquipmentList().size(),4);
    }

}
