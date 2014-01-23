/*
 * @author Martini Didier - Fabien Pinel - Maxime Touroute
 */

package lib;

import java.lang.reflect.Array;

/**
 * The Class ArrayUtils.
 */
public class ArrayUtils {

    /**
     * Concatenate.
     * 
     * @param <T>
     *            the generic type
     * @param arrayOne
     *            the a
     * @param arrayTwo
     *            the b
     * @return the t[]
     */
    public static <T> T[] concatenate(final T[] arrayOne, final T[] arrayTwo) {

        final int aLen = arrayOne.length;
        final int bLen = arrayTwo.length;
        @SuppressWarnings("unchecked")
        final T[] arrayThree = (T[]) Array.newInstance(arrayOne.getClass()
                .getComponentType(), aLen + bLen);
        System.arraycopy(arrayOne, 0, arrayThree, 0, aLen);
        System.arraycopy(arrayTwo, 0, arrayThree, aLen, bLen);
        return arrayThree;
    }

    /**
     * Instantiates a new array utils.
     */
    private ArrayUtils() {

    }
}
