
package model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

import config.Config;
import config.Error;

/**
 * The Class Person.
 */
public abstract class Person {

    /**
     * Exist.
     *
     * @param id the id
     * @return true, if successful
     */
    public static boolean exist( final String id ) {

        for( final Person person : Person.person ) {
            if( person == null ) {
                continue;
            }
            if( id.equals( person.getId( ) ) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the persons.
     *
     * @return the persons
     */
    public static List<Person> getPersons( ) {

        return Person.person;
    }

    /** The name. */
    @Expose
    private String              name;

    /** The type. */
    @Expose
    private String              type;

    /** The id. */
    @Expose
    private String              id;

    /** The person. */
    @Expose
    private static List<Person> person = new ArrayList<>( );

    /** The password. */
    @Expose
    private String              password;

    /**
     * Instantiates a new person.
     *
     * @param name the name
     * @param id the id
     * @param password the password
     * @throws InvalidParameterException the invalid parameter exception
     */
    public Person( final String name, final String id, final String password )
            throws InvalidParameterException {

        if( Person.exist( id ) ) {
            throw new InvalidParameterException( Error.PERSON_ALREADY_EXIST );
        }

        this.name = name;
        this.id = id;
        this.password = password;
        Person.person.add( this );
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId( ) {

        return this.id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName( ) {

        return this.name;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword( ) {

        return this.password;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType( ) {

        return this.type;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId( final String id ) {

        this.id = id;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName( final String name ) {

        this.name = name;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword( final String password ) {

        this.password = password;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    protected void setType( final String type ) {

        this.type = type;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString( ) {

        String template = ( String ) ( ( Map ) Config.getConfiguration( ).get(
                Config.TEMPLATE ) ).get( Config.PERSON );

        template = template.replaceAll( "\\{name\\}", this.name );
        template = template.replaceAll( "\\{type\\}", this.type );

        template = template.replaceAll( "\\{id\\}", this.getId( ) );

        return template;

    }

}
