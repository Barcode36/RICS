package Models;

public class Rig
{
    //private properties
    private int rigNo;
    private String rigName;
    private String wellName;
    private String clientName;

    //getters and setters
    public int getRigNo()
    {
        return rigNo;
    }

    public String getRigName()
    {
        return rigName;
    }

    public String getWellName()
    {
        return wellName;
    }

    public String getClientName()
    {
        return clientName;
    }

    public void setRigNo(int rigNo)
    {
        this.rigNo = rigNo;
    }

    public void setRigName(String rigName)
    {
        this.rigName = rigName;
    }

    public void setWellName(String wellName)
    {
        this.wellName = wellName;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    //Constructors
    public Rig()
    {
        this.rigNo = 0;
        this.rigName = "";
        this.wellName = "";
        this.clientName = "";
    }

    public Rig(int rigNo, String rigName, String wellName, String clientName)
    {
        this.rigNo = rigNo;
        this.rigName = rigName;
        this.wellName = wellName;
        this.clientName = clientName;
    }

    //all other methods and functions


}
