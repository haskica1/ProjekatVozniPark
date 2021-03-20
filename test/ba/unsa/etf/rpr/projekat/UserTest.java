package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserTest {

    @Test
    public void constructorTest(){
        User asim = new User("Asim","Haskic","000/000-000","Fakultet br.1",LevelOfResponsibility.ADMINISTRATOR);
        User vedran = new User("Vedran","Ljubovic","000/000-001","Fakultet br.2",LevelOfResponsibility.ADMINISTRATOR);
        assertEquals(asim.getFirstName(),"Asim");
        assertEquals(vedran.getFirstName(),"Vedran");
        assertEquals(asim.getLastName(),"Haskic");
        assertEquals(vedran.getTelephoneNumber(),"000/000-001");
        assertEquals(asim.getAddress(),"Fakultet br.1");
    }
    @Test
    public void settersTest(){
        User asim = new User("Asim","Haskic","000/000-000","Fakultet br.1",LevelOfResponsibility.ADMINISTRATOR);
        User vedran = new User("Vedran","Ljubovic","000/000-001","Fakultet br.2",LevelOfResponsibility.ADMINISTRATOR);
        assertEquals(asim.getFirstName(),"Asim");
        asim.setFirstName("Asim2");
        assertNotEquals(asim.getFirstName(),"Asim");
        assertEquals(vedran.getFirstName(),"Vedran");
        assertEquals(asim.getLastName(),"Haskic");
        assertEquals(vedran.getTelephoneNumber(),"000/000-001");
        vedran.setTelephoneNumber("000/000-100");
        assertNotEquals(vedran.getTelephoneNumber(),"000/000-001");
        assertEquals(asim.getAddress(),"Fakultet br.1");
        asim.setAddress("Fakultet br.100");
        assertNotEquals(asim.getAddress(),"Fakultet br.1");
    }
}
