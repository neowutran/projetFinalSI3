
package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.person.Administrator;
import model.person.Borrower;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import controllers.MiniProjectController;
import demonstrateur.MiniProject;

/**
 * Created by neowutran on 22/01/14. edited by Fabien Pinel
 */
public class InventoryTest {

    static String boId = "";

    @BeforeClass
    public static void onlyOnce() {

        try {
            final Method m = MiniProject.class.getDeclaredMethod(
                    "loadConfigFile", Path.class);
            m.setAccessible(true);
            m.invoke(null, Paths.get(MiniProject.FOLDER, MiniProject.CONFIG));
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
        try {
            SaveLoad.load(SaveLoad.DATA);
        } catch (final MiniProjectException e) {
            MiniProjectController.LOGGER.severe(java.util.Arrays.toString(e
                    .getStackTrace()));
        }
    }

    @Test
    public void testAddBorrow() throws Exception {

        final model.person.Borrower b = new model.person.Borrower("test",
                "3333", "student", "test");
        final List<String> liste = new ArrayList<String>();
        liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
        final GregorianCalendar date1 = new GregorianCalendar();
        final GregorianCalendar date2 = new GregorianCalendar();
        date2.add(Calendar.DAY_OF_YEAR, 2);
        final String id = b.borrow(liste, date1, date2);
        final Administrator admin = new Administrator("name", "000", "pass");
        admin.setBorrowStat(Inventory.findBorrowById(id), BorrowState.ACCEPT);
        Assert.assertTrue(Inventory.isBorrowed(liste, date1, date2));
        admin.setBorrowStat(Inventory.findBorrowById(id), BorrowState.RETURNED);
    }

    @Test
    public void testAddEquipment() throws Exception {

        final Equipment e = new Equipment("tablet", new ArrayList<Feature>(),
                new Health(HealthState.OK), false);
        int nbTablettes = Inventory.findQuantityEquipment(e);
        Inventory.addEquipment("tablet", "1");
        nbTablettes++;
        Assert.assertEquals((int) Inventory.findQuantityEquipment(e),
                nbTablettes);
    }

    @Test
    public void testFind() throws Exception {

        final List<Equipment> ret = Inventory.find("tablet",
                new ArrayList<String>(), new ArrayList<String>(),
                new ArrayList<String>());
        // cet ID correspond à l'item qui devrait etre trouvé
        Assert.assertEquals(ret.get(0).getId(),
                "ca072236-8f15-486d-93e4-2b21abb831a5");
    }

    @Test
    public void testFindActualBorrowByBorrower() throws Exception {

        final model.person.Borrower b = new model.person.Borrower("test",
                "4444", "student", "test");
        final List<String> liste = new ArrayList<String>();
        liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
        final GregorianCalendar date1 = new GregorianCalendar();
        final GregorianCalendar date2 = new GregorianCalendar();
        date2.add(Calendar.MONTH, 1);
        InventoryTest.boId = b.borrow(liste, date1, date2);
        final List<Borrower.Borrow> borrows = Inventory
                .findActualBorrowByBorrower("4444");
        Assert.assertTrue(borrows.get(0).getEquipmentId().get(0)
                .equals("ca072236-8f15-486d-93e4-2b21abb831a5"));
    }

    @Test
    public void testFindAvailable() throws Exception {

        final GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        final List<Equipment> eq = Inventory.findAvailable(
                new GregorianCalendar(), cal);
        Assert.assertTrue(!eq.get(0).getId()
                .equals("ca072236-8f15-486d-93e4-2b21abb831a5"));
    }

    @Test
    public void testFindBorrowByBorrower() throws Exception {

        final List<Borrower.Borrow> bo = Inventory.findBorrowByBorrower("4444");
        Assert.assertEquals(InventoryTest.boId, bo.get(0).getId());
    }

    @Test
    public void testFindBorrowById() throws Exception {

        final model.person.Borrower b = new model.person.Borrower("test",
                "8888", "student", "test");
        final List<String> liste = new ArrayList<String>();
        liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
        final GregorianCalendar date1 = new GregorianCalendar();
        final GregorianCalendar date2 = new GregorianCalendar();
        date2.add(Calendar.MONTH, 1);
        InventoryTest.boId = b.borrow(liste, date1, date2);
        final Borrower.Borrow bobo = Inventory
                .findBorrowById(InventoryTest.boId);
        Assert.assertEquals(bobo.getEquipmentId().get(0),
                "ca072236-8f15-486d-93e4-2b21abb831a5");
    }

    @Test
    public void testFindBorrowWaitingForAdministrator() throws Exception {

        // TODO
    }

    @Test
    public void testFindBorrowWithEquipmentNotOk() throws Exception {

        final model.person.Borrower b = new model.person.Borrower("test",
                "1010", "student", "test");
        final List<String> liste = new ArrayList<String>();
        liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
        final GregorianCalendar date1 = new GregorianCalendar();
        final GregorianCalendar date2 = new GregorianCalendar();
        date2.add(Calendar.DAY_OF_YEAR, 1);
        final String id = b.borrow(liste, date1, date2);
        final Equipment e = Inventory
                .findEquipmentById("ca072236-8f15-486d-93e4-2b21abb831a5");
        e.setHealth(new Health(HealthState.NOT_OK));
        final List<Borrower.Borrow> listeEquipNotOK = Inventory
                .findBorrowWithEquipmentNotOk();
        Assert.assertEquals(listeEquipNotOK.get(listeEquipNotOK.size() - 1)
                .getId(), id);
    }

    @Test
    public void testFindBorrowWithEquipmentUnderRepair() throws Exception {

        final model.person.Borrower b = new model.person.Borrower("test",
                "9999", "student", "test");
        final List<String> liste = new ArrayList<String>();
        liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
        final GregorianCalendar date1 = new GregorianCalendar();
        final GregorianCalendar date2 = new GregorianCalendar();
        date2.add(Calendar.DAY_OF_YEAR, 1);
        final String id = b.borrow(liste, date1, date2);
        final Equipment e = Inventory
                .findEquipmentById("ca072236-8f15-486d-93e4-2b21abb831a5");
        e.setHealth(new Health(HealthState.NOT_OK));
        e.setUnderRepair(true);
        final List<Borrower.Borrow> listeEquipRepair = Inventory
                .findBorrowWithEquipmentUnderRepair();
        Assert.assertEquals(listeEquipRepair.get(0).getId(), id);
    }

    @Test
    public void testFindEquipmentById() throws Exception {

        final Equipment e = Inventory
                .findEquipmentById("ca072236-8f15-486d-93e4-2b21abb831a5");
        Assert.assertEquals(e.getId(), "ca072236-8f15-486d-93e4-2b21abb831a5");
        Assert.assertEquals(e.getType(), "tablet");
    }

    @Test
    public void testFindEquipmentUnderRepair() throws Exception {

        final Equipment e = Inventory
                .findEquipmentById("ca072236-8f15-486d-93e4-2b21abb831a5");
        e.setHealth(new Health(HealthState.NOT_OK));
        e.setUnderRepair(true);
        final List<Equipment> liste = Inventory.findEquipmentUnderRepair();
        Assert.assertEquals(liste.get(0).getId(),
                "ca072236-8f15-486d-93e4-2b21abb831a5");
    }

    @Test
    public void testFindEquipmentWhoNeedRepair() throws Exception {

        final Equipment e = Inventory
                .findEquipmentById("ca072236-8f15-486d-93e4-2b21abb831a5");
        e.setHealth(new Health(HealthState.NOT_OK));
        e.setUnderRepair(false);
        final List<Equipment> listeWNR = Inventory.findEquipmentWhoNeedRepair();
        Assert.assertEquals(listeWNR.get(0).getId(), e.getId());
    }

    @Test
    public void testFindLateBorrow() throws Exception {

        final model.person.Borrower b = new model.person.Borrower("test",
                "5555", "student", "test");
        final List<String> liste = new ArrayList<String>();
        liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
        final GregorianCalendar date1 = new GregorianCalendar();
        final GregorianCalendar date2 = new GregorianCalendar();
        date2.add(Calendar.DAY_OF_YEAR, 1);
        final String id = b.borrow(liste, date1, date2);
        Inventory.findBorrowById(id).getBorrowEnd().set(Calendar.MONTH, -2);
        final List<Borrower.Borrow> listeLate = Inventory.findLateBorrow();
        Assert.assertEquals(listeLate.get(1).getId(), id);
    }

    @Test
    public void testFindPersonById() throws Exception {

        Assert.assertEquals(Inventory.findPersonById("1111").getName(), "admin");
    }

    @Test
    public void testFindQuantityEquipment() throws Exception {

        final Equipment equip = new Equipment("tablet",
                new ArrayList<Feature>(), new Health(HealthState.NOT_OK), false);
        Assert.assertEquals((int) Inventory.findQuantityEquipment(equip), 1);
    }

    @Test
    public void testIsBorrowed() throws Exception {

        final model.person.Borrower b = new model.person.Borrower("test",
                "6666", "student", "test");
        final List<String> liste = new ArrayList<String>();
        liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
        final GregorianCalendar date1 = new GregorianCalendar();
        final GregorianCalendar date2 = new GregorianCalendar();
        date2.add(Calendar.DAY_OF_YEAR, 1);
        final String id = b.borrow(liste, date1, date2);
        final Administrator admin = new Administrator("name", "0001", "pass");
        admin.setBorrowStat(Inventory.findBorrowById(id), BorrowState.ACCEPT);
        Assert.assertTrue(Inventory.isBorrowed(liste, date1, date2));
        admin.setBorrowStat(Inventory.findBorrowById(id), BorrowState.RETURNED);
    }

    @Test
    public void testIsBorrower() throws Exception {

        final model.person.Borrower b = new model.person.Borrower("test",
                "7777", "student", "test");
        Assert.assertTrue(Inventory.isBorrower(b.getId()));
    }
}
