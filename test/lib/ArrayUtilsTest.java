package lib;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/*
 *  Author : Fabien Pinel
 */
public class ArrayUtilsTest {
	
	@BeforeClass
    public static void onlyOnce() {
	
    }
	@Test
	public void concatenateTest() {
		String tabOne[] = {"1","2","3"};
		String tabTwo[] = {"4","5","6"};
		String[] tabtab = lib.ArrayUtils.concatenate(tabOne, tabTwo);
		assertEquals(tabOne.length+tabTwo.length, tabtab.length);
	}

}
