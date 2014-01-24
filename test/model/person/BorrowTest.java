package model.person;

import static org.junit.Assert.*;
import controllers.MiniProjectController;
import demonstrateur.MiniProject;
import model.BorrowState;
import model.Inventory;
import model.MiniProjectException;
import model.SaveLoad;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by neowutran on 22/01/14.
 */
public class BorrowTest {

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
    public void testCheckExistence() throws Exception {
    	
    	
    }

    @Test
    public void testSetState() throws Exception {
    	model.person.Borrower b = new model.person.Borrower("test","1110", "student","test");
    	List<String> liste = new ArrayList<String>();
    	liste.add("ca072236-8f15-486d-93e4-2b21abb831a5");
    	GregorianCalendar date1 = new GregorianCalendar();
    	GregorianCalendar date2 = new GregorianCalendar();
    	date2.add(GregorianCalendar.DAY_OF_YEAR,1);
    	String id = b.borrow(liste, date1, date2);
    	Inventory.findBorrowById(id).setState(BorrowState.ACCEPT, "000");
    	assertEquals(Inventory.findBorrowById(id).getState(), BorrowState.ACCEPT);

    }

}
