package Models;

import javafx.collections.ObservableList;

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
    private String orderNumber;


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

    public String getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber)
    {
        this.orderNumber = orderNumber;
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
                     int receivedQty, String manifestId)
    {
        this.orderLineId = orderLineId;
        this.quantity = quantity;
        this.part = part;
        this.lineTotal = lineTotal;
        this.requestedBy = requestedBy;
        this.status = status;
        this.receivedQty = receivedQty;
        this.manifestId = manifestId;
    }

    public OrderLine(int orderLineId, int quantity, Part part, double lineTotal, String requestedBy, char status,
                     int receivedQty, String manifestId, String orderNumber)
    {
        this.orderLineId = orderLineId;
        this.quantity = quantity;
        this.part = part;
        this.lineTotal = lineTotal;
        this.requestedBy = requestedBy;
        this.status = status;
        this.receivedQty = receivedQty;
        this.manifestId = manifestId;
        this.orderNumber = orderNumber;
    }

    /**
     * Returns OrderLine from DB OrderLines Table
     * @param orderLineId - the orderLineId of orderLine to be returned
     * @param orderNumber - orderNumber of the Order the OrderLines belong to
     * @return orderLine - orderLine to be returned, else return null
     */
    public static OrderLine returnOrderLine( int orderLineId, String orderNumber)
    {
        DBManager dbm = new DBManager();
        ObservableList<OrderLine> orderLines = dbm.loadOrderLines(orderNumber);

        //Loop through 'orderLines', return orderLine if orderLine.orderLineId = 'orderLineId'
        for (OrderLine orderLine : orderLines)
        {
            if (orderLine.getOrderLineId()== orderLineId)
            {
                return orderLine;
            }
        }
        return null;
    }


}
