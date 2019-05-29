package Models;

import javafx.collections.ObservableList;

/**
 *
 */
public class Part
{

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

    /**
     * Returns Part where Part.partNumber = 'partNumber'
     * @param partNumber - partNumber of Part to be returned
     * @return part - Found Part, else returns null
     */
    public static Part returnPart(String partNumber)
    {
        try
        {
            DBManager dbm = new DBManager();
            ObservableList<Part> parts = dbm.loadParts();

            /**
             * Loop through 'parts', returns Part 'part' if part.partNumber = 'partNumber'
             */
            for (Part part : parts)
            {
                if (part.getPartNumber().equals(partNumber))
                {
                    return part;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //getters and setters
    public String getPartNumber()
    {
        return partNumber;
    }

    public String getVendorNumber()
    {
        return vendorNumber;
    }

    public int getMinRecVal()
    {
        return minRecVal;
    }

    public int getMaxRecVal()
    {
        return maxRecVal;
    }

    public String getPartNoun()
    {
        return partNoun;
    }

    public String getDescription()
    {
        return description;
    }

    public double getUnitCost()
    {
        return unitCost;
    }

    public int getOnHand()
    {
        return onHand;
    }

    public int getOnOrder()
    {
        return onOrder;
    }

    public int getFlagged()
    {
        return flagged;
    }

    public String getLastOrder()
    {
        return lastOrder;
    }

    public String getUnitOfMeasure()
    {
        return unitOfMeasure;
    }

    public String getLocation(){return location;}

    public int getVendorId(){return vendorId;}

    public int getAccountCode(){return accountCode;}

    public void setPartNumber(String partNumber)
    {
        this.partNumber = partNumber;
    }

    public void setVendorNumber(String vendorNumber)
    {
        this.vendorNumber = vendorNumber;
    }

    public void setMinRecVal(int minRecVal)
    {
        this.minRecVal = minRecVal;
    }

    public void setMaxRecVal(int maxRecVal)
    {
        this.maxRecVal = maxRecVal;
    }

    public void setPartNoun(String partNoun)
    {
        this.partNoun = partNoun;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setUnitCost(float unitCost)
    {
        this.unitCost = unitCost;
    }

    public void setOnHand(int onHand)
    {
        this.onHand = onHand;
    }

    public void setOnOrder(int onOrder)
    {
        this.onOrder = onOrder;
    }

    public void setFlagged(int flagged)
    {
        this.flagged = flagged;
    }

    public void setLastOrder(String lastOrder)
    {
        this.lastOrder = lastOrder;
    }

    public void setUnitOfMeasure(String unitOfMeasure)
    {
        this.unitOfMeasure = unitOfMeasure;
    }

    public void setLocation(String location) { this.location = location;}

    public void setVendorId(int vendorId) { this.vendorId = vendorId;}

    public void setAccountCode(int accountCode) { this.accountCode = accountCode;}

    //constructors
    public Part()
    {
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
                String location)
    {
        this.partNumber = partNumber;
        this.partNoun = partNoun;
        this.description = description;
        this.minRecVal = minRecVal;
        this.maxRecVal = maxRecVal;
        this.unitCost = unitCost;
        this.location = location;
    }

    public Part(String partNumber, int accountCode, String vendorNumber, int minRecVal, int maxRecVal, String partNoun,
                String description, String location, int vendorId,  double unitCost, int onHand, String unitOfMeasure)
    {
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

    public Part( int accountCode, String vendorNumber, int vendorId, String partNoun, String description,
                 int minRecVal, int maxRecVal, double unitCost, String location)
    {
        this.partNumber ="0";
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

    public Part(String partNumber, int accountCode, String vendorNumber, int minRecVal, int maxRecVal, String partNoun,
                String description, String location, int vendorId, double unitCost, int onHand, int onOrder, int flagged,
                String lastOrder, String unitOfMeasure)
    {
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

    //all other methods and functions
    @Override
    public String toString()
    {
        String part = this.partNumber;

        return part;
    }




}
