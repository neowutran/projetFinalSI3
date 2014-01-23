/*
 * @author Martini Didier - Fabien Pinel - Maxime Touroute
 */

package model;

import java.util.Map;

import com.google.gson.annotations.Expose;

import config.Config;

/**
 * The Class Health.
 */
public class Health {

    /** The health state. */
    @Expose
    private HealthState healthState;
    /** The cause. */
    @Expose
    private String      cause = "";

    /**
     * Instantiates a new health.
     * 
     * @param healthState
     *            the health state
     */
    public Health(final HealthState healthState) {

        this.healthState = healthState;
    }

    /**
     * Gets the cause.
     * 
     * @return the cause
     */
    public String getCause() {

        return this.cause;
    }

    /**
     * Gets the health state.
     * 
     * @return the health state
     */
    public HealthState getHealthState() {

        return this.healthState;
    }

    /**
     * Sets the cause.
     * 
     * @param cause
     *            the new cause
     */
    public void setCause(final String cause) {

        this.cause = cause;
    }

    /**
     * Sets the health state.
     * 
     * @param healthState
     *            the new health state
     */
    public void setHealthState(final HealthState healthState) {

        this.healthState = healthState;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        String template = (String) ((Map) Config.getConfiguration().get(
                Config.TEMPLATE)).get(Config.HEALTH);
        template = template.replaceAll("\\{cause\\}", this.cause);
        template = template.replaceAll("\\{state\\}",
                this.healthState.toString());
        return template;
    }
}
