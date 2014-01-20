package model;

import com.google.gson.*;
import demonstrateur.*;
import model.feature.*;
import org.hamcrest.*;
import org.junit.*;

import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;

/**
 * Created by draragar on 18/11/13.
 */
public class MaterielTest {

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
    public void testSetAttributs() throws Exception {

    }

    @Test
    public void testEquals() throws Exception{

        Feature c1 = new OperatingSystem("Windows");
        Feature c2 = new OperatingSystem("Linux");
        List<Feature> lc1 = new ArrayList<>();
        lc1.add(c1);
        List<Feature> lc2 = new ArrayList<>();
        lc2.add(c2);

        Feature c3 = new OperatingSystem("Windows");
        List<Feature> lc3 = new ArrayList<>();
        lc3.add(c3);

        Equipment m1 = new Equipment("tablet",lc1);
        Equipment m2 = new Equipment("tablet", lc1);
        Equipment m3 = new Equipment("tablet", lc1);
        Equipment m4 = new Equipment("phone", lc1);
        Equipment m5 = new Equipment("tablet", lc2);
        Equipment m6 = new Equipment("tablet", lc3);

        boolean e1 = m1.equals(m2);
        boolean e2 = m1.equals(m3);
        boolean e3 = m1.equals(m4);
        boolean e4 = m1.equals(m1);
        boolean e5 = m1.equals(m5);
        boolean e6 = m1.equals(m6);

        Assert.assertThat(true, CoreMatchers.equalTo(e1));
        Assert.assertThat(true, CoreMatchers.equalTo(e2));
        Assert.assertThat(false, CoreMatchers.equalTo(e3));
        Assert.assertThat(true, CoreMatchers.equalTo(e4));
        Assert.assertThat(false, CoreMatchers.equalTo(e5));
        Assert.assertThat(true, CoreMatchers.equalTo(e6));


        final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        System.out.println(gson.toJson(m1));



    }

    @Test
    public void testConstructor() throws Exception {

        Equipment materiel;


        try {
            materiel = new Equipment("tablet", null);
            //BON
        } catch (Exception e) {
            //Pas BON
            Assert.assertThat(true, CoreMatchers.equalTo(false));

        }


        List<Feature> caracteristiques = new ArrayList<>();
        caracteristiques.add(new OperatingSystem("Windows", "tablet"));

        try{
            materiel = new Equipment("tablet", caracteristiques);
        }catch (Exception e){
            Assert.assertThat(true, CoreMatchers.equalTo(false));
            //PAS BON

        }

        try{
            materiel = new Equipment("headphone", caracteristiques);
            Assert.assertThat(true, CoreMatchers.equalTo(false));
            //PAS BON

        }catch(Exception e){
            //BON
        }

        try{
            materiel = new Equipment("Tabblette", caracteristiques);
            Assert.assertThat(true, CoreMatchers.equalTo(false));
        }catch (Exception e){

        }

    }
}
