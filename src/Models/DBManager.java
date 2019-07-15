package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static java.lang.Class.forName;

/**
 * DBManager Class handles all DB CRUD Actions & User Login
 */
public class DBManager {

    /**
     * driver string
     */
    private final String driver = "org.sqlite.JDBC";

    /**
     * DB connection string
     */
    private final String connectionString = "jdbc:sqlite:RICS.sqlite";


    /**
     * Inserts new User 'u' to DB Users Table
     *
     * @param u - new user to be store
     * @return boolean true is user registration is successful, false if unsuccessful
     */
    public boolean insertUser(User u) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * storing value as an int to correct issue reading 'true' value from DB.
             */
            int adminUser;

            if (u.getAdminUser().equals(true)) {
                adminUser = 1;
            } else {
                adminUser = 0;
            }

            /*
             * Insert User u to DB Users Table
             */
            stmt.executeUpdate("INSERT INTO Users( username, password, firstName, lastName, rig, adminUser)" +
                    "VALUES ('" + u.getUsername() + "','" + u.getPassword() + "','" + u.getFirstName() + "','" +
                    u.getLastName() + "','" + u.getRig() + "','" + adminUser + "')");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * loads all users from DB Users Table
     *
     * @return users - an ObservableList of all users loaded from DB
     */
    public ObservableList<User> loadUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();


        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Select all users from DB Users Table
             */
            ResultSet userList = stmt.executeQuery("SELECT * FROM Users");

            /*
             * Builds user for each entry in 'userList' and adds to 'users'
             */
            while (userList.next()) {
                users.add(new User(
                        userList.getString("username"),
                        userList.getString("password"),
                        userList.getString("firstName"),
                        userList.getString("lastName"),
                        userList.getInt("rig"),
                        userList.getBoolean("adminUser")
                ));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;

    }

    /**
     * Updates User 'u' in DB Users Table
     *
     * @param u - User to be updated
     */
    public void updateUser(User u) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * storing value as int to correct issue reading 'true' value from DB
             */
            int adminUser;
            if (u.getAdminUser().equals(true)) {
                adminUser = 1;
            } else {
                adminUser = 0;
            }

            /*
             * Update Users Table, update Part where 'partNumber' = p.partNumber
             */
            stmt.executeUpdate("UPDATE Users SET username = '" + u.getUsername() + "', password = '" + u.getPassword() +
                    "', firstName = '" + u.getFirstName() + "', lastName = '" + u.getLastName() + "', rig = '" + u.getRig() +
                    "', adminUser = '" + adminUser + "'WHERE username = '" + u.getUsername() + "'");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes User 'u' from DB Users Table
     *
     * @param u - User to be deleted
     */
    public void deleteUser(User u) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Delete User 'u' from DB Users Table where 'username' = u.username
             */
            stmt.executeUpdate("DELETE FROM Users WHERE username ='" + u.getUsername() + "'");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
         * Handles User Login. Validates Users login info matches a record in DB Users Table
     *
     * @param username - users supplied username to be checked
     * @param password - users supplied password   to be checked
     * @return User - if 'username' & 'password' match record in DB Users Table. else returns null.
     */
    public User login(String username, String password) {
        ObservableList<User> usersOBS = loadUsers();

        /*
         * Loops through each 'user' in 'usersOBS' and checks if user supplied information matches a record
         */
        for (User user : usersOBS) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }


    /**
     * Insert new Part 'p' to DB Parts Table
     *
     * @param p - Part to be inserted to DB
     */
    public void createPart(Part p) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Insert Part 'p' to DB Parts Table
             */
            stmt.executeUpdate("INSERT INTO Parts(partNumber, accountCode, vendorPartNumber, partNoun, description, " +
                    "vendor, location, unitCost, onHand, minLvl, maxLvl, onOrder, lastOrder, unitOfMeasure, flagged)" +
                    "VALUES ('" + p.getPartNumber() + "','" + p.getAccountCode() + "','" + p.getVendorNumber() +
                    "','" + p.getPartNoun() + "','" + p.getDescription() + "','" + p.getVendorId() + "','" + p.getLocation() +
                    "','" + p.getUnitCost() + "','" + p.getOnHand() + "','" + p.getMinRecVal() + "','" + p.getMaxRecVal() +
                    "','" + p.getOnOrder() + "','" + p.getLastOrder() + "','" + p.getUnitOfMeasure() + "','" + p.getFlagged() + "')");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load all Parts from DB Parts Table
     *
     * @return parts - ObservableList of all parts in DB, else returns null.
     */
    public ObservableList<Part> loadParts() {
        ObservableList<Part> parts = FXCollections.observableArrayList();


        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Select all Parts from DB Parts Table Order partNumber ascending
             */
            ResultSet partList = stmt.executeQuery("SELECT * FROM Parts ORDER BY partNumber");

            /*
             * Build Part for each entry in 'partList' and add to 'parts'
             */
            while (partList.next()) {
                parts.add(new Part(
                        partList.getString("partNumber"),
                        partList.getInt("accountCode"),
                        partList.getString("vendorPartNumber"),
                        partList.getInt("minLvl"),
                        partList.getInt("maxLvl"),
                        partList.getString("partNoun"),
                        partList.getString("description"),
                        partList.getString("location"),
                        partList.getInt("vendor"),
                        partList.getDouble("unitCost"),
                        partList.getInt("onHand"),
                        partList.getInt("onOrder"),
                        partList.getInt("flagged"),
                        partList.getString("lastOrder"),
                        partList.getString("unitOfMeasure")
                ));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parts;
    }

    /**
     * Update Part 'p' in DB Parts Table
     *
     * @param p - Part to be updated in DB
     */
    public void updatePart(Part p) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();


            /*
             * Update Parts Table, update Part where 'partNumber' = p.partNumber
             */
            stmt.executeUpdate("UPDATE Parts SET partNoun = '" + p.getPartNoun() + "', description = '" + p.getDescription() +
                    "', location = '" + p.getLocation() + "', unitCost = '" + p.getUnitCost() + "', minLvl = '" + p.getMinRecVal() +
                    "', maxLvl = '" + p.getMaxRecVal() + "', onOrder = '" + p.getOnOrder() + "', lastOrder = '" + p.getLastOrder() +
                    "', flagged = '" + p.getFlagged() + "'WHERE partNumber =" + " '" + p.getPartNumber() + "'");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete Part 'p' from DB Parts Table
     *
     * @param p - Part to be deleted from DB
     */
    public void deletePart(Part p) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();

            /*
             *Inserts part 'p' into PartsArchive Table
             */
            stmt.executeUpdate("INSERT INTO PartsArchive(partNumber, accountCode, vendorPartNumber, partNoun, " +
                    "description, " +
                    "vendor, location, unitCost, onHand, minLvl, maxLvl, onOrder, lastOrder, unitOfMeasure, flagged)" +
                    "VALUES ('" + p.getPartNumber() + "','" + p.getAccountCode() + "','" + p.getVendorNumber() +
                    "','" + p.getPartNoun() + "','" + p.getDescription() + "','" + p.getVendorId() + "','" + p.getLocation() +
                    "','" + p.getUnitCost() + "','" + p.getOnHand() + "','" + p.getMinRecVal() + "','" + p.getMaxRecVal() +
                    "','" + p.getOnOrder() + "','" + p.getLastOrder() + "','" + p.getUnitOfMeasure() + "','" + p.getFlagged() + "')");
            /*
             * Delete Part 'p' from DB Parts Table where 'partNumber' = p.partNumber
             */
            stmt2.executeUpdate("DELETE FROM Parts WHERE partNumber ='" + p.getPartNumber() + "'");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Generates Unique partNumber for new Parts
     * Creates a partNumber using the parts Inventory Account as a prefix, suffixes are generated sequentially
     *
     * @param part - new Part which needs partNumber
     * @return partNumber - String value, PK which uniquely identifies new part in DB
     */
    public String generateUniquePartNo(Part part) {
        int PXparts = 0;
        String partNumber;

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Select Count of Parts in DB Parts Table where 'accountCode' = part.accountCode AS 'parts'
             */
            ResultSet partsList = stmt.executeQuery("SELECT COUNT (*) AS parts FROM Parts WHERE accountCode = '" + part.getAccountCode() + "'");
            PXparts = partsList.getInt("parts");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * Generates the suffix portion of partNumber using sequential value
         * StringBuilder used to pad suffix to correspond to business rule
         * partNumber format '000-00000'
         */
        do {
            PXparts++;
            String newPart = String.valueOf(PXparts);
            StringBuilder sb = new StringBuilder();

            while (sb.length() + newPart.length() < 5) {
                sb.append('0');
            }

            sb.append(PXparts);

            /*
             * Generates prefix portion of partNumber using first 3 digits of accountCode
             */
            String suffix = sb.toString();
            String prefix = String.valueOf(part.getAccountCode()).substring(0, 3);

            partNumber = prefix + "-" + suffix;

        }while(Part.returnPart(partNumber) != null);
        {
            return partNumber;
        }

    }

    /**
     * Performs basic search the DB Parts Table for entries with properties which are LIKE 'criteria'
     *
     * @param criteria - the users search criteria
     * @return parts - ObservableList of Parts which has properties LIKE 'criteria'
     */
    public ObservableList<Part> basicSearchParts(String criteria) {
        ObservableList<Part> parts = FXCollections.observableArrayList();


        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Select all from DB Parts Table where partNumber OR partNoun OR vendorPartNumber is LIKE 'criteria'
             * order by partNumber asc
             */
            ResultSet partList = stmt.executeQuery("SELECT * FROM Parts WHERE partNumber || partNoun || vendorPartNumber || " +
                    "location || description LIKE" + "'%" + criteria + "%' ORDER BY partNumber");


            /*
             * Build Part for each entry in 'partList' and add to 'parts'
             */
            while (partList.next()) {
                parts.add(new Part(
                        partList.getString("partNumber"),
                        partList.getInt("accountCode"),
                        partList.getString("vendorPartNumber"),
                        partList.getInt("minLvl"),
                        partList.getInt("maxLvl"),
                        partList.getString("partNoun"),
                        partList.getString("description"),
                        partList.getString("location"),
                        partList.getInt("vendor"),
                        partList.getDouble("unitCost"),
                        partList.getInt("onHand"),
                        partList.getInt("onOrder"),
                        partList.getInt("flagged"),
                        partList.getString("lastOrder"),
                        partList.getString("unitOfMeasure")
                ));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parts;
    }

    /**
     * Updates onHand for Part with 'partNo' in DB Parts Table on issue or receipt of Part
     *
     * @param newStockLevel - the new stock level to be stored
     * @param partNo        - the partNumber of the Part which requires updating
     */
    public void updateStockLevel(int newStockLevel, String partNo) {
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Update DB Parts Table, set onHand = 'newStockLevel' for Part where partNumber = 'partNo'
             */
            stmt.executeUpdate("UPDATE Parts SET onHand = '" + newStockLevel + "' WHERE partNumber = '" + partNo +
                    "'");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert new InventoryAccount 'account' to DB InventoryAccounts Table
     *
     * @param account - InventoryAccount to be inserted to DB
     */
    public void createInventoryAccount(InventoryAccount account) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Insert InventoryAccount 'account' into DB InventoryAccounts Table
             */
            stmt.executeUpdate("INSERT INTO InventoryAccounts VALUES ('" + account.getAccountCode() + "','" +
                    account.getAccountName() + "')");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all Inventory Accounts from DB InventoryAccounts Table
     *
     * @return accounts - ObservableList of all InventoryAccounts loaded from DB
     */
    public ObservableList<InventoryAccount> loadInventoryAccounts() {
        ObservableList<InventoryAccount> accounts = FXCollections.observableArrayList();

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Select All from DB InventoryAccounts Table
             */
            ResultSet accountList = stmt.executeQuery("SELECT * FROM InventoryAccounts");

            /*
             * Build InventoryAccount from each entry in 'accountList' and add to 'accounts'
             */
            while (accountList.next()) {
                accounts.add(new InventoryAccount(
                        accountList.getInt("accountCode"),
                        accountList.getString("accountName")
                ));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return accounts;
        }
    }

    /**
     * Update InventoryAccount 'account' in InventoryAccounts Table
     *
     * @param account
     */
    public void updateInventoryAccount(InventoryAccount account) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Update DB InventoryAccounts Table where accountCode = account.accountCode
             */
            stmt.executeUpdate("UPDATE InventoryAccounts SET accountCode = '" + account.getAccountCode() + "', " +
                    "accountName" + " = '" + account.getAccountName() + "'WHERE accountCode =" + " '" + account.getAccountCode() + "'");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts new Vendor 'vendor' to DB Vendors Table
     *
     * @param vendor - new Vendor to be added to DB
     */
    public void createVendor(Vendor vendor) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Insert new Vendor 'vendor' into DB Rigs Table
             */
            stmt.executeUpdate("INSERT INTO Vendors VALUES ('" + vendor.getVendorId() + "','" +
                    vendor.getVendorName() + "','" + vendor.getPhoneNumber() + "','" + vendor.getShippingAddress() + "')");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all Vendors from DB Vendors Table
     *
     * @return vendors = ObservableList of all Vendors loaded from DB
     */
    public ObservableList<Vendor> loadVendors() {
        ObservableList<Vendor> vendors = FXCollections.observableArrayList();

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Select all from DB Vendors table order by vendorName asc
             */
            ResultSet vendorList = stmt.executeQuery("SELECT * FROM Vendors ORDER BY vendorName");

            /*
             * Build Vendor from each entry in 'vendorList' and add to 'vendors'
             */
            while (vendorList.next()) {
                vendors.add(new Vendor(
                        vendorList.getInt("vendorId"),
                        vendorList.getString("vendorName"),
                        vendorList.getString("contactNumber"),
                        vendorList.getString("shippingAddress")
                ));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vendors;
    }

    /**
     * Update Vendor 'vendor' in DB Vendors Table
     *
     * @param vendor - Vendor to be updated in DB
     */
    public void updateVendor(Vendor vendor) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Update DB Vendors Table where vendorId = 'vendorId'
             */
            stmt.executeUpdate("UPDATE Vendors SET vendorName = '" + vendor.getVendorName() +
                    "', contactNumber = '" + vendor.getPhoneNumber() + "', shipingAddress = '" + vendor.getShippingAddress() +
                    "'WHERE " + "rigNo =" + " '" + vendor.getVendorId() + "'");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all Locations from DB Locations Table
     *
     * @return locations - ObservableList of all Locations loaded from DB
     */
    public ObservableList<Location> loadLocations() {
        ObservableList<Location> locations = FXCollections.observableArrayList();

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Select all from DB Locations Table order by locationId asc
             */
            ResultSet locationList = stmt.executeQuery("SELECT * FROM Locations ORDER BY locationId");

            /*
             * Build Location from each entry in 'locationList' and add to 'locations'
             */
            while (locationList.next()) {
                locations.add(new Location(
                        locationList.getString("locationId")
                ));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locations;
    }


    /**
     * Inserts new Location 'loc' to DB Locations Table
     *
     * @param loc
     */
    public void addLocation(Location loc) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Insert Location 'loc' to DB Locations Table
             */
            stmt.executeUpdate("INSERT INTO Locations VALUES ('" + loc.getLocationId() + "')");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Inserts new Rig 'rig' to DB Rigs Table
     *
     * @param rig - new Rig to be added to DB
     */
    public void createRig(Rig rig) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Insert new Rig 'rig' into DB Rigs Table
             */
            stmt.executeUpdate("INSERT INTO Rigs VALUES ('" + rig.getRigNo() + "','" +
                    rig.getRigName() + "','" + rig.getClientName() + "','" + rig.getWellName() + "')");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all Rigs from DB Rigs Table
     *
     * @return rigs - ObservableList of all Rigs loaded from DB
     */
    public ObservableList<Rig> loadRigs() {
        ObservableList<Rig> rigs = FXCollections.observableArrayList();

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Select all from DB Rigs Table order by rigNo asc
             */
            ResultSet rigList = stmt.executeQuery("SELECT * FROM Rigs ORDER BY rigNo");

            /*
             * Build Rig for each entry in 'rigList' and add to 'rigs'
             */
            while (rigList.next()) {
                rigs.add(new Rig(rigList.getInt("rigNo"), rigList.getString("rigName"),
                        rigList.getString("clientName"), rigList.getString("wellName")));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rigs;
    }


    /**
     * Update Rig 'rig' in DB Rigs Table
     *
     * @param rig - Rig to be updated in DB
     */
    public void updateRig(Rig rig) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Update DB Rigs Table where rigNo = 'rigNo'
             */
            stmt.executeUpdate("UPDATE Rigs SET  rigName = '" + rig.getRigName() +
                    "', clientName = '" + rig.getClientName() + "', wellName = '" + rig.getWellName() + "'WHERE rigNo =" +
                    " '" + rig.getRigNo() + "'");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Inserts new Transaction to DB PartHistory Table when Ordering, Issuing or Receiving a Part
     *
     * @param p         - Part the transaction belongs to
     * @param type      - whether it was an Issue, Receipt, or Ordering of goods
     * @param quantity  - the quantity of the part
     * @param reference - Personnel who requested, got Issued to OR Manifest ID if receipt of goods
     */
    public void saveTransaction(Part p, char type, int quantity, String reference) {
        LocalDateTime now = LocalDateTime.now();
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            double totalVal = quantity * p.getUnitCost();

            /*
             * INSERT Transaction to DB PartHistory Table
             */
            stmt.executeUpdate("INSERT INTO partHistory( transType, transDate, partNo, quantity, " +
                    "reference, price, totalVal)" + "VALUES ('" + type + "','" + now + "','" + p.getPartNumber() +
                    "','" + quantity + "','" + reference + "', '" + p.getUnitCost() + "', '" + totalVal + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all transactions for Part 'p' from DB PartHistory Table
     *
     * @param p - Part which we want the list of transactions for;
     * @return transactions - ObservableList of all Transactions from DB PartHistory Table where partNo = p.partNumber
     */
    public ObservableList<Transaction> loadTransactions(Part p) {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * SELECT all from DB PartHistory Table where partNo = p.partNumber
             */
            ResultSet transactionList =
                    stmt.executeQuery("SELECT * FROM partHistory WHERE partNo = '" + p.getPartNumber() + "'");


            /*
             * Build Transaction for each entry in 'transactionList' and add to 'transactions'
             */
            while (transactionList.next()) {
                transactions.add(new Transaction(
                        transactionList.getString("transType").charAt(0),
                        transactionList.getString("transDate"),
                        transactionList.getString("partNo"),
                        transactionList.getInt("quantity"),
                        transactionList.getString("reference"),
                        transactionList.getDouble("price"),
                        transactionList.getDouble("totalVal")
                ));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }


    /**
     * Generates unique orderNumber for new Orders
     *
     * @returns orderNumber - String value, PK which uniquely identifies new Order in DB
     */
    public String generateUniqueOrderNo() {
        int PXorders = 0;

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * SELECT number of Orders in DB Orders Table AS 'orders'
             */
            ResultSet ordersList =
                    stmt.executeQuery("SELECT COUNT (*) AS orders FROM Orders");

            //return count of existing parts in that Inventory account
            PXorders = ordersList.getInt("orders");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * Generate orderNumber suffix by getting sequential value 'PXorders' padding with 0's to match business rule
         * requirement
         */
        PXorders++;
        String newOrder = String.valueOf(PXorders);
        StringBuilder sb = new StringBuilder();

        while (sb.length() + newOrder.length() < 5) {
            sb.append('0');
        }

        sb.append(PXorders);

        String suffix = sb.toString();
        String prefix = "164";

        String orderNumber = prefix + "-" + suffix;

        return orderNumber;

    }


    /**
     * Insert new Order 'o' to DB Orders Table
     *
     * @param o - new Order to be added to DB
     */
    public void addOrder(Order o) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * all new orders are created unapproved
             */
            int orderApproved = 0;

            /*
             * Insert new Order 'o' to DB orders Table
             */
            stmt.executeUpdate("INSERT INTO Orders(orderNumber, orderType, shippingMethod, orderDate, header," +
                    "orderStatus, orderTotal, orderApproved) VALUES ('" + o.getOrderNumber() + "','" + o.getOrderType() + "','" + o.getShippingMethod() +
                    "','" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(o.getDate()) + "','" + o.getHeader() +
                    "','" + o.getOrderStatus() + "','" + o.getOrderTotal() + "'," +
                    "'" + orderApproved + "')");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all Orders from DB Orders Table
     *
     * @return orders - ObservableList of all Orders loaded from DB
     */
    public ObservableList<Order> loadOrders() {
        ObservableList<Order> orders = FXCollections.observableArrayList();

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * SELECT all Orders from DB OrdersTable order by orderNumber asc
             */
            ResultSet orderList = stmt.executeQuery("SELECT * FROM Orders ORDER BY orderNumber");

            /*
             * Build Order for each entry in 'orderList' and add to 'orders'
             */
            while (orderList.next()) {
                Date orderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderList.getString("orderDate"));
                orders.add(new Order(orderList.getString("orderNumber"), orderList.getString("orderType").charAt(0),
                        orderList.getString("shippingMethod"), orderDate, orderList.getString(
                        "header"), orderList.getString("orderStatus").charAt(0), orderList.getDouble("orderTotal"),
                        orderList.getBoolean("orderApproved")));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Update Order 'o' in DB Orders Table
     *
     * @param o - Order to be updated in DB
     */
    public void updateOrder(Order o) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();


            /*
             * UPDATE DB Orders Table, Update Order where orderNumber = o.orderNumber
             */
            stmt.executeUpdate("UPDATE Orders SET orderType = '" + o.getOrderType() + "', shippingMethod = '" + o.getShippingMethod() +
                    "', header = '" + o.getHeader() + "', orderStatus = '" + o.getOrderStatus() + "'WHERE orderNumber" +
                    " =" + " '" + o.getOrderNumber() + "'");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set Order 'order' APPROVED in DB Orders Table & Update associated OrderLines in DB OrderLines Table
     *
     * @param order - order to be approved in DB
     */
    public void approveOrder(Order order) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * storing value as an int to correct issue reading 'true' value from DB.
             */
            int orderApproved = 1;


            /*
             * Update DB Orders Table, set orderApproved = 'orderApproved'  and set orderStatus = 'O'
             * where orderNumber = order.orderNumber
             */
            stmt.executeUpdate("UPDATE Orders SET orderApproved = '" + orderApproved + "', orderStatus = '" + 'O' +
                    "'WHERE orderNumber =" + " '" + order.getOrderNumber() + "'");

            /*
             * Update DB OrderLines Table, set status = 'O' where orderNumber = order.orderNumber
             */
            stmt.executeUpdate("UPDATE OrderLines SET status = '" + 'O' + "'WHERE orderNumber =" +
                    " '" + order.getOrderNumber() + "'");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set Order 'order' orderStatus to 'X'in DB OrdersTable & Update assoc. OrderLines in DB OrderLines Table
     *
     * @param order - order to be cancelled in DB
     */
    public void cancelOrder(Order order) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();


            /*
             * Update DB Orders Table, set orderStatus = 'X' (Cancelled) where orderNumber = order.orderNumber
             */
            stmt.executeUpdate("UPDATE Orders SET orderStatus = '" + 'X' + "'WHERE orderNumber =" + " '" + order.getOrderNumber() + "'");

            /*
             * Update DB OrderLines Table, set status = 'X' , set lineTotal = '0' where orderNumber = order.orderNumber
             */
            stmt.executeUpdate("UPDATE OrderLines SET status =  '" + 'X' + "', lineTotal = '" + 0 + "'WHERE orderNumber =" + " '" + order.getOrderNumber() + "'");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * update Order orderTotal in DB Orders Table
     *
     * @param orderNumber - orderNumber of Order to be updated
     * @param orderTotal  - the new value of orderTotal;
     */
    public void updateOrderTotal(String orderNumber, double orderTotal) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Update DB Orders Table, set Order orderTotal = 'orderTotal' where orderNumber = 'orderNumber'
             */
            stmt.executeUpdate("UPDATE Orders SET orderTotal = '" + orderTotal + "'WHERE orderNumber =" +
                    " '" + orderNumber + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of Orders from DB Orders Table which match 'criteria'
     *
     * @param criteria - user specified search criteria
     * @return orders - ObservableList of Orders that match 'criteria'
     */
    public ObservableList<Order> searchOrders(String criteria) {
        ObservableList<Order> orders = FXCollections.observableArrayList();

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Select all from DB Orders Table where orderNumber OR header OR orderDate OR location matches 'criteria' order by orderNumber;
             */
            ResultSet orderList =
                    stmt.executeQuery("SELECT * FROM Orders WHERE orderNumber || header || orderDate LIKE" + "'%" + criteria + "%' ORDER BY orderNumber");


            /*
             * Build Order from each entry in 'orderList' and add to 'orders'
             */
            while (orderList.next()) {
                Date orderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderList.getString("orderDate"));
                orders.add(new Order(orderList.getString("orderNumber"), orderList.getString("orderType").charAt(0),
                        orderList.getString("shippingMethod"), orderDate, orderList.getString(
                        "header"), orderList.getString("orderStatus").charAt(0), orderList.getDouble("orderTotal"),
                        orderList.getBoolean("orderApproved")));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Returns a list of Open OrderLines fromDB OrderLines Table which match 'selection'
     *
     * @param selection - the inventory accounts we need orders for
     * @return ObservableList of Open OrderLines that match 'selection'
     */
    public ObservableList<OrderLine> openOrders(String selection) {
        ObservableList<OrderLine> orderLines = FXCollections.observableArrayList();


        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            char open = 'O';
            ResultSet openLinesSet = stmt.executeQuery("SELECT * FROM OrderLines WHERE status =" +
                    " '" + open + "' AND part LIKE" + " '" + selection + "%'");

            while (openLinesSet.next()) {
                String partNumber = openLinesSet.getString("part");
                Part part = Part.returnPart(partNumber);
                orderLines.add(new OrderLine(openLinesSet.getInt("orderLineId"), openLinesSet.getInt(
                        "quantity"), part, openLinesSet.getDouble("lineTotal"),
                        openLinesSet.getString("requestedBy"), openLinesSet.getString("status").charAt(0),
                        openLinesSet.getInt("receivedQty"), openLinesSet.getString("manifestId"),
                        openLinesSet.getString("orderNumber")));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderLines;
    }

    /**
     * Generates orderLineId unqiue to that Order
     *
     * @param orderNumber - orderNumber of Order 'orderLines' belongs to
     * @return orderLineId - orderLineId generated
     */
    public int generateUniqueOrderLineId(String orderNumber) {
        int orderLineId = 1;
        ObservableList<OrderLine> orderLines = loadOrderLines(orderNumber);


        /*
         * Loop through 'orderLines' if, 'orderLineId' already exists, increment 'orderLineId'
         */
        for (OrderLine orderLine : orderLines) {
            if (orderLine.getOrderLineId() == orderLineId) {
                orderLineId++;
            }
        }
        return orderLineId;
    }

    /**
     * Loads all OrderLines from DB OrderLines Table where orderNumber = 'orderNumber'
     *
     * @param orderNumber - Order to retrieve orderLines for
     * @return orderLines - ObservableList of orderLines loaded from DB
     */
    public ObservableList<OrderLine> loadOrderLines(String orderNumber) {
        ObservableList<OrderLine> orderLines = FXCollections.observableArrayList();

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            /*
             * Select all from DB OrderLines Table where orderNumber = 'orderNumber'
             */
            ResultSet olList = stmt.executeQuery("SELECT * FROM OrderLines WHERE orderNumber = '" + orderNumber + "'");

            /*
             * Build OrderLine from each entry in 'olList' and add to 'orderLines'
             */
            while (olList.next()) {
                String partNumber = olList.getString("part");
                Part part = Part.returnPart(partNumber);
                orderLines.add(new OrderLine(olList.getInt("orderLineId"), olList.getInt("quantity"), part,
                        olList.getDouble(
                                "lineTotal"), olList.getString("requestedBy"), olList.getString("status").charAt(0),
                        olList.getInt("receivedQty"), olList.getString("manifestId")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return orderLines;
        }
    }

    /**
     * Insert new OrderLine 'ol' to DB OrderLines Table
     *
     * @param ol          - new OrderLine to be added to DB OrderLines Table
     * @param orderNumber - orderNumber of Order that orderLines belong to
     */
    public void addOrderLine(OrderLine ol, String orderNumber) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();


            /*
             * Insert new OrderLine 'ol' to DB OrderLines Table
             */
            stmt.executeUpdate("INSERT INTO OrderLines(orderLineId, quantity, part, lineTotal, requestedBy," +
                    "status, receivedQty, manifestId, orderNumber) VALUES " + "('" + ol.getOrderLineId() + "','" + ol.getQuantity() +
                    "','" + ol.getPart() + "','" + ol.getLineTotal() + "','" + ol.getRequestedBy() + "','" + ol.getStatus() +
                    "','" + ol.getReceivedQty() + "','" + ol.getManifestId() + "'," + "'" + orderNumber + "')");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update OrderLine 'orderLine' in DB OrderLines Table
     *
     * @param orderLine   - OrderLine to be updated
     * @param orderNumber - orderNumber of Order that 'orderLine' belongs to
     */
    public void updateOrderLine(OrderLine orderLine, String orderNumber) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();


            //Update DB OrderLines Table, update OrderLine where orderLineId = orderLine.orderLineId && orderNumber ='orderNumber'
            stmt.executeUpdate("UPDATE OrderLines SET status = '" + orderLine.getStatus() + "', manifestId = '" +
                    orderLine.getManifestId() + "', receivedQty = '" + orderLine.getReceivedQty() + "'WHERE orderLineId =" +
                    " '" + orderLine.getOrderLineId() + "' AND orderNumber =" + " '" + orderNumber + "'");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove OrderLine 'orderLine' from DB OrderLines Table
     *
     * @param orderLine   - orderLine to be deleted from DB
     * @param orderNumber - orderNumber of Order 'orderLine' belongs to
     * @return boolean success value
     */
    public boolean removeOrderLine(OrderLine orderLine, String orderNumber) {
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement smt = conn.createStatement();

            //Delete OrderLine from DB where orderLineId = orderLine.orderLineId && orderNumber = 'orderNumber'
            smt.executeUpdate("DELETE FROM OrderLines WHERE orderLineId = '" + orderLine.getOrderLineId() +
                    "' AND orderNumber =" + " '" + orderNumber + "'");
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}



