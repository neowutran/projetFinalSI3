
package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import controllers.MiniProjectController;
import demonstrateur.MiniProject;

/**
 * Created by neowutran on 22/01/14. Edited by Fabien Pinel
 */
public class FeatureTest {

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
    public void testIsDoubleValue() throws Exception {

        final Feature f1 = new Feature("OperatingSystem", "tablet", "Windows");
        Assert.assertTrue(!f1.getIsDoubleValue());
    }

    @Test
    public void testIsEquals() throws Exception {

        final Feature f1 = new Feature("OperatingSystem", "tablet", "Windows");
        final Feature f2 = new Feature("OperatingSystem", "tablet", "Windows");
        Assert.assertTrue(f1.isEquals(f2.getValue()));
    }
}
