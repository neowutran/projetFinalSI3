/*
 * @author Martini Didier - Fabien Pinel - Maxime Touroute
 */

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

import config.Config;
import config.Error;

/**
 * The Class Equipment.
 */
public class Equipment extends InventoryElement {

    /**
     * The type.
     */
    @Expose
    private String        type;
    /** The nb borrowed. */
    private Stats stats;
    /** The health. */
    @Expose
    private Health        health;
    /** The under repair. */
    @Expose
    private Boolean       underRepair;
    /**
     * The features.
     */
    @Expose
    private List<Feature> features = new ArrayList<>();

    /**
     * Instantiates a new equipment.
     *
     * @param type
     *            the type
     * @param features
     *            the features
     * @param health
     *            the health
     * @param underRepair
     *            the under repair
     * @throws MiniProjectException
     *             the mini project exception
     */
    public Equipment(final String type, final List<Feature> features,
            final Health health, final Boolean underRepair)
            throws MiniProjectException {

        if (underRepair && health.getHealthState().equals(HealthState.OK)) {
            throw new MiniProjectException(Error.REPAIR_OK);
        }
        stats = new Stats();
        this.checkType(type);
        this.checkFeature(features);
        this.health = health;
        this.underRepair = underRepair;
        this.setId();
        final List<Equipment> inventory = Inventory.getInstance()
                .getEquipments();
        inventory.add(this);
        Inventory.getInstance().setEquipments(inventory);
    }

    /*
     * (non-Javadoc)
     *
     * @see model.person.InventoryElement#checkExistence(java.lang.String)
     */
    @Override
    protected void checkExistence(final String id) throws MiniProjectException {

        if (Inventory.findEquipmentById(id) != null) {
            throw new MiniProjectException(Error.EQUIPMENT_ALREADY_EXIST);
        }
    }

    /**
     * Check feature.
     *
     * @param features
     *            the features
     * @throws MiniProjectException
     *             the invalid parameter exception
     */
    private void checkFeature(final List<Feature> features)
            throws MiniProjectException {

        if ((null == features) || features.isEmpty()) {
            return;
        }
        for (final Feature feature : features) {
            if (!((Map) ((Map) Config.getConfiguration().get(Config.EQUIPMENT))
                    .get(this.type)).containsKey(feature.getName())) {
                throw new MiniProjectException(
                        Error.FEATURE_EQUIPMENT_INVALID);
            }
        }
        this.features = features;
    }
    
    
    /**
     * Check type.
     *
     * @param type
     *            the type
     * @throws MiniProjectException
     *             the invalid parameter exception
     */
    private void checkType(final String type) throws MiniProjectException {

        if (!((Map) Config.getConfiguration().get(Config.EQUIPMENT))
                .containsKey(type)) {
            throw new MiniProjectException(Error.EQUIPMENT_DO_NOT_EXIST);
        }
        this.type = type;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {

        return this.getClass().equals(obj.getClass())
                && this.type.equals(((Equipment) obj).type)
                && this.features.equals(((Equipment) obj).features);
    }

    /**
     * Gets the features.
     *
     * @return the features
     */
    public List<model.Feature> getFeatures() {

        return this.features;
    }

    /**
     * Gets the health.
     *
     * @return the health
     */
    public Health getHealth() {

        return this.health;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {

        return this.type;
    }

    /**
     * Gets the under repair.
     *
     * @return the under repair
     */
    public Boolean getUnderRepair() {

        return this.underRepair;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        int result = this.type.hashCode();
        result = (31 * result)
                + (this.features != null ? this.features.hashCode() : 0);
        result = (31 * result) + this.getId().hashCode();
        return result;
    }

    /**
     * Sets the features.
     *
     * @param features
     *            the new features
     * @throws MiniProjectException
     *             the invalid parameter exception
     */
    public void setFeatures(final List<model.Feature> features)
            throws MiniProjectException {

        this.checkFeature(features);
    }

    /**
     * Sets the health.
     *
     * @param health
     *            the new health
     */
    public void setHealth(final Health health) {

        this.health = health;
        if (this.health.getHealthState().equals(HealthState.OK)) {
            this.underRepair = false;
        }
    }
    
    /**
     * Increments stat : nbBorrowed
     *
     * 
     *   
     */
    public void incrStatsNbBorrowed(){
    	stats.incrNbBorrowed();
    }

    /**
     * Sets the under repair.
     *
     * @param underRepair
     *            the new under repair
     * @throws MiniProjectException
     *             the mini project exception
     */
    public void setUnderRepair(final Boolean underRepair)
            throws MiniProjectException {

        if (this.health.getHealthState().equals(HealthState.OK)) {
            throw new MiniProjectException(Error.REPAIR_OK);
        }
        this.underRepair = underRepair;
        if (!underRepair) {
            stats.incrNbUnderRepair();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	System.out.println(stats);
        String template = (String) ((Map) Config.getConfiguration().get(
                Config.TEMPLATE)).get(Config.EQUIPMENT);
        template = template.replaceAll("\\{type\\}", this.type);
        template = template.replaceAll("\\{features\\}",
                this.features.toString());
        template = template.replaceAll("\\{id\\}", this.getId());
        template = template.replaceAll("\\{underRepair\\}",
                this.underRepair.toString());
        template = template.replaceAll("\\{health\\}", this.health.toString());
        return template;
    }
}
