package main.traceUnited.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

//Business class which is a subclass of User
public class Business extends User {
	//private variables
	private String businessName;
	private String businessAddress;
	//Default Constructor
	public Business() {
		super();
	}
	
	//Other Constructor
	public Business(String userID, String password, String userType, String nric, String name, String phoneNumber, String emailAddress, boolean isActive, String businessName, String businessAddress) {
		super(userID, password, userType, nric, name, phoneNumber, emailAddress, isActive);
		this.businessName = businessName;
		this.businessAddress = businessAddress;
	}
	
	//create business user method with parameters of user id, business name and business address
	public boolean create(String userID, String businessName, String businessAddress) {
		String sqlStatement = "INSERT INTO Businesses (UserID, BusinessName, BusinessAddress) VALUES(?, ?, ?)";
		String[] parameters = {userID, businessName, businessAddress};
		int rows = createUpdateHelper(sqlStatement, parameters);
		
		return rows>0;
	}
	
	//update business user method with parameters of user id, business name and business address
	public boolean updateInfoBusiness(String userID, String businessName, String businessAddress){
		String sqlStatement = "UPDATE Businesses SET BusinessName=?, Businessaddress=? WHERE UserID=?";
        String[] parameters = {businessName, businessAddress, userID};

		int rows = createUpdateHelper(sqlStatement, parameters);
		return rows>0;
	}
	
	// retrieve user ids from business address with the parameter of arraylist of contact trace
	public ArrayList<String> retrieveUserIDsFromBusinessAddresses(ArrayList<ContactTrace> contactTraces){
		try {
			int arrayListSize = contactTraces.size();
			
			//constructing sql statement
			String sqlStatement = "SELECT UserID FROM Businesses WHERE BusinessAddress IN(";
			for(int i=0; i<(arrayListSize-1); i++) {
				sqlStatement += "?, ";
			}
			sqlStatement += "?)";
			
			//constructing parameters
			ArrayList<String> businessAddresses = new ArrayList<String>();
			for(ContactTrace contactTrace: contactTraces) {
				businessAddresses.add(contactTrace.getBusinessAddress());
			}
			String[] parameters = businessAddresses.toArray(new String[businessAddresses.size()]);
			
			ArrayList<String> businessUserIDs = new ArrayList<String>();
			ResultSet result = queryHelper(sqlStatement, parameters);
			
			//retrieve user id from result
			while(result != null && result.next()) {
				businessUserIDs.add(result.getString("UserID"));
			}
			return businessUserIDs;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	//get business alert with parameter of user id
	public ResultSet getBusinessAlert(String userID) {
		String sqlStatement = "SELECT ALERTS.ID, ALERTS.AlertMessage, ALERTS.AlertDateTime, ALERTS.AcknowledgedDateTime, ALERTS.IsAcknowledged from Alerts Inner Join Businesses ON Businesses.UserID = Alerts.UserID WHERE Alerts.UserID=?"; //user id used to logged in with 
		// logging in as business so i will not view other businessesID
		String[] parameters = {userID};
		return queryHelper(sqlStatement, parameters);
	}
	
	//retrieve all available business addresses
	public ResultSet retrieveLocation() {
		String sqlStatement = "SELECT DISTINCT Businessaddress From Businesses";
		String[] parameters = {};
		ResultSet result = queryHelper(sqlStatement, parameters);
        return result;
	}
	
	//retrieve business address from user id
	public ResultSet businessRetrieveLocation(String userID) {
		String sqlStatement = "SELECT Businessaddress From Businesses WHERE UserID=?";
		String[] parameters = {userID};
		ResultSet result = queryHelper(sqlStatement, parameters);
        return result;
	}
	
	public double retrieveTotalBusinessesCount(){
		double totalBusinessesCount = 0;
		try {
			// get total number of Businesses and assign it to total business variable
			String BusinessCountStatement = "SELECT COUNT(UserID) FROM Businesses";
			String[] parameters = {};
			ResultSet rs = queryHelper(BusinessCountStatement, parameters);
			rs.next();
			totalBusinessesCount = rs.getDouble(1);
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
		}
		return totalBusinessesCount;
	}

	//set up db connection
	private Connection dbConnection() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=TraceUnitedDB;instance=SQLSERVER;";
		String username = "sa";
		String password = "password123";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(connectionUrl, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	//method to query database with input parameters of sql statement and its corresponding parameters
	private ResultSet queryHelper(String sqlStatement, String[] parameters) {
		Connection connection = dbConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			for(int i = 0; i < parameters.length; i++) {
				statement.setString((i+1), parameters[i]);
			}
			return statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//method to insert and update database with input parameters of sql statement and its corresponding parameters
	private int createUpdateHelper(String sqlStatement, String[] parameters) {
		Connection connection = dbConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			for(int i = 0; i < parameters.length; i++) {
				statement.setString((i+1), parameters[i]);
			}
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
