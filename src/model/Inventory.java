/*
 * @author Martini Didier - Fabien Pinel - Maxime Touroute
 */

package model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import model.person.Borrower;

import com.google.gson.annotations.Expose;

import config.Config;
import config.Error;
import controllers.MiniProjectController;

/**
 * The Class Inventory.
 */
public final class Inventory {

    /**
     * The instance.
     */
    private static Inventory instance = null;

    /**
     * Adds the equipment.
     *
     * @param type
     *            the type
     * @param numb
     *            the numb
     * @throws MiniProjectException
     *             the mini project exception
     */
    public static void addEquipment(final String type, final String numb)
            throws MiniProjectException { // TODO "Ajouter la saisie"? Maxime, voit ce qu'il faut faire. A TESTER

        if (((Map) Config.getConfiguration().get(Config.EQUIPMENT))
                .containsKey(type)) {
            for (int i = 0; i < Integer.parseInt(numb); i++) {
                try {
                    Equipment equipment = new Equipment(type, new ArrayList<Feature>(), new Health(
                            HealthState.OK), false);
                    new Log(Logs.Type.ADD_EQUIPMENT, equipment, null,null, numb);
                } catch (final MiniProjectException e) {
                    throw new MiniProjectException(e);
                }
            }
            return;
        }
        throw new MiniProjectException(Error.EQUIPMENT_DO_NOT_EXIST);
    }

    /**
     * Adds the feature by id.
     *
     * @param equipmentId
     *            the equipment id
     * @param name
     *            the name
     * @param value
     *            the value
     * @throws MiniProjectException
     *             the invalid parameter exception
     */
    public static void addFeatureById(final String equipmentId,
            final String name, final String value)
            throws MiniProjectException { //TODO !!A TESTER!!->C'est bon tous les �as marchent. (Maxime).

        final Equipment equipment = Inventory.findEquipmentById(equipmentId);
        if (null == equipment) {
            throw new MiniProjectException(Error.EQUIPMENT_DO_NOT_EXIST);
        }
        final Map tmpList = (Map) ((Map) (Config.getConfiguration()
                .get(Config.EQUIPMENT))).get(equipment.getType());
        if(tmpList.get(name) == null){
            throw new MiniProjectException(Error.FEATURE_DOESNT_EXIST);
        }
        if (!((List) tmpList.get(name)).contains(value)) {
            throw new MiniProjectException(Error.FEATURE_DOESNT_EXIST);
        }

        final List<Feature> tmp = equipment.getFeatures();

        try {
            tmp.add(new Feature(name, Inventory.findEquipmentById(equipmentId)
                    .getType(), value));

        } catch (final MiniProjectException e) {
            MiniProjectController.LOGGER.severe(java.util.Arrays.toString(e
                    .getStackTrace()));
        }
        Inventory.findEquipmentById(equipmentId).setFeatures(tmp);
        new Log(Logs.Type.ADD_FEATURE, Inventory.findEquipmentById(equipmentId), null, null, name+":"+value);

    }

    /**
     * Check size.
     *
     * @param features
     *            the features
     * @param operator
     *            the operator
     * @param value
     *            the value
     * @throws MiniProjectException
     *             the invalid parameter exception
     */
    private static void checkSize(final List<String> features,
            final List<String> operator, final List<String> value)
            throws MiniProjectException {

        if ((features.size() != operator.size())
                || (operator.size() != value.size())) {
            throw new MiniProjectException(config.Error.NOT_SAME_SIZE);
        }
    }

    /**
     * Evaluate.
     *
     * @param operator
     *            the operator
     * @param value
     *            the value
     * @param feature
     *            the feature
     * @return the boolean
     * @throws MiniProjectException
     *             the mini project exception
     */
    private static Boolean evaluate(final String operator, final String value,
            final Feature feature) throws MiniProjectException {

        Integer type = 0;
        try {
            if (feature.getIsDoubleValue()) {
                type = 2;
            } else {
                type = 1;
            }

            switch (operator) {
                case "=":
                    return (Boolean) feature.getClass()
                            .getMethod("isEquals", String.class)
                            .invoke(feature, value);
                case ">=":
                    if (type != 2) {
                        throw new MiniProjectException(
                                Error.CANNOT_USE_THIS_OPERATOR);
                    }
                    return (Boolean) feature.getClass()
                            .getMethod("greaterThanOrEquals", String.class)
                            .invoke(feature, value);
                case "<=":
                    if (type != 2) {
                        throw new MiniProjectException(
                                Error.CANNOT_USE_THIS_OPERATOR);
                    }
                    return (Boolean) feature.getClass()
                            .getMethod("lesserThanOrEquals", String.class)
                            .invoke(feature, value);
                case "<":
                    if (type != 2) {
                        throw new MiniProjectException(
                                Error.CANNOT_USE_THIS_OPERATOR);
                    }
                    return (Boolean) feature.getClass()
                            .getMethod("lesserThan", String.class)
                            .invoke(feature, value);
                case ">":
                    if (type != 2) {
                        throw new MiniProjectException(
                                Error.CANNOT_USE_THIS_OPERATOR);
                    }
                    return (Boolean) feature.getClass()
                            .getMethod("greaterThan", String.class)
                            .invoke(feature, value);
                default:
                    throw new MiniProjectException(
                            Error.CANNOT_USE_THIS_OPERATOR);
            }
        } catch (NoSuchMethodException | InvocationTargetException
                | IllegalAccessException e) {
            throw new MiniProjectException(e);
        }
    }

    /**
     * Find.
     *
     * @param type
     *            the type
     * @param features
     *            the features
     * @param operators
     *            the operators
     * @param value
     *            the value
     * @return the list
     * @throws MiniProjectException
     *             the mini project exception
     */
    public static List<Equipment> find(final String type,
            final List<String> features, final List<String> operators,
            final List<String> value) throws MiniProjectException {

        Inventory.checkSize(features, operators, value);
        final List<Equipment> equipments = new ArrayList<>();
        for (final Equipment equipment : Inventory.getInstance()
                .getEquipments()) {
            if (!equipment.getType().equals(type) && (type != null)) {
                continue;
            }
            boolean good = true;
            for (int i = 0; i < features.size(); i++) {
                for (final Feature equipmentFeature : equipment.getFeatures()) {
                    if (!Inventory.evaluate(operators.get(i), value.get(i),
                            equipmentFeature)) {
                        good = false;
                    }
                }
            }
            if (good) {
                equipments.add(equipment);
            }
        }
        return equipments;
    }

    /**
     * Find actual borrow by borrower.
     *
     * @param borrowerId
     *            the borrower id
     * @return the list
     */
    public static List<Borrower.Borrow> findActualBorrowByBorrower(
            final String borrowerId) {

        final List<Borrower.Borrow> borrows = new ArrayList<>();
        for (final Borrower.Borrow borrow : Inventory.getInstance()
                .getBorrows()) {
            if (borrow.getBorrowerId().equals(borrowerId)
                    && !borrow.getState().equals(BorrowState.RETURNED)) {
                borrows.add(borrow);
            }
        }
        return borrows;
    }

    /**
     * Find available.
     *
     * @param start
     *            the start
     * @param end
     *            the end
     * @return the list
     */
    public static List<Equipment> findAvailable(final Calendar start,
            final Calendar end) {

        final List<Equipment> equipments = new ArrayList<>();
        for (final Equipment equipment : Inventory.getInstance()
                .getEquipments()) {
            Boolean available = true;
            for (final Borrower.Borrow borrow : Inventory.getInstance()
                    .getBorrows()) {
                if (borrow.getEquipmentId().contains(equipment.getId())
                        && (borrow.getBorrowStart().getTimeInMillis() < end
                                .getTimeInMillis())
                        && (borrow.getBorrowEnd().getTimeInMillis() > start
                                .getTimeInMillis())) {
                    available = false;
                }
            }
            if (available) {
                equipments.add(equipment);
            }
        }
        return equipments;
    }

    /**
     * Find borrow by borrower.
     *
     * @param borrowerId
     *            the borrower id
     * @return the list
     */
    public static List<Borrower.Borrow> findBorrowByBorrower(
            final String borrowerId) {

        final List<Borrower.Borrow> borrows = new ArrayList<>();
        for (final Borrower.Borrow borrow : Inventory.getInstance()
                .getBorrows()) {
            if (borrow.getBorrowerId().equals(borrowerId)) {
                borrows.add(borrow);
            }
        }
        return borrows;
    }

    /**
     * Find borrow by id.
     *
     * @param id
     *            the id
     * @return the borrow
     */
    public static Borrower.Borrow findBorrowById(final String id) {

        for (final Borrower.Borrow borrow : Inventory.getInstance()
                .getBorrows()) {
            if (borrow.getId().equals(id)) {
                return borrow;
            }
        }
        return null;
    }

    /**
     * Find borrow waiting for administrator.
     *
     * @return the list
     */
    public static List<Borrower.Borrow> findBorrowWaitingForAdministrator() {

        final List<Borrower.Borrow> borrows = new ArrayList<>();
        for (final Borrower.Borrow borrow : Inventory.getInstance()
                .getBorrows()) {
            if (borrow.getState().equals(BorrowState.ASK_BORROW)) {
                borrows.add(borrow);
            }
        }
        return borrows;
    }

    /**
     * Find borrow with equipment not ok.
     *
     * @return the list
     */
    public static List<Borrower.Borrow> findBorrowWithEquipmentNotOk() {

        final List<Borrower.Borrow> borrows = new ArrayList<>();
        for (final Borrower.Borrow borrow : Inventory.getInstance().borrows) {
            if (borrow.getBorrowEnd().getTimeInMillis() <= Calendar
                    .getInstance().getTimeInMillis()) {
                continue;
            }
            for (final String equipmentId : borrow.getEquipmentId()) {
                if (Inventory.findEquipmentById(equipmentId).getHealth()
                        .getHealthState().equals(HealthState.NOT_OK)) {
                    borrows.add(borrow);
                    break;
                }
            }
        }
        return borrows;
    }

    /**
     * Find borrow with equipment under repair.
     *
     * @return the list
     */
    public static List<Borrower.Borrow> findBorrowWithEquipmentUnderRepair() {

        final List<Borrower.Borrow> borrows = new ArrayList<>();
        for (final Borrower.Borrow borrow : Inventory.getInstance().borrows) {
            if (borrow.getBorrowEnd().getTimeInMillis() <= Calendar
                    .getInstance().getTimeInMillis()) {
                continue;
            }
            for (final String equipmentId : borrow.getEquipmentId()) {
                if (Inventory.findEquipmentById(equipmentId).getUnderRepair()) {
                    borrows.add(borrow);
                    break;
                }
            }
        }
        return borrows;
    }

    /**
     * Find equipment by id.
     *
     * @param id
     *            the id
     * @return the equipment
     */
    public static Equipment findEquipmentById(final String id) {

        for (final Equipment equipment : Inventory.getInstance()
                .getEquipments()) {
            if (equipment.getId().equals(id)) {
                return equipment;
            }
        }
        return null;
    }

    /**
     * Find equipment under repair.
     *
     * @return the list
     */
    public static List<Equipment> findEquipmentUnderRepair() {

        final List<Equipment> equipments = new ArrayList<>();
        for (final Equipment equipment : Inventory.getInstance()
                .getEquipments()) {
            if (equipment.getUnderRepair()) {
                equipments.add(equipment);
            }
        }
        return equipments;
    }

    /**
     * Find equipments who need repair.
     *
     * @return the list
     */
    public static List<Equipment> findEquipmentWhoNeedRepair() {

        final List<Equipment> equipments = new ArrayList<>();
        for (final Equipment equipment : Inventory.getInstance()
                .getEquipments()) {
            if (!equipment.getUnderRepair()
                    && equipment.getHealth().getHealthState()
                            .equals(HealthState.NOT_OK)) {
                equipments.add(equipment);
            }
        }
        return equipments;
    }

    /**
     * Find late borrow.
     *
     * @return the list
     */
    public static List<Borrower.Borrow> findLateBorrow() {

        final List<Borrower.Borrow> borrows = new ArrayList<>();
        for (final Borrower.Borrow borrow : Inventory.getInstance()
                .getBorrows()) {
            if (borrow.getBorrowEnd().getTimeInMillis() < Calendar
                    .getInstance().getTimeInMillis()) {
                borrows.add(borrow);
            }
        }
        return borrows;
    }

    /**
     * Find person by id.
     *
     * @param id
     *            the id
     * @return the person
     */
    public static Person findPersonById(final String id) {

        for (final Person person : Person.getPersons()) {
            if (person.getId().equals(id)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Find quantity equipment.
     *
     * @param findEquipment
     *            the find equipment
     * @return the integer
     */
    public static Integer findQuantityEquipment(final Equipment findEquipment) {

        Integer quantity = 0;
        for (final Equipment equipment : Inventory.getInstance()
                .getEquipments()) {
            if (equipment.equals(findEquipment)) {
                quantity++;
            }
        }
        return quantity;
    }

    /**
     * Gets the single instance of Inventory.
     *
     * @return single instance of Inventory
     */
    public static Inventory getInstance() {

        if (Inventory.instance == null) {
            Inventory.instance = new Inventory();
        }
        return Inventory.instance;
    }

    /**
     * Checks if is borrowed.
     *
     * @param equipmentsId
     *            the equipments id
     * @param start
     *            the start
     * @param end
     *            the end
     * @return true, if is borrowed
     */
    public static boolean isBorrowed(final List<String> equipmentsId,
            final Calendar start, final Calendar end) {

        for (final Borrower.Borrow borrow : Inventory.getInstance()
                .getBorrows()) {
            for (final String borrowEquipment : borrow.getEquipmentId()) {
                for (final String materielId : equipmentsId) {
                    if (borrowEquipment.equals(materielId)
                            && (borrow.getBorrowStart().getTimeInMillis() < end
                                    .getTimeInMillis())
                            && (borrow.getBorrowEnd().getTimeInMillis() > start
                                    .getTimeInMillis())
                            && borrow.getState().equals(BorrowState.ACCEPT)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if is borrower.
     *
     * @param id
     *            the id
     * @return true, if is borrower
     * @throws MiniProjectException
     *             the invalid parameter exception
     */
    public static boolean isBorrower(final String id)
            throws MiniProjectException {

        final Person person = Inventory.findPersonById(id);
        if (person == null) {
            throw new MiniProjectException(Error.INVALID_ID);
        }
        return ((Map) Config.getConfiguration().get(
                SaveLoad.PERSON_TYPE_BORROWER)).containsKey(person.getType());
    }

    /**
     * The equipments.
     */
    @Expose
    private java.util.List<Equipment>   equipments = new ArrayList<>();
    /**
     * The borrows.
     */
    @Expose
    private final List<Borrower.Borrow> borrows    = new ArrayList<>();

    /**
     * Instantiates a new inventory.
     */
    protected Inventory() {

        // Exists only to defeat instantiation.
    }

    /**
     * Adds the borrow.
     *
     * @param borrow
     *            the borrow
     */
    public void addBorrow(final Borrower.Borrow borrow) {

        this.borrows.add(borrow);
    }

    /**
     * Gets the borrows.
     *
     * @return the borrows
     */
    public List<Borrower.Borrow> getBorrows() {

        return this.borrows;
    }

    /**
     * Gets the equipments.
     *
     * @return the equipments
     */
    public java.util.List<Equipment> getEquipments() {

        return this.equipments;
    }

    /**
     * Sets the equipments.
     *
     * @param equipments
     *            the new equipments
     */
    public void setEquipments(final java.util.List<Equipment> equipments) {

        this.equipments = equipments;
    }
}
