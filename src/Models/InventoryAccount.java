package Models;

import java.util.HashMap;

public class InventoryAccount
{
    //private properties
    private int accountCode;
    private String accountName;
    private HashMap<Integer, Part> parts;


    //getters and setters
    public int getAccountCode()
    {
        return accountCode;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public HashMap<Integer, Part> getParts()
    {
        return parts;
    }

    public void setAccountCode(int accountCode)
    {
        this.accountCode = accountCode;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public void setParts(HashMap<Integer, Part> parts)
    {
        this.parts = parts;
    }

    //constructors
    public InventoryAccount()
    {
        this.accountCode = 0;
        this.accountName = "";
        this.parts = new HashMap();
    }

    public InventoryAccount(int accountCode, String accountName)
    {
        this.accountCode = accountCode;
        this.accountName = accountName;
        this.parts = new HashMap();
    }

    //all other methods and functions


}
