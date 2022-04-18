package main.traceUnited.controller;

import main.traceUnited.entity.Business;
import main.traceUnited.entity.HealthOrganisation;
import main.traceUnited.entity.HealthStaff;
import main.traceUnited.entity.PublicUser;
import main.traceUnited.entity.User;

public class CreateUserController {
	private User _user = new User();
	private PublicUser publicUser = new PublicUser();
	private Business business = new Business();
	
	public boolean validateCreate(String userID, String password, String userType, String nric, String name, String phoneNumber, String emailAddress, String businessName, String businessAddress) {
		if(userID.matches("^[a-zA-Z0-9]*$") && nric.matches("^[a-zA-Z0-9]*$")) {
			if(_user.isUserIDExist(userID)) {
				throw new IllegalArgumentException("User ID already exist");
			}
			
			if(_user.create(userID, password, userType, nric, name, phoneNumber, emailAddress)) {
				if(userType == "Public") {
					return publicUser.create(userID);
				}
				else if(userType == "Business") {
					return business.create(userID, businessName, businessAddress);
				}
				else {
					return true;
				}
			}
			return false;
		}
		else {
			throw new IllegalArgumentException("Invalid input format");
		}
	}
}
