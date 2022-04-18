package main.traceUnited.controller;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

import main.traceUnited.entity.HealthStaff;
import main.traceUnited.entity.PublicUser;
import main.traceUnited.entity.User;

public class HealthStaffViewAndUpdateVaccinationStatusController { 
	User user = new User();
	PublicUser publicUser = new PublicUser();
	// controller methods validate parameters before calling entity methods
	public boolean updateVaccinationStatus(String nric, String vstatus) {
		if(nric.matches("^[a-zA-Z0-9]*$")) {
			String userID = user.retrieveUserIDFromNRIC(nric, "Public");
			if(userID != null && userID.length()>0) {
				return publicUser.updateVaccinationStatus(userID, vstatus);
			}
			else {
				return false;
			}
		}
		else {
			throw new IllegalArgumentException("Invalid input format");
		}
	}
	
	public String retrieveVaccinationStatus(String nric) {
		if(nric.matches("^[a-zA-Z0-9]*$")) {
			String userID = user.retrieveUserIDFromNRIC(nric, "Public");
			if(userID != null && userID.length()>0) {
				return publicUser.retrieveVaccinationStatus(userID);
			}
			else {
				return "";
			}
		}
		else {
			throw new IllegalArgumentException("Invalid input format");
		}
	}
}