
package views.etat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.Inventory;
import views.Command;
import views.State;
import views.View;

/**
 * The Class Main.
 */
public class Main extends State {

    /**
     * Login.
     *
     * @param id
     *            the id
     * @param password
     *            the password
     */
    @SuppressWarnings( "unused" )
    private void login( final String id, final String password ) {

        if( model.User.getInstance( ).login( id, password ) ) {

            System.out.println("Welcome "
                    + Inventory.findPersonById(id).getName());
            if( Inventory.isBorrower( id ) ) {
                System.out.println( "Interface borrower" );
                View.setState( new Borrower( ) );
            } else {
                System.out.println( "Interface administrator" );
                View.setState( new Administrator( ) );
            }

        } else {

            System.out.println( "Wrong username or password" );

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
        args1.add( "id" );
        args1.add( "password" );
        final Command command1 = new Command( "login", args1, this, "login",
                "Se connecte a l'interface.\n\t Exemple d'utilisation:\n\t login 123 pass " );

        commands.add( command1 );
        commands.addAll( super.setCommands( ) );

        return commands;

    }
}
