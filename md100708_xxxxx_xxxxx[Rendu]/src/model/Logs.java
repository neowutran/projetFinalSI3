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
import java.util.List;
import java.util.Map;

import lib.Json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import controllers.MiniProjectController;

/**
 * The Class Logs.
 */
public class Logs {

    /**
     * The Enum Type.
     */
    public enum Type {
        /** The add equipment. */
        ADD_EQUIPMENT("add_equipment"),
        /** The add person. */
        ADD_PERSON("add_person"),
        /** The change health. */
        CHANGE_HEALTH("change_health"),
        /** The add borrow. */
        ADD_BORROW("add_borrow"),
        /** The change borrow state. */
        CHANGE_BORROW_STATE("change_borrow_state"),
        /** The add feature. */
        ADD_FEATURE("add_feature"),
        /** The change under repair. */
        CHANGE_UNDER_REPAIR("change_under_repair");

        /** The text. */
        private String text;

        /**
         * Instantiates a new type.
         * 
         * @param text
         *            the text
         */
        Type(final String text) {

            this.text = text;
        }

        /**
         * Gets the text.
         * 
         * @return the text
         */
        public String getText() {

            return this.text;
        }
    }

    /** The Constant EQUIPMENT. */
    private static final String EQUIPMENT = "equipment";
    /** The Constant PERSON. */
    private static final String PERSON    = "person";
    /** The Constant BORROW. */
    private static final String BORROW    = "borrow";
    /** The Constant INFOS. */
    private static final String INFOS     = "infos";
    /** The instance. */
    private static Logs         instance  = null;

    /**
     * Gets the single instance of Logs.
     * 
     * @return single instance of Logs
     */
    public static Logs getInstance() {

        if (Logs.instance == null) {
            Logs.instance = new Logs();
        }
        return Logs.instance;
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
        if (!Files.exists(path)) {
            return;
        }
        final Map data = Json.loadFile(path);
        final List<Map> stats = (List<Map>) data.get("logs");
        for (final Map stat : stats) {
            String equipment = null;
            final Type type = Type.valueOf((String) stat.get("type"));
            final String userId = (String) stat.get("userId");
            final Long calendar = ((Double) stat.get("calendar")).longValue();
            String borrow = null;
            String person = null;
            String infos = null;
            if (stat.containsKey(Logs.EQUIPMENT)) {
                equipment = (String) stat.get(Logs.EQUIPMENT);
            }
            if (stat.containsKey(Logs.PERSON)) {
                person = (String) stat.get(Logs.PERSON);
            }
            if (stat.containsKey(Logs.BORROW)) {
                borrow = (String) stat.get(Logs.BORROW);
            }
            if (stat.containsKey(Logs.INFOS)) {
                infos = (String) stat.get(Logs.INFOS);
            }
            new Log(type, equipment, borrow, person, infos, userId, calendar);
        }
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
            writer.write(Logs.getState());
            writer.newLine();
        } catch (final IOException e) {
            MiniProjectController.LOGGER
                    .severe("message:" + e.getMessage() + "\ntrace:"
                            + java.util.Arrays.toString(e.getStackTrace()));
        }
    }

    /** The logs. */
    @Expose
    private List<Log>          logs  = new ArrayList<>();
    /** The Constant STATS. */
    public static final String STATS = (String) ((Map) config.Config
                                             .getConfiguration())
                                             .get("stats_file");

    /**
     * Gets the state.
     * 
     * @return the state
     */
    private static String getState() {

        final Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(Logs.getInstance());
    }

    /**
     * Save.
     */
    public static void save() {

        Logs.save(Logs.STATS);
    }

    /**
     * Instantiates a new logs.
     */
    private Logs() {

    }

    /**
     * Gets the logs.
     * 
     * @return the logs
     */
    public List<Log> getLogs() {

        return this.logs;
    }

    /**
     * Sets the logs.
     * 
     * @param logs
     *            the new logs
     */
    public void setLogs(final List<Log> logs) {

        this.logs = logs;
    }
}
