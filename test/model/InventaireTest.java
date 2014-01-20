package model;

import demonstrateur.*;
import org.junit.*;

import java.lang.reflect.*;
import java.nio.file.*;

/**
 * Created by draragar on 20/11/13.
 */
public class InventaireTest {

    @Before
    public void setUp() {

        try {
            final Method m = MiniProject.class.getDeclaredMethod("loadConfigFile",
                    Path.class);
            m.setAccessible(true);
            m.invoke(null, Paths.get(MiniProject.FOLDER, MiniProject.CONFIG));

        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testAddEmprunt() throws Exception {


    }
}
