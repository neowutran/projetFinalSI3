
package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import config.Error;
import controllers.MiniProjectController;
import demonstrateur.MiniProject;

/**
 * Created by neowutran on 22/01/14. Edited by Fabien Pinel
 */
public class EquipmentTest {

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

        final Equipment e = new Equipment("tablet", new ArrayList<Feature>(),
                new Health(HealthState.NOT_OK), false);
        final Equipment second = e;
        try {
            second.checkExistence(second.getId());
        } catch (final InvalidParameterException ex) {
            final InvalidParameterException test = new InvalidParameterException(
                    Error.EQUIPMENT_ALREADY_EXIST);
            Assert.assertEquals(ex.getMessage(), test.getMessage());
        }
    }

    @Test
    public void testEquals() throws Exception {

        final Equipment e1 = new Equipment("tablet", new ArrayList<Feature>(),
                new Health(HealthState.NOT_OK), false);
        final Equipment e2 = new Equipment("tablet", new ArrayList<Feature>(),
                new Health(HealthState.NOT_OK), false);
        Assert.assertTrue(e1.equals(e2));
    }

    @Test
    public void testSetFeatures() throws Exception {

        final Equipment e1 = new Equipment("tablet", new ArrayList<Feature>(),
                new Health(HealthState.NOT_OK), false);
        final List<Feature> f = new ArrayList<Feature>();
        e1.setFeatures(f);
        Assert.assertEquals(e1.getFeatures(), f);
    }

    /**
     * @author Fabien Pinel
     * @throws Exception
     */
    @Test
    public void testSetHealth() throws Exception {

        final Health h = new Health(HealthState.OK);
        final Equipment e = new Equipment("tablet", new ArrayList<Feature>(),
                new Health(HealthState.NOT_OK), false);
        e.setHealth(h);
        Assert.assertEquals(h, e.getHealth());
    }

    @Test
    public void testSetUnderRepair() throws Exception {

        final Equipment e = new Equipment("tablet", new ArrayList<Feature>(),
                new Health(HealthState.NOT_OK), false);
        e.setUnderRepair(true);
        Assert.assertEquals(true, e.getUnderRepair());
    }
}
