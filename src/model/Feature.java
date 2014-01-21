
package model;

import com.google.gson.annotations.Expose;
import com.google.gson.internal.LinkedTreeMap;
import config.*;
import config.Error;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Class Feature.
 */
public class Feature {


    /**
     * The acceptable value.
     */
    private List<String> acceptableValue;

    public String getFeatureName() {
        return featureName;
    }

    /**
     * The name.
     */
    @Expose
    private String featureName;

    public Boolean getIsDoubleValue() {
        return isDoubleValue;
    }

    private Boolean isDoubleValue;
    /**
     * The value.
     */
    @Expose
    private String value;

    public Feature(String featureName, String equipmentType, String value) throws Exception {
        this.featureName = featureName;
        this.setAcceptableValue(equipmentType);
        if(!acceptableValue.contains(value)){
            throw new Exception(Error.VALUE_DO_NOT_EXIST);
        }
        this.value = value;
    }


    /**
     * Gets the value.
     *
     * @return the value
     */
    protected String getValue() {

        return this.value;
    }


    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {

        int result = this.acceptableValue.hashCode();
        result = 31 * result + this.featureName.hashCode();
        result = 31 * result + this.value.hashCode();

        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see model.finder.IString#equals(model.finder.IString)
     */
    public boolean isEquals(final String string) {
        if(this.isDoubleValue){
            return Double.parseDouble(this.value) == Double.parseDouble(string);
        }
        return this.value.equals(string);
    }


    public void isDoubleValue(List<String> values){

        for(String value: values){

            try
            {
                Double.parseDouble(value);
            }
            catch(NumberFormatException nfe)
            {
                this.isDoubleValue = false;
                return;
            }

        }

        this.isDoubleValue = true;

    }

    /**
     * Sets the acceptable value.
     *
     * @param typeMateriel the new acceptable value
     * @throws java.security.InvalidParameterException the invalid parameter exception
     */
    private void setAcceptableValue(final String typeMateriel)
            throws InvalidParameterException {

        if (typeMateriel == null) {

            final List<String> fullList = new ArrayList<String>();
            for (final Map.Entry entry : (Set<Map.Entry>) ((Map) Config
                    .getConfiguration().get(Config.EQUIPMENT)).entrySet()) {

                if (((Map) ((Map) Config.getConfiguration().get(
                        Config.EQUIPMENT)).get(entry.getKey()))
                        .get(this.featureName) != null) {
                    isDoubleValue((List) ((Map) ((Map) Config
                            .getConfiguration().get(Config.EQUIPMENT))
                            .get(entry.getKey())).get(this.featureName));
                    fullList.addAll((List) ((Map) ((Map) Config
                            .getConfiguration().get(Config.EQUIPMENT))
                            .get(entry.getKey())).get(this.featureName));
                }

            }

            this.acceptableValue = fullList;

        } else {

            if (((Map) Config.getConfiguration().get(
                    Config.EQUIPMENT)).containsKey(typeMateriel)) {
                this.acceptableValue = (List) ((LinkedTreeMap) ((LinkedTreeMap) Config
                        .getConfiguration().get(Config.EQUIPMENT))
                        .get(typeMateriel)).get(this.featureName);

            } else {

                throw new InvalidParameterException(
                        config.Error.EQUIPMENT_DO_NOT_EXIST);

            }

        }

    }

    /**
     * Greater than.
     *
     * @param object the object
     * @return true, if successful
     */
    public boolean greaterThan(String object){
        return this.isDoubleValue && Double.parseDouble(object) > Double.parseDouble(this.value);
    }

    /**
     * Greater than or equals.
     *
     * @param object the object
     * @return true, if successful
     */
    public boolean greaterThanOrEquals(String object){
        return this.isDoubleValue && Double.parseDouble(object) >= Double.parseDouble(this.value);
    }

    /**
     * Lesser than.
     *
     * @param object the object
     * @return true, if successful
     */
    public boolean lesserThan(String object){
        return this.isDoubleValue && Double.parseDouble(object) < Double.parseDouble(this.value);
    }

    /**
     * Lesser than or equals.
     *
     * @param object the object
     * @return true, if successful
     */
    public boolean lesserThanOrEquals(String object){
        return this.isDoubleValue && Double.parseDouble(object) <= Double.parseDouble(this.value);
    }
    /**
     * Sets the value.
     *
     * @param value the new value
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
        String template = (String) ((Map) ((Map) Config
                .getConfiguration().get(Config.TEMPLATE))
                .get(Config.FEATURES)).get(this.featureName);
        template = template.replaceAll("\\{name\\}", this.featureName);
        template = template.replaceAll("\\{value\\}", this.getValue());

        return template;
    }
}
