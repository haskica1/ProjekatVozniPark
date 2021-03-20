package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PartTest {

    @Test
    public void constuctorTest(){
        Part accumulator = new Part(1,"Akumulator",true);
        assertEquals(accumulator.getNameOfPart(),"Akumulator");
        assertEquals(accumulator.isUsefulPart(),true);
    }

    @Test
    public void setterMethodTest(){
        Part accumulator = new Part(1,"Akumulator",true);
        assertEquals(accumulator.getNameOfPart(),"Akumulator");
        assertEquals(accumulator.isUsefulPart(),true);
        accumulator.setUsefulPart(false);
        assertNotEquals(accumulator.isUsefulPart(),true);
    }
}
