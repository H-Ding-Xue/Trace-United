package main.traceUnited.controller;

import java.util.ArrayList;

import main.traceUnited.entity.Alert;
import main.traceUnited.entity.ContactTrace;
import main.traceUnited.entity.User;

public class HealthStaffSendAlertToPublicController {
	// Declare new Alert instance
	ArrayList<String> noExist = new ArrayList<String>();
	ArrayList<String> notPublicOrNotNeeded = new ArrayList<String>();
	ArrayList<String> nricList = new ArrayList<String>();
	ArrayList<String> userIdList = new ArrayList<String>();
	Alert alert = new Alert();
	User user = new User();
	ContactTrace contactTrace = new ContactTrace();
	
	public ArrayList<String> validateAlert(String nric, String message) {
		// conduct controller validation if pass call entity methods 
		if(nric.matches("^[a-zA-Z0-9]*$")&& !message.isEmpty()){
			// check if nric exist
			if(user.checkIfNricExist(nric)==false) {
				noExist.add("error");
				return noExist;
			}
			// check if nric is public or if the public user does not have existing contact tracing records
			else if(user.checkIfNricIsPublicWithContactTraceRecords(nric)==false) {
				notPublicOrNotNeeded.add("error2");
				return notPublicOrNotNeeded;
			}
			
			else {
				// get Nric of people who made close contact
				nricList = user.getNricOfCloseContacts(nric,contactTrace.getContactTraceInfoForCovidPatient(nric));
				// return condition if there are no close contacts made
				if(nricList.isEmpty()) {
					notPublicOrNotNeeded.add("error2");
					return notPublicOrNotNeeded;
				}
				// get userid for all the nrics that came into close contact
				userIdList = user.getUserIdOfCloseContacts(nricList);
				// insert into alert table
				alert.sendAlertToPublic(userIdList, message);
				
			}
			return nricList;
		}
		else {
			throw new IllegalArgumentException("Invalid input format");
		}
	}
}
