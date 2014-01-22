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
public class EquipmentTest {

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
    public void testSetHealth() throws Exception {
        //TODO
    }

    @Test
    public void testSetUnderRepair() throws Exception {
        //TODO
    }

    @Test
    public void testCheckExistence() throws Exception {
        //TODO
    }

    @Test
    public void testEquals() throws Exception {
        //TODO
    }

    @Test
    public void testSetFeatures() throws Exception {
        //TODO
    }

}