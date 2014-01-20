
package views.etat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.Inventory;
import views.Command;

/**
 * The Class Administrator.
 */
public class Administrator extends User {

    /**
     * Accept.
     *
     * @param id
     *            the id
     */
    @SuppressWarnings( "unused" )
    private void accept( final String id ) {

        final model.person.Borrower.Borrow borrow = Inventory.findBorrowById( id );
        if( borrow == null ) {
            this.printHelp( );
            return;
        }
        if( ( ( model.person.Administrator ) Inventory.findPersonById(model.User
                .getInstance().getPersonId()) ).setBorrowStat( borrow,
                model.State.ACCEPT ) ) {
            System.out.println( "Borrow accepted" );
        } else {
            System.out.println( "Error, the borrow cannot be accepted" );
        }
    }

    /**
     * Find actual borrow by borrower.
     *
     * @param id
     *            the id
     */
    @SuppressWarnings( "unused" )
    private void findActualBorrowByBorrower( final String id ) {

        System.out.println( Inventory.findActualBorrowByBorrower( id ) );

    }

    /**
     * Find borrow by borrower.
     *
     * @param id
     *            the id
     */
    @SuppressWarnings( "unused" )
    private void findBorrowByBorrower( final String id ) {

        System.out.println( Inventory.findBorrowByBorrower( id ) );

    }

    /**
     * Find borrow by id.
     *
     * @param id
     *            the id
     */
    @SuppressWarnings( "unused" )
    private void findBorrowById( final String id ) {

        System.out.println( Inventory.findBorrowByBorrower( id ) );

    }

    /**
     * Find borrow waiting for administrator.
     */
    @SuppressWarnings( "unused" )
    private void findBorrowWaitingForAdministrator( ) {
        System.out.println( Inventory.findBorrowWaitingForAdministrator( ) );
    }

    /**
     * Find late borrow.
     */
    @SuppressWarnings( "unused" )
    private void findLateBorrow( ) {
        System.out.println( Inventory.findLateBorrow( ) );

    }

    /**
     * Refuse.
     *
     * @param id
     *            the id
     */
    @SuppressWarnings( "unused" )
    private void refuse( final String id ) {

        final model.person.Borrower.Borrow borrow = Inventory.findBorrowById( id );
        if( borrow == null ) {
            this.printHelp( );
            return;
        }
        ( ( model.person.Administrator ) Inventory.findPersonById( model.User
                .getInstance( ).getPersonId( ) ) ).setBorrowStat( borrow,
                model.State.REFUSE );
        System.out.println( "Borrow refused" );

    }

    /**
     * Returned.
     *
     * @param id
     *            the id
     */
    @SuppressWarnings( "unused" )
    private void returned( final String id ) {

        final model.person.Borrower.Borrow borrow = Inventory.findBorrowById( id );
        if( borrow == null ) {
            this.printHelp( );
            return;
        }
        ( ( model.person.Administrator ) Inventory.findPersonById( model.User
                .getInstance( ).getPersonId( ) ) ).setBorrowStat( borrow,
                model.State.RETURNED );
        System.out.println( "Borrow returned" );

    }

    /*
     * (non-Javadoc)
     *
     * @see views.IView#setCommands()
     */
    @Override
    public List<Command> setCommands( ) {

        final List<Command> commands = new ArrayList<>( );

        final Command command1 = new Command( "findLateBorrow",
                new LinkedList<String>( ), this, "findLateBorrow",
                "Cherche les emprunts en retard (ceux qui devrait deja avoir été rendu)" );
        final Command command2 = new Command(
                "findBorrowWaitingForAdministrator", new LinkedList<String>( ),
                this, "findBorrowWaitingForAdministrator",
                "Cherche les demandes d'emprunt en attente d'une reponse par un administrateur" );

        final List<String> args3 = new LinkedList<>( );
        args3.add( "borrowId" );
        final Command command3 = new Command( "findBorrowById", args3, this,
                "findBorrowById", "Cherche un emprunt par son id" );

        final List<String> args4 = new LinkedList<>( );
        args4.add( "borrowerId" );
        final Command command4 = new Command( "findActualBorrowByBorrower",
                args4, this, "findActualBorrowByBorrower",
                "Cherche les emprunt en cours pour un emprunteur" );

        final List<String> args5 = new LinkedList<>( );
        args5.add( "borrowId" );
        final Command command5 = new Command( "accept", args5, this, "accept",
                "Accepte un emprunt" );

        final Command command6 = new Command( "refuse", args5, this, "refuse",
                "Refuse un emprunt" );
        final Command command7 = new Command( "return", args5, this, "return",
                "Marque un emprunt comment etant rendu à la date courante" );

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
}
