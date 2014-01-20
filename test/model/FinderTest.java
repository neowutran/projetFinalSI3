package model;

import static org.junit.Assert.*;
import controllers.*;
import demonstrateur.*;

import org.junit.*;

import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FinderTest {

    @BeforeClass
    public static void onlyOnce() {

        try {
            final Method m = MiniProject.class.getDeclaredMethod("loadConfigFile",
                    Path.class);
            m.setAccessible(true);
            m.invoke(null, Paths.get(MiniProject.FOLDER, MiniProject.CONFIG));

        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }

        try {

            SaveLoad.load( SaveLoad.DATA );
        } catch( final MiniProjectException e ) {
            MiniProjectController.LOGGER.severe( java.util.Arrays.toString( e
                    .getStackTrace( ) ) );
        }
    }



    @Test
    public void testFindActualBorrowByBorrower() {
       assertEquals(Inventory.findActualBorrowByBorrower("123").size(), 1);
    }

    @Test
    public void testFindAvailable() {

        GregorianCalendar start = new GregorianCalendar(2013,10,2);
        GregorianCalendar end = new GregorianCalendar(2013,10,7);
        assertEquals(Inventory.findAvailable(start, end).size(),1);
        assertEquals(Inventory.findAvailable(new GregorianCalendar(2013,12,2),new GregorianCalendar(2013,12,4)).size(),3);
    }

    @Test
    public void testFindBorrowByBorrower() {
        assertEquals(Inventory.findBorrowByBorrower("123").size(),1);
    }

    @Test
    public void testFindBorrowById() {

        assertNotNull(Inventory.findBorrowById("e69f2659-b0c8-4dc3-8d70-63a221fd20c2"));
        //Verifie que la fonction ne prend que l id de l emprunteur et non de l objet
        assertNull(Inventory.findBorrowById("ca072236-8f15-486d-93e4-2b21abb831a5"));
    }

    @Test
    public void testFindBorrowWaitingForAdministrator() {
      assertEquals(Inventory.findBorrowWaitingForAdministrator().size(),1);
    }

    @Test
    public void testFindEquipmentById() {
        assertEquals(Inventory.findEquipmentById("ca072236-8f15-486d-93e4-2b21abb831a5").getType(),"tablet");
    }

    @Test
    public void testFindLateBorrow() {
        assertEquals(Inventory.findLateBorrow().get(0).getId(),"e69f2659-b0c8-4dc3-8d70-63a221fd20c2");
    }

    @Test
    public void testFind(){

        List<String> features = new LinkedList<>();
        features.add("OperatingSystem");
        List<String> operateurs = new LinkedList<>();
        operateurs.add("=");
        List<String> values = new LinkedList<>();
        values.add("Windows");
        try {
            //TODO tester
            assertEquals(Inventory.find(null,features,operateurs,values).size(),2);
        } catch (MiniProjectException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindPersonById() {
        assertEquals(Inventory.findPersonById("123").getId(),"123");
        assertEquals(Inventory.findPersonById("123").getName(),"Toto");
        assertEquals(Inventory.findPersonById("123").getType(),"student");
    }

    @Test
    public void testIsBorrowed() {
        ArrayList<String> equipmentsId = new ArrayList();
        equipmentsId.add("ca072236-8f15-486d-93e4-2b21abb831a5");
        equipmentsId.add("95984d07-575a-49d1-a6cd-d964c9a38ffe");
        assertEquals(Inventory.isBorrowed(equipmentsId, new GregorianCalendar(2013,10,2), new GregorianCalendar(2013,10,7)),false);
    }

    @Test
    public void testIsBorrower() {
        assertEquals(Inventory.isBorrower("123"),true);
        assertEquals(Inventory.isBorrower("9000"),false);

    }

}
