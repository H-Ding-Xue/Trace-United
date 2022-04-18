package main.traceUnited.controller;

import java.sql.ResultSet;

import main.traceUnited.entity.Business;
import main.traceUnited.entity.User;

public class UpdateUserController {
	//use USEr
    private User _user = new User();
    private Business _business = new Business();

    public ResultSet validateRetrieve(String userID, String userType) {
        return _user.retrieveInfo(userID, userType);
    }
    
    public ResultSet validateRetrieveBusiness(String userID) {
        return _user.retrieveInfoBusiness(userID);
    }

    public boolean validateUpdate(String userID, String password, String nric, String name, String phoneNumber, String emailAddress) {
    	if(userID.matches("^[a-zA-Z0-9]*$") && nric.matches("^[a-zA-Z0-9]*$")) {
    		return _user.updateInfo(userID, password, nric, name, phoneNumber, emailAddress);
    	}
    	else {
    		throw new IllegalArgumentException("Invalid input format");
    	}
    }
    
    public boolean validateUpdateBusiness(String userID, String password, String nric, String name, String phoneNumber, String emailAddress, String businessName, String businessAddress) {
    	if(userID.matches("^[a-zA-Z0-9]*$") && nric.matches("^[a-zA-Z0-9]*$")) {
	    	if(_user.updateInfo(userID, password, nric, name, phoneNumber, emailAddress)) {
	    		return _business.updateInfoBusiness(userID, businessName, businessAddress);
	    	}
	    	else {
	    		return false;
	    	}
    	}
    	else {
    		throw new IllegalArgumentException("Invalid input format");
    	}
    }
}