
package model.person;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.BorrowState;
import model.Inventory;
import model.MiniProjectException;
import model.SaveLoad;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import controllers.MiniProjectController;
import demonstrateur.MiniProject;

/**
 * Created by neowutran on 22/01/14.
 */
public class BorrowTest {

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
    public void testCheckExistence() throws Exception {

    }

    @Test
    public void testSetState() throws Exception {

        final model.person.Borrower b = new model.person.Borrower("test",
                "1110", "student", "test");
        final List<String> liste = new ArrayList<String>();
        liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
        final GregorianCalendar date1 = new GregorianCalendar();
        final GregorianCalendar date2 = new GregorianCalendar();
        date2.add(Calendar.DAY_OF_YEAR, 1);
        final String id = b.borrow(liste, date1, date2);
        Inventory.findBorrowById(id).setState(BorrowState.ACCEPT, "000");
        Assert.assertEquals(Inventory.findBorrowById(id).getState(),
                BorrowState.ACCEPT);
    }
}
