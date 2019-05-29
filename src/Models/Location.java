package Models;

import javafx.collections.ObservableList;

public class Location
{
    //private properties
    private String locationId;

    /**
     * Returns whether or not Location with 'locationId' exists in DB Locations Table
     * @param locs - ObserableList of all Locations from DB Locations Table
     * @param locationId - locationId of Location to be checked for
     * @return boolean success value
     */
    public static boolean containsLocation(ObservableList<Location> locs, String locationId)
    {
        /**
         * Loop through 'locs' return true if loc.locationId = 'locationId'
         */
        for (Location loc : locs)
        {
            if (loc.getLocationId().equals(locationId))
            {
                return true;
            }
        }
        return false;
    }

    //getter and setter
    public String getLocationId()
    {
        return locationId;
    }

    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }

    //contructors
    public Location()
    {
        this.locationId = "";
    }

    public Location(String locationId)
    {
        this.locationId = locationId;
    }

    //all other methods and functions
    @Override
    public String toString()
    {
        String location = this.locationId;

        return location;
    }
}
