package model;

import static org.junit.Assert.*;
import controllers.MiniProjectController;
import demonstrateur.MiniProject;
import model.person.Administrator;
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
 * edited by Fabien Pinel
 */
public class InventoryTest {
	static String boId = "";
	
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
    	model.person.Borrower b = new model.person.Borrower("test","3333", "student","test");
    	List<String> liste = new ArrayList<String>();
    	liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
    	GregorianCalendar date1 = new GregorianCalendar();
    	GregorianCalendar date2 = new GregorianCalendar();
    	date2.add(GregorianCalendar.DAY_OF_YEAR,2);
    	String id = b.borrow(liste, date1, date2);
    	
    	Administrator admin = new Administrator("name", "000", "pass");
    	admin.setBorrowStat(Inventory.findBorrowById(id), BorrowState.ACCEPT);
    	assertTrue(Inventory.isBorrowed(liste, date1, date2));
    	admin.setBorrowStat(Inventory.findBorrowById(id), BorrowState.RETURNED);
    }

    @Test
    public void testFind() throws Exception {
    	List<Equipment> ret = Inventory.find("tablet", new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
    	//cet ID correspond à l'item qui devrait etre trouvé
    	assertEquals(ret.get(0).getId(), "ca072236-8f15-486d-93e4-2b21abb831a5");
    }

    @Test
    public void testFindActualBorrowByBorrower() throws Exception {
    	model.person.Borrower b = new model.person.Borrower("test","4444", "student","test");
    	List<String> liste = new ArrayList<String>();
    	liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
    	GregorianCalendar date1 = new GregorianCalendar();
    	GregorianCalendar date2 = new GregorianCalendar();
    	date2.add(GregorianCalendar.MONTH,1);
    	boId = b.borrow(liste, date1, date2);
        List<Borrower.Borrow> borrows = Inventory.findActualBorrowByBorrower("4444");
       assertTrue(borrows.get(0).getEquipmentId().get(0).equals("ca072236-8f15-486d-93e4-2b21abb831a5"));
    }

    @Test
    public void testFindAvailable() throws Exception {
    	GregorianCalendar cal = new GregorianCalendar();
    	cal.add(Calendar.DAY_OF_YEAR,1);
    	List<Equipment> eq = Inventory.findAvailable(new GregorianCalendar(), cal);
    	assertTrue(!eq.get(0).getId().equals("ca072236-8f15-486d-93e4-2b21abb831a5"));
    }

    @Test
    public void testFindBorrowByBorrower() throws Exception {
    	List<Borrower.Borrow> bo = Inventory.findBorrowByBorrower("4444");
    	assertEquals(boId, bo.get(0).getId());
    }

    @Test
    public void testFindBorrowById() throws Exception {
    	model.person.Borrower b = new model.person.Borrower("test","8888", "student","test");
    	List<String> liste = new ArrayList<String>();
    	liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
    	GregorianCalendar date1 = new GregorianCalendar();
    	GregorianCalendar date2 = new GregorianCalendar();
    	date2.add(GregorianCalendar.MONTH,1);
    	boId = b.borrow(liste, date1, date2);
    	
    	Borrower.Borrow bobo = Inventory.findBorrowById(boId);
    	assertEquals(bobo.getEquipmentId().get(0), "ca072236-8f15-486d-93e4-2b21abb831a5");
    }

    @Test
    public void testFindBorrowWaitingForAdministrator() throws Exception {
        //TODO
    }

    @Test
    public void testFindEquipmentById() throws Exception {
        Equipment e = Inventory.findEquipmentById("ca072236-8f15-486d-93e4-2b21abb831a5");
        assertEquals(e.getId(), "ca072236-8f15-486d-93e4-2b21abb831a5");
        assertEquals(e.getType(), "tablet");
    }

    @Test
    public void testFindEquipmentUnderRepair() throws Exception {
    	 Equipment e = Inventory.findEquipmentById("ca072236-8f15-486d-93e4-2b21abb831a5");
    	 e.setHealth(new Health(HealthState.NOT_OK));
    	 e.setUnderRepair(true);
    	 List<Equipment> liste = Inventory.findEquipmentUnderRepair();
    	 assertEquals(liste.get(0).getId(),"ca072236-8f15-486d-93e4-2b21abb831a5");
    }

    @Test
    public void testFindEquipmentWhoNeedRepair() throws Exception {
    	Equipment e = Inventory.findEquipmentById("ca072236-8f15-486d-93e4-2b21abb831a5");
   	 	e.setHealth(new Health(HealthState.NOT_OK));
   	 	e.setUnderRepair(false);
   	 	List<Equipment> listeWNR = Inventory.findEquipmentWhoNeedRepair();
   	 	assertEquals(listeWNR.get(0).getId(),e.getId());
    }

    @Test
    public void testFindLateBorrow() throws Exception {
    	model.person.Borrower b = new model.person.Borrower("test","5555", "student","test");
    	List<String> liste = new ArrayList<String>();
    	liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
    	GregorianCalendar date1 = new GregorianCalendar();
    	GregorianCalendar date2 = new GregorianCalendar();
    	date2.add(GregorianCalendar.DAY_OF_YEAR,1);
    	String id = b.borrow(liste, date1, date2);
    	Inventory.findBorrowById(id).getBorrowEnd().set(Calendar.MONTH, -2);
    	List<Borrower.Borrow> listeLate = Inventory.findLateBorrow();
    	assertEquals(listeLate.get(1).getId(), id);
    	
    }

    @Test
    public void testFindPersonById() throws Exception {
        assertEquals(Inventory.findPersonById("1111").getName(), "admin");
    }

    @Test
    public void testFindQuantityEquipment() throws Exception {
    	Equipment equip = new Equipment("tablet", new ArrayList<Feature>(), new Health(HealthState.NOT_OK), false);
        assertEquals((int)Inventory.findQuantityEquipment(equip), 1);
    }

    @Test
    public void testIsBorrowed() throws Exception {
    	model.person.Borrower b = new model.person.Borrower("test","6666", "student","test");
    	List<String> liste = new ArrayList<String>();
    	liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
    	GregorianCalendar date1 = new GregorianCalendar();
    	GregorianCalendar date2 = new GregorianCalendar();
    	date2.add(GregorianCalendar.DAY_OF_YEAR,1);
    	String id = b.borrow(liste, date1, date2);
    	Administrator admin = new Administrator("name", "0001", "pass");
    	admin.setBorrowStat(Inventory.findBorrowById(id), BorrowState.ACCEPT);
    	assertTrue(Inventory.isBorrowed(liste,date1, date2));
    	admin.setBorrowStat(Inventory.findBorrowById(id), BorrowState.RETURNED);
    	
    }

    @Test
    public void testIsBorrower() throws Exception {
    	model.person.Borrower b = new model.person.Borrower("test","7777", "student","test");
    	assertTrue(Inventory.isBorrower(b.getId()));
    }

    @Test
    public void testAddEquipment() throws Exception {
    	Equipment e = new Equipment("tablet", new ArrayList<Feature>(), new Health(HealthState.OK), false);
    	int nbTablettes = (int)Inventory.findQuantityEquipment(e);
    	Inventory.addEquipment("tablet", "1");
    	nbTablettes++;
    	assertEquals((int)Inventory.findQuantityEquipment(e),nbTablettes);
    }

    @Test
    public void testFindBorrowWithEquipmentUnderRepair() throws Exception{
    	model.person.Borrower b = new model.person.Borrower("test","9999", "student","test");
    	List<String> liste = new ArrayList<String>();
    	liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
    	GregorianCalendar date1 = new GregorianCalendar();
    	GregorianCalendar date2 = new GregorianCalendar();
    	date2.add(GregorianCalendar.DAY_OF_YEAR,1);
    	String id = b.borrow(liste, date1, date2);
    	Equipment e = Inventory.findEquipmentById("ca072236-8f15-486d-93e4-2b21abb831a5");
   	 	e.setHealth(new Health(HealthState.NOT_OK));
   	 	e.setUnderRepair(true);
   	 	List<Borrower.Borrow> listeEquipRepair = Inventory.findBorrowWithEquipmentUnderRepair();
   	 	assertEquals(listeEquipRepair.get(0).getId(),id);
        
    }

    @Test
    public void testFindBorrowWithEquipmentNotOk() throws Exception{
    	model.person.Borrower b = new model.person.Borrower("test","1010", "student","test");
    	List<String> liste = new ArrayList<String>();
    	liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
    	GregorianCalendar date1 = new GregorianCalendar();
    	GregorianCalendar date2 = new GregorianCalendar();
    	date2.add(GregorianCalendar.DAY_OF_YEAR,1);
    	String id = b.borrow(liste, date1, date2);
    	Equipment e = Inventory.findEquipmentById("ca072236-8f15-486d-93e4-2b21abb831a5");
   	 	e.setHealth(new Health(HealthState.NOT_OK));
   	 	List<Borrower.Borrow> listeEquipNotOK = Inventory.findBorrowWithEquipmentNotOk();
   	 	assertEquals(listeEquipNotOK.get(listeEquipNotOK.size()-1).getId(),id);
    }
}
