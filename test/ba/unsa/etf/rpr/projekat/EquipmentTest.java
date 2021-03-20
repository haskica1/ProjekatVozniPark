package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EquipmentTest {

    @Test
    public void constructorTest(){
        Equipment firstAid = new Equipment("Prva pomoc","Obavezna oprema");
        assertEquals(firstAid.getNameOfEquipment(),"Prva pomoc");
        assertEquals(firstAid.getInformationOfEquipment(),"Obavezna oprema");
    }

    @Test
    public void setterMethodsTest(){
        Equipment firstAid = new Equipment("Prva pomoc","Obavezna oprema");
        assertEquals(firstAid.getNameOfEquipment(),"Prva pomoc");
        firstAid.setNameOfEquipment("Prva pomoc 2");
        assertNotEquals(firstAid.getNameOfEquipment(),"Prva pomoc");
        assertEquals(firstAid.getInformationOfEquipment(),"Obavezna oprema");
        firstAid.setInformationOfEquipment("Obavezna oprema 2");
        assertNotEquals(firstAid.getInformationOfEquipment(),"Obavezna oprema");
    }
}
