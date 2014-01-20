
package views;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lib.ArrayUtils;
import model.MiniProjectException;
import config.Error;
import controllers.MiniProjectController;

/**
 * The Class Command.
 */
public class Command {

    /** The name. */
    private final String name;

    /** The args. */
    private List<String> args = new LinkedList<>( );

    /** The description. */
    private final String description;

    /** The state. */
    private final State  state;

    /** The method. */
    private final String method;

    /**
     * Instantiates a new command.
     * 
     * @param name
     *            the name
     * @param args
     *            the args
     * @param state
     *            the state
     * @param method
     *            the method
     * @param description
     *            the description
     */
    public Command( final String name, final List<String> args,
            final State state, final String method, final String description ) {

        this.name = name;
        this.args = args;
        this.state = state;
        this.method = method;
        this.description = description;

    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName( ) {

        return this.name;
    }

    /**
     * Invoke.
     * 
     * @param arg
     *            the arg
     * @throws MiniProjectException
     *             the mini project exception
     */
    public void invoke( final String[ ] arg ) throws MiniProjectException {

        if( ( arg.length - 1 ) != this.args.size( ) ) {

            this.state.printHelp( );
            return;
        }

        final Method[ ] methods = ArrayUtils.concatenate( this.state.getClass( )
                .getDeclaredMethods( ), this.state.getClass( ).getMethods( ) );

        for( final Method method : methods ) {

            if( !method.getName( ).equals( this.method ) ) {
                continue;
            }

            final Class[ ] parameters = method.getParameterTypes( );
            if( parameters.length != this.args.size( ) ) {
                continue;
            }

            Boolean goodMethod = true;

            for( final Class parameter : parameters ) {

                if( !parameter.equals( String.class ) ) {
                    goodMethod = false;
                    break;
                }
            }

            if( goodMethod ) {

                method.setAccessible( true );
                try {
                    method.invoke( this.state, Arrays.copyOfRange(
                            ( Object[ ] ) arg, 1, arg.length ) );
                } catch( IllegalAccessException | InvocationTargetException e ) {
                    MiniProjectController.LOGGER.severe( "message:"
                            + e.getMessage( ) + "\ntrace:"
                            + java.util.Arrays.toString( e.getStackTrace( ) ) );
                }
                return;

            }

        }
        throw new MiniProjectException( Error.METHOD_NOT_FOUND );

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString( ) {

        String show = "- " + this.name + " : " + this.name;
        for( final String arg : this.args ) {
            show += " " + arg;
        }
        show += "\n";
        show += "\t" + this.description + "\n";

        return show;
    }
}
