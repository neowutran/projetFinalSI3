
package lib;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * Author : Fabien Pinel
 */
public class ArrayUtilsTest {

    @BeforeClass
    public static void onlyOnce() {

    }

    @Test
    public void concatenateTest() {

        final String tabOne[] = { "1", "2", "3" };
        final String tabTwo[] = { "4", "5", "6" };
        final String[] tabtab = lib.ArrayUtils.concatenate(tabOne, tabTwo);
        Assert.assertEquals(tabOne.length + tabTwo.length, tabtab.length);
    }
}
