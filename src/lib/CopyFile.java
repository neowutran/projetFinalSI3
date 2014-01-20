/*
 * @author Martini Didier
 */

package lib;

import java.io.InputStream;

import controllers.MiniProjectController;

/**
 * The Class CopyFile.
 */
public final class CopyFile {

    /** The Constant BUFFER_SIZE. */
    private static final int BUFFER_SIZE = 1024;

    /**
     * Copy file.
     * 
     * @param sources
     *            the sources
     * @param destination
     *            the destination
     */
    public static void copyFile( final InputStream sources,
            final String destination ) {

        try {
            final java.io.File f2 = new java.io.File( destination );
            final java.io.OutputStream out = new java.io.FileOutputStream( f2 );
            final byte[ ] buf = new byte[ CopyFile.BUFFER_SIZE ];
            int len;
            while( ( len = sources.read( buf ) ) > 0 ) {
                out.write( buf, 0, len );
            }
            sources.close( );
            out.close( );
        } catch( final java.io.IOException ex ) {
            MiniProjectController.LOGGER.severe( java.util.Arrays.toString( ex
                    .getStackTrace( ) ) );

        }
    }

    /**
     * Instantiates a new copy file.
     */
    private CopyFile( ) {
    }

}
