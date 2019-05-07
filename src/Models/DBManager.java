package Models;

import javax.crypto.interfaces.PBEKey;
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

            stmt.executeUpdate("INSERT INTO Users( username, password, firstName, lastName, adminUser, rig)" +
                    "VALUES ('"+ u.getUsername() + "','" + u.getPassword() +"','" + u.getFirstName() + "','" +
                    u.getLastName() +"','" + u.getAdminUser() + "','" + u.getRig()+ "')");
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



    //edit user record
    public void updateUser(User u)
    {
        try
        {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement stmt = conn.createStatement();

            //Update User record in DB
            stmt.executeUpdate("UPDATE Users SET username = '"+ u.getUsername() +"', password = '"+ u.getPassword() +
                    "', firstName = '"+ u.getFirstName() + "', lastName = '"+ u.getLastName() + "', rig = '"+ u.getRig() +
                    "', adminUser = '"+ u.getAdminUser() +"'");

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
