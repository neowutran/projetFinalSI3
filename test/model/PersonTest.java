package model;

import controllers.MiniProjectController;
import demonstrateur.MiniProject;
import model.person.Administrator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by neowutran on 22/01/14.
 */
public class PersonTest {

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
    public void testExist() {
        assertTrue(Person.exist("123"));
        assertFalse(Person.exist("blabloubliblop"));

    }

    @Test
    public void testConstructor(){

        try {
            new Administrator("tutu", "9000", "pass");
            assertTrue(false);
        } catch (MiniProjectException e) {

        }
        try {
            new Administrator("tata", "tata", "pass");
        } catch (MiniProjectException e) {
            assertTrue(false);
        }

    }
}
