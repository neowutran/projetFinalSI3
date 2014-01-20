
package model;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import com.google.gson.annotations.Expose;

import config.Config;
import config.Error;

/**
 * The Class Equipment.
 */
public class Equipment extends InventoryElement {

    /** The type. */
    @Expose
    private String        type;

    /** The features. */
    @Expose
    private List<Feature> features;

    /**
     * Instantiates a new equipment.
     *
     * @param type the type
     * @param features the features
     * @throws MiniProjectException the mini project exception
     */
    public Equipment( final String type, final List<Feature> features )
            throws MiniProjectException {

        this.checkType( type );
        this.checkFeature( features );
        this.setId( );

        final List<Equipment> inventory = Inventory.getInstance( )
                .getEquipments( );
        inventory.add( this );
        Inventory.getInstance( ).setEquipments( inventory );

    }

    /*
     * (non-Javadoc)
     *
     * @see model.person.InventoryElement#checkExistence(java.lang.String)
     */
    @Override
    protected void checkExistence( final String id )
            throws MiniProjectException {
        if( Inventory.findEquipmentById( id ) != null ) {
            throw new InvalidParameterException( Error.EQUIPMENT_ALREADY_EXIST );
        }
    }

    /**
     * Check feature.
     *
     * @param features the features
     * @throws InvalidParameterException the invalid parameter exception
     */
    private void checkFeature( final List<Feature> features )
            throws InvalidParameterException {

        if( null == features || features.isEmpty( ) ) {
            return;
        }
        for( final Feature feature : features ) {

            if( !( ( Map ) ( ( Map ) Config.getConfiguration( ).get(
                    Config.EQUIPMENT ) ).get( this.type ) )
                    .containsKey( feature.getName( ) ) ) {
                throw new InvalidParameterException(
                        Error.FEATURE_EQUIPMENT_INVALID );
            }

        }

        this.features = features;

    }

    /**
     * Check type.
     *
     * @param type the type
     * @throws InvalidParameterException the invalid parameter exception
     */
    private void checkType( final String type )
            throws InvalidParameterException {

        if( !( ( Map ) Config.getConfiguration( ).get( Config.EQUIPMENT ) )
                .containsKey( type ) ) {

            throw new InvalidParameterException( Error.EQUIPMENT_DO_NOT_EXIST );

        }

        this.type = type;

    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( final Object obj ) {

        return this.getClass( ).equals( obj.getClass( ) )
                && this.type.equals( ( ( Equipment ) obj ).type )
                && this.features.equals( ( ( Equipment ) obj ).features );
    }

    /**
     * Gets the features.
     *
     * @return the features
     */
    public List<model.Feature> getFeatures( ) {

        return this.features;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType( ) {

        return this.type;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode( ) {

        int result = this.type.hashCode( );
        result = 31 * result
                + ( this.features != null ? this.features.hashCode( ) : 0 );
        result = 31 * result + this.getId( ).hashCode( );
        return result;
    }

    /**
     * Sets the features.
     *
     * @param features the new features
     * @throws InvalidParameterException the invalid parameter exception
     */
    public void setFeatures( final List<model.Feature> features )
            throws InvalidParameterException {

        this.checkFeature( features );
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString( ) {

        String template = ( String ) ( ( Map ) Config.getConfiguration( ).get(
                Config.TEMPLATE ) ).get( Config.EQUIPMENT );

        template = template.replaceAll( "\\{type\\}", this.type );
        template = template.replaceAll( "\\{features\\}",
                this.features.toString( ) );
        template = template.replaceAll( "\\{id\\}", this.getId( ) );

        return template;
    }

}
