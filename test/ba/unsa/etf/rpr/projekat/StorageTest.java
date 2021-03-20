package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {

    @Test
    public void constructorTest(){
        Storage storage = new Storage("Skladiste",null,null);
        assertEquals(storage.getNameOfStorage(),"Skladiste");
    }

    @Test
    public void constructorTest2(){
        User asim = new User("Asim","Haskic","000/000-000","Fakultet br.1",LevelOfResponsibility.ADMINISTRATOR);
        Storage storage = new Storage("Skladiste",asim,null);
        assertEquals(storage.getManager().getFirstName(),"Asim");
    }

    @Test
    public void constructorTest3(){
        Part accumulator = new Part(1,"Akumulator",true);
        List<Part> partList = new ArrayList<>();
        partList.add(accumulator);
        User asim = new User("Asim","Haskic","000/000-000","Fakultet br.1",LevelOfResponsibility.ADMINISTRATOR);
        Storage storage = new Storage("Skladiste",asim,partList);
        assertEquals(storage.getParts().size(),1);
    }

    @Test
    public void addingPartsTest(){
        Part accumulator = new Part(1,"Akumulator",true);
        List<Part> partList = new ArrayList<>();
        partList.add(accumulator);
        User asim = new User("Asim","Haskic","000/000-000","Fakultet br.1",LevelOfResponsibility.ADMINISTRATOR);
        Storage storage = new Storage("Skladiste",asim,partList);
        assertEquals(storage.getParts().size(),1);
        Part engine = new Part(2,"Motor", false);
        storage.addPart(engine);
        assertEquals(storage.getParts().size(),2);
    }
}
