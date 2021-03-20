package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ServiceTest {

    @Test
    public void constructorTest(){
        LocalDate date = LocalDate.of(2000,1,1);
        Service service = new Service("Mali servis",date,"Asim Haskic","Uradjeno sve sto se tice malog servisa");
        assertEquals(service.getNameOfService(),"Mali servis");
        assertEquals(service.getDateOfService(),date);
        assertEquals(service.getServicePerson(),"Asim Haskic");
    }

    @Test
    public void setterMethodTest(){
        LocalDate date = LocalDate.of(2000,1,1);
        Service service = new Service("Mali servis",date,"Asim Haskic","Uradjeno sve sto se tice malog servisa");
        assertEquals(service.getNameOfService(),"Mali servis");
        service.setNameOfService("Veliki servis");
        assertNotEquals(service.getNameOfService(),"Mali servis");
        assertEquals(service.getDateOfService(),date);
        LocalDate date2 = LocalDate.of(2000,1,2);
        service.setDateOfService(date2);
        assertNotEquals(service.getDateOfService(),date);
        assertEquals(service.getServicePerson(),"Asim Haskic");
        service.setServicePerson("Vedran Ljubovic");
        assertNotEquals(service.getServicePerson(),"Asim Haskic");
    }

}
