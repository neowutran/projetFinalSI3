
package model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import lib.Json;
import model.person.Administrator;
import model.person.Borrower;
import model.person.Borrower.Borrow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import config.Error;
import controllers.MiniProjectController;

/**
 * The Class SaveLoad.
 */
public final class SaveLoad {

    /** The Constant INVENTORY. */
    public static final String INVENTORY                 = "inventory";

    /** The Constant INVENTORY_EQUIPMENT. */
    public static final String INVENTORY_EQUIPMENT       = "equipments";

    /** The Constant INVENTORY_BORROW. */
    public static final String INVENTORY_BORROW          = "borrows";

    /** The Constant PERSON. */
    public static final String PERSON                    = "person";

    /** The Constant TYPE. */
    public static final String TYPE                      = "type";

    /** The Constant PERSON_TYPE_STUDENT. */
    public static final String PERSON_TYPE_STUDENT       = "student";

    /** The Constant PERSON_TYPE_TEACHER. */
    public static final String PERSON_TYPE_TEACHER       = "teacher";

    /** The Constant PERSON_TYPE_ADMINISTRATOR. */
    public static final String PERSON_TYPE_ADMINISTRATOR = "administrator";

    /** The Constant NAME. */
    public static final String NAME                      = "name";

    /** The Constant ID. */
    public static final String ID                        = "id";

    /** The Constant EQUIPMENT_FEATURE. */
    public static final String EQUIPMENT_FEATURE         = "features";

    /** The Constant VALUE. */
    public static final String VALUE                     = "value";

    /** The Constant PACKAGE_FEATURE. */
    public static final String PACKAGE_FEATURE           = "model.feature.";

    /** The Constant BORROW_START. */
    public static final String BORROW_START              = "borrowStart";

    /** The Constant BORROW_END. */
    public static final String BORROW_END                = "borrowEnd";

    /** The Constant DAY. */
    public static final String DAY                       = "dayOfMonth";

    /** The Constant HOUR. */
    public static final String HOUR                      = "hourOfDay";

    /** The Constant MONTH. */
    public static final String MONTH                     = "month";

    /** The Constant MINUTE. */
    public static final String MINUTE                    = "minute";

    /** The Constant SECOND. */
    public static final String SECOND                    = "second";

    /** The Constant YEAR. */
    public static final String YEAR                      = "year";

    /** The Constant EQUIPMENT_ID. */
    public static final String EQUIPMENT_ID              = "equipmentId";

    /** The Constant BORROWER_ID. */
    public static final String BORROWER_ID               = "borrowerId";

    /** The Constant STATE. */
    public static final String STATE                     = "state";

    /** The Constant ADMINISTRATOR_ID. */
    public static final String ADMINISTRATOR_ID          = "administratorId";

    /** The Constant PASSWORD. */
    public static final String PASSWORD                  = "password";

    /** The Constant DATA. */
    public static final String DATA                      = ( String ) ( ( Map ) config.Config
                                                                 .getConfiguration( ) )
                                                                 .get( "data_file" );

    /**
     * Gets the state.
     *
     * @return the state
     */
    private static String getState( ) {

        final Gson gson = new GsonBuilder( )
                .excludeFieldsWithoutExposeAnnotation( ).create( );
        String state = "{\"" + SaveLoad.PERSON + "\":";
        state += gson.toJson( Person.getPersons( ) );
        state += ",\"" + SaveLoad.INVENTORY + "\":";
        state += gson.toJson( Inventory.getInstance( ) );
        state += "}";
        return state;

    }

    /**
     * Load.
     *
     * @param file
     *            the file
     * @throws MiniProjectException
     *             the mini project exception
     */
    public static void load( final String file ) throws MiniProjectException {

        final Path path = Paths.get( file );
        final Map data = Json.loadFile( path );
        final List<Map> equipments = ( List<Map> ) ( ( Map ) data
                .get( SaveLoad.INVENTORY ) ).get( SaveLoad.INVENTORY_EQUIPMENT );
        final List<Map> borrows = ( List<Map> ) ( ( Map ) data
                .get( SaveLoad.INVENTORY ) ).get( SaveLoad.INVENTORY_BORROW );
        final List<Map> persons = ( List<Map> ) data.get( SaveLoad.PERSON );

        for( final Map person : persons ) {

            final String type = ( String ) person.get( SaveLoad.TYPE );
            switch( type ) {
            case PERSON_TYPE_STUDENT:
                new Borrower( ( String ) person.get( SaveLoad.NAME ),
                        ( String ) person.get( SaveLoad.ID ),
                        SaveLoad.PERSON_TYPE_STUDENT,
                        ( String ) person.get( SaveLoad.PASSWORD ) );
                break;
            case PERSON_TYPE_TEACHER:
                new Borrower( ( String ) person.get( SaveLoad.NAME ),
                        ( String ) person.get( SaveLoad.ID ),
                        SaveLoad.PERSON_TYPE_TEACHER,
                        ( String ) person.get( SaveLoad.PASSWORD ) );
                break;
            case PERSON_TYPE_ADMINISTRATOR:
                new Administrator( ( String ) person.get( SaveLoad.NAME ),
                        ( String ) person.get( SaveLoad.ID ),
                        ( String ) person.get( SaveLoad.PASSWORD ) );
                break;
            default:
                throw new MiniProjectException( Error.CANNOT_CREATE_PERSON );

            }

        }

        for( final Map equipment : equipments ) {

            final String id = ( String ) equipment.get( SaveLoad.ID );
            final String type = ( String ) equipment.get( SaveLoad.TYPE );
            final List<Feature> listFeatures = new ArrayList<>( );
            final List<Map> features = ( List<Map> ) equipment
                    .get( SaveLoad.EQUIPMENT_FEATURE );

            for( final Map feature : features ) {
                final String name = ( String ) feature.get( SaveLoad.NAME );
                final String value = ( String ) feature.get( SaveLoad.VALUE );
                try {
                    final Class featureClass = Class
                            .forName( SaveLoad.PACKAGE_FEATURE + name );
                    final Feature c = ( Feature ) featureClass.getConstructor(
                            String.class, String.class ).newInstance( value,
                            type );
                    listFeatures.add( c );
                } catch( ClassNotFoundException | InstantiationException
                        | NoSuchMethodException | InvocationTargetException
                        | IllegalAccessException e ) {
                    MiniProjectController.LOGGER.severe( "message:"
                            + e.getMessage( ) + "\ntrace:"
                            + java.util.Arrays.toString( e.getStackTrace( ) ) );

                }

            }

            final Equipment equipmentObject = new Equipment( type, listFeatures );
            equipmentObject.setId( id );

        }

        for( final Map borrow : borrows ) {
            final String id = ( String ) borrow.get( SaveLoad.ID );
            final Integer startDay = ( ( Double ) ( ( Map ) borrow
                    .get( SaveLoad.BORROW_START ) ).get( SaveLoad.DAY ) )
                    .intValue( );
            final Integer startHour = ( ( Double ) ( ( Map ) borrow
                    .get( SaveLoad.BORROW_START ) ).get( SaveLoad.HOUR ) )
                    .intValue( );
            final Integer startMonth = ( ( Double ) ( ( Map ) borrow
                    .get( SaveLoad.BORROW_START ) ).get( SaveLoad.MONTH ) )
                    .intValue( );
            final Integer startMinute = ( ( Double ) ( ( Map ) borrow
                    .get( SaveLoad.BORROW_START ) ).get( SaveLoad.MINUTE ) )
                    .intValue( );
            final Integer startSecond = ( ( Double ) ( ( Map ) borrow
                    .get( SaveLoad.BORROW_START ) ).get( SaveLoad.SECOND ) )
                    .intValue( );
            final Integer startYear = ( ( Double ) ( ( Map ) borrow
                    .get( SaveLoad.BORROW_START ) ).get( SaveLoad.YEAR ) )
                    .intValue( );
            final Calendar debut = Calendar.getInstance( );
            debut.set( startYear, startMonth, startDay, startHour, startMinute,
                    startSecond );

            final Integer endDay = ( ( Double ) ( ( Map ) borrow
                    .get( SaveLoad.BORROW_END ) ).get( SaveLoad.DAY ) )
                    .intValue( );
            final Integer endHour = ( ( Double ) ( ( Map ) borrow
                    .get( SaveLoad.BORROW_END ) ).get( SaveLoad.HOUR ) )
                    .intValue( );
            final Integer endMonth = ( ( Double ) ( ( Map ) borrow
                    .get( SaveLoad.BORROW_END ) ).get( SaveLoad.MONTH ) )
                    .intValue( );
            final Integer endMinute = ( ( Double ) ( ( Map ) borrow
                    .get( SaveLoad.BORROW_END ) ).get( SaveLoad.MINUTE ) )
                    .intValue( );
            final Integer endSecond = ( ( Double ) ( ( Map ) borrow
                    .get( SaveLoad.BORROW_END ) ).get( SaveLoad.SECOND ) )
                    .intValue( );
            final Integer endYear = ( ( Double ) ( ( Map ) borrow
                    .get( SaveLoad.BORROW_END ) ).get( SaveLoad.YEAR ) )
                    .intValue( );
            final Calendar fin = Calendar.getInstance( );
            fin.set( endYear, endMonth, endDay, endHour, endMinute, endSecond );

            final List<String> listEquipment = new ArrayList<>( );
            final List<String> equipmentsBorrow = ( List<String> ) borrow
                    .get( SaveLoad.EQUIPMENT_ID );
            for( final String equipmentBorrow : equipmentsBorrow ) {
                listEquipment.add( equipmentBorrow );
            }

            final String borrowerId = ( String ) borrow
                    .get( SaveLoad.BORROWER_ID );
            final String state = ( String ) borrow.get( SaveLoad.STATE );
            final Borrower borrower = ( Borrower ) Inventory
                    .findPersonById( borrowerId );
            Borrow borrowObject = null;
            try {
                borrowObject = Inventory.findBorrowById( borrower.saveLoadBorrow(
                        listEquipment, debut, fin ) );
            } catch( final InvalidParameterException e ) {
                MiniProjectController.LOGGER.severe( "message:"
                        + e.getMessage( ) + "\ntrace:"
                        + java.util.Arrays.toString( e.getStackTrace( ) ) );
            }
            borrowObject.setId( id );

            if( borrow.containsKey( SaveLoad.ADMINISTRATOR_ID ) ) {
                final String administrator = ( String ) borrow
                        .get( SaveLoad.ADMINISTRATOR_ID );
                try {
                    borrowObject.setState( State.valueOf( state ),
                            administrator );
                } catch( final Exception e ) {
                    MiniProjectController.LOGGER.severe( "message:"
                            + e.getMessage( ) + "\ntrace:"
                            + java.util.Arrays.toString( e.getStackTrace( ) ) );

                }

            }

        }

    }

    /**
     * Save.
     */
    public static void save( ) {
        SaveLoad.save( SaveLoad.DATA );
    }

    /**
     * Save.
     *
     * @param file
     *            the file
     */
    public static void save( final String file ) {

        final Path path = Paths.get( file );
        try( BufferedWriter writer = Files.newBufferedWriter( path,
                StandardCharsets.UTF_8 ) ) {

            writer.write( SaveLoad.getState( ) );
            writer.newLine( );

        } catch( final IOException e ) {

            MiniProjectController.LOGGER.severe( "message:" + e.getMessage( )
                    + "\ntrace:"
                    + java.util.Arrays.toString( e.getStackTrace( ) ) );

        }

    }

    /**
     * Instantiates a new save load.
     */
    private SaveLoad( ) {
    }

}
