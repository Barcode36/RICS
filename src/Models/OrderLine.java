package Models;

public class OrderLine
{
    //private properties
    private int orderLineId;
    private int quantity;
    private Part part;
    private double lineTotal;
    private String requestedBy;


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

    //constructors
    public OrderLine(int orderLineId, int quantity, Part part, String requestedBy) {
        this.orderLineId = orderLineId;
        this.quantity = quantity;
        this.part = part;
        this.requestedBy = requestedBy;
    }

    public OrderLine(int orderLineId, int quantity, Part part, double lineTotal, String requestedBy) {
        this.orderLineId = orderLineId;
        this.quantity = quantity;
        this.part = part;
        this.lineTotal = lineTotal;
        this.requestedBy = requestedBy;
    }
}
