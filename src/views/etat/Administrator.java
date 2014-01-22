
package views.etat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import config.*;
import config.Error;
import model.*;
import model.person.Borrower;
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
                BorrowState.ACCEPT ) ) {
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
                BorrowState.REFUSE );
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
                BorrowState.RETURNED );
        System.out.println( "Borrow returned" );

    }


    @SuppressWarnings( "unused" )
    private void findEquipmentUnderRepair( ) {
        System.out.println(Inventory.findEquipmentUnderRepair());
    }

    @SuppressWarnings( "unused" )
    private void findEquipmentWhoNeedRepair( ) {
        System.out.println(Inventory.findEquipmentWhoNeedRepair());
    }


    @SuppressWarnings( "unused" )
    private void equipmentHealth(final String id, final String healthState, final String message ) {

        final Equipment equipment = Inventory.findEquipmentById( id );
        if( equipment == null ) {
            this.printHelp( );
            return;
        }

        HealthState state;
        switch (healthState){
            case "OK":
                state = HealthState.OK;
                break;
            case "NOT_OK":
                state = HealthState.NOT_OK;
                break;
            default:
                System.out.println(Error.STATE_DO_NOT_EXIST);
                return;

        }

        Health health = equipment.getHealth();
        health.setCause(message);
        health.setHealthState(state);
        equipment.setHealth(health);

         //TODO Lors du changement d'etat de santé de materiel (exemple: une tablette tactile qui se casse, il faut prendre en compte que les personnes ayant fait des reservations de cet equipement doivent etre modifier.

    }

    @SuppressWarnings( "unused" )
    private void equipmentHealth(final String id, final String healthState, final String message, final boolean underRepair ) {

        this.equipmentHealth(id, healthState, message);
        try {
            Inventory.findEquipmentById( id ).setUnderRepair(underRepair);
        } catch (MiniProjectException e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings( "unused" )
    private void addEquipment(String type, String numb) {  	
            Inventory.addEquipment( type , numb );   	// TODO faire un try-catch si n�cessaire	
    }
    
    @SuppressWarnings( "unused" )
    private void addFeatureById(String id, String name , String value) {  	
            Inventory.addFeatureById( id , name , value );   	// TODO faire un try-catch si n�cessaire	
    }
    
    /**
     * Méthode pour créer un nouvel utilisateur
     * @throws MiniProjectException 
     */
    private void createUser(String type, String name, String id, String password) throws MiniProjectException{
    	switch (type) {
        case SaveLoad.PERSON_TYPE_STUDENT:
            new model.person.Borrower(name, id, SaveLoad.PERSON_TYPE_STUDENT, password);
            break;
        case SaveLoad.PERSON_TYPE_TEACHER:
            new model.person.Borrower(name, id, SaveLoad.PERSON_TYPE_TEACHER, password);
            break;
        case SaveLoad.PERSON_TYPE_ADMINISTRATOR:
            new model.person.Administrator(name, id, password);
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

        final Command command8 = new Command(
                "findEquipmentUnderRepair", new LinkedList<String>( ),
                this, "findEquipmentUnderRepair",
                "Cherche les equipements en cours de reparation" );


        final Command command9 = new Command(
                "findEquipmentWhoNeedRepair", new LinkedList<String>( ),
                this, "findEquipmentWhoNeedRepair",
                "Cherche les equipements qui ont besoin de reparation et qui ne sont pas en reparation" );



        final List<String> args10 = new LinkedList<>( );
        args10.add( "equipmentId" );
        args10.add("healthState");
        args10.add("message");
        final Command command10 = new Command( "equipmentHealth", args10, this,
                "equipmentHealth", "Change l'etat de santé d'un equipement" );


        final List<String> args11 = new LinkedList<>( );
        args11.add( "equipmentId" );
        args11.add("healthState");
        args11.add("message");
        args11.add("underRepair");
        final Command command11 = new Command( "equipmentHealth", args11, this,
                "equipmentHealth", "Change l'etat de santé d'un equipement" );


        final List<String> args12 = new LinkedList<>( );
        args12.add( "type" );
        args12.add("number");
        final Command command12 = new Command( "addEquipment", args12, this,
                "addEquipment", "Ajout d'un �quipement � l'inventaire" );
        
        /* Ajout de la méthode pour créer un nouvel utilisateur */
        final List<String> args13 = new LinkedList<>( );
        args13.add( "type" );
        args13.add("name");
        args13.add("id");
        args13.add("password");
        final Command command13 = new Command( "createUser",
                args13, this, "createUser",
                "Créer un nouvel utilisateur" );
        
        

        final List<String> args14 = new LinkedList<>( );
        args14.add( "id" );
        args14.add("type");
        args14.add("value");
        final Command command14 = new Command( "addFeatureById", args14, this,
                "addFeatureById", "Ajout d'une feature a un equipement" );
        
        commands.add( command1 );
        commands.add( command2 );
        commands.add( command3 );
        commands.add( command4 );
        commands.add( command5 );
        commands.add( command6 );
        commands.add( command7 );
        commands.add( command8 );
        commands.add( command9 );
        commands.add( command10 );
        commands.add( command11 );
        commands.add( command12 );
        commands.add( command13 );
        commands.add( command14 );
        commands.addAll( super.setCommands( ) );

        return commands;

    }
}
