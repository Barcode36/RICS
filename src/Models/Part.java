package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class contains the data that describes a Part in Stock and the data which acts on it
 */
public class Part {
    //private properties
    private String partNumber;
    private String vendorNumber;
    private int minRecVal;
    private int maxRecVal;
    private String partNoun;
    private String description;
    private double unitCost;
    private int onHand;
    private int onOrder;
    private int flagged;
    private String lastOrder;
    private String unitOfMeasure;
    private String location;
    private int vendorId;
    private int accountCode;


    //constructors
    public Part() {
        this.partNumber = "";
        this.accountCode = 0;
        this.vendorNumber = "";
        this.minRecVal = 0;
        this.maxRecVal = 0;
        this.partNoun = "";
        this.description = "";
        this.location = "";
        this.vendorId = 0;
        this.unitCost = 0.00;
        this.onHand = 0;
        this.onOrder = 0;
        this.flagged = 0;
        this.unitOfMeasure = "Ea";
    }

    public Part(String partNumber, String partNoun, String description, int minRecVal, int maxRecVal, double unitCost,
                String location) {
        this.partNumber = partNumber;
        this.partNoun = partNoun;
        this.description = description;
        this.minRecVal = minRecVal;
        this.maxRecVal = maxRecVal;
        this.unitCost = unitCost;
        this.location = location;
    }

    public Part(String partNumber, int accountCode, String vendorNumber, int minRecVal, int maxRecVal, String partNoun,
                String description, String location, int vendorId, double unitCost, int onHand, String unitOfMeasure) {
        this.partNumber = partNumber;
        this.accountCode = accountCode;
        this.vendorNumber = vendorNumber;
        this.minRecVal = minRecVal;
        this.maxRecVal = maxRecVal;
        this.partNoun = partNoun;
        this.description = description;
        this.location = location;
        this.vendorId = vendorId;
        this.unitCost = unitCost;
        this.onHand = onHand;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Part(int accountCode, String vendorNumber, int vendorId, String partNoun, String description,
                int minRecVal, int maxRecVal, double unitCost, String location) {
        this.partNumber = "0";
        this.accountCode = accountCode;
        this.vendorNumber = vendorNumber;
        this.vendorId = vendorId;
        this.partNoun = partNoun;
        this.description = description;
        this.minRecVal = minRecVal;
        this.maxRecVal = maxRecVal;
        this.unitCost = unitCost;
        this.location = location;
        this.onHand = 0;
        this.onOrder = 0;
        this.flagged = 0;
        this.unitOfMeasure = "Ea";


    }

    /**
     * Constructor
     *
     * @param partNumber
     * @param accountCode   Inventory Account the part belongs to
     * @param vendorNumber  the vendors Part Number
     * @param minRecVal     minimum recommended stock level
     * @param maxRecVal     maximum recommended stock level
     * @param partNoun      descriptive noun
     * @param description   a brief description of the part
     * @param location      the location the part is stored in
     * @param vendorId      the vendors ID in DB Vendors Table
     * @param unitCost      Cost of the part
     * @param onHand        level currently in stock
     * @param onOrder       quantity currently on order
     * @param flagged       quantity flagged for order
     * @param lastOrder     last order the part appeared on
     * @param unitOfMeasure unit of measure for the part (Each, Sets of 100 etc)
     */
    public Part(String partNumber, int accountCode, String vendorNumber, int minRecVal, int maxRecVal, String partNoun,
                String description, String location, int vendorId, double unitCost, int onHand, int onOrder, int flagged,
                String lastOrder, String unitOfMeasure) {
        this.partNumber = partNumber;
        this.accountCode = accountCode;
        this.vendorNumber = vendorNumber;
        this.minRecVal = minRecVal;
        this.maxRecVal = maxRecVal;
        this.partNoun = partNoun;
        this.description = description;
        this.location = location;
        this.vendorId = vendorId;
        this.unitCost = unitCost;
        this.onHand = onHand;
        this.onOrder = onOrder;
        this.flagged = flagged;
        this.lastOrder = lastOrder;
        this.unitOfMeasure = unitOfMeasure;
    }

    /**
     * Returns Part where Part.partNumber = 'partNumber'
     *
     * @param partNumber - partNumber of Part to be returned
     * @return part - Found Part, else returns null
     */
    public static Part returnPart(String partNumber) {
        try {
            DBManager dbm = new DBManager();
            ObservableList<Part> parts = dbm.loadParts();

            /**
             * Loop through 'parts', returns Part 'part' if part.partNumber = 'partNumber'
             */
            for (Part part : parts) {
                if (part.getPartNumber().equals(partNumber)) {
                    return part;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a list of all parts that are currently flagged for order
     *
     * @return
     */
    public static ObservableList<Part> flaggedParts() {
        DBManager dbm = new DBManager();
        ObservableList<Part> parts = dbm.loadParts();
        ObservableList<Part> flaggedParts = FXCollections.observableArrayList();
        for (Part part : parts) {
            if (part.getFlagged() > 0) {
                flaggedParts.add(part);
            }
        }

        return flaggedParts;
    }

    /**
     * Returns whether or not a new part already exists in inventory
     *
     * @param venNum the vendors part number
     * @param venId  the vendors Id
     * @return
     */
    public static boolean dupeFound(String venNum, int venId) {
        DBManager dbm = new DBManager();
        ObservableList<Part> parts = dbm.loadParts();
        for (Part part : parts) {
            if (part.getVendorNumber().equals(venNum) && part.getVendorId() == venId) {
                return true;
            }
        }
        return false;
    }

    //getters and setters
    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getVendorNumber() {
        return vendorNumber;
    }

    public int getMinRecVal() {
        return minRecVal;
    }

    public int getMaxRecVal() {
        return maxRecVal;
    }

    public String getPartNoun() {
        return partNoun;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public int getOnHand() {
        return onHand;
    }

    public int getOnOrder() {
        return onOrder;
    }

    public void setOnOrder(int onOrder) {
        this.onOrder = onOrder;
    }

    public int getFlagged() {
        return flagged;
    }

    public void setFlagged(int flagged) {
        this.flagged = flagged;
    }

    public String getLastOrder() {
        return lastOrder;
    }

    public void setLastOrder(String lastOrder) {
        this.lastOrder = lastOrder;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public String getLocation() {
        return location;
    }

    //all other methods and functions

    public void setLocation(String location) {
        this.location = location;
    }

    public int getVendorId() {
        return vendorId;
    }

    public int getAccountCode() {
        return accountCode;
    }

    /**
     * Part toString method
     *
     * @returns PartNumber
     */
    @Override
    public String toString() {
        String part = this.partNumber;

        return part;
    }


}
