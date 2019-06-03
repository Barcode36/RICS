package Models;

import javafx.collections.ObservableList;

/**
 * Contains Data describing Stock Locations and the Methods which act on it
 */
public class Location {
    private String locationId;

    /**
     * Empty Constructor
     */
    public Location() {
        this.locationId = "";
    }


    /**
     * Constructor
     *
     * @param locationId locationId provided by user
     */
    public Location(String locationId) {
        this.locationId = locationId;
    }

    /**
     * Returns whether or not Location with 'locationId' exists in DB Locations Table
     *
     * @param locs       - ObserableList of all Locations from DB Locations Table
     * @param locationId - locationId of Location to be checked for
     * @return boolean success value
     */
    public static boolean containsLocation(ObservableList<Location> locs, String locationId) {
        /**
         * Loop through 'locs' return true if loc.locationId = 'locationId'
         */
        for (Location loc : locs) {
            if (loc.getLocationId().equals(locationId)) {
                return true;
            }
        }
        return false;
    }

    public String getLocationId() {
        return locationId;
    }

    /**
     * Inventory Account toString method
     *
     * @return String value of inventoryAccount
     */
    @Override
    public String toString() {
        String location = this.locationId;

        return location;
    }
}
