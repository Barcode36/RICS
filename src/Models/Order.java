package Models;

import java.util.Date;
import java.util.HashMap;

public class Order
{
    //private properties
    private String orderNumber;
    private String orderType;
    private String shippingMethod;
    private Date date;
    private String header;
    private char orderStatus;
    private double orderTotal;
    private Boolean orderApproved;
    private HashMap<Integer, OrderLine> orderLines;

    //getters and setters
    public String getOrderNumber()
    {
        return orderNumber;
    }

    public String getOrderType()
    {
        return orderType;
    }

    public String getShippingMethod()
    {
        return shippingMethod;
    }

    public Date getDate()
    {
        return date;
    }

    public String getHeader()
    {
        return header;
    }

    public char getOrderStatus()
    {
        return orderStatus;
    }

    public double getOrderTotal()
    {
        return orderTotal;
    }

    public Boolean getOrderApproved()
    {
        return orderApproved;
    }

    public HashMap<Integer, OrderLine> getOrderLines()
    {
        return orderLines;
    }

    //constructors
    public Order()
    {
        this.orderNumber = "";
        this.orderType = "";
        this.shippingMethod = "";
        this.date = new Date();
        this.header = "";
        this.orderStatus = 'O';
        this.orderTotal = 0.00;
        this.orderApproved = false;
        this.orderLines = new HashMap();
    }

    public Order(String orderNumber, String orderType, String shippingMethod, Date date,
                 String header, char orderStatus, double orderTotal, Boolean orderApproved)
    {
        this.orderNumber = orderNumber;
        this.orderType = orderType;
        this.shippingMethod = shippingMethod;
        this.date = date;
        this.header = header;
        this.orderStatus = orderStatus;
        this.orderTotal = orderTotal;
        this.orderApproved = orderApproved;
        this.orderLines = new HashMap();
    }
}
