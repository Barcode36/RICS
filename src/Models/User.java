package Models;

import javafx.collections.ObservableList;

/**
 * Class contains data that describes a user and the methods which act on it
 */
public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int rig;
    private Boolean adminUser;

    /**
     * Empty Constructor
     */
    public User() {
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.rig = 0;
        this.adminUser = false;
    }



    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getRig() {
        return rig;
    }

    public Boolean getAdminUser() {
        return adminUser;
    }

    //constructors
    /**
     * Constructor
     *
     * @param username  username of user
     * @param password  users passsword
     * @param firstName first name of user
     * @param lastName  users surname
     * @param rig       rig the user belongs to
     * @param adminUser whether they are are an admin or not
     */
    public User(String username, String password, String firstName, String lastName, int rig, Boolean adminUser) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rig = rig;
        this.adminUser = adminUser;
    }

    //all other methods and functions

    /**
     * Checks if User with 'username' exists in list of Users DB Users Table
     *
     * @param users    - List of all users
     * @param username - username to search for in 'users'
     * @return Boolean success value
     */
    public static boolean containsUser(ObservableList<User> users, String username) {
        /**
         * Loops through 'users' looking for user with username = 'username'
         */
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the user
     *
     * @param username user to be returned
     * @return found User
     */
    public static User returnUser(String username) {
        DBManager dbm = new DBManager();
        ObservableList<User> users = dbm.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
