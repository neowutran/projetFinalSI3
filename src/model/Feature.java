
package model;

import com.google.gson.annotations.Expose;

/**
 * The Class Feature.
 */
public abstract class Feature implements model.IFeature {

    /** The value. */
    @Expose
    private String value;

    /**
     * Gets the value.
     *
     * @return the value
     */
    protected String getValue( ) {

        return this.value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    protected void setValue( final String value ) {

        this.value = value;

    }

}
