
package model.finder;

/**
 * The Interface IInteger.
 */
public interface IInteger {

    /**
     * Greater than.
     *
     * @param object the object
     * @return true, if successful
     */
    boolean greaterThan( String object );

    /**
     * Greater than or equals.
     *
     * @param object the object
     * @return true, if successful
     */
    boolean greaterThanOrEquals( String object );

    /**
     * Checks if is equals.
     *
     * @param object the object
     * @return true, if is equals
     */
    boolean isEquals( String object );

    /**
     * Lesser than.
     *
     * @param object the object
     * @return true, if successful
     */
    boolean lesserThan( String object );

    /**
     * Lesser than or equals.
     *
     * @param object the object
     * @return true, if successful
     */
    boolean lesserThanOrEquals( String object );

}
