package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class Order
{
    //private properties
    private String orderNumber;
    private char orderType;
    private String shippingMethod;
    private Date date;
    private String header;
    private char orderStatus;
    private double orderTotal;
    private Boolean orderApproved;
    private ObservableList<OrderLine> orderLines;

    //getters and setters
    public String getOrderNumber()
    {
        return orderNumber;
    }

    public char getOrderType()
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

    public ObservableList<OrderLine> getOrderLines()
    {
        return orderLines;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setOrderType(char orderType) {
        this.orderType = orderType;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setOrderStatus(char orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void setOrderApproved(Boolean orderApproved) {
        this.orderApproved = orderApproved;
    }

    public void setOrderLines(ObservableList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    //constructors
    public Order()
    {
        this.orderNumber = "";
        this.orderType = 'P';
        this.shippingMethod = "";
        this.date = new Date();
        this.header = "";
        this.orderStatus = 'U';
        this.orderTotal = 0.00;
        this.orderApproved = false;
        this.orderLines = FXCollections.observableArrayList();
    }

    public Order(String orderNumber, char orderType, String shippingMethod)
    {
        this.orderNumber = orderNumber;
        this.orderType = orderType;
        this.shippingMethod = shippingMethod;
        this.date = new Date();
        this.header = "";
        this.orderStatus = 'U';
        this.orderTotal = 0.00;
        this.orderApproved = false;
        this.orderLines = FXCollections.observableArrayList();
    }

    public Order(String orderNumber, char orderType, String shippingMethod, Date date,
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
        this.orderLines = FXCollections.observableArrayList();
    }

    public void calculateOrderTotal()
    {
        try
        {
            DBManager dbm = new DBManager();
            double orderTotal = 0;

            ObservableList<OrderLine> orderLines = dbm.loadOrderLines(orderNumber);
            for (OrderLine orderLine : orderLines) {
                orderTotal = orderTotal + orderLine.getLineTotal();
            }

            dbm.updateOrderTotal(orderNumber, orderTotal);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }



}
