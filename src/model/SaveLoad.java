/*
 * @author Martini Didier - Fabien Pinel - Maxime Touroute
 */

package model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import lib.Json;
import model.person.Administrator;
import model.person.Borrower;
import model.person.Borrower.Borrow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import config.Config;
import config.Error;
import controllers.MiniProjectController;

/**
 * The Class SaveLoad.
 */
public final class SaveLoad {

    /**
     * The Constant INVENTORY.
     */
    public static final String INVENTORY                 = "inventory";
    /**
     * The Constant INVENTORY_EQUIPMENT.
     */
    public static final String INVENTORY_EQUIPMENT       = "equipments";
    /**
     * The Constant INVENTORY_BORROW.
     */
    public static final String INVENTORY_BORROW          = "borrows";
    /**
     * The Constant PERSON.
     */
    public static final String PERSON                    = "person";
    /**
     * The Constant TYPE.
     */
    public static final String TYPE                      = "type";
    /**
     * The Constant PERSON_TYPE_STUDENT.
     */
    public static final String PERSON_TYPE_BORROWER      = "borrower";
    /**
     * The Constant PERSON_TYPE_ADMINISTRATOR.
     */
    public static final String PERSON_TYPE_ADMINISTRATOR = "administrator";
    /**
     * The Constant NAME.
     */
    public static final String NAME                      = "name";
    /**
     * The Constant ID.
     */
    public static final String ID                        = "id";
    /**
     * The Constant EQUIPMENT_FEATURE.
     */
    public static final String EQUIPMENT_FEATURE         = "features";
    /**
     * The Constant VALUE.
     */
    public static final String VALUE                     = "value";
    /**
     * The Constant BORROW_START.
     */
    public static final String BORROW_START              = "borrowStart";
    /**
     * The Constant BORROW_END.
     */
    public static final String BORROW_END                = "borrowEnd";
    /**
     * The Constant DAY.
     */
    public static final String DAY                       = "dayOfMonth";
    /**
     * The Constant HOUR.
     */
    public static final String HOUR                      = "hourOfDay";
    /**
     * The Constant MONTH.
     */
    public static final String MONTH                     = "month";
    /**
     * The Constant MINUTE.
     */
    public static final String MINUTE                    = "minute";
    /**
     * The Constant SECOND.
     */
    public static final String SECOND                    = "second";
    /**
     * The Constant YEAR.
     */
    public static final String YEAR                      = "year";
    /**
     * The Constant EQUIPMENT_ID.
     */
    public static final String EQUIPMENT_ID              = "equipmentId";
    /**
     * The Constant BORROWER_ID.
     */
    public static final String BORROWER_ID               = "borrowerId";
    /**
     * The Constant STATE.
     */
    public static final String STATE                     = "state";
    /**
     * The Constant ADMINISTRATOR_ID.
     */
    public static final String ADMINISTRATOR_ID          = "administratorId";
    /**
     * The Constant PASSWORD.
     */
    public static final String PASSWORD                  = "password";
    /** The Constant EQUIPMENT_HEALTH. */
    public static final String EQUIPMENT_HEALTH          = "health";
    /** The Constant HEALTH_CAUSE. */
    public static final String HEALTH_CAUSE              = "cause";
    /** The Constant HEALTH_STATE. */
    public static final String HEALTH_STATE              = "healthState";
    /** The Constant EQUIPMENT_UNDER_REPAIR. */
    public static final String EQUIPMENT_UNDER_REPAIR    = "underRepair";
    /**
     * The Constant DATA.
     */
    public static final String DATA                      = (String) ((Map) config.Config
                                                                 .getConfiguration())
                                                                 .get("data_file");

    /**
     * Gets the state.
     * 
     * @return the state
     */
    private static String getState() {

        final Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation().create();
        String state = "{\"" + SaveLoad.PERSON + "\":";
        state += gson.toJson(Person.getPersons());
        state += ",\"" + SaveLoad.INVENTORY + "\":";
        state += gson.toJson(Inventory.getInstance());
        state += "}";
        return state;
    }

    /**
     * Load.
     * 
     * @param file
     *            the file
     * @throws MiniProjectException
     *             the mini project exception
     */
    public static void load(final String file) throws MiniProjectException {

        final Path path = Paths.get(file);
        final Map data = Json.loadFile(path);
        final List<Map> equipments = (List<Map>) ((Map) data
                .get(SaveLoad.INVENTORY)).get(SaveLoad.INVENTORY_EQUIPMENT);
        final List<Map> borrows = (List<Map>) ((Map) data
                .get(SaveLoad.INVENTORY)).get(SaveLoad.INVENTORY_BORROW);
        final List<Map> persons = (List<Map>) data.get(SaveLoad.PERSON);
        for (final Map person : persons) {
            final String type = (String) person.get(SaveLoad.TYPE);
            if (SaveLoad.PERSON_TYPE_ADMINISTRATOR.equals(type)) {
                new Administrator((String) person.get(SaveLoad.NAME),
                        (String) person.get(SaveLoad.ID),
                        (String) person.get(SaveLoad.PASSWORD));
            } else {
                if (((Map) Config.getConfiguration().get(
                        SaveLoad.PERSON_TYPE_BORROWER)).containsKey(type)) {
                    new Borrower((String) person.get(SaveLoad.NAME),
                            (String) person.get(SaveLoad.ID), type,
                            (String) person.get(SaveLoad.PASSWORD));
                } else {
                    throw new MiniProjectException(Error.CANNOT_CREATE_PERSON);
                }
            }
        }
        for (final Map equipment : equipments) {
            final String id = (String) equipment.get(SaveLoad.ID);
            final String type = (String) equipment.get(SaveLoad.TYPE);
            final Boolean underRepair = (Boolean) equipment
                    .get(SaveLoad.EQUIPMENT_UNDER_REPAIR);
            final List<Feature> listFeatures = new ArrayList<>();
            final Map stats = (Map) equipment.get("stats");
            final Stats stat = new Stats();
            if (stats != null) {
                final Integer nbBorrowed = ((Double) stats.get("nbBorrowed"))
                        .intValue();
                final Integer nbUnderRepair = ((Double) stats
                        .get("nbUnderRepair")).intValue();
                final List<String> logOperations = (List<String>) stats
                        .get("logOperations");
                stat.setLogOperations(logOperations);
                stat.setNbBorrowed(nbBorrowed);
                stat.setNbUnderRepair(nbUnderRepair);
            }
            final List<Map> features = (List<Map>) equipment
                    .get(SaveLoad.EQUIPMENT_FEATURE);
            for (final Map feature : features) {
                final String name = (String) feature.get(SaveLoad.NAME);
                final String value = (String) feature.get(SaveLoad.VALUE);
                try {
                    final Feature c = new Feature(name, type, value);
                    listFeatures.add(c);
                } catch (final Exception e) {
                    MiniProjectController.LOGGER.severe("message:"
                            + e.getMessage() + "\ntrace:"
                            + java.util.Arrays.toString(e.getStackTrace()));
                }
            }
            final String healthState = (String) ((Map) equipment
                    .get(SaveLoad.EQUIPMENT_HEALTH)).get(SaveLoad.HEALTH_STATE);
            final String healthCause = (String) ((Map) equipment
                    .get(SaveLoad.EQUIPMENT_HEALTH)).get(SaveLoad.HEALTH_CAUSE);
            Health health;
            switch (healthState) {
                case "OK":
                    health = new Health(HealthState.OK);
                    break;
                case "NOT_OK":
                    health = new Health(HealthState.NOT_OK);
                    break;
                default:
                    throw new MiniProjectException(Error.STATE_DO_NOT_EXIST);
            }
            health.setCause(healthCause);
            final Equipment equipmentObject = new Equipment(type, listFeatures,
                    health, underRepair);
            equipmentObject.setStats(stat);
            equipmentObject.setId(id);
        }
        for (final Map borrow : borrows) {
            final String id = (String) borrow.get(SaveLoad.ID);
            final Integer startDay = ((Double) ((Map) borrow
                    .get(SaveLoad.BORROW_START)).get(SaveLoad.DAY)).intValue();
            final Integer startHour = ((Double) ((Map) borrow
                    .get(SaveLoad.BORROW_START)).get(SaveLoad.HOUR)).intValue();
            final Integer startMonth = ((Double) ((Map) borrow
                    .get(SaveLoad.BORROW_START)).get(SaveLoad.MONTH))
                    .intValue();
            final Integer startMinute = ((Double) ((Map) borrow
                    .get(SaveLoad.BORROW_START)).get(SaveLoad.MINUTE))
                    .intValue();
            final Integer startSecond = ((Double) ((Map) borrow
                    .get(SaveLoad.BORROW_START)).get(SaveLoad.SECOND))
                    .intValue();
            final Integer startYear = ((Double) ((Map) borrow
                    .get(SaveLoad.BORROW_START)).get(SaveLoad.YEAR)).intValue();
            final Calendar debut = Calendar.getInstance();
            debut.set(startYear, startMonth, startDay, startHour, startMinute,
                    startSecond);
            final Integer endDay = ((Double) ((Map) borrow
                    .get(SaveLoad.BORROW_END)).get(SaveLoad.DAY)).intValue();
            final Integer endHour = ((Double) ((Map) borrow
                    .get(SaveLoad.BORROW_END)).get(SaveLoad.HOUR)).intValue();
            final Integer endMonth = ((Double) ((Map) borrow
                    .get(SaveLoad.BORROW_END)).get(SaveLoad.MONTH)).intValue();
            final Integer endMinute = ((Double) ((Map) borrow
                    .get(SaveLoad.BORROW_END)).get(SaveLoad.MINUTE)).intValue();
            final Integer endSecond = ((Double) ((Map) borrow
                    .get(SaveLoad.BORROW_END)).get(SaveLoad.SECOND)).intValue();
            final Integer endYear = ((Double) ((Map) borrow
                    .get(SaveLoad.BORROW_END)).get(SaveLoad.YEAR)).intValue();
            final Calendar fin = Calendar.getInstance();
            fin.set(endYear, endMonth, endDay, endHour, endMinute, endSecond);
            final List<String> listEquipment = new ArrayList<>();
            final List<String> equipmentsBorrow = (List<String>) borrow
                    .get(SaveLoad.EQUIPMENT_ID);
            for (final String equipmentBorrow : equipmentsBorrow) {
                listEquipment.add(equipmentBorrow);
            }
            final String borrowerId = (String) borrow.get(SaveLoad.BORROWER_ID);
            final String state = (String) borrow.get(SaveLoad.STATE);
            final Borrower borrower = (Borrower) Inventory
                    .findPersonById(borrowerId);
            Borrow borrowObject = null;
            try {
                borrowObject = Inventory.findBorrowById(borrower
                        .saveLoadBorrow(listEquipment, debut, fin));
            } catch (final MiniProjectException e) {
                MiniProjectController.LOGGER.severe("message:" + e.getMessage()
                        + "\ntrace:"
                        + java.util.Arrays.toString(e.getStackTrace()));
            }
            borrowObject.setId(id);
            if (borrow.containsKey(SaveLoad.ADMINISTRATOR_ID)) {
                final String administrator = (String) borrow
                        .get(SaveLoad.ADMINISTRATOR_ID);
                try {
                    borrowObject.setState(BorrowState.valueOf(state),
                            administrator);
                } catch (final Exception e) {
                    MiniProjectController.LOGGER.severe("message:"
                            + e.getMessage() + "\ntrace:"
                            + java.util.Arrays.toString(e.getStackTrace()));
                }
            }
        }
    }

    /**
     * Save.
     */
    public static void save() {

        SaveLoad.save(SaveLoad.DATA);
    }

    /**
     * Save.
     * 
     * @param file
     *            the file
     */
    public static void save(final String file) {

        final Path path = Paths.get(file);
        try (BufferedWriter writer = Files.newBufferedWriter(path,
                StandardCharsets.UTF_8)) {
            writer.write(SaveLoad.getState());
            writer.newLine();
        } catch (final IOException e) {
            MiniProjectController.LOGGER
                    .severe("message:" + e.getMessage() + "\ntrace:"
                            + java.util.Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Instantiates a new save load.
     */
    private SaveLoad() {

    }
}
