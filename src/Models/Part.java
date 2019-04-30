package Models;

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
    private Order lastOrder;
    private String unitOfMeasure;
    private int annualUsage;

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

    public Order getLastOrder()
    {
        return lastOrder;
    }

    public String getUnitOfMeasure()
    {
        return unitOfMeasure;
    }

    public int getAnnualUsage()
    {
        return annualUsage;
    }

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

    public void setLastOrder(Order lastOrder)
    {
        this.lastOrder = lastOrder;
    }

    public void setUnitOfMeasure(String unitOfMeasure)
    {
        this.unitOfMeasure = unitOfMeasure;
    }

    public void setAnnualUsage(int annualUsage)
    {
        this.annualUsage = annualUsage;
    }

    //constructors
    public Part()
    {
        this.partNumber = "";
        this.vendorNumber = "";
        this.minRecVal = 0;
        this.maxRecVal = 0;
        this.partNoun = "";
        this.description = "";
        this.unitCost = 0.00;
        this.onHand = 0;
        this.onOrder = 0;
        this.flagged = 0;
        this.unitOfMeasure = "Ea";
        this.annualUsage = 0;
    }

    public Part(String partNumber, String vendorNumber, int minRecVal, int maxRecVal, String partNoun,
                String description, double unitCost, int onHand, int onOrder, int flagged,
                Order lastOrder, String unitOfMeasure, int annualUsage)
    {
        this.partNumber = partNumber;
        this.vendorNumber = vendorNumber;
        this.minRecVal = minRecVal;
        this.maxRecVal = maxRecVal;
        this.partNoun = partNoun;
        this.description = description;
        this.unitCost = unitCost;
        this.onHand = onHand;
        this.onOrder = onOrder;
        this.flagged = flagged;
        this.lastOrder = lastOrder;
        this.unitOfMeasure = unitOfMeasure;
        this.annualUsage = annualUsage;
    }

    //all other methods and functions

}
