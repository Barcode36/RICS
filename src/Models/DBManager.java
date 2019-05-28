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

public class DBManager {
    //driver string
    private final String driver = "org.sqlite.JDBC";
    //DB Connection String
    private final String connectionString = "jdbc:sqlite:RICS.sqlite";


    //***USER FUNCTIONS***
    public boolean registerUser(User u) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            int adminUser;
            if (u.getAdminUser().equals(true)) {
                adminUser = 1;
            } else {
                adminUser = 0;
            }

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

    public ObservableList<User> loadUsersOBS() {
        ObservableList<User> users = FXCollections.observableArrayList();


        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //DB select statement
            ResultSet userList = stmt.executeQuery("SELECT * FROM Users");

            //iterate through result set
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

            //close connection to DB
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }

    //edit user record
    public void updateUser(User u) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement stmt = conn.createStatement();

            int adminUser;
            if (u.getAdminUser().equals(true)) {
                adminUser = 1;
            } else {
                adminUser = 0;
            }

            //Update User record in DB
            stmt.executeUpdate("UPDATE Users SET username = '" + u.getUsername() + "', password = '" + u.getPassword() +
                    "', firstName = '" + u.getFirstName() + "', lastName = '" + u.getLastName() + "', rig = '" + u.getRig() +
                    "', adminUser = '" + adminUser + "'WHERE Username = '" + u.getUsername() + "'");

            //close connection to DB
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //delete user record
    public void deleteUser(User u) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement stmt = conn.createStatement();

            //Remove user record from DB
            stmt.executeUpdate("DELETE FROM Users WHERE username ='" + u.getUsername() + "'");

            //close connection to DB
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //user login
    public User login(String username, String password) {
        ObservableList<User> usersOBS = loadUsersOBS();

        for (User user : usersOBS) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static boolean containsUser(ObservableList<User> users, String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static User returnUser( String username) {
        DBManager dbm = new DBManager();
        ObservableList<User> users = dbm.loadUsersOBS();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }


    //***PART FUNCTIONS***

    //add part to DB
    public void addPart(Part p) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

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

    //load parts from DB
    public ObservableList<Part> loadParts() {
        ObservableList<Part> parts = FXCollections.observableArrayList();


        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //DB select statement
            ResultSet partList = stmt.executeQuery("SELECT * FROM Parts ORDER BY partNumber");

            //iterate through result set
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

            //close connection to DB
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return parts;
        }
    }

    public void deletePart(Part p)
    {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement stmt = conn.createStatement();

            //Remove user record from DB
            stmt.executeUpdate("DELETE FROM Parts WHERE partNumber ='" + p.getPartNumber() + "'");

            //close connection to DB
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updatePart(Part p) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement stmt = conn.createStatement();


            //Update Part in DB
            stmt.executeUpdate("UPDATE Parts SET partNoun = '" + p.getPartNoun() + "', description = '" + p.getDescription() +
                    "', location = '" + p.getLocation() + "', unitCost = '" + p.getUnitCost() + "', minLvl = '" + p.getMinRecVal() +
                    "', maxLvl = '" + p.getMaxRecVal() + "', onOrder = '" + p.getOnOrder() + "', lastOrder = '" + p.getLastOrder() +
                    "', flagged = '" + p.getFlagged() + "'WHERE partNumber =" + " '" + p.getPartNumber() + "'");

            //close connection to DB
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //generates unique partNumber based on AccountCode
    public String generateUniquePartNo(Part part) {
        int PXparts = -1;

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            ResultSet partsList =
                    stmt.executeQuery("SELECT COUNT (*) AS parts FROM Parts WHERE accountCode = '" + part.getAccountCode() +
                            "'");

            //return count of existing parts in that Inventory account
            PXparts = partsList.getInt("parts");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PXparts++;
        String newPart = String.valueOf(PXparts);
        StringBuilder sb = new StringBuilder();

        while (sb.length() + newPart.length() < 5) {
            sb.append('0');
        }

        sb.append(PXparts);

        String suffix = sb.toString();
        String prefix = String.valueOf(part.getAccountCode()).substring(0, 3);

        String partNumber = prefix + "-" + suffix;

        return partNumber;

    }



    public static Part returnPart(String partNumber) {
        DBManager dbm = new DBManager();
        ObservableList<Part> parts = dbm.loadParts();
        for (Part part : parts) {
            if (part.getPartNumber().equals(partNumber)) {
                return part;
            }
        }
        return null;
    }


    public ObservableList<Part> searchParts(String criteria) {
        ObservableList<Part> parts = FXCollections.observableArrayList();


        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //DB select statement
            ResultSet partList =
                    stmt.executeQuery("SELECT * FROM Parts WHERE partNumber || partNoun || vendorPartNumber || " +
                            "location || description LIKE" + "'%" + criteria + "%' ORDER BY partNumber");



            //iterate through result set
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

            //close connection to DB
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return parts;
        }
    }
    //***ACCOUNT FUNCTIONS***
    public ObservableList<InventoryAccount> loadInventoryAccounts() {
        ObservableList<InventoryAccount> accounts = FXCollections.observableArrayList();

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            ResultSet accountList = stmt.executeQuery("SELECT * FROM InventoryAccounts");

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

    public void addInventoryAccount(InventoryAccount account) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO InventoryAccounts VALUES ('" + account.getAccountCode() + "','" +
                    account.getAccountName()  + "')");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateInventoryAccount(InventoryAccount account) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement stmt = conn.createStatement();

            //Update Rig record in DB
            stmt.executeUpdate("UPDATE InventoryAccounts SET accountCode = '" + account.getAccountCode() + "', " +
                    "accountName" +
                    " = '" + account.getAccountName() +
                     "'WHERE accountCode =" +
                    " '" + account.getAccountCode() + "'");

            //close connection to DB
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean containsAccount(ObservableList<InventoryAccount> accounts, int accountCode) {
        for (InventoryAccount account : accounts) {
            if (account.getAccountCode() == accountCode) {
                return true;
            }
        }
        return false;
    }

    //***VENDOR FUNCTIONS****
    public ObservableList<Vendor> loadVendors() {
        ObservableList<Vendor> vendors = FXCollections.observableArrayList();

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            ResultSet vendorList = stmt.executeQuery("SELECT * FROM Vendors ORDER BY vendorName");

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
        } finally {
            return vendors;
        }
    }

    //***LOCATION FUNCTIONS***
    public ObservableList<Location> loadLocations() {
        ObservableList<Location> locations = FXCollections.observableArrayList();

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            ResultSet locationList = stmt.executeQuery("SELECT * FROM Locations ORDER BY locationId");

            while (locationList.next()) {
                locations.add(new Location(
                        locationList.getString("locationId")
                ));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return locations;
        }
    }

    public void addLocation(Location loc) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO Locations VALUES ('" + loc.getLocationId() + "')");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean containsLocation(ObservableList<Location> locs, String locationId) {
        for (Location loc : locs) {
            if (loc.getLocationId().equals(locationId)) {
                return true;
            }
        }
        return false;
    }


    //***RIG FUNCTIONS***
    public ObservableList<Rig> loadRigs() {
        ObservableList<Rig> rigs = FXCollections.observableArrayList();

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            ResultSet rigList = stmt.executeQuery("SELECT * FROM Rigs ORDER BY rigNo");

            while (rigList.next()) {
                rigs.add(new Rig(rigList.getInt("rigNo"), rigList.getString("rigName"),
                        rigList.getString("clientName"), rigList.getString("wellName")));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return rigs;
        }
    }

    public void addRig(Rig rig) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO Rigs VALUES ('" + rig.getRigNo() + "','" +
                    rig.getRigName() + "','" + rig.getClientName() + "','" + rig.getWellName() + "')");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean containsRig(ObservableList<Rig> rigs, int rigNo) {
        for (Rig rig : rigs) {
            if (rig.getRigNo() == rigNo) {
                return true;
            }
        }
        return false;
    }

    public void updateRig(Rig rig) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement stmt = conn.createStatement();

            //Update Rig record in DB
            stmt.executeUpdate("UPDATE Rigs SET rigNo = '" + rig.getRigNo() + "', rigName = '" + rig.getRigName() +
                    "', clientName = '" + rig.getClientName() + "', wellName = '" + rig.getWellName() + "'WHERE rigNo =" +
                    " '" + rig.getRigNo() + "'");

            //close connection to DB
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //***TRANSACTION FUNCTIONS***
    //insert  part transaction to DB
    public void saveTransaction(Part p, char type, int quantity, String reference) {
        LocalDateTime now = LocalDateTime.now();
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            double totalVal = quantity*p.getUnitCost();

            stmt.executeUpdate("INSERT INTO partHistory( transType, transDate, partNo, quantity, " +
                    "reference, price, totalVal)" + "VALUES ('" + type + "','" + now + "','" + p.getPartNumber() +
                    "','" + quantity + "','" + reference + "', '" + p.getUnitCost() + "', '"+ totalVal + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Transaction> loadTransactions(Part p)
    {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            ResultSet transactionList =
                    stmt.executeQuery("SELECT * FROM partHistory WHERE partNo = '" + p.getPartNumber() + "'");

            while (transactionList.next())
            {
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
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            return transactions;
        }
    }

    //updates part stockLevel on issue/receipt
    public void updateStockLevel(int newStockLevel, String partNo) {
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("UPDATE Parts SET onHand = '" + newStockLevel + "' WHERE partNumber = '" + partNo +
                    "'");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //***ORDER FUNCTIONS***
    //generates unique orderNumber based on users Rig
    public String generateUniqueOrderNo()
    {
        int PXorders = -1;

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            ResultSet ordersList =
                    stmt.executeQuery("SELECT COUNT (*) AS orders FROM Orders");

            //return count of existing parts in that Inventory account
            PXorders = ordersList.getInt("orders");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    //add part to DB
    public void addOrder(Order o)
    {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            int orderApproved = 0;

            stmt.executeUpdate("INSERT INTO Orders(orderNumber, orderType, shippingMethod, orderDate, header," +
                    "orderStatus, orderTotal, orderApproved) VALUES ('"+ o.getOrderNumber() + "','" + o.getOrderType() + "','" + o.getShippingMethod() +
                    "','" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(o.getDate()) + "','" + o.getHeader() +
                    "','" + o.getOrderStatus() + "','" + o.getOrderTotal() + "'," +
                    "'" + orderApproved + "')");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Order> loadOrders()
    {
        ObservableList<Order> orders = FXCollections.observableArrayList();

        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            ResultSet orderList = stmt.executeQuery("SELECT * FROM Orders ORDER BY orderNumber");

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
        } finally {
            return orders;
        }
    }

    public void updateOrder(Order o) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement stmt = conn.createStatement();


            //Update Part in DB
            stmt.executeUpdate("UPDATE Orders SET orderType = '" + o.getOrderType() + "', shippingMethod = '" + o.getShippingMethod() +
                    "', header = '" + o.getHeader() + "', orderStatus = '" + o.getOrderStatus() +   "'WHERE orderNumber" +
                    " =" + " '" + o.getOrderNumber() + "'");

            //close connection to DB
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Order returnOrder(String orderNumber) {
        DBManager dbm = new DBManager();
        ObservableList<Order> orders = dbm.loadOrders();
        for (Order order : orders) {
            if (order.getOrderNumber().equals(orderNumber)) {
                return order;
            }
        }
        return null;
    }

    public void approveOrder(Order order) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement stmt = conn.createStatement();

            int orderApproved = 1;


            //Update Rig record in DB
            stmt.executeUpdate("UPDATE Orders SET orderApproved = '" + orderApproved + "', orderStatus = '" + 'O' +
                    "'WHERE orderNumber =" +
                    " '" + order.getOrderNumber() + "'");

            stmt.executeUpdate("UPDATE OrderLines SET status = '" + 'O'  + "'WHERE orderNumber =" +
                    " '" + order.getOrderNumber() + "'");



            //close connection to DB
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelOrder(Order order) {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement stmt = conn.createStatement();


            //Update Rig record in DB
            stmt.executeUpdate("UPDATE Orders SET orderStatus = '" + 'X' +
                    "'WHERE orderNumber =" +
                    " '" + order.getOrderNumber() + "'");

            stmt.executeUpdate("UPDATE OrderLines SET status =  '" + 'X' + "', lineTotal = '" + 0 +
                    "'WHERE orderNumber =" + " '" + order.getOrderNumber() + "'");

            //close connection to DB
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateOrderTotal(String orderNumber, double orderTotal)
    {
        try{
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("UPDATE Orders SET orderTotal = '" + orderTotal  + "'WHERE orderNumber =" +
                            " '" + orderNumber + "'");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public ObservableList<Order> searchOrders(String criteria) {
        ObservableList<Order> orders = FXCollections.observableArrayList();


        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //DB select statement
            ResultSet orderList =
                    stmt.executeQuery("SELECT * FROM Orders WHERE orderNumber || header || orderDate || " +
                            "location || description LIKE" + "'%" + criteria + "%' ORDER BY orderNumber");


            //iterate through result set
            while (orderList.next()) {
                Date orderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderList.getString("orderDate"));
                orders.add(new Order(orderList.getString("orderNumber"), orderList.getString("orderType").charAt(0),
                        orderList.getString("shippingMethod"), orderDate, orderList.getString(
                        "header"), orderList.getString("orderStatus").charAt(0), orderList.getDouble("orderTotal"),
                        orderList.getBoolean("orderApproved")));
            }

            //close connection to DB
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return orders;
        }
    }

    //***ORDERLINE FUNCTIONS***
    //all other methods and functions
    public int generateUniqueOrderLineId(String orderNumber)
    {
        int orderLineId =1;
        ObservableList<OrderLine> orderLines = loadOrderLines(orderNumber);


        for(OrderLine orderLine : orderLines)
        {
            if(orderLine.getOrderLineId()==orderLineId)
            {
                orderLineId++;
            }
        }
        return orderLineId;
    }

    public ObservableList<OrderLine> loadOrderLines(String orderNumber)
    {
        ObservableList<OrderLine> orderLines = FXCollections.observableArrayList();

        try
        {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            ResultSet olList = stmt.executeQuery("SELECT * FROM OrderLines WHERE orderNumber = '" + orderNumber + "'");
            while(olList.next())
            {
                String partNumber = olList.getString("part");
                Part part = returnPart(partNumber);
                orderLines.add(new OrderLine(olList.getInt("orderLineId"), olList.getInt("quantity"), part,
                        olList.getDouble(
                        "lineTotal"), olList.getString("requestedBy"), olList.getString("status").charAt(0),
                        olList.getInt("receivedQty"), olList.getString("manifestId")));
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return orderLines;
        }
    }

    public static OrderLine returnOrderLine( int orderLineId, String orderNumber) {
        DBManager dbm = new DBManager();
        ObservableList<OrderLine> orderLines = dbm.loadOrderLines(orderNumber);
        for (OrderLine orderLine : orderLines) {
            if (orderLine.getOrderLineId()== orderLineId) {
                return orderLine;
            }
        }
        return null;
    }

    public void addOrderLine(OrderLine ol, String orderNumber)
    {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();


            stmt.executeUpdate("INSERT INTO OrderLines(orderLineId, quantity, part, lineTotal, requestedBy," +
                    "status, receivedQty, manifestId, orderNumber) VALUES " + "('"+ ol.getOrderLineId()+ "','" + ol.getQuantity() +
                    "','" + ol.getPart() + "','" + ol.getLineTotal() + "','" + ol.getRequestedBy() + "','" + ol.getStatus() +
                    "','" + ol.getReceivedQty() +"','" + ol.getManifestId() + "'," + "'" + orderNumber + "')");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateOrderLine(OrderLine orderLine, String orderNumber)
    {
        try {
            forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement stmt = conn.createStatement();


            //Update orderLine
            stmt.executeUpdate("UPDATE OrderLines SET status = '" + orderLine.getStatus() + "', manifestId = '" +
                    orderLine.getManifestId() + "', receivedQty = '" + orderLine.getReceivedQty() +  "'WHERE orderLineId =" +
                    " '" + orderLine.getOrderLineId() + "' AND orderNumber =" + " '" + orderNumber+ "'");

            //close connection to DB
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //delete OrderLine from DB
    public boolean removeOrderLine(OrderLine orderLine, String orderNumber)
    {
        try
        {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);

            Statement smt = conn.createStatement();

            //delete orderLine entry in DB
            smt.executeUpdate("DELETE FROM OrderLines WHERE orderLineId = '" + orderLine.getOrderLineId() +
                    "' AND orderNumber =" + " '" + orderNumber + "'");
            conn.close();

            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

}



