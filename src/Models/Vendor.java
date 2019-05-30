package Models;

import javafx.collections.ObservableList;

public class Vendor
{
    //private properties
    private int vendorId;
    private String vendorName;
    private String phoneNumber;
    private String shippingAddress;

    //getters and setters
    public int getVendorId()
    {
        return vendorId;
    }

    public String getVendorName()
    {
        return vendorName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getShippingAddress()
    {
        return shippingAddress;
    }

    public void setVendorId(int vendorId)
    {
        this.vendorId = vendorId;
    }

    public void setVendorName(String vendorName)
    {
        this.vendorName = vendorName;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setShippingAddress(String shippingAddress)
    {
        this.shippingAddress = shippingAddress;
    }

    //constructors
    public Vendor()
    {
        this.vendorId = 0;
        this.vendorName = "";
        this.phoneNumber = "";
        this.shippingAddress = "";
    }

    public Vendor(int vendorId, String vendorName, String phoneNumber, String shippingAddress)
    {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
    }

    //all other methods and functions
    @Override
    public String toString()
    {
        String vendor = this.vendorName;

        return vendor;
    }

    /**
     * Checks if Vendor with 'vendorId' exists in DB Vendors Table
     * @param vendors - ObservableList of all Vendors loaded from DB Vendors Table
     * @param vendorId - rigNo of Vendor to be checked for
     * @returns boolean success value
     */
    public static boolean containsVendor(ObservableList<Vendor> vendors, int vendorId)
    {
        for (Vendor vendor : vendors)
        {
            if (vendor.getVendorId() == vendorId)
            {
                return true;
            }
        }
        return false;
    }

}

