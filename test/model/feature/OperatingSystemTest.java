package model.feature;

import demonstrateur.*;
import org.hamcrest.*;
import org.junit.*;

import java.lang.reflect.*;
import java.nio.file.*;

/**
 * Created by draragar on 18/11/13.
 */
public class OperatingSystemTest {

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
    public void testEquals() throws Exception {

        OperatingSystem os1 = new OperatingSystem("Linux");
        OperatingSystem os2 = new OperatingSystem("Linux");
        OperatingSystem os3 = new OperatingSystem("Windows");
        boolean equals1 = os1.equals(os2);
        boolean equals2 = os1.equals(os3);
        Assert.assertThat(true, CoreMatchers.equalTo(equals1));
        Assert.assertThat(false, CoreMatchers.equalTo(equals2));

    }

    @Test
    public void testConstructor() throws Exception {

        try {
            OperatingSystem os = new OperatingSystem("Windows");
            //BON
        } catch (Exception e) {
            //PAS BON
            Assert.assertThat(true, CoreMatchers.equalTo(false));
        }

        try {
            OperatingSystem os = new OperatingSystem("Windows7");
            //PAS BON
            Assert.assertThat(false, CoreMatchers.equalTo(true));
        } catch (Exception e) {
            //BON
        }

    }
}
