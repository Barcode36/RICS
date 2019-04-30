package Models;

import sun.security.util.Password;

public class User
{
    //private properties
    private int userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Rig rig;
    private Boolean adminUser;

    //getters and setters
    public int getUserId()
    {
        return userId;
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

    public Rig getRig()
    {
        return rig;
    }

    public Boolean getAdminUser()
    {
        return adminUser;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
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

    public void setRig(Rig rig)
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
        this.userId = 0;
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.rig = new Rig();
        this.adminUser = false;
    }

    public User(int userId, String username, String password, String firstName,
                String lastName, Rig rig, Boolean adminUser)
    {
      this.userId = userId;
      this.username = username;
      this.password = password;
      this.firstName = firstName;
      this.lastName = lastName;
      this.rig = rig;
      this.adminUser = adminUser;
    }

    //all other methods and functions

}
