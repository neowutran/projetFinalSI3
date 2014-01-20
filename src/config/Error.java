
package config;

import java.util.Map;

/**
 * The Class Error.
 */
public final class Error {

    /** The Constant CANNOT_CREATE_PERSON. */
    public static final String CANNOT_CREATE_PERSON      = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "1" );

    /** The Constant PERSON_ALREADY_EXIST. */
    public static final String PERSON_ALREADY_EXIST      = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "2" );

    /** The Constant CANNOT_USE_THIS_OPERATOR. */
    public static final String CANNOT_USE_THIS_OPERATOR  = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "3" );

    /** The Constant FEATURE_DOESNT_EXIST. */
    public static final String FEATURE_DOESNT_EXIST      = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "4" );

    /** The Constant NOT_SAME_SIZE. */
    public static final String NOT_SAME_SIZE             = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "5" );

    /** The Constant INVALID_ID. */
    public static final String INVALID_ID                = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "6" );

    /** The Constant EQUIPMENT_DO_NOT_EXIST. */
    public static final String EQUIPMENT_DO_NOT_EXIST    = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "7" );

    /** The Constant FEATURE_EQUIPMENT_INVALID. */
    public static final String FEATURE_EQUIPMENT_INVALID = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "8" );

    /** The Constant EQUIPMENT_ALREADY_EXIST. */
    public static final String EQUIPMENT_ALREADY_EXIST   = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "9" );

    /** The Constant RANDOM_ERROR. */
    public static final String RANDOM_ERROR              = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "10" );

    /** The Constant UNIMPLEMENTED. */
    public static final String UNIMPLEMENTED             = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "11" );

    /** The Constant CANNOT_BORROW_SO_LONG. */
    public static final String CANNOT_BORROW_SO_LONG     = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "12" );

    /** The Constant INVALID_DATE. */
    public static final String INVALID_DATE              = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "13" );

    /** The Constant EQUIPMENT_UNAVAILABLE. */
    public static final String EQUIPMENT_UNAVAILABLE     = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "14" );

    /** The Constant CANNOT_BORROW_ADVANCE. */
    public static final String CANNOT_BORROW_ADVANCE     = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "15" );

    /** The Constant OS_DO_NOT_EXIST. */
    public static final String OS_DO_NOT_EXIST           = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "16" );

    /** The Constant METHOD_NOT_FOUND. */
    public static final String METHOD_NOT_FOUND          = ( String ) ( ( Map ) Config
                                                                 .getConfiguration( )
                                                                 .get( "error" ) )
                                                                 .get( "17" );

    /**
     * Instantiates a new error.
     */
    private Error( ) {

    }

}
