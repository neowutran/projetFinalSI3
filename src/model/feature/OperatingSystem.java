
package model.feature;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.Feature;

import com.google.gson.annotations.Expose;
import com.google.gson.internal.LinkedTreeMap;

import config.Config;
import config.Error;

/**
 * The Class OperatingSystem.
 */
public class OperatingSystem extends Feature implements model.finder.IString {

    /** The acceptable value. */
    private List<String> acceptableValue;

    /** The name. */
    @Expose
    private String       name = "OperatingSystem";

    /**
     * Instantiates a new operating system.
     *
     * @param os the os
     * @throws InvalidParameterException the invalid parameter exception
     */
    public OperatingSystem( final String os ) throws InvalidParameterException {

        this.setAcceptableValue( null );
        this.checkOS( os );

    }

    /**
     * Instantiates a new operating system.
     *
     * @param os the os
     * @param typeMateriel the type materiel
     * @throws InvalidParameterException the invalid parameter exception
     */
    public OperatingSystem( final String os, final String typeMateriel )
            throws InvalidParameterException {

        this.setAcceptableValue( typeMateriel );
        this.checkOS( os );

    }

    /**
     * Check os.
     *
     * @param os the os
     * @throws InvalidParameterException the invalid parameter exception
     */
    private void checkOS( final String os ) throws InvalidParameterException {

        if( !this.acceptableValue.contains( os ) ) {
            throw new InvalidParameterException( Error.OS_DO_NOT_EXIST );
        } else {
            this.setValue( os );
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( final Object obj ) {

        return this.getClass( ).getName( ).equals( obj.getClass( ).getName( ) )
                && this.getValue( ).equals(
                        ( ( OperatingSystem ) obj ).getValue( ) );
    }

    /*
     * (non-Javadoc)
     *
     * @see model.IFeature#getName()
     */
    @Override
    public String getName( ) {

        return this.name;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode( ) {

        int result = this.acceptableValue.hashCode( );
        result = 31 * result + this.name.hashCode( );
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see model.finder.IString#equals(model.finder.IString)
     */
    @Override
    public boolean isEquals( final String string ) {

        return this.getValue( ).equals( string );
    }

    /**
     * Sets the acceptable value.
     *
     * @param typeMateriel the new acceptable value
     * @throws InvalidParameterException the invalid parameter exception
     */
    private void setAcceptableValue( final String typeMateriel )
            throws InvalidParameterException {

        if( typeMateriel == null ) {

            final List<String> fullList = new ArrayList<String>( );
            for( final Entry entry : ( Set<Entry> ) ( ( Map ) Config
                    .getConfiguration( ).get( Config.EQUIPMENT ) ).entrySet( ) ) {

                if( ( ( Map ) ( ( Map ) Config.getConfiguration( ).get(
                        Config.EQUIPMENT ) ).get( entry.getKey( ) ) )
                        .get( this.name ) != null ) {

                    fullList.addAll( ( List ) ( ( Map ) ( ( Map ) Config
                            .getConfiguration( ).get( Config.EQUIPMENT ) )
                            .get( entry.getKey( ) ) ).get( this.name ) );
                }

            }

            this.acceptableValue = fullList;

        } else {

            if( ( ( Map ) Config.getConfiguration( ).get(
                    Config.EQUIPMENT ) ).containsKey( typeMateriel ) ) {
                this.acceptableValue = ( List ) ( ( LinkedTreeMap ) ( ( LinkedTreeMap ) Config
                        .getConfiguration( ).get( Config.EQUIPMENT ) )
                        .get( typeMateriel ) ).get( this.name );

            } else {

                throw new InvalidParameterException(
                        Error.EQUIPMENT_DO_NOT_EXIST );

            }

        }

    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString( ) {
        String template = ( String ) ( ( Map ) ( ( Map ) Config
                .getConfiguration( ).get( Config.TEMPLATE ) )
                .get( Config.FEATURES ) ).get( this.name );
        template = template.replaceAll( "\\{name\\}", this.name );
        template = template.replaceAll( "\\{value\\}", this.getValue( ) );

        return template;
    }
}
