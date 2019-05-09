package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class DBManager
{
    //driver string
    private final String driver = "org.sqlite.JDBC";
    //DB Connection String
    private final String connectionString = "jdbc:sqlite:RICS.sqlite";



    //***User Functions***
    //Register User
    public boolean registerUser(User u)
    {
        try
        {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt= conn.createStatement();

            int adminUser = 0;
            if(u.getAdminUser().equals(true))
            {
                adminUser = 1;
            }
            else
            {
                 adminUser =0;
            }

            stmt.executeUpdate("INSERT INTO Users( username, password, firstName, lastName, rig, adminUser)" +
                    "VALUES ('"+ u.getUsername() + "','" + u.getPassword() +"','" + u.getFirstName() + "','" +
                    u.getLastName() +"','" +u.getRig() + "','" + adminUser +  "')");
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public HashMap<String, User> loadUsers()
    {
        HashMap<String, User> users = new HashMap();

        try
        {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //DB select statement
            ResultSet userList = stmt.executeQuery("SELECT * FROM Users");

            //iterate through result set
            while(userList.next())
            {
                String username = userList.getString("username");
                String password = userList.getString("password");
                String firstName = userList.getString("firstName");
                String lastName = userList.getString("lastName");
                int rig = userList.getInt("rig");
                Boolean adminUser = userList.getBoolean("adminUser");


                //Build user with data from DB
                User user = new User(username, password, firstName, lastName, rig, adminUser);

                //Adding users to HashMap using userId as key
                users.put(username, user);
            }

            //close connection to DB
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return users;
        }
    }

    //for populating tbl_users in usersMenu.fxml
    //javafx table views can only be populated with observable lists
    public ObservableList<User> loadUsersOBS()
    {
        ObservableList<User> users = FXCollections.observableArrayList();


        try
        {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //DB select statement
            ResultSet userList = stmt.executeQuery("SELECT * FROM Users");

            //iterate through result set
            while(userList.next())
            {
                users.add(new User(
                        userList.getString("username"),
                        userList.getString("password"),
                        userList.getString("firstName"),
                        userList.getString("lastName"),
                        userList.getInt("rig"),
                        userList.getBoolean("adminUser")
                ));
            }

            //close connection to DB
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return users;
        }
    }

    //edit user record
    public void updateUser(User u)
    {
        try
        {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement stmt = conn.createStatement();

            int adminUser = 0;
            if(u.getAdminUser().equals(true))
            {
                adminUser = 1;
            }
            else
            {
                adminUser =0;
            }

            //Update User record in DB
            stmt.executeUpdate("UPDATE Users SET username = '"+ u.getUsername() +"', password = '"+ u.getPassword() +
                    "', firstName = '"+ u.getFirstName() + "', lastName = '"+ u.getLastName() + "', rig = '"+ u.getRig() +
                    "', adminUser = '"+ adminUser + "'WHERE Username = '" + u.getUsername() + "'");

            //close connection to DB
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteUser(User u)
    {
        try
        {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement stmt = conn.createStatement();

            //Remove user record from DB
            stmt.executeUpdate("DELETE FROM Users WHERE username ='" + u.getUsername() + "'");

            //close connection to DB
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public User login(String username, String password)
    {
        HashMap<String, User> users = loadUsers();

        if(users.containsKey(username))
        {
            User foundUser = users.get(username);

            if(foundUser.getPassword().equals(password))
            {
                return foundUser;
            }
            else
                {
                    return null;
                }
        }
        return null;
    }

}
