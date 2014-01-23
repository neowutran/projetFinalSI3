package model;

import static org.junit.Assert.*;
import controllers.MiniProjectController;
import demonstrateur.MiniProject;
import model.person.Borrower;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by neowutran on 22/01/14.
 */
public class InventoryTest {
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

            SaveLoad.load(SaveLoad.DATA);
        } catch( final MiniProjectException e ) {
            MiniProjectController.LOGGER.severe( java.util.Arrays.toString( e
                    .getStackTrace( ) ) );
        }
    }

    @Test
    public void testAddBorrow() throws Exception {
    	model.person.Borrower b = new model.person.Borrower("test","3333", SaveLoad.PERSON_TYPE_BORROWER,"test");
    	List<String> liste = new ArrayList<String>();
    	liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
    	GregorianCalendar date1 = new GregorianCalendar();
    	GregorianCalendar date2 = new GregorianCalendar();
    	date2.add(GregorianCalendar.MONTH,1);
    	b.borrow(liste, date1, date2);
    	assertTrue(Inventory.isBorrowed(liste, date1, date2));
    }

    @Test
    public void testFind() throws Exception {
    	List<Equipment> ret = Inventory.find("tablet", new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
    	//cet ID correspond à l'item qui devrait etre trouvé
    	assertEquals(ret.get(0).getId(), "ca072236-8f15-486d-93e4-2b21abb831a5");
    }

    @Test
    public void testFindActualBorrowByBorrower() throws Exception {
    	model.person.Borrower b = new model.person.Borrower("test","4444", SaveLoad.PERSON_TYPE_BORROWER,"test");
    	List<String> liste = new ArrayList<String>();
    	liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
    	GregorianCalendar date1 = new GregorianCalendar();
    	GregorianCalendar date2 = new GregorianCalendar();
    	date2.add(GregorianCalendar.MONTH,1);
    	b.borrow(liste, date1, date2);
        List<Borrower.Borrow> borrows = Inventory.findActualBorrowByBorrower("4444");
        
       assertTrue(borrows.get(0).getEquipmentId().equals("ca072236-8f15-486d-93e4-2b21abb831a5"));
    }

    @Test
    public void testFindAvailable() throws Exception {
        //TODO
    }

    @Test
    public void testFindBorrowByBorrower() throws Exception {
        //TODO
    }

    @Test
    public void testFindBorrowById() throws Exception {
        //TODO
    }

    @Test
    public void testFindBorrowWaitingForAdministrator() throws Exception {
        //TODO
    }

    @Test
    public void testFindEquipmentById() throws Exception {
        //TODO
    }

    @Test
    public void testFindEquipmentUnderRepair() throws Exception {
        //TODO
    }

    @Test
    public void testFindEquipmentWhoNeedRepair() throws Exception {
        //TODO
    }

    @Test
    public void testFindLateBorrow() throws Exception {
        //TODO
    }

    @Test
    public void testFindPersonById() throws Exception {
        //TODO
    }

    @Test
    public void testFindQuantityEquipment() throws Exception {
        //TODO
    }

    @Test
    public void testIsBorrowed() throws Exception {
        //TODO
    }

    @Test
    public void testIsBorrower() throws Exception {
        //TODO
    }

    @Test
    public void testAddEquipment() throws Exception {
        //TODO
    }

    @Test
    public void testFindBorrowWithEquipmentUnderRepair() throws Exception{
        //TODO
    }

    @Test
    public void testFindBorrowWithEquipmentNotOk() throws Exception{
        //TODO
    }
}
