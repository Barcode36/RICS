package Models;


import javafx.collections.ObservableList;

public class InventoryAccount
{
    //private properties
    private int accountCode;
    private String accountName;

    /**
     * Returns whether or not an account exists in the DB InventoryAccounts Table
     * @param accounts - ObservableList of all InventoryAccounts from DB InventoryAccounts Table
     * @param accountCode - accountCode of InventoryAccount to be checked for
     * @returns boolean success value
     */
    public static boolean containsAccount(ObservableList<InventoryAccount> accounts, int accountCode)
    {
        /**
         * Loops through 'accounts' looking for InventoryAccount where account.accountCode = 'accountCode'
         * returns true if found, else false.
         */
        for (InventoryAccount account : accounts)
        {
            if (account.getAccountCode() == accountCode)
            {
                return true;
            }
        }
        return false;
    }


    public int getAccountCode()
    {
        return accountCode;
    }
    public String getAccountName()
    {
        return accountName;
    }
    public void setAccountCode(int accountCode)
    {
        this.accountCode = accountCode;
    }
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }


    //constructors
    public InventoryAccount()
    {
        this.accountCode = 0;
        this.accountName = "";
    }

    /**
     * Constructor
     * @param accountCode
     * @param accountName
     */
    public InventoryAccount(int accountCode, String accountName)
    {
        this.accountCode = accountCode;
        this.accountName = accountName;
    }

    //all other methods and functions

    /**
     * Returns String value of accountCode
     * @return
     */
    @Override
    public String toString()
    {
        String accountCode = Integer.toString(this.accountCode);

        return accountCode;
    }

}
