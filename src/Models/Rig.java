package Models;

import javafx.collections.ObservableList;

/**
 * Class contains the data that describes a Rig and the methods which act on it
 */
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


    //Constructors
    public Rig()
    {
        this.rigNo = 0;
        this.rigName = "";
        this.wellName = "";
        this.clientName = "";
    }

    /**
     * Constructor
     * @param rigNo the rigs ID
     * @param rigName the name of the rig
     * @param wellName the rigs current location
     * @param clientName the rigs operator
     */
    public Rig(int rigNo, String rigName, String wellName, String clientName)
    {
        this.rigNo = rigNo;
        this.rigName = rigName;
        this.wellName = wellName;
        this.clientName = clientName;
    }

    //all other methods and functions

    /**
     * Checks if Rig with 'rigNo' exists in DB Rigs Table
     * @param rigs - ObservableList of all Rigs loaded from DB Rigs Table
     * @param rigNo - rigNo of Rig to be checked for
     * @returns boolean success value
     */
    public static boolean containsRig(ObservableList<Rig> rigs, int rigNo)
    {
        for (Rig rig : rigs)
        {
            if (rig.getRigNo() == rigNo)
            {
                return true;
            }
        }
        return false;
    }

}
