/*
 * @author Martini Didier
 */

package views;

import java.util.Scanner;

import views.etat.Main;

/**
 * The Class View.
 */
public class View {

    /** The state. */
    private static State   state = new Main( );

    /** The exit. */
    private static Boolean exit  = false;

    /**
     * Exit.
     */
    public static void exit( ) {
        View.exit = true;
    }

    /**
     * Gets the state.
     * 
     * @return the state
     */
    public static State getState( ) {

        return View.state;
    }

    /**
     * Launch.
     */
    public static void launch( ) {
        System.out
                .println( "Welcome to miniProject version 0.1 (pray for not having a bug)" );
        View.getState( ).printHelp( );

        View.readLine( );
    }

    /**
     * Read line.
     */
    private static void readLine( ) {

        final Scanner scanner = new Scanner( System.in );

        while( !View.exit ) {

            final String line = scanner.nextLine( );
            View.state.interpreter( line );

        }
        scanner.close( );

    }

    /**
     * Sets the state.
     * 
     * @param state
     *            the new state
     */
    public static void setState( final State state ) {
        View.state = state;
    }

    /**
     * Instantiates a new view.
     */
    private View( ) {
    }
}
