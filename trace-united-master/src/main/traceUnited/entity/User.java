package main.traceUnited.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import javax.swing.JOptionPane;

//user class
public class User {
	//private variables
	private String userID;
	private String password;
	private String userType;
	private String nric;
	private String name;
	private String phoneNumber;
	private String emailAddress;
	private boolean isActive;

	//default constructor
	public User() {}

	//other constructor
	public User(String userID, String password, String userType, String nric, String name, String phoneNumber, String emailAddress, boolean isActive) {
		this.userID = userID;
		this.password = password;
		this.userType = userType;
		this.nric = nric;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.isActive = isActive;
	}

	//accessor method
	public boolean getIsActive() {
		return isActive;
	}

	//login method with parameter of user type, user id and password
	public String login (String userType, String userID, String password) {
		try {
			String sqlStatement = "SELECT * FROM Users WHERE UserID=? AND Password=? AND UserType=? AND IsActive=1";
			String[] parameters = {userID, password, userType};

			ResultSet result = queryHelper(sqlStatement, parameters);

			//retrieve user name from result
			while(result != null && result.next()) {
				return result.getString("Name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	//check if user id exist with parameter of user id
	public boolean isUserIDExist(String userID) {
		try {
			String sqlStatement = "SELECT * FROM Users WHERE UserID=?";
			String[] parameters = {userID};
			ResultSet result = queryHelper(sqlStatement, parameters);

			while(result != null && result.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	//create user method with parameter user id, password, user type, nric, name, phone number and email address
	public boolean create(String userID, String password, String userType, String nric, String name, String phoneNumber, String emailAddress) {
		String sqlStatement = "INSERT INTO Users (UserID, Password, UserType, NRIC, Name, PhoneNumber, EmailAddress, IsActive) VALUES(?, ?, ?, ?, ?, ?, ?, 1)";
		String[] parameters = {userID, password, userType, nric, name, phoneNumber, emailAddress};
		int rows = createUpdateHelper(sqlStatement, parameters);

		return rows>0;
	}

	//retrieve user id from nric with parameter of nric and user type
	public String retrieveUserIDFromNRIC(String nric, String userType) {
		String userID = "";
		try {
			String sqlStatement = "SELECT UserID FROM Users WHERE NRIC=? AND UserType=?";
			String[] parameters = {nric, userType};
			ResultSet result = queryHelper(sqlStatement, parameters);

			while(result != null && result.next()) {
				userID = result.getString("UserID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userID;
	}

	//suspend user with parameter of user id
	public boolean suspend(String userID) {
		String sqlStatement = "UPDATE Users SET IsActive=0 WHERE UserID=?";
		String[] parameters = {userID};
		int rows = createUpdateHelper(sqlStatement, parameters);

		return rows>0;
	}

	//retrieve user info with parameter of user id and user type
	public ResultSet retrieveInfo(String userID, String userType) {
		String sqlStatement = "SELECT * from Users WHERE UserID=? AND UserType=?";
		String[] parameters = {userID, userType};
		ResultSet result = queryHelper(sqlStatement, parameters);
		return result;
	}

	// retrieve business info with parameter of user id
	public ResultSet retrieveInfoBusiness(String userID) {
		String sqlStatement = "SELECT * from Users INNER JOIN Businesses ON Users.UserID = Businesses.UserID WHERE Businesses.UserID=?;";
		String[] parameters = {userID};
		ResultSet result = queryHelper(sqlStatement, parameters);
		return result;
	}

	//update info with parameter of user id, password, nric, name, phone number and email address
	public boolean updateInfo(String userID, String password, String nric, String name, String phoneNumber, String emailAddress){
		String sqlStatement = "UPDATE Users SET NRIC=?, Password=?, Name=?, PhoneNumber=?, EmailAddress=? WHERE UserID=?";
		String[] parameters = {nric, password, name, phoneNumber, emailAddress, userID};

		int rows = createUpdateHelper(sqlStatement, parameters);
		return rows>0;
	}

	//retrieve nrics from user ids with parameters of user ids and user type
	public ArrayList<String> retrieveNRICsFromUserIDs(ArrayList<String> userIDs, String userType){
		try {
			//construct sql statement
			String sqlStatement = "SELECT NRIC FROM Users WHERE UserID IN (";
			ArrayList<String> arrayListParameters = new ArrayList<String>();

			for(String userID : userIDs) {
				arrayListParameters.add(userID);
			}
			arrayListParameters.add(userType);

			int arrayListSize = userIDs.size();
			for(int i=0; i<(arrayListSize-1); i++) {
				sqlStatement += "?, ";
			}
			sqlStatement += "?) AND UserType=?";

			//convert array list string to string array for parameters
			String[] parameters = arrayListParameters.toArray(new String[arrayListParameters.size()]);

			ArrayList<String> nrics = new ArrayList<String>();
			ResultSet result = queryHelper(sqlStatement, parameters);

			//retrieve nrics from result
			while(result!=null && result.next()) {
				nrics.add(result.getString("NRIC"));
			}

			return nrics;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public boolean checkIfNricIsPublicWithContactTraceRecords(String nric){
		boolean exist = false;
		try {
			// check if entered NRIC is a publics NRIC else return a error
			String CheckNricIsPublic = "SELECT NRIC FROM Users INNER JOIN ContactTraces ON Users.UserID = ContactTraces.UserID WHERE NRIC=?";
			String[] CheckNricIsPublicparameters = {nric};
			ResultSet CheckNricIsPublicResult = queryHelper(CheckNricIsPublic, CheckNricIsPublicparameters);
			exist = CheckNricIsPublicResult.next();

		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
		}
		return exist;
	}

	public boolean checkIfNricExist(String nric){
		boolean exist = false;
		try {
			//check if NRIC entered exist in DB else return an error
			String CheckNricExistence = "SELECT NRIC FROM Users WHERE NRIC=?";
			String[] CheckNricExistenceparameters = {nric};
			ResultSet CheckNricExistenceResult = queryHelper(CheckNricExistence, CheckNricExistenceparameters);
			exist = CheckNricExistenceResult.next();

		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
		}
		return exist;
	}

	public ArrayList<String> getNricOfCloseContacts(String nric, ArrayList<ContactTrace> covidPatientHistory){
		ArrayList<String> nriclist = new ArrayList<String>();
		ArrayList<String> nriclistnoduplicate = new ArrayList<String>();
		try {
			// get the nrics of any other public users that have come into overlapping contact with the covid positive public user and assign values to nriclist
			for(int i=0; i < covidPatientHistory.size(); i++){
				String B_address = covidPatientHistory.get(i).getBusinessAddress();
				String dateout = covidPatientHistory.get(i).getCheckOutDateTime();
				String datein = covidPatientHistory.get(i).getCheckInDateTime();
				String getCloseContactStatement = "SELECT DISTINCT NRIC FROM Users Inner Join ContactTraces on Users.UserID=ContactTraces.UserID WHERE "
						+ "BusinessAddress =? AND (CheckInDateTime BETWEEN ? AND ? OR CheckOutDateTime BETWEEN ? AND ? OR (CheckInDateTime < ? AND CheckOutDateTime > ?) ) AND NOT NRIC = ?";
				String[] getCloseContactParameters = {B_address, datein, dateout , datein, dateout , datein, dateout,nric};
				ResultSet getCloseContactResult = queryHelper(getCloseContactStatement, getCloseContactParameters);
				while(getCloseContactResult.next()) {
					nriclist.add(getCloseContactResult.getString(1));
				}
			}
			if(!nriclist.isEmpty()) {
				// remove any duplicate from nriclist populating nriclistnoduplicate
				LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>(nriclist);
				nriclistnoduplicate = new ArrayList<String>(linkedHashSet);
			}
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
		}
		return nriclistnoduplicate;
	}

	public ArrayList<String> getUserIdOfCloseContacts(ArrayList<String> nrics){
		ArrayList<String> Idlist = new ArrayList<String>();
		try {
			// get user IDs for all the NRICS of the people who came in close contact
			for(int i=0; i < nrics.size(); i++){
				String GetIdStatement = "SELECT UserID FROM Users WHERE NRIC=? AND UserType='Public' ";
				String[] GetIdParameters = {nrics.get(i)};
				ResultSet GetIdResult = queryHelper(GetIdStatement, GetIdParameters);
				GetIdResult.next();
				Idlist.add(GetIdResult.getString(1));
			}
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
		}
		return Idlist;
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
