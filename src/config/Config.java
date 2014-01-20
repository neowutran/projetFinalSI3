/*
 * @author Martini Didier
 */

package config;

/**
 * The Class Config.
 */
public final class Config {

    /** The Constant EQUIPMENT. */
    public static final String                   EQUIPMENT           = "equipment";

    /** The Constant BORROWER. */
    public static final String                   BORROWER            = "borrower";

    /** The Constant MAXIMUM_ADVANCE_DAY. */
    public static final String                   MAXIMUM_ADVANCE_DAY = "maximum_advance_days";

    /** The Constant MAXIMUM_HOUR. */
    public static final String                   MAXIMUM_HOUR        = "maximum_hours";

    /** The Constant FEATURES. */
    public static final String                   FEATURES            = "features";

    /** The Constant TEMPLATE. */
    public static final String                   TEMPLATE            = "template";

    /** The Constant BORROW. */
    public static final String                   BORROW              = "borrow";

    /** The Constant FORMAT. */
    public static final String                   FORMAT              = "format";

    /** The Constant PERSON. */
    public static final String                   PERSON              = "person";

    /** The Constant QUANTITY_TIME. */
    public static final String                   QUANTITY_TIME       = "quantity_time";

    /** The Constant MAXIMUM_BORROW_TIME. */
    public static final String                   MAXIMUM_BORROW_TIME = "maximum_borrow_time";

    /** The Constant RANDOM_COUNTER. */
    public static final String                   RANDOM_COUNTER      = "random_counter";

    /** The configuration. */
    private static java.util.Map<String, Object> configuration;

    /**
     * Gets the configuration.
     *
     * @return the configuration
     */
    public static java.util.Map<String, Object> getConfiguration( ) {

        return config.Config.configuration;
    }

    /**
     * Sets the configuration.
     *
     * @param configuration the configuration
     */
    public static void setConfiguration(
            final java.util.Map<String, Object> configuration ) {

        config.Config.configuration = configuration;
    }

    /**
     * Instantiates a new config.
     */
    private Config( ) {

    }

}
