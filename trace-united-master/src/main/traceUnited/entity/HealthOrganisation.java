package main.traceUnited.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

//Health organisation class
public class HealthOrganisation extends User{
	
	//Default constructor
	public HealthOrganisation() {
		super();
	}
	
	//Other constructor
	public HealthOrganisation(String userID, String password, String userType, String nric, String name, String phoneNumber, String emailAddress, boolean isActive) {
		super(userID, password, userType, nric, name, phoneNumber, emailAddress, isActive);
	}
}
