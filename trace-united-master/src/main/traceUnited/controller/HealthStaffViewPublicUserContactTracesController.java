package main.traceUnited.controller;

import java.sql.ResultSet;

import main.traceUnited.entity.ContactTrace;
import main.traceUnited.entity.User;

public class HealthStaffViewPublicUserContactTracesController {
	private User user = new User();
	private ContactTrace contactTrace = new ContactTrace();
	
	public ResultSet retrievePublicUserContactTraces(String nric) {
		if(nric.matches("^[a-zA-Z0-9]*$")) {
			String userId = user.retrieveUserIDFromNRIC(nric, "Public");
			return contactTrace.viewContactTraces(userId);
		}
		else {
			throw new IllegalArgumentException("Invalid NRIC");
		}
	}
}
