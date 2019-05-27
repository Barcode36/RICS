package Models;



public class Transaction
{
    private char transType;
    private String transDate;
    private String partNo;
    private int quantity;
    private String reference;
    private Double price;
    private Double totalVal;

    public char getTransType() {
        return transType;
    }

    public void setTransType(char transType) {
        this.transType = transType;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalVal() {return totalVal;}

    public void setTotalVal(Double totalVal) {this.totalVal = totalVal;}

    public Transaction(char transType, String transDate, String partNo, int quantity, String reference, Double price,
                       Double totalVal) {
        this.transType = transType;
        this.transDate = transDate;
        this.partNo = partNo;
        this.quantity = quantity;
        this.reference = reference;
        this.price = price;
        this.totalVal = totalVal;
    }
}
