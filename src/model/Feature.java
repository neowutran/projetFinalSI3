/*
 * @author Martini Didier - Fabien Pinel - Maxime Touroute
 */

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.annotations.Expose;

import config.Config;
import config.Error;

/**
 * The Class Feature.
 */
public class Feature {

    /**
     * The acceptable value.
     */
    private List<String> acceptableValue;
    /**
     * The name.
     */
    @Expose
    private final String name;
    /** The is double value. */
    private Boolean      isDoubleValue;
    /**
     * The value.
     */
    @Expose
    private String       value;

    /**
     * Instantiates a new feature.
     * 
     * @param name
     *            the name
     * @param equipmentType
     *            the equipment type
     * @param value
     *            the value
     * @throws MiniProjectException
     *             the mini project exception
     */
    public Feature(final String name, final String equipmentType,
            final String value) throws MiniProjectException {

        this.name = name;
        this.setAcceptableValue(equipmentType);
        if (!this.acceptableValue.contains(value)) {
            throw new MiniProjectException(Error.VALUE_DO_NOT_EXIST);
        }
        this.value = value;
    }

    /**
     * Gets the checks if is double value.
     * 
     * @return the checks if is double value
     */
    public Boolean getIsDoubleValue() {

        return this.isDoubleValue;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {

        return this.name;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    protected String getValue() {

        return this.value;
    }

    /**
     * Greater than.
     * 
     * @param object
     *            the object
     * @return true, if successful
     */
    public boolean greaterThan(final String object) {

        return this.isDoubleValue
                && (Double.parseDouble(object) > Double.parseDouble(this.value));
    }

    /**
     * Greater than or equals.
     * 
     * @param object
     *            the object
     * @return true, if successful
     */
    public boolean greaterThanOrEquals(final String object) {

        return this.isDoubleValue
                && (Double.parseDouble(object) >= Double
                        .parseDouble(this.value));
    }

    /**
     * Checks if is double value.
     * 
     * @param values
     *            the values
     */
    public void isDoubleValue(final List<String> values) {

        for (final String value : values) {
            try {
                Double.parseDouble(value);
            } catch (final NumberFormatException nfe) {
                this.isDoubleValue = false;
                return;
            }
        }
        this.isDoubleValue = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see model.finder.IString#equals(model.finder.IString)
     */
    /**
     * Checks if is equals.
     * 
     * @param string
     *            the string
     * @return true, if is equals
     */
    public boolean isEquals(final String string) {

        if (this.isDoubleValue) {
            return Double.parseDouble(this.value) == Double.parseDouble(string);
        }
        return this.value.equals(string);
    }

    /**
     * Lesser than.
     * 
     * @param object
     *            the object
     * @return true, if successful
     */
    public boolean lesserThan(final String object) {

        return this.isDoubleValue
                && (Double.parseDouble(object) < Double.parseDouble(this.value));
    }

    /**
     * Lesser than or equals.
     * 
     * @param object
     *            the object
     * @return true, if successful
     */
    public boolean lesserThanOrEquals(final String object) {

        return this.isDoubleValue
                && (Double.parseDouble(object) <= Double
                        .parseDouble(this.value));
    }

    /**
     * Sets the acceptable value.
     * 
     * @param typeMateriel
     *            the new acceptable value
     * @throws MiniProjectException
     *             the invalid parameter exception
     */
    private void setAcceptableValue(final String typeMateriel)
            throws MiniProjectException {

        if (typeMateriel == null) {
            final List<String> fullList = new ArrayList<String>();
            for (final Map.Entry entry : (Set<Map.Entry>) ((Map) Config
                    .getConfiguration().get(Config.EQUIPMENT)).entrySet()) {
                if (((Map) ((Map) Config.getConfiguration().get(
                        Config.EQUIPMENT)).get(entry.getKey())).get(this.name) != null) {
                    this.isDoubleValue((List) ((Map) ((Map) Config
                            .getConfiguration().get(Config.EQUIPMENT))
                            .get(entry.getKey())).get(this.name));
                    fullList.addAll((List) ((Map) ((Map) Config
                            .getConfiguration().get(Config.EQUIPMENT))
                            .get(entry.getKey())).get(this.name));
                }
            }
            this.acceptableValue = fullList;
        } else {
            if (((Map) Config.getConfiguration().get(Config.EQUIPMENT))
                    .containsKey(typeMateriel)) {
                this.acceptableValue = (List) ((Map) ((Map) Config
                        .getConfiguration().get(Config.EQUIPMENT))
                        .get(typeMateriel)).get(this.name);
                this.isDoubleValue((List) ((Map) ((Map) Config
                        .getConfiguration().get(Config.EQUIPMENT))
                        .get(typeMateriel)).get(this.name));
            } else {
                throw new MiniProjectException(
                        config.Error.EQUIPMENT_DO_NOT_EXIST);
            }
        }
    }

    /**
     * Sets the value.
     * 
     * @param value
     *            the new value
     */
    protected void setValue(final String value) {

        this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        String template = (String) ((Map) ((Map) Config.getConfiguration().get(
                Config.TEMPLATE)).get(Config.FEATURES)).get(this.name);
        template = template.replaceAll("\\{name\\}", this.name);
        template = template.replaceAll("\\{value\\}", this.getValue());
        return template;
    }
}
