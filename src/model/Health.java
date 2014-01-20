package model;

import com.google.gson.annotations.Expose;
import config.Config;

import java.util.Map;

public class Health {
    public HealthState getHealthState() {
        return healthState;
    }

    public void setHealthState(HealthState healthState) {
        this.healthState = healthState;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Expose
    private HealthState healthState;
    @Expose
    private String cause = "";

    public Health(final HealthState healthState) {
        this.healthState = healthState;
    }

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
