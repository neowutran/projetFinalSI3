
package views.etat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import model.Inventory;
import views.Command;
import views.State;
import views.View;

/**
 * The Class User.
 */
public abstract class User extends State {

    /**
     * Find.
     */
    public void find( ) {

        View.setState( new Find( ) );
    }

    /**
     * List_available_equipment.
     *
     * @param startDayMonthYear
     *            the start day month year
     * @param startHourMinute
     *            the start hour minute
     * @param endDayMonthYear
     *            the end day month year
     * @param endHourMinute
     *            the end hour minute
     */
    public void listAvailableEquipment( final String startDayMonthYear,
            final String startHourMinute, final String endDayMonthYear,
            final String endHourMinute ) {

        final Calendar start = Calendar.getInstance( );
        final Calendar end = Calendar.getInstance( );

        final String[ ] stringStartDayMonthYear = startDayMonthYear.split( "/" );
        final String[ ] stringStartHourMinute = startHourMinute.split( ":" );

        final String[ ] stringEndDayMonthYear = endDayMonthYear.split( "/" );
        final String[ ] stringEndHourMinute = endHourMinute.split( ":" );

        if( ( stringEndDayMonthYear.length != 3 )
                || ( stringEndHourMinute.length != 2 )
                || ( stringStartDayMonthYear.length != 3 )
                || ( stringStartHourMinute.length != 2 ) ) {
            this.printHelp( );
            return;
        }

        start.set( Integer.valueOf( stringStartDayMonthYear[ 2 ] ),
                Integer.valueOf( stringStartDayMonthYear[ 1 ] ) - 1,
                Integer.valueOf( stringStartDayMonthYear[ 0 ] ),
                Integer.valueOf( stringStartHourMinute[ 0 ] ),
                Integer.valueOf( stringStartHourMinute[ 1 ] ), 0 );

        end.set( Integer.valueOf( stringEndDayMonthYear[ 2 ] ),
                Integer.valueOf( stringEndDayMonthYear[ 1 ] ) - 1,
                Integer.valueOf( stringEndDayMonthYear[ 0 ] ),
                Integer.valueOf( stringEndHourMinute[ 0 ] ),
                Integer.valueOf( stringEndHourMinute[ 1 ] ), 0 );

        System.out.println( Inventory.findAvailable( start, end ) );
    }

    /**
     * List_borrow.
     */
    public void listBorrow( ) {

        System.out.println( Inventory.getInstance( ).getBorrows( ) );
    }

    /**
     * List_equipment.
     */
    public void listEquipment( ) {

        System.out.println( Inventory.getInstance( ).getEquipments( ) );
    }

    /**
     * Logout.
     */
    public void logout( ) {

        model.User.getInstance( ).logout( );
        View.setState( new Main( ) );
        System.out.println( "logged out" );
    }

    /*
     * (non-Javadoc)
     *
     * @see views.State#setCommands()
     */
    @Override
    public List<Command> setCommands( ) {

        final List<Command> commands = new ArrayList<>( );

        final Command command3 = new Command( "logout",
                new LinkedList<String>( ), this, "logout", "Obvious" );
        final Command command4 = new Command( "listBorrow",
                new LinkedList<String>( ), this, "listBorrow",
                "Affiche la liste des emprunts" );
        final Command command5 = new Command( "listEquipment",
                new LinkedList<String>( ), this, "listEquipment",
                "Affiche la liste des equipements" );

        final List<String> args6 = new LinkedList<>( );
        args6.add( "borrowId" );
        final Command command6 = new Command( "showBorrow", args6, this,
                "showBorrow", "Affiche un emprunt" );

        final List<String> args7 = new LinkedList<>( );
        args7.add( "equipmentId" );
        final Command command7 = new Command( "showEquipment", args7, this,
                "showEquipment", "Affiche un equipement" );

        final List<String> args8 = new LinkedList<>( );
        args8.add( "personId" );
        final Command command8 = new Command( "showPerson", args8, this,
                "showPerson", "Affiche les informations sur une personne" );

        final List<String> args9 = new LinkedList<>( );
        args9.add( "dd/MM/yyyy" );
        args9.add( "hh:mm" );
        args9.add( "dd/MM/yyyy" );
        args9.add( "hh:mm" );
        final Command command9 = new Command(
                "listAvailableEquipment",
                args9,
                this,
                "listAvailableEquipment",
                "Affiche les equipement actuellement disponible à l'emprunt (les dates a donnes sont 'debut' puis 'fin' de l'emprunt" );

        final Command command10 = new Command( "find",
                new LinkedList<String>( ), this, "find",
                "Rentre en mode de recherche avancé" );

        commands.add( command3 );
        commands.add( command4 );
        commands.add( command5 );
        commands.add( command6 );
        commands.add( command7 );
        commands.add( command8 );
        commands.add( command9 );
        commands.add( command10 );

        commands.addAll( super.setCommands( ) );

        return commands;
    }

    /**
     * Show_borrow.
     *
     * @param id
     *            the id
     */
    public void showBorrow( final String id ) {

        System.out.println( Inventory.findBorrowById( id ) );

    }

    /**
     * Show_equipment.
     *
     * @param id
     *            the id
     */
    public void showEquipment( final String id ) {

        System.out.println( Inventory.findEquipmentById( id ) );

    }

    /**
     * Show_person.
     *
     * @param id
     *            the id
     */
    public void showPerson( final String id ) {

        System.out.println( Inventory.findPersonById( id ) );

    }

}
