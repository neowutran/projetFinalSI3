/*
 * @author Martini Didier - Fabien Pinel - Maxime Touroute
 */

package model.person;

import model.BorrowState;
import model.Log;
import model.Logs;
import model.MiniProjectException;
import model.SaveLoad;
import controllers.MiniProjectController;

/**
 * The Class Administrator.
 */
public class Administrator extends model.Person {

    /**
     * Instantiates a new administrator.
     * 
     * @param name
     *            the name
     * @param id
     *            the id
     * @param password
     *            the password
     * @throws MiniProjectException
     *             the mini project exception
     */
    public Administrator(final String name, final String id,
            final String password) throws MiniProjectException {

        super(name, id, password);
        this.setType(SaveLoad.PERSON_TYPE_ADMINISTRATOR);
    }

    /**
     * Sets the borrow stat.
     * 
     * @param borrow
     *            the borrow
     * @param state
     *            the state
     * @return true, if successful
     */
    public boolean setBorrowStat(final Borrower.Borrow borrow,
            final BorrowState state) {

        try {
            borrow.setState(state, this.getId());
        } catch (final MiniProjectException e) {
            MiniProjectController.LOGGER
                    .severe("message:" + e.getMessage() + "\ntrace:"
                            + java.util.Arrays.toString(e.getStackTrace()));
            return false;
        }
        new Log(Logs.Type.CHANGE_BORROW_STATE, null, borrow, null,
                state.toString());
        return true;
    }
}
