/*
 * @author Martini Didier - Fabien Pinel - Maxime Touroute
 */

package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * The Class Stats.
 */
public class Stats {

    /** The nb borrowed. */
    @Expose
    private int          nbBorrowed;
    /** The nb under repair. */
    @Expose
    private int          nbUnderRepair;
    /** The log of operations. */
    @Expose
    private List<String> logOperations;

    /**
     * Sets the stats.
     */
    public Stats() {

        this.nbBorrowed = 0;
        this.nbUnderRepair = 0;
        this.logOperations = new ArrayList<String>();
    }

    /**
     * Gets the nb borrowed.
     * 
     * @return number of borrows made
     */
    public int getNbBorrowed() {

        return this.nbBorrowed;
    }

    /**
     * Increments variable nbUnderRepair.
     */
    public void incrNbUnderRepair() {

        this.nbUnderRepair++;
    }

    /**
     * Sets the log operations.
     * 
     * @param logOperations
     *            the new log operations
     */
    public void setLogOperations(final List<String> logOperations) {

        this.logOperations = logOperations;
    }

    /**
     * Sets the nb borrowed.
     * 
     * @param nbBorrowed
     *            the new nb borrowed
     */
    public void setNbBorrowed(final int nbBorrowed) {

        this.nbBorrowed = nbBorrowed;
    }

    /**
     * Sets the nb under repair.
     * 
     * @param nbUnderRepair
     *            the new nb under repair
     */
    public void setNbUnderRepair(final int nbUnderRepair) {

        this.nbUnderRepair = nbUnderRepair;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "nbBorrowed = " + this.nbBorrowed + "\nbUnderRepair = "
                + this.nbUnderRepair;
    }

    /**
     * to update the logOperation list and stats.
     * 
     * @param userId
     *            the user id
     * @param operationName
     *            the operation name
     */
    public void updateLog(final String userId, final String operationName) {

        switch (operationName) {
            case "borrowed": {
                this.nbBorrowed++;
                this.writeToLog(userId, "Has been borrowed");
                break;
            }
            case "returned": {
                this.writeToLog(userId, "Has been returned");
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * to write on the logOperation list with the right syntax.
     * 
     * @param userId
     *            the user id
     * @param operation
     *            the operation
     */
    private void writeToLog(final String userId, final String operation) {

        String tmp = "[ " + Calendar.getInstance().toString() + " ]";
        tmp += "[ " + operation + " ]";
        tmp += "[ by user " + userId + " ]";
        this.logOperations.add(tmp);
    }
}
