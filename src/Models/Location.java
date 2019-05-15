package Models;

public class Location
{
    //private properties
    private String locationId;

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
