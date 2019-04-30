package Models;

import java.util.Date;
import java.util.HashMap;

public class Manifest {

    //private properties
    private String manifestId;
    private Date date;
    private String shippingMethod;
    private String header;
    private String immedDest;
    private String finalDest;
    private Boolean manifestApproved;
    private HashMap<Integer, ManifestLine> manifestLines;

    //getters and setters
    public String getManifestId() {
        return manifestId;
    }

    public Date getDate() {
        return date;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public String getHeader() {
        return header;
    }

    public String getImmedDest() {
        return immedDest;
    }

    public String getFinalDest() {
        return finalDest;
    }

    public Boolean getManifestApproved()
    {
        return manifestApproved;
    }

    public HashMap<Integer, ManifestLine> getManifestLines() {
        return manifestLines;
    }

    public void setManifestId(String manifestId) {
        this.manifestId = manifestId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setImmedDest(String immedDest) {
        this.immedDest = immedDest;
    }

    public void setFinalDest(String finalDest) {
        this.finalDest = finalDest;
    }

    public void setManifestApproved(Boolean manifestApproved)
    {
        this.manifestApproved = manifestApproved;
    }

    public void setManifestLines(HashMap<Integer, ManifestLine> manifestLines) {
        this.manifestLines = manifestLines;
    }

    //constructors
    public Manifest()
    {
        this.manifestId = "";
        this.date = new Date();
        this.shippingMethod = "";
        this.header = "";
        this.immedDest = "";
        this.finalDest = "";
        this.manifestApproved = false;
        this.manifestLines = new HashMap();
    }

    public Manifest(String manifestId, Date date, String shippingMethod,
                    String header, String immedDest, String finalDest, Boolean manifestApproved)
    {
        this.manifestId = manifestId;
        this.date = date;
        this.shippingMethod = shippingMethod;
        this.header = header;
        this.immedDest = immedDest;
        this.finalDest = finalDest;
        this.manifestApproved = manifestApproved;
        this.manifestLines = new HashMap();
    }

    //all other methods and functions
}