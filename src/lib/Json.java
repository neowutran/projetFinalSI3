
package lib;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import com.google.gson.Gson;

import controllers.MiniProjectController;

/**
 * The Class Json.
 */
public final class Json {

    /**
     * Load file.
     * 
     * @param file
     *            the file
     * @return the map
     */
    public static Map loadFile( final Path file ) {

        // Read the file
        final java.nio.charset.Charset charset = java.nio.charset.Charset
                .forName( "UTF-8" );
        try( java.io.BufferedReader reader = Files.newBufferedReader( file,
                charset ) ) {
            String line;
            String text = "";
            while( ( line = reader.readLine( ) ) != null ) {
                text += line;
            }
            // Convert the JSON file to an java object
            final Gson gson = new Gson( );
            return gson.fromJson( text, java.util.Map.class );
        } catch( final java.io.IOException x ) {

            MiniProjectController.LOGGER.severe( java.util.Arrays.toString( x
                    .getStackTrace( ) ) );
            return null;
        }
    }

    /**
     * Instantiates a new json.
     */
    private Json( ) {
    }

}
