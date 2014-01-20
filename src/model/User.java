
package model;

/**
 * The Class User.
 */
public class User {

    /** The person id. */
    private String      personId;

    /** The instance. */
    private static User instance = null;

    /**
     * Gets the single instance of User.
     *
     * @return single instance of User
     */
    public static User getInstance( ) {
        if( User.instance == null ) {
            User.instance = new User( );
        }
        return User.instance;
    }

    /**
     * Instantiates a new user.
     */
    protected User( ) {

    }

    /**
     * Gets the person id.
     *
     * @return the person id
     */
    public String getPersonId( ) {

        return this.personId;
    }

    /**
     * Login.
     *
     * @param id the id
     * @param password the password
     * @return true, if successful
     */
    public boolean login( final String id, final String password ) {

        final Person person = Inventory.findPersonById( id );
        if( person == null ) {
            return false;
        }
        if( person.getPassword( ).equals( password ) ) {
            this.setPersonId( id );
            return true;
        }
        return false;
    }

    /**
     * Logout.
     */
    public void logout( ) {
        this.setPersonId( null );
    }

    /**
     * Sets the person id.
     *
     * @param personId the new person id
     */
    private void setPersonId( final String personId ) {

        this.personId = personId;
    }

}
