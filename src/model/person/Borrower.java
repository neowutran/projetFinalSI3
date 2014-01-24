/*
 * @author Martini Didier - Fabien Pinel - Maxime Touroute
 */

package model.person;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.*;

import com.google.gson.annotations.Expose;

import config.Config;
import config.Error;
import controllers.MiniProjectController;

/**
 * The Class Borrower.
 */
public class Borrower extends model.Person {

    /**
     * The Class Borrow.
     */
    public final class Borrow extends InventoryElement {

        /**
         * The equipment id.
         */
        @Expose
        private List<String>       equipmentId;
        /**
         * The borrower id.
         */
        @Expose
        private String             borrowerId;
        /**
         * The administrator id.
         */
        @Expose
        private String             administratorId;
        /**
         * The state.
         */
        @Expose
        private BorrowState        state = BorrowState.ASK_BORROW;
        /**
         * The borrow start.
         */
        @Expose
        private java.util.Calendar borrowStart;
        /**
         * The borrow end.
         */
        @Expose
        private java.util.Calendar borrowEnd;
        /**
         * The returned.
         */
        private Calendar           returned;

        /**
         * Instantiates a new borrow.
         */
        private Borrow() {

        }

        /**
         * Instantiates a new borrow.
         *
         * @param equipments
         *            the equipments
         * @param borrowStart
         *            the borrow start
         * @param borrowEnd
         *            the borrow end
         * @throws MiniProjectException
         *             the mini project exception
         */
        private Borrow(final List<String> equipments,
                final java.util.Calendar borrowStart,
                final java.util.Calendar borrowEnd) throws MiniProjectException {

            this.equipmentId = equipments;
            this.borrowerId = Borrower.this.getId();
            this.borrowStart = borrowStart;
            this.borrowEnd = borrowEnd;
            this.setId();
        }

        /*
         * (non-Javadoc)
         *
         * @see model.person.InventoryElement#checkExistence(java.lang.String)
         */
        @Override
        protected boolean checkExistence(final String id)
                throws MiniProjectException {

            return Inventory.findBorrowById(id) != null;
        }

        /**
         * Gets the borrow end.
         *
         * @return the borrow end
         */
        public java.util.Calendar getBorrowEnd() {

            return this.borrowEnd;
        }

        /**
         * Gets the borrower id.
         *
         * @return the borrower id
         */
        public String getBorrowerId() {

            return this.borrowerId;
        }

        /**
         * Gets the borrow start.
         *
         * @return the borrow start
         */
        public java.util.Calendar getBorrowStart() {

            return this.borrowStart;
        }

        /**
         * Gets the equipment id.
         *
         * @return the equipment id
         */
        public List<String> getEquipmentId() {

            return this.equipmentId;
        }

        /**
         * Gets the state.
         *
         * @return the state
         */
        public BorrowState getState() {

            return this.state;
        }

        /**
         * Sets the state.
         *
         * @param state
         *            the state
         * @param administrator
         *            the administrator
         * @throws MiniProjectException
         *             the mini project exception
         */
        public void setState(final BorrowState state, final String administrator)
                throws MiniProjectException {

            if (this.state.equals(state)) {
                return;
            }
            else if (BorrowState.RETURNED.equals(state)) {
                this.returned = Calendar.getInstance();
               for(int i = 0 ; i < equipmentId.size() ; i++)
            		Inventory.getInstance().findEquipmentById(equipmentId.get(i)).updateLog( borrowerId , "returned");
            }else if (BorrowState.ACCEPT.equals(state)
                    && Inventory.isBorrowed(this.getEquipmentId(),
                            this.getBorrowStart(), this.getBorrowEnd())) {
                throw new MiniProjectException(Error.EQUIPMENT_UNAVAILABLE);
            }
            
            this.state = state;
            this.administratorId = administrator;
            for(int i = 0 ; i < equipmentId.size() ; i++)
        		Inventory.getInstance().findEquipmentById(equipmentId.get(i)).updateLog( borrowerId , "borrowed");
           
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            String template = (String) ((Map) Config.getConfiguration().get(
                    Config.TEMPLATE)).get(Config.BORROW);
            final SimpleDateFormat format = new SimpleDateFormat(
                    (String) ((Map) Config.getConfiguration().get(
                            Config.TEMPLATE)).get(Config.FORMAT));
            template = template.replaceAll("\\{equipmentId\\}", this
                    .getEquipmentId().toString());
            template = template.replaceAll("\\{borrowerId\\}",
                    this.getBorrowerId());
            if (this.administratorId != null) {
                template = template.replaceAll("\\{administratorId\\}",
                        this.administratorId);
            } else {
                template = template.replaceAll("\\{administratorId\\}", "null");
            }
            if (this.returned == null) {
                template = template.replaceAll("\\{returned\\}", "null");
            } else {
                template = template.replaceAll("\\{returned\\}",
                        this.state.toString());
            }
            template = template
                    .replaceAll("\\{state\\}", this.state.toString());
            template = template.replaceAll("\\{borrowStart\\}",
                    format.format(this.borrowStart.getTime()));
            template = template.replaceAll("\\{borrowEnd\\}",
                    format.format(this.borrowEnd.getTime()));
            template = template.replaceAll("\\{id\\}", this.getId());
            return template;
        }
    }

    /**
     * The maximum advance days.
     */
    private final Long maximumAdvanceDays;
    /**
     * The maximum hours.
     */
    private final Long maximumHours;

    /**
     * Instantiates a new borrower.
     *
     * @param name
     *            the name
     * @param id
     *            the id
     * @param type
     *            the type
     * @param password
     *            the password
     */
    public Borrower(final String name, final String id, final String type,
            final String password) throws MiniProjectException{

        super(name, id, password);
        this.setType(type);
        this.maximumAdvanceDays = ((Double) ((Map) ((Map) Config
                .getConfiguration().get(Config.BORROWER)).get(this.getType()))
                .get(Config.MAXIMUM_ADVANCE_DAY)).longValue();
        this.maximumHours = ((Double) ((Map) ((Map) Config.getConfiguration()
                .get(Config.BORROWER)).get(this.getType()))
                .get(Config.MAXIMUM_HOUR)).longValue();

    }

    /**
     * Borrow.
     *
     * @param equipment
     *            the equipment
     * @param start
     *            the start
     * @param end
     *            the end
     * @return the string
     * @throws MiniProjectException
     *             the invalid parameter exception
     */
    public String borrow(final List<String> equipment, final Calendar start,
            final Calendar end) throws MiniProjectException {

        if (start.getTimeInMillis() >= end.getTimeInMillis()) {
            throw new MiniProjectException(Error.INVALID_DATE);
        }
        if (Inventory.isBorrowed(equipment, start, end)) {
            throw new MiniProjectException(Error.EQUIPMENT_UNAVAILABLE);
        }
        final Calendar now = Calendar.getInstance();
        final Long maximumAdvance = 1000 * 60 * 60 * 24
                * this.maximumAdvanceDays;
        if (((start.getTimeInMillis() - now.getTimeInMillis()) > maximumAdvance)
                && (this.maximumAdvanceDays != 0)) {
            throw new MiniProjectException(Error.CANNOT_BORROW_ADVANCE);
        }
        final Long maximumTimeDuration = 1000 * 60 * 60 * this.maximumHours;
        if (((end.getTimeInMillis() - start.getTimeInMillis()) > maximumTimeDuration)
                && (this.maximumHours != 0)) {
            throw new MiniProjectException(Error.CANNOT_BORROW_SO_LONG);
        }
        final Long maxTimeHours = this.maxTime(equipment);
        if ((end.getTimeInMillis() - start.getTimeInMillis()) > (maxTimeHours * 1000 * 60 * 60 * 24)) {
            throw new MiniProjectException(Error.CANNOT_BORROW_SO_LONG);
        }
        Borrow borrow;
        try {
            borrow = new Borrow(equipment, start, end);
        } catch (final Exception e) {
            MiniProjectController.LOGGER
                    .severe("message:" + e.getMessage() + "\ntrace:"
                            + java.util.Arrays.toString(e.getStackTrace()));
            return null;
        }
        Inventory.getInstance().addBorrow(borrow);
        new Log(Logs.Type.ADD_BORROW, null, borrow, null,null);
        return borrow.getId();
    }

    /**
     * Max time.
     *
     * @param equipmentsId
     *            the equipments id
     * @return the long
     */
    private Long maxTime(final List<String> equipmentsId) {

        Long maxTime = null;
        for (final String equipmentId : equipmentsId) {
            final Equipment equipment = Inventory
                    .findEquipmentById(equipmentId);
            final Integer quantity = Inventory.findQuantityEquipment(equipment);
            final Integer days = ((Double) ((Map) ((Map) Config
                    .getConfiguration().get(Config.EQUIPMENT)).get(equipment
                    .getType())).get(Config.MAXIMUM_BORROW_TIME)).intValue();
            final Map quantityTime = (Map) Config.getConfiguration().get(
                    Config.QUANTITY_TIME);
            Integer max = 1;
            for (final String key : (Set<String>) quantityTime.keySet()) {
                if (Integer.valueOf(key) < quantity) {
                    max = Integer.valueOf(key);
                } else {
                    break;
                }
            }
            final Double weighting = (Double) ((Map) Config.getConfiguration()
                    .get(Config.QUANTITY_TIME)).get(max.toString());
            if ((maxTime == null) || (maxTime > (weighting.longValue() * days))) {
                maxTime = weighting.longValue() * days;
            }
        }
        return maxTime;
    }

    /**
     * SaveLoad Borrow.
     *
     * @param equipment
     *            the equipment
     * @param start
     *            the start
     * @param end
     *            the end
     * @return the string
     * @throws MiniProjectException
     *             the invalid parameter exception
     */
    public String saveLoadBorrow(final List<String> equipment,
            final Calendar start, final Calendar end)
            throws MiniProjectException {

        if (start.getTimeInMillis() >= end.getTimeInMillis()) {
            throw new MiniProjectException(Error.INVALID_DATE);
        }
        if (Inventory.isBorrowed(equipment, start, end)) {
            throw new MiniProjectException(Error.EQUIPMENT_UNAVAILABLE);
        }
        Borrow borrow;
        try {
            borrow = new Borrow(equipment, start, end);
        } catch (final Exception e) {
            MiniProjectController.LOGGER
                    .severe("message:" + e.getMessage() + "\ntrace:"
                            + java.util.Arrays.toString(e.getStackTrace()));
            return null;
        }
        Inventory.getInstance().addBorrow(borrow);
        return borrow.getId();
    }
}
