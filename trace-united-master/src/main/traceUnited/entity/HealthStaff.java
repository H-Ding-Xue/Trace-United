package main.traceUnited.entity;

import java.sql.*;
import javax.swing.JOptionPane;
import java.util.ArrayList; 

//health staff class
public class HealthStaff extends User{
	
	//Default constructor
	public HealthStaff() {
		super();
	}
	
	//Other constructor
	public HealthStaff(String userID, String password, String userType, String nric, String name, String phoneNumber, String emailAddress, boolean isActive) {
		super(userID, password, userType, nric, name, phoneNumber, emailAddress, isActive);
	}
}
