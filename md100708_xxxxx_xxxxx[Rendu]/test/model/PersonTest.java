
package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;

import model.person.Administrator;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import controllers.MiniProjectController;
import demonstrateur.MiniProject;

/**
 * Created by neowutran on 22/01/14.
 */
public class PersonTest {

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
    public void testConstructor() {

        try {
            new Administrator("tutu", "9000", "pass");
            Assert.assertTrue(false);
        } catch (final MiniProjectException e) {
        }
        try {
            new Administrator("tata", "tata", "pass");
        } catch (final MiniProjectException e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testExist() {

        Assert.assertTrue(Person.exist("123"));
        Assert.assertFalse(Person.exist("blabloubliblop"));
    }
}
