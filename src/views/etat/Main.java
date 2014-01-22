
package views.etat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import config.Error;

import model.Inventory;
import model.MiniProjectException;
import model.SaveLoad;
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
    /**
     * Nouvelle méthode pour l'inscription d'un utilisateur
     * 
     * @param name nom du nouveau user
     * @param id	id du nouveau user
     * @param type	type du nouveau user
     * @param password password du nouveau user
     * @throws MiniProjectException 
     */
    @SuppressWarnings("unused")
	private void register(String name, String id, String type, String password) throws MiniProjectException{
    	switch (type) {
        case SaveLoad.PERSON_TYPE_STUDENT:
            new model.person.Borrower(name, id, SaveLoad.PERSON_TYPE_STUDENT, password);
            break;
        case SaveLoad.PERSON_TYPE_TEACHER:
            new model.person.Borrower(name, id, SaveLoad.PERSON_TYPE_TEACHER, password);
            break;
        default:
            throw new MiniProjectException(Error.CANNOT_CREATE_PERSON);
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
        
        final List<String> args2 = new LinkedList<>( );
        args2.add( "name" );
        args2.add( "id" );
        args2.add( "type" );
        args2.add( "password" );
        final Command command2 = new Command( "register", args2, this, "register",
                "Inscription d'un nouvel utilisateur (type: teacher ou student)" );

        commands.add( command1 );
        commands.add( command2 );
        commands.addAll( super.setCommands( ) );

        return commands;

    }
}
