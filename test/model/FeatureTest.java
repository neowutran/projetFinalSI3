package model;

import static org.junit.Assert.*;
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
public class FeatureTest {

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
    public void testIsEquals() throws Exception {
    	Feature f1 = new Feature("nom", "tablet", "Windows");
    	Feature f2 = new Feature("nom2", "tablet", "Windows");
    	assertTrue(f1.equals(f2));
    	
    }

    @Test
    public void testIsDoubleValue() throws Exception {
        //TODO
    }
}
