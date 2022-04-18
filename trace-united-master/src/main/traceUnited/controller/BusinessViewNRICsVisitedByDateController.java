package main.traceUnited.controller;

import java.sql.ResultSet;
import java.util.ArrayList;

import main.traceUnited.entity.Business;
import main.traceUnited.entity.ContactTrace;
import main.traceUnited.entity.User;

public class BusinessViewNRICsVisitedByDateController {
	private ContactTrace _contTrace = new ContactTrace(); 
	private User _user = new User();
	private Business _business = new Business();
	
	public ResultSet validateRetrieveLocation (String userID) {
		return _business.businessRetrieveLocation(userID);
	}
	
	public ArrayList<String> validateRetrieveNric(String checkinDate, String BusinessAddress) {
		ArrayList<String> userIDs = _contTrace.businessRetrieveUserIDs(checkinDate, BusinessAddress);
		if(userIDs != null && userIDs.size()>0) {
			return _user.retrieveNRICsFromUserIDs(userIDs, "Public");
		}
		return null;
	}
}
