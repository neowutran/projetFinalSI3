/*
 * @author Martini Didier
 */

package controllers;

import model.MiniProjectException;
import model.SaveLoad;
import views.View;

/**
 * The Class MiniProjectController.
 */
public class MiniProjectController {

    /** The instance. */
    private static MiniProjectController         instance = null;

    /** The Constant LOGGER. */
    public static final java.util.logging.Logger LOGGER   = java.util.logging.Logger
                                                                  .getLogger( "MiniProject" );

    /**
     * Gets the single instance of MiniProjectController.
     * 
     * @return single instance of MiniProjectController
     */
    public static MiniProjectController getInstance( ) {
        if( MiniProjectController.instance == null ) {
            MiniProjectController.instance = new MiniProjectController( );
        }
        return MiniProjectController.instance;
    }

    /**
     * Instantiates a new mini project controller.
     */
    protected MiniProjectController( ) {
        this.loggingConfig( );

        try {
            SaveLoad.load( SaveLoad.DATA );
        } catch( final MiniProjectException e ) {
            MiniProjectController.LOGGER.severe( java.util.Arrays.toString( e
                    .getStackTrace( ) ) );
        }
        View.launch( );

    }

    /**
     * Logging config.
     */
    private void loggingConfig( ) {

        MiniProjectController.LOGGER.setLevel( java.util.logging.Level.INFO );
        final java.util.logging.XMLFormatter xmlFormatter = new java.util.logging.XMLFormatter( );
        java.util.logging.FileHandler logFile = null;
        try {
            logFile = new java.util.logging.FileHandler( "log.xml" );
        } catch( SecurityException | java.io.IOException e ) {

            MiniProjectController.LOGGER.severe( java.util.Arrays.toString( e
                    .getStackTrace( ) ) );
        }

        if( logFile != null ) {
            logFile.setFormatter( xmlFormatter );
        }
        MiniProjectController.LOGGER.addHandler( logFile );

    }

}
