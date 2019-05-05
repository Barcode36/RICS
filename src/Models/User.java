package Models;

public class User
{
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int rig;
    private Boolean adminUser;

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
