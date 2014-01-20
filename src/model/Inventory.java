
package model;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import config.Error;
import model.finder.IInteger;
import model.finder.IString;
import model.person.Borrower;

import com.google.gson.annotations.Expose;

/**
 * The Class Inventory.
 */
public final class Inventory {

    /** The instance. */
    private static Inventory instance = null;

    /**
     * Gets the single instance of Inventory.
     *
     * @return single instance of Inventory
     */
    public static Inventory getInstance( ) {
        if( Inventory.instance == null ) {
            Inventory.instance = new Inventory( );
        }
        return Inventory.instance;
    }

    /** The equipments. */
    @Expose
    private java.util.List<Equipment>   equipments = new ArrayList<>( );

    /** The borrows. */
    @Expose
    private final List<Borrower.Borrow> borrows    = new ArrayList<>( );

    /**
     * Instantiates a new inventory.
     */
    protected Inventory( ) {
        // Exists only to defeat instantiation.
    }

    /**
     * Adds the borrow.
     *
     * @param borrow the borrow
     */
    public void addBorrow( final Borrower.Borrow borrow ) {

        this.borrows.add( borrow );

    }

    /**
     * Gets the borrows.
     *
     * @return the borrows
     */
    public List<Borrower.Borrow> getBorrows( ) {

        return this.borrows;
    }

    /**
     * Gets the equipments.
     *
     * @return the equipments
     */
    public java.util.List<Equipment> getEquipments( ) {

        return this.equipments;
    }

    /**
     * Sets the equipments.
     *
     * @param equipments the new equipments
     */
    public void setEquipments( final java.util.List<Equipment> equipments ) {

        this.equipments = equipments;
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
     * @throws java.security.InvalidParameterException
     *             the invalid parameter exception
     */
    private static void checkSize( final List<String> features,
                                   final List<String> operator, final List<String> value )
            throws InvalidParameterException {

        if( ( features.size( ) != operator.size( ) )
                || ( operator.size( ) != value.size( ) ) ) {
            throw new InvalidParameterException( config.Error.NOT_SAME_SIZE );
        }

    }

    /**
     * Evaluate.
     *
     * @param featureString
     *            the feature string
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
    private static Boolean evaluate( final String featureString,
                                     final String operator, final String value, final Feature feature )
            throws MiniProjectException {

        Integer type = 0;
        try {
            final Class classFeature = Class.forName( "model.feature."
                    + featureString );
            final Class[ ] interfaceFeature = classFeature.getInterfaces( );
            for( final Class anInterfaceFeature : interfaceFeature ) {
                if( anInterfaceFeature.equals( IString.class ) && ( type != 2 ) ) {
                    type = 1;
                }
                if( anInterfaceFeature.equals( IInteger.class ) ) {
                    type = 2;
                }
            }

            if( type == 0 ) {
                throw new MiniProjectException( Error.FEATURE_DOESNT_EXIST );

            }

            switch( operator ) {
                case "=":

                    return ( Boolean ) feature.getClass( )
                            .getMethod( "isEquals", String.class )
                            .invoke( feature, value );

                case ">=":

                    if( type != 2 ) {
                        throw new MiniProjectException(
                                Error.CANNOT_USE_THIS_OPERATOR );
                    }
                    return ( Boolean ) feature.getClass( )
                            .getMethod( "greaterThanOrEquals", String.class )
                            .invoke( feature, value );

                case "<=":
                    if( type != 2 ) {
                        throw new MiniProjectException(
                                Error.CANNOT_USE_THIS_OPERATOR );
                    }
                    return ( Boolean ) feature.getClass( )
                            .getMethod( "lesserThanOrEquals", String.class )
                            .invoke( feature, value );

                case "<":
                    if( type != 2 ) {
                        throw new MiniProjectException(
                                Error.CANNOT_USE_THIS_OPERATOR );
                    }
                    return ( Boolean ) feature.getClass( )
                            .getMethod( "lesserThan", String.class )
                            .invoke( feature, value );

                case ">":
                    if( type != 2 ) {
                        throw new MiniProjectException(
                                Error.CANNOT_USE_THIS_OPERATOR );
                    }
                    return ( Boolean ) feature.getClass( )
                            .getMethod( "greaterThan", String.class )
                            .invoke( feature, value );
                default:
                    throw new MiniProjectException( Error.CANNOT_USE_THIS_OPERATOR );

            }

        } catch( ClassNotFoundException | NoSuchMethodException
                | InvocationTargetException | IllegalAccessException e ) {
            throw new MiniProjectException( e );
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
    public static List<Equipment> find( final String type,
                                        final List<String> features, final List<String> operators,
                                        final List<String> value ) throws MiniProjectException {
        Inventory.checkSize( features, operators, value );
        final List<Equipment> equipments = new ArrayList<>( );
        for( final Equipment equipment : Inventory.getInstance( )
                .getEquipments( ) ) {

            if( !equipment.getType( ).equals( type ) && ( type != null ) ) {
                continue;
            }
            boolean good = true;
            for( int i = 0; i < features.size( ); i++ ) {

                for( final Feature equipmentFeature : equipment.getFeatures( ) ) {

                    if( !Inventory.evaluate( features.get( i ),
                            operators.get( i ), value.get( i ),
                            equipmentFeature ) ) {
                        good = false;
                    }

                }

            }

            if( good ) {
                equipments.add( equipment );
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
            final String borrowerId ) {
        final List<Borrower.Borrow> borrows = new ArrayList<>( );

        for( final Borrower.Borrow borrow : Inventory.getInstance( ).getBorrows( ) ) {

            if( borrow.getBorrowerId( ).equals( borrowerId )
                    && !borrow.getState( ).equals( State.RETURNED ) ) {
                borrows.add( borrow );
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
    public static List<Equipment> findAvailable( final Calendar start,
                                                 final Calendar end ) {

        final List<Equipment> equipments = new ArrayList<>( );
        for( final Equipment equipment : Inventory.getInstance( )
                .getEquipments( ) ) {

            Boolean available = true;

            for( final Borrower.Borrow borrow : Inventory.getInstance( ).getBorrows( ) ) {

                if( borrow.getEquipmentId( ).contains( equipment.getId( ) )
                        && ( borrow.getBorrowStart( ).getTimeInMillis( ) < end
                        .getTimeInMillis( ) )
                        && ( borrow.getBorrowEnd( ).getTimeInMillis( ) > start
                        .getTimeInMillis( ) ) ) {

                    available = false;

                }

            }

            if( available ) {
                equipments.add( equipment );
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
    public static List<Borrower.Borrow> findBorrowByBorrower( final String borrowerId ) {

        final List<Borrower.Borrow> borrows = new ArrayList<>( );
        for( final Borrower.Borrow borrow : Inventory.getInstance( ).getBorrows( ) ) {

            if( borrow.getBorrowerId( ).equals( borrowerId ) ) {
                borrows.add( borrow );
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
    public static Borrower.Borrow findBorrowById( final String id ) {

        for( final Borrower.Borrow borrow : Inventory.getInstance( ).getBorrows( ) ) {
            if( borrow.getId( ).equals( id ) ) {
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
    public static List<Borrower.Borrow> findBorrowWaitingForAdministrator( ) {
        final List<Borrower.Borrow> borrows = new ArrayList<>( );
        for( final Borrower.Borrow borrow : Inventory.getInstance( ).getBorrows( ) ) {
            if( borrow.getState( ).equals( State.ASK_BORROW ) ) {
                borrows.add( borrow );
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
    public static Equipment findEquipmentById( final String id ) {

        for( final Equipment equipment : Inventory.getInstance( )
                .getEquipments( ) ) {
            if( equipment.getId( ).equals( id ) ) {
                return equipment;
            }
        }

        return null;

    }

    /**
     * Find late borrow.
     *
     * @return the list
     */
    public static List<Borrower.Borrow> findLateBorrow( ) {
        final List<Borrower.Borrow> borrows = new ArrayList<>( );
        for( final Borrower.Borrow borrow : Inventory.getInstance( ).getBorrows( ) ) {

            if( borrow.getBorrowEnd( ).getTimeInMillis( ) < Calendar
                    .getInstance( ).getTimeInMillis( ) ) {
                borrows.add( borrow );
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
    public static Person findPersonById( final String id ) {

        for( final Person person : Person.getPersons( ) ) {
            if( person.getId( ).equals( id ) ) {
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
    public static Integer findQuantityEquipment( final Equipment findEquipment ) {

        Integer quantity = 0;
        for( final Equipment equipment : Inventory.getInstance( )
                .getEquipments( ) ) {

            if( equipment.equals( findEquipment ) ) {
                quantity++;
            }

        }
        return quantity;

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
    public static boolean isBorrowed( final List<String> equipmentsId,
                                      final Calendar start, final Calendar end ) {

        for( final Borrower.Borrow borrow : Inventory.getInstance( ).getBorrows( ) ) {
            for( final String borrowEquipment : borrow.getEquipmentId( ) ) {
                for( final String materielId : equipmentsId ) {
                    if( borrowEquipment.equals( materielId )
                            && ( borrow.getBorrowStart( ).getTimeInMillis( ) < end
                            .getTimeInMillis( ) )
                            && ( borrow.getBorrowEnd( ).getTimeInMillis( ) > start
                            .getTimeInMillis( ) )
                            && borrow.getState( ).equals( State.ACCEPT ) ) {
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
     * @throws InvalidParameterException
     *             the invalid parameter exception
     */
    public static boolean isBorrower( final String id )
            throws InvalidParameterException {

        final Person person = Inventory.findPersonById( id );
        if( person == null ) {
            throw new InvalidParameterException( Error.INVALID_ID );
        }
        return person.getType( ).equals( SaveLoad.PERSON_TYPE_STUDENT )
                || person.getType( ).equals( SaveLoad.PERSON_TYPE_TEACHER );

    }
}
