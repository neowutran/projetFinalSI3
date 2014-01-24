package model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Stats {
    public void setNbBorrowed(int nbBorrowed) {
        this.nbBorrowed = nbBorrowed;
    }

    /** The nb borrowed */
	@Expose
	private int nbBorrowed;

    public void setNbUnderRepair(int nbUnderRepair) {
        this.nbUnderRepair = nbUnderRepair;
    }

    /** The nb under repair */
	@Expose
	private int nbUnderRepair;

    public void setLogOperations(List<String> logOperations) {
        this.logOperations = logOperations;
    }

    /** The log of operations */
	@Expose
	private List<String> logOperations;

    /**
     * Sets the stats.
     */
	public Stats(){
		nbBorrowed=0;
		nbUnderRepair=0;
		logOperations = new ArrayList<String>();
	}


	 /**
	 * to update the logOperation list and stats
     * @param id of the user
     * @param operation made
     */

	public void updateLog(String userId , String operationName) {

		switch(operationName) {
		case "borrowed":{
			nbBorrowed++;
			writeToLog( userId , "Has been borrowed");
			break;
		}
		case "returned":{
			writeToLog( userId , "Has been returned");
				break;
		}
		default:{
			break;
		}


		}


	}

	 /**
	 * to write on the logOperation list with the right syntax
    * @param id of the user
    * @param log to add
    */
	private void writeToLog(String userId, String operation) {

		String tmp="[ "+ Calendar.getInstance().toString() +" ]";
		tmp+="[ "+operation+" ]";
		tmp+= "[ by user " + userId +" ]";

		logOperations.add(tmp);

	}


	 /**
     * @return number of borrows made
     */
	public int getNbBorrowed() {
		return nbBorrowed;
	}

    /**
     * Increments variable nbUnderRepair
     */
	public void incrNbUnderRepair(){
		nbUnderRepair++;
	}

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
	public String toString(){
		return "nbBorrowed = " + nbBorrowed + "\nbUnderRepair = " + nbUnderRepair;
	}

}
