package Models;

public class OrderLine
{
    //private properties
    private int orderLineId;
    private int quantity;
    private char status;
    private int receivedQty;
    private Part part;
    private double lineTotal;
    private String requestedBy;
    private String manifestId;

    //getters and setters

    public int getOrderLineId()
    {
        return orderLineId;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public Part getPart()
    {
        return part;
    }

    public double getLineTotal()
    {
        return lineTotal;
    }

    public String getRequestedBy()
    {
        return requestedBy;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }


    public void setOrderLineId(int orderLineId)
    {
        this.orderLineId = orderLineId;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public void setPart(Part part)
    {
        this.part = part;
    }

    public void setLineTotal(double lineTotal)
    {
        this.lineTotal = lineTotal;
    }

    public void setRequestedBy(String requestedBy)
    {
        this.requestedBy = requestedBy;
    }

    public int getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(int receivedQty) {
        this.receivedQty = receivedQty;
    }

    public String getManifestId() {
        return manifestId;
    }

    public void setManifestId(String manifestId) {
        this.manifestId = manifestId;
    }

    //constructors
    public OrderLine()
    {
        this.orderLineId = 1;
        this.quantity = 0;
        this.part = new Part();
        this.requestedBy = "";
    }
    public OrderLine(int orderLineId, int quantity, Part part, String requestedBy) {
        this.orderLineId = orderLineId;
        this.quantity = quantity;
        this.part = part;
        this.requestedBy = requestedBy;
        this.status = 'U';
        this.manifestId ="";
        this.lineTotal = part.getUnitCost() * quantity;
        this.receivedQty = 0;
    }

    public OrderLine(int orderLineId, int quantity, Part part, double lineTotal, String requestedBy, char status,
                     int receivedQty, String manifestId) {
        this.orderLineId = orderLineId;
        this.quantity = quantity;
        this.part = part;
        this.lineTotal = lineTotal;
        this.requestedBy = requestedBy;
        this.status = status;
        this.receivedQty = receivedQty;
        this.manifestId = manifestId;
    }
}
