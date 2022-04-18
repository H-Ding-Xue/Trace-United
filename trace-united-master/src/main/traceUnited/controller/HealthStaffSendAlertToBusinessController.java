package main.traceUnited.controller;

import java.util.ArrayList;

import main.traceUnited.entity.Alert;
import main.traceUnited.entity.Business;
import main.traceUnited.entity.ContactTrace;
import main.traceUnited.entity.User;

public class HealthStaffSendAlertToBusinessController {
	private User user = new User();
	private ContactTrace contractTrace = new ContactTrace();
	private Business business = new Business();
	private Alert alert = new Alert();
	
	public ArrayList<String> validateAlert(String nric, String message){
		if(nric.matches("^[a-zA-Z0-9]*$")&& !message.isEmpty()){
			String userID = user.retrieveUserIDFromNRIC(nric, "Public");
			if(userID !=  null && userID.length()>0) {
				ArrayList<ContactTrace> contactTraces = contractTrace.viewContactedBusinesses(userID);
				ArrayList<String> userIDs = business.retrieveUserIDsFromBusinessAddresses(contactTraces);
				if(alert.sendAlertToBusinesses(contactTraces, message)) {
					return user.retrieveNRICsFromUserIDs(userIDs, "Business");
				}
				else {
					throw new RuntimeException("Error");
				}
			}
			else {
				throw new RuntimeException("Invalid NRIC");
			}
		}
		else {
			throw new IllegalArgumentException("Invalid input format");
		}
	}
}
