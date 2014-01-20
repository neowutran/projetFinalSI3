
package views.etat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.Equipment;
import model.Inventory;
import model.MiniProjectException;
import views.Command;
import views.State;
import views.View;

/**
 * The Class Find.
 */

public class Find extends State {

    /** The features. */
    private final List<String> features  = new LinkedList<>( );

    /** The operators. */
    private final List<String> operators = new LinkedList<>( );

    /** The values. */
    private final List<String> values    = new LinkedList<>( );

    /** The type. */
    private String             type;

    /**
     * Adds the.
     *
     * @param feature
     *            the feature
     * @param operator
     *            the operator
     * @param value
     *            the value
     */
    @SuppressWarnings( "unused" )
    private void add( final String feature, final String operator,
            final String value ) {
        this.features.add( feature );
        this.operators.add( operator );
        this.values.add( value );
        System.out.println( "Condition: " + feature + " " + operator + " "
                + value + " added" );

    }

    /**
     * Cancel.
     */
    private void cancel( ) {
        if( Inventory.isBorrower(model.User.getInstance().getPersonId()) ) {
            System.out.println( "back to the borrower interface" );
            View.setState( new Borrower( ) );

        } else {
            System.out.println( "back to the administrator interface" );
            View.setState( new Administrator( ) );

        }
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
        args1.add( "feature" );
        args1.add( "operator" );
        args1.add( "value" );
        final Command command1 = new Command(
                "add",
                args1,
                this,
                "add",
                "Ajoute une condition a la recherche (toute les conditions sont liées par un && , pas de moyen de faire de || ni d'ajouter de parenthese.).\n\t Les operateurs disponible sont: '=', '>=', '<=', '<', '>'.\n\t Exemple d'utilisation:\n\t\t add OperatingSystem = Windows" );

        final List<String> args2 = new LinkedList<>( );
        args2.add( "type" );
        final Command command2 = new Command( "type", args2, this, "type",
                "Defini le type du materiel (exemple: 'tablet') " );

        final Command command5 = new Command( "validate",
                new LinkedList<String>( ), this, "validate",
                "Effectue la recherche" );
        final Command command6 = new Command( "cancel",
                new LinkedList<String>( ), this, "cancel",
                "Annule la demande de recherche" );
        final Command command7 = new Command( "show",
                new LinkedList<String>( ), this, "show",
                "Affiche la demande de recherche" );

        commands.add( command1 );
        commands.add( command2 );
        commands.add( command5 );
        commands.add( command6 );
        commands.add( command7 );
        commands.addAll( super.setCommands( ) );

        return commands;

    }

    /**
     * Show.
     */
    @SuppressWarnings( "unused" )
    private void show( ) {

        final String show = "type:" + this.type + "\n" + "features:"
                + this.features + "\n" + "operators" + this.operators + "\n"
                + "values" + this.values;
        System.out.println( show );

    }

    /**
     * Validate.
     */
    @SuppressWarnings( "unused" )
    private void validate( ) {

        try {
            final List<Equipment> equipments = Inventory.find( this.type,
                    this.features, this.operators, this.values );
            System.out.println( equipments );
            this.cancel( );
        } catch( final MiniProjectException e ) {
            System.out.println( e.getMessage( ) );
        }

    }

}
