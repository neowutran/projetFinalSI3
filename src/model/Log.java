/*
 * @author Martini Didier - Fabien Pinel - Maxime Touroute
 */

package model;

import java.util.Calendar;
import java.util.List;

import model.person.Borrower;

import com.google.gson.annotations.Expose;

/**
 * The Class Log.
 */
public class Log {

    /** The type. */
    @Expose
    private Logs.Type type;
    /** The user id. */
    @Expose
    private String    userId;
    /** The equipment. */
    @Expose
    private String    equipment;
    /** The borrow. */
    @Expose
    private String    borrow;
    /** The calendar. */
    @Expose
    private Long      calendar;
    /** The person. */
    @Expose
    private String    person;
    /** The infos. */
    @Expose
    private String    infos;

    /**
     * Instantiates a new log.
     * 
     * @param type
     *            the type
     * @param equipment
     *            the equipment
     * @param borrow
     *            the borrow
     * @param person
     *            the person
     * @param info
     *            the info
     */
    public Log(final Logs.Type type, final Equipment equipment,
            final Borrower.Borrow borrow, final Person person, final String info) {

        this.type = type;
        if (User.getInstance().getPersonId() == null) {
            return;
        }
        this.userId = User.getInstance().getPersonId();
        if (equipment != null) {
            this.equipment = equipment.getId();
        }
        if (borrow != null) {
            this.borrow = borrow.getId();
        }
        this.calendar = Calendar.getInstance().getTimeInMillis();
        if (person != null) {
            this.person = person.getId();
        }
        this.infos = info;
        final List<Log> logs = Logs.getInstance().getLogs();
        logs.add(this);
        Logs.getInstance().setLogs(logs);
    }

    /**
     * Instantiates a new log.
     * 
     * @param type
     *            the type
     * @param equipment
     *            the equipment
     * @param borrow
     *            the borrow
     * @param person
     *            the person
     * @param info
     *            the info
     * @param userId
     *            the user id
     * @param calendar
     *            the calendar
     */
    public Log(final Logs.Type type, final String equipment,
            final String borrow, final String person, final String info,
            final String userId, final Long calendar) {

        this.type = type;
        this.userId = userId;
        this.equipment = equipment;
        this.borrow = borrow;
        this.calendar = calendar;
        this.person = person;
        this.infos = info;
        final List<Log> logs = Logs.getInstance().getLogs();
        logs.add(this);
        Logs.getInstance().setLogs(logs);
    }
}
