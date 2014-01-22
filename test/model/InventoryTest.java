package model;

import controllers.MiniProjectController;
import demonstrateur.MiniProject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        //TODO
    }

    @Test
    public void testFind() throws Exception {
        //TODO
    }

    @Test
    public void testFindActualBorrowByBorrower() throws Exception {
        //TODO
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
