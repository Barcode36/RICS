package Models;

public class ManifestLine
{
    //private properties
    private int manifestLineId;
    private OrderLine lineItem;
    private String referenceDoc;
    private double weight;

    //getters and setters
    public int getManifestLineId()
    {
        return manifestLineId;
    }

    public String getReferenceDoc()
    {
        return referenceDoc;
    }

    public double getWeight() {
        return weight;
    }

    public OrderLine getLineItem()
    {
        return lineItem;
    }

    public void setManifestLineId(int manifestLineId)
    {
        this.manifestLineId = manifestLineId;
    }

    public void setReferenceDoc(String referenceDoc)
    {
        this.referenceDoc = referenceDoc;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public void setLineItem(OrderLine lineItem)
    {
        this.lineItem = lineItem;
    }

    //conrstructors
    public ManifestLine()
    {
        this.manifestLineId = 1;
        this.lineItem = new OrderLine();
        this.referenceDoc = "";
        this.weight = 0.00;
    }

    public ManifestLine(int manifestLineId, OrderLine lineItem, String referenceDoc, double weight) {
        this.manifestLineId = manifestLineId;
        this.lineItem = lineItem;
        this.referenceDoc = referenceDoc;
        this.weight = weight;
    }
}
