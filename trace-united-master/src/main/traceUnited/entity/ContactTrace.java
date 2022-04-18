package main.traceUnited.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

//Contact trace class 
public class ContactTrace {
	//private variables
	private int id;
	private String userID;
	private String checkInDateTime;
	private String checkOutDateTime;
	private String businessAddress;

	//Default constructor
	public ContactTrace() {}

	//Other constructor
	public ContactTrace(int id, String userID, String checkInDateTime, String checkOutDateTime, String businessAddress) {
		this.id = id;
		this.userID = userID;
		this.checkInDateTime = checkInDateTime;
		this.checkOutDateTime = checkOutDateTime;
		this.businessAddress = businessAddress;
	}
	
	//Other constructor
	public ContactTrace(String checkInDateTime, String checkOutDateTime, String businessAddress) {
		this.checkInDateTime = checkInDateTime;
		this.checkOutDateTime = checkOutDateTime;
		this.businessAddress = businessAddress;
	}

	//Accessor method
	public String getUserID() {
		return userID;
	}

	//Accessor method
	public String getBusinessAddress() {
		return businessAddress;
	}

	//Accessor method
	public String getCheckInDateTime() {
		return checkInDateTime;
	}

	//Accessor method
	public String getCheckOutDateTime() {
		return checkOutDateTime;
	}

	//view contact traces with parameter of user id
	public ResultSet viewContactTraces(String userID) {
		String sqlStatement = "SELECT * from ContactTraces WHERE UserID=?";
		String[] parameters = {userID};
		ResultSet result = queryHelper(sqlStatement, parameters);
		return result;
	}

	//checkoutcontroller get business address for combo box  with parameter of user id
	public ResultSet getBusinessAddress(String userID) {
		String sqlStatement = "SELECT ContactTraces.ID, ContactTraces.BusinessAddress, ContactTraces.CheckInDateTime FROM ContactTraces WHERE UserID=?";
		String[] parameters = {userID};
		return queryHelper(sqlStatement, parameters);
	}

	// UPDATE checkout date with parameter of checkout date, id, user id and business address
	public boolean updateCheckOutDate(String checkOutDate, int id, String userID, String businessAddress) {
		String sqlStatement = "UPDATE ContactTraces SET CheckOutDateTime=? Where ContactTraces.ID=? and ContactTraces.UserID=? and ContactTraces.BusinessAddress=? ";
		String [] parameters = {checkOutDate, id+"" , userID, businessAddress};
		int rows = createUpdateHelper(sqlStatement, parameters);
		return rows>0;
	}

	//retrieve check in date with parameter of user id and business address
	public String getCheckInDate(String userID, String businessAddress) {
		String sqlStatement = "SELECT ContactTraces.CheckInDateTime FROM ContactTraces WHERE ContactTraces.USERID=? and ContactTraces.BusinessAddress=?";
		String [] parameters = {userID, businessAddress};
		return queryHelper(sqlStatement, parameters).toString();
	}

	//view contacted business with parameter of user id
	public ArrayList<ContactTrace> viewContactedBusinesses(String userID) {
		try {
			String sqlStatement = "SELECT C.ID, B.UserID, C.CheckInDateTime, C.CheckOutDateTime, C.BusinessAddress FROM ContactTraces AS C LEFT JOIN Businesses AS B ON C.BusinessAddress = B.BusinessAddress WHERE C.UserID = ?";
			String[] parameters = {userID};
			ResultSet result = queryHelper(sqlStatement, parameters);

			ArrayList<ContactTrace> contactTraces = new ArrayList<ContactTrace>();

			//convert from result to arraylist of contact trace
			while(result != null && result.next()) {
				ContactTrace contactTrace = new ContactTrace(
						result.getInt("ID"), 
						result.getString("UserID"), 
						result.getString("CheckInDateTime"), 
						result.getString("CheckOutDateTime"), 
						result.getString("BusinessAddress")
						);
				contactTraces.add(contactTrace);
			}
			return contactTraces;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	//insert check in date time and location with parameters of user id, concatenated string of date time and business address
	public boolean checkInDateTimeLocation(String userID, String concatenatedString, String selectedAddress) {
		String sqlStatement = "INSERT INTO ContactTraces (UserID, CheckInDateTime, Businessaddress) VALUES(?, ?, ?)";
		String[] parameters = {userID, concatenatedString, selectedAddress};
		int rows = createUpdateHelper(sqlStatement, parameters);

		return rows>0;
	}

	//business retrieve nrics by date with parameters of check in date and business address
	public ArrayList<String> businessRetrieveUserIDs(String checkinDate, String businessAddress) {
		try {
			String sqlStatement = "SELECT UserID FROM ContactTraces WHERE BusinessAddress = ? AND CheckInDateTime LIKE ?";
			String[] parameters = {businessAddress, checkinDate};
			ResultSet rs = queryHelper(sqlStatement, parameters);
			ArrayList<String> result = new ArrayList<String>();
			while(rs !=null && rs.next()) {
				result.add(rs.getString("UserID"));
			}
			return result;
		}catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public ArrayList<ContactTrace> getContactTraceInfoForCovidPatient(String nric){
		ArrayList<ContactTrace> TraceInfo = new ArrayList<ContactTrace>();
		try {
			// get the checkin checkout and Address of the covid positive individual
			String GetTraceInfoStatement = "SELECT CheckInDateTime,CheckOutDateTime,BusinessAddress FROM ContactTraces Inner Join Users on Users.UserID=ContactTraces.UserID WHERE NRIC=?";
			String[] GetTraceInfoparameters = {nric};
			ResultSet GetTraceInfoResult = queryHelper(GetTraceInfoStatement, GetTraceInfoparameters);
			while(GetTraceInfoResult.next()) {
				String datein = GetTraceInfoResult.getString(1);
				String dateout = GetTraceInfoResult.getString(2);
				String B_address = GetTraceInfoResult.getString(3);
				ContactTrace instance = new ContactTrace(datein,dateout,B_address);
				TraceInfo.add(instance);
			}
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
		}
		return TraceInfo;
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
