package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import controllers.MiniProjectController;
import lib.Json;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Logs {

    public enum Type{
        ADD_EQUIPMENT("add_equipment"),
        ADD_PERSON("add_person"),
        CHANGE_HEALTH("change_health"),
        ADD_BORROW("add_borrow"),
        CHANGE_BORROW_STATE("change_borrow_state"),
        ADD_FEATURE("add_feature"),
        CHANGE_UNDER_REPAIR("change_under_repair");

        private String text;

        Type( String text ) {
            this.text = text;
        }

        public String getText( ) {
            return this.text;
        }
    }

    private static Logs instance = null;

    public static Logs getInstance() {

        if (Logs.instance == null) {
            Logs.instance = new Logs();
        }
        return Logs.instance;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    @Expose
    private List<Log> logs = new ArrayList<>();

    private Logs(){}

    public static final String STATS                      = (String) ((Map) config.Config
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

        Logs.save(STATS);
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
        if(!Files.exists(path)) return;
        final Map data = Json.loadFile(path);
        List<Map> stats = (List<Map>)data.get("logs");
        for(Map stat: stats){
            String equipment = null;
            Type type = Type.valueOf((String)stat.get("type"));
            String userId = (String)stat.get("userId");
            Long calendar = ((Double)stat.get("calendar")).longValue();
            String borrow = null;
            String person = null;
            String infos = null;
            if(stat.containsKey("equipment")){
               equipment = (String)stat.get("equipment");
            }
            if(stat.containsKey("person")){
                person = (String)stat.get("person");
            }
            if(stat.containsKey("borrow")){
                borrow = (String)stat.get("borrow");
            }
            if(stat.containsKey("infos")){
                infos = (String)stat.get("infos");
            }

            new Log(type, equipment,borrow,person,infos,userId,calendar);

        }

    }

}
