package Models;

import javafx.collections.ObservableList;

/**
 * Class contains data which describes a vendor and the methods which act on it
 */
public class Vendor {
    //private properties
    private int vendorId;
    private String vendorName;
    private String phoneNumber;
    private String shippingAddress;


    //getters and setters
    public int getVendorId() {
        return vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    //constructors
    public Vendor() {
        this.vendorId = 0;
        this.vendorName = "";
        this.phoneNumber = "";
        this.shippingAddress = "";
    }

    /**
     * Constructor
     *
     * @param vendorId        vendors ID (Provided by Purchasing Dept)
     * @param vendorName      name of supplier
     * @param phoneNumber     contact number of supplier
     * @param shippingAddress shipping Address for returns / contact
     */
    public Vendor(int vendorId, String vendorName, String phoneNumber, String shippingAddress) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
    }

    //all other methods and functions

    /**
     * Checks if Vendor with 'vendorId' exists in DB Vendors Table
     *
     * @param vendors  - ObservableList of all Vendors loaded from DB Vendors Table
     * @param vendorId - rigNo of Vendor to be checked for
     * @returns boolean success value
     */
    public static boolean containsVendor(ObservableList<Vendor> vendors, int vendorId) {
        for (Vendor vendor : vendors) {
            if (vendor.getVendorId() == vendorId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vendor to String method
     *
     * @returns Vendors Name
     */
    @Override
    public String toString() {
        String vendor = this.vendorName;

        return vendor;
    }

}

