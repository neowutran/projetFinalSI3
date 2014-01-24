/*
 * @author Martini Didier - Fabien Pinel - Maxime Touroute
 */

package views.etat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Inventory;
import model.MiniProjectException;
import model.SaveLoad;
import views.Command;
import views.State;
import views.View;
import config.Config;
import config.Error;

/**
 * The Class Main.
 */
public class Main extends State {

    /**
     * Login.
     * 
     * @param id
     *            the id
     * @param password
     *            the password
     */
    @SuppressWarnings("unused")
    private void login(final String id, final String password) {

        if (model.User.getInstance().login(id, password)) {
            System.out.println("Welcome "
                    + Inventory.findPersonById(id).getName());
            try {
                if (Inventory.isBorrower(id)) {
                    System.out.println("Interface borrower");
                    View.setState(new Borrower());
                } else {
                    System.out.println("Interface administrator");
                    View.setState(new Administrator());
                }
            } catch (final MiniProjectException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Wrong username or password");
        }
    }

    /**
     * Nouvelle méthode pour l'inscription d'un utilisateur.
     * 
     * @param name
     *            nom du nouveau user
     * @param id
     *            id du nouveau user
     * @param type
     *            type du nouveau user
     * @param password
     *            password du nouveau user
     * @throws MiniProjectException
     *             the mini project exception
     */
    @SuppressWarnings("unused")
    private void register(final String name, final String id,
            final String type, final String password)
            throws MiniProjectException {

        if (((Map) Config.getConfiguration().get(SaveLoad.PERSON_TYPE_BORROWER))
                .containsKey(type)) {
            new model.person.Borrower(name, id, type, password);
        } else {
            throw new MiniProjectException(Error.CANNOT_CREATE_PERSON);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see views.IView#setCommands()
     */
    @Override
    public List<Command> setCommands() {

        final List<Command> commands = new ArrayList<>();
        final List<String> args1 = new LinkedList<>();
        args1.add("id");
        args1.add("password");
        final Command command1 = new Command("login", args1, this, "login",
                "Se connecte a l'interface.\n\t Exemple d'utilisation:\n\t login 123 pass ");
        final List<String> args2 = new LinkedList<>();
        args2.add("name");
        args2.add("id");
        args2.add("type");
        args2.add("password");
        final Command command2 = new Command("register", args2, this,
                "register",
                "Inscription d'un nouvel utilisateur (type: teacher ou student)");
        commands.add(command1);
        commands.add(command2);
        commands.addAll(super.setCommands());
        return commands;
    }
}
