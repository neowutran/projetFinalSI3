package model;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;

import model.person.Borrower;
import model.person.Borrower.Borrow;

import org.junit.*;

import controllers.MiniProjectController;
import demonstrateur.MiniProject;

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

            SaveLoad.load( SaveLoad.DATA );
        } catch( final MiniProjectException e ) {
            MiniProjectController.LOGGER.severe( java.util.Arrays.toString( e
                    .getStackTrace( ) ) );
        }
    }


    @Test
    public void testAddBorrow() {
       
      Inventory.getInstance().addBorrow(null);
    }

    @Test
    public void testGetBorrows() {
        assertEquals(Inventory.getInstance().getBorrows().get(0).getBorrowerId(),"123");
    }

    @Test
    public void testGetEquipments() {
        assertEquals(Inventory.getInstance().getEquipments().size(),3);
        assertEquals(Inventory.getInstance().getEquipments().get(0).getType(),"tablet");
        assertEquals(Inventory.getInstance().getEquipments().get(1).getType(),"tablet");
        assertEquals(Inventory.getInstance().getEquipments().get(2).getType(),"phone");
    }

}
