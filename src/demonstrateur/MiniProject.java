/*
 * @author Martini Didier
 */

package demonstrateur;

import java.nio.file.Path;
import java.nio.file.Paths;

import lib.CopyFile;
import lib.Json;
import controllers.MiniProjectController;

/**
 * The Class MiniProject.
 */
public final class MiniProject {

    /** The Constant FOLDER. */
    public static final String FOLDER = "MiniProject" + java.io.File.separator;

    /** The Constant CONFIG. */
    public static final String CONFIG = "config.json";

    /** The Constant DATA. */
    public static final String DATA   = "data.json";

    /**
     * Load config file.
     * 
     * @param configFile
     *            the config file
     */
    private static void loadConfigFile( final Path configFile ) {

        config.Config.setConfiguration( Json.loadFile( configFile ) );

    }

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public static void main( final String[ ] args ) {

        new MiniProject( );

    }

    /**
     * Instantiates a new mini project.
     */
    private MiniProject( ) {

        // Create the config folder
        if( !new java.io.File( MiniProject.FOLDER ).isDirectory( ) ) {
            this.createConfigFolder( );
        }
        // Copy the default config file into the folder if no config file was
        // found
        if( !new java.io.File( MiniProject.FOLDER + MiniProject.CONFIG )
                .exists( ) ) {

            CopyFile.copyFile( this.getClass( ).getClassLoader( )
                    .getResourceAsStream( MiniProject.CONFIG ),
                    MiniProject.FOLDER + MiniProject.CONFIG );
            CopyFile.copyFile( this.getClass( ).getClassLoader( )
                    .getResourceAsStream( MiniProject.DATA ),
                    MiniProject.FOLDER + MiniProject.DATA );
        }
        MiniProject.loadConfigFile( Paths.get( MiniProject.FOLDER,
                MiniProject.CONFIG ) );
        MiniProjectController.getInstance( );

    }

    /**
     * Creates the config folder.
     */
    private void createConfigFolder( ) {

        new java.io.File( MiniProject.FOLDER ).mkdirs( );
    }
}
