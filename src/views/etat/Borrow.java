
package views.etat;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import model.Inventory;
import model.MiniProjectException;
import model.User;
import views.Command;
import views.State;
import views.View;

/**
 * The Class Borrow.
 */
public class Borrow extends State {

    /** The equipments. */
    private final List<String> equipments = new ArrayList<>( );

    /** The start. */
    private final Calendar     start      = Calendar.getInstance( );

    /** The end. */
    private final Calendar     end        = Calendar.getInstance( );

    /**
     * Adds the.
     *
     * @param id
     *            the id
     */
    @SuppressWarnings( "unused" )
    private void add( final String id ) {
        this.equipments.add( id );
        System.out.println( "Equipment " + id + " added" );

    }

    /**
     * Cancel.
     */
    private void cancel( ) {
        View.setState( new Borrower( ) );
    }

    /**
     * Removes the.
     *
     * @param id
     *            the id
     */
    @SuppressWarnings( "unused" )
    private void remove( final String id ) {

        this.equipments.remove( id );
        System.out.println( "Equipment " + id + " removed" );

    }

    /*
     * (non-Javadoc)
     *
     * @see views.IView#setCommands()
     */
    @Override
    public List<Command> setCommands( ) {

        final List<Command> commands = new ArrayList<>( );

        final List<String> args1 = new LinkedList<>( );
        args1.add( "equipmentId" );
        final Command command1 = new Command( "add", args1, this, "add",
                "Ajoute un equipement a la liste de la demande d'emprunt" );
        final Command command2 = new Command( "remove", args1, this, "add",
                "Supprime un equipement de la liste de la demande d'emprunt" );

        final List<String> args3 = new LinkedList<>( );
        args3.add( "dd/MM/yyyy" );
        args3.add( "hh/mm" );
        final Command command3 = new Command( "start", args3, this, "setStart",
                "Defini le debut de la demande d'emprunt" );

        final List<String> args4 = new LinkedList<>( );
        args4.add( "dd/MM/yyyy" );
        args4.add( "hh/mm" );
        final Command command4 = new Command( "end", args4, this, "setEnd",
                "Defini la fin de la demande d'emprunt" );

        final Command command5 = new Command( "validate",
                new LinkedList<String>( ), this, "validate",
                "Confirme la demande d'emprunt" );
        final Command command6 = new Command( "cancel",
                new LinkedList<String>( ), this, "cancel",
                "Annule la demande d'emprunt" );
        final Command command7 = new Command( "show",
                new LinkedList<String>( ), this, "show",
                "Affiche la demande d'emprunt" );

        commands.add( command1 );
        commands.add( command2 );
        commands.add( command3 );
        commands.add( command4 );
        commands.add( command5 );
        commands.add( command6 );
        commands.add( command7 );
        commands.addAll( super.setCommands( ) );

        return commands;

    }

    /**
     * Sets the end.
     *
     * @param stringDayMonthYear
     *            the string day month year
     * @param stringHourMinute
     *            the string hour minute
     */
    @SuppressWarnings( "unused" )
    private void setEnd( final String stringDayMonthYear,
            final String stringHourMinute ) {

        final String[ ] dayMonthYear = stringDayMonthYear.split( "/" );
        final String[ ] hourMinute = stringHourMinute.split( ":" );
        if( ( dayMonthYear.length != 3 ) || ( hourMinute.length != 2 ) ) {
            this.printHelp( );
        }

        this.end.set( Integer.valueOf( dayMonthYear[ 2 ] ),
                Integer.valueOf( dayMonthYear[ 1 ] ) - 1,
                Integer.valueOf( dayMonthYear[ 0 ] ),
                Integer.valueOf( hourMinute[ 0 ] ),
                Integer.valueOf( hourMinute[ 1 ] ), 0 );

        System.out.println( "end date set" );

    }

    /**
     * Sets the start.
     *
     * @param stringDayMonthYear
     *            the string day month year
     * @param stringHourMinute
     *            the string hour minute
     */
    @SuppressWarnings( "unused" )
    private void setStart( final String stringDayMonthYear,
            final String stringHourMinute ) {

        final String[ ] dayMonthYear = stringDayMonthYear.split( "/" );
        final String[ ] hourMinute = stringHourMinute.split( ":" );
        if( ( dayMonthYear.length != 3 ) || ( hourMinute.length != 2 ) ) {
            this.printHelp( );
        }

        this.start.set( Integer.valueOf( dayMonthYear[ 2 ] ),
                Integer.valueOf( dayMonthYear[ 1 ] ) - 1,
                Integer.valueOf( dayMonthYear[ 0 ] ),
                Integer.valueOf( hourMinute[ 0 ] ),
                Integer.valueOf( hourMinute[ 1 ] ), 0 );
        System.out.println( "start date set" );

    }

    /**
     * Show.
     */
    @SuppressWarnings( "unused" )
    private void show( ) {
        final SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm" );
        final String show = "equipment:" + this.equipments + "\n" + "start:"
                + format.format( this.start.getTime( ) ) + "\n" + "end:"
                + format.format( this.end.getTime( ) );
        System.out.println( show );

    }

    /**
     * Validate.
     */
    @SuppressWarnings( "unused" )
    private void validate( ) {
        if( Inventory.isBorrowed(this.equipments, this.start, this.end) ) {
            System.out.println( "Equipment unavailable" );
            return;
        }

        try {
            ( ( model.person.Borrower ) Inventory.findPersonById( User
                    .getInstance( ).getPersonId( ) ) ).borrow( this.equipments,
                    this.start, this.end );
            System.out.println( "Borrowed." );
            this.cancel( );
        } catch( InvalidParameterException | MiniProjectException e ) {
            System.out.println( e.getMessage( ) );
        }

    }
}
