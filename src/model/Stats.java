package model;

public class Stats {
	
	private int nbBorrowed;
	private int nbUnderRepair;
	
    /**
     * Sets the health.
     *
     * @param health
     *            the new health
     */
	public Stats(){
		nbBorrowed=0;
		nbUnderRepair=0;
	}
	
    /**
     * Increments variable nbBorrowed
     */
	public void incrNbBorrowed(){
		nbBorrowed++;
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
