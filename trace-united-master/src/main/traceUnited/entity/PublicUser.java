package main.traceUnited.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

//public user class
public class PublicUser extends User {
	//private variables
	private boolean isVaccinated;
	
	//Default constructor
	public PublicUser() {
		super();
	}

	//Other constructor
	public PublicUser(String userID, String password, String userType, String nric, String name, String phoneNumber, String emailAddress, boolean isActive, boolean isVaccinated) {
		super(userID, password, userType, nric, name, phoneNumber, emailAddress, isActive);
		this.isVaccinated = isVaccinated;
	}
	
	//create public user with parameter of user id
	public boolean create(String userID) {
		String sqlStatement = "INSERT INTO PublicUsers (UserID, IsVaccinated) VALUES(?, 0)";
		String[] parameters = {userID};
		int rows = createUpdateHelper(sqlStatement, parameters);

		return rows>0;
	}
	
	//retrieve user info with parameter of user id
	public ResultSet userGetInfo(String userID) {
		// User is a reserved keyword got to use [] to enclosed it
		String sqlStatement = "SELECT Users.UserID, Users.Name, PublicUsers.isVaccinated"
				+" FROM Users"
				+" LEFT JOIN PublicUsers ON Users.UserID = PublicUsers.UserID"
				+" WHERE Users.UserID=?";
		String[] parameters = {userID};
		return queryHelper(sqlStatement, parameters);
	}
	
	// update vaccination status with parameter of userID and vaccination status
	public boolean updateVaccinationStatus(String userID, String vstatus) {
		try {
			String UpdateVstatus = "UPDATE PublicUsers SET PublicUsers.IsVaccinated = ? where PublicUsers.UserID = ?";
			String[] UpdateParameters = {vstatus , userID};
			int rows = createUpdateHelper(UpdateVstatus, UpdateParameters);
			return rows>0;
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
		}
		return false;
	}
	
	//retrieve vaccination status with parameter of userId
	public String retrieveVaccinationStatus(String userId) {
		String vaxstatus = "";
		try {
			String VStatusSelect = "SELECT IsVaccinated FROM PublicUsers where UserID = ?";
			String[] VStatusParameters = {userId};
			ResultSet rs = queryHelper(VStatusSelect, VStatusParameters);
			if(rs.next()) {
				vaxstatus = rs.getString(1);
			}
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
		}
		return vaxstatus;
	}
	
	//retrieve total public users count
	public double retrieveTotalPublicUsersCount() {
		double totalPublicUsersCount = 0;
		try {
			// get total number of public users
			String publiCountStatement = "SELECT COUNT(IsVaccinated) FROM PublicUsers";
			String[] parameters = {};
			ResultSet rs = queryHelper(publiCountStatement, parameters);
			rs.next();
			totalPublicUsersCount = rs.getDouble(1);
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
		}
		return totalPublicUsersCount;
	}
	
	//retrieve total vaccinated public users count
	public double retrieveTotalVaccinatedPublicUsersCount() {
		double totalVaccinatedPublicUsersCount = 0;
		try {
			String publicVaxCountStatement = "SELECT COUNT(IsVaccinated) FROM PublicUsers WHERE IsVaccinated = 1";
			String[] parameters2 = {};
			ResultSet rs = queryHelper(publicVaxCountStatement, parameters2);
			rs.next();
			totalVaccinatedPublicUsersCount = rs.getDouble(1);
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
		}
		return totalVaccinatedPublicUsersCount;
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

