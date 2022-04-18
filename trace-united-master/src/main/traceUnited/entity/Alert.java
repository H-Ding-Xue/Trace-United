package main.traceUnited.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;

import javax.swing.JOptionPane;

//Alert class
public class Alert {
	//private fields
	private int id;
	private String userID;
	private String alertMessage;
	private String alertDateTime;
	private boolean isAcknowledged;
	private String acknowledgedDateTime;

	//default constructor
	public Alert() {}

	//other constructor
	public Alert(int id, String userID, String alertMessage, String alertDateTime, boolean isAcknowledged, String acknowledgedDateTime) {
		this.id = id;
		this.userID = userID;
		this.alertMessage = alertMessage;
		this.alertDateTime = alertDateTime;
		this.isAcknowledged = isAcknowledged;
		this.acknowledgedDateTime = acknowledgedDateTime;
	}

	//send alert to business with parameters of ArrayList of contact trace and message
	public boolean sendAlertToBusinesses(ArrayList<ContactTrace> contactTraces, String message) {
		ArrayList<String> arrayListParameters = new ArrayList<String>();
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Calendar currentDateTime = Calendar.getInstance();
		String date = dateFormat.format(currentDateTime.getTime());
		String sqlStatement = "";

		//converting array list contact trace to contact trace array
		ContactTrace[] contactTracesArray = contactTraces.toArray(new ContactTrace[contactTraces.size()]);

		//constructing the sql statement and parameters
		for(int i = 0; i<contactTraces.size(); i++) {
			String updatedMessage = message + "\n Patient Checked in date: " + contactTracesArray[i].getCheckInDateTime().substring(0,10) + "\n Checked out date: " + contactTracesArray[i].getCheckInDateTime().substring(0,10);

			sqlStatement += "INSERT INTO Alerts (UserID, AlertMessage, AlertDateTime, IsAcknowledged) VALUES (?, ?, ?, 0);";
			arrayListParameters.add(contactTracesArray[i].getUserID());
			arrayListParameters.add(updatedMessage);
			arrayListParameters.add(date);
		}

		//converting array list of string to string array
		String[] parameters = arrayListParameters.toArray(new String[arrayListParameters.size()]);

		int rows = createUpdateHelper(sqlStatement, parameters);

		return rows>0;
	}
	
	public boolean updateAlert(String isAcknowledged, String acknowledgedDateTime, String id) {
		String sqlStatement = "UPDATE Alerts SET Alerts.IsAcknowledged=?, Alerts.AcknowledgedDateTime=? WHERE Alerts.ID=?";
		String[] parameters = {isAcknowledged, acknowledgedDateTime, id};
		int rows = createUpdateHelper(sqlStatement, parameters);
		return rows>0;
	}
	
	// display all 
	public ResultSet displayPublicAlert(String userID) {
		String sqlStatement = "SELECT * FROM Alerts WHERE Alerts.UserID=?";
		String[] parameters = {userID};
		return queryHelper(sqlStatement, parameters);
	}

	public void sendAlertToPublic(ArrayList<String> idList, String msg) {
		// initialze current datetime
		Date date = new Date();
		java.sql.Date sqldate = new java.sql.Date(date.getTime());
		SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
		String currentdate = sqldate.toString();
		String currenttime = time.format(date);
		String currentdatetime = currentdate +" " +currenttime;
		try {
			// send alert to all the IDs passed in
			for(int i=0; i < idList.size(); i++){
				String ID = idList.get(i);
				String InsertAlert = "INSERT INTO ALERTS (UserID, AlertMessage, AlertDateTime,IsAcknowledged) VALUES (?, ?, ?, ?)";
				String[] AlertParameters = {ID,msg,currentdatetime,"0"};
				createUpdateHelper(InsertAlert, AlertParameters);
			}

		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
		}

	}
	
	public double retrieveTotalBusinessesVisitedByPatientCount() {
		double totalBusinessesVisitedByPatienCount = 0;
		try {
			//get number of businesses that have received alert at least once 
			String affectedbusinessCountStatement = "SELECT COUNT (DISTINCT Alerts.UserID) FROM Alerts INNER JOIN Businesses ON Alerts.UserID = Businesses.UserID";
			String[] parameters = {};
			ResultSet rs = queryHelper(affectedbusinessCountStatement, parameters);
			rs.next();
			totalBusinessesVisitedByPatienCount = rs.getDouble(1);
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
		}
		return totalBusinessesVisitedByPatienCount;
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
