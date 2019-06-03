package Models;


/**
 * Class contains the data the describes a stock transaction and the methods which act on it
 */
public class Transaction {
    private char transType;
    private String transDate;
    private String partNo;
    private int quantity;
    private String reference;
    private Double price;
    private Double totalVal;

    /**
     * Constructor
     *
     * @param transType the type of transaction (order, receive, issue)
     * @param transDate date and time of the transaction
     * @param partNo    the part
     * @param quantity  the quantity of the part
     * @param reference who requested the part, who it was issued to, or the manifest ID it was received on
     * @param price     the value of the part
     * @param totalVal  total value of the transaction
     */
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

    public char getTransType() {
        return transType;
    }

    public String getTransDate() {
        return transDate;
    }

    public String getPartNo() {
        return partNo;
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

    public Double getPrice() {
        return price;
    }

    public Double getTotalVal() {
        return totalVal;
    }
}
