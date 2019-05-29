package Models;

import javafx.collections.ObservableList;

public class User
{
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int rig;
    private Boolean adminUser;

    /**
     * Checks if User with 'username' exists in list of Users DB Users Table
     * @param users - List of all users
     * @param username - username to search for in 'users'
     * @return  Boolean success value
     */
    public static boolean containsUser(ObservableList<User> users, String username)
    {
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

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public int getRig()
    {
        return rig;
    }

    public Boolean getAdminUser()
    {
        return adminUser;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setRig(int rig)
    {
        this.rig = rig;
    }

    public void setAdminUser(Boolean adminUser)
    {
        this.adminUser = adminUser;
    }

    //constructors
    public User()
    {
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.rig = 0;
        this.adminUser = false;
    }

    public User(String username, String password, String firstName, String lastName, int rig, Boolean adminUser)
    {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rig = rig;
        this.adminUser = adminUser;
    }

    //all other methods and functions

}
