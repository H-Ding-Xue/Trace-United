package main.traceUnited.controller;

import java.sql.ResultSet;

import main.traceUnited.entity.Business;
import main.traceUnited.entity.ContactTrace;

public class PublicCheckInController {
	private ContactTrace _contTrace = new ContactTrace();
	private Business _business = new Business();
	
	public ResultSet validateRetrieveLocation() {
		return _business.retrieveLocation();
	}
	
	public boolean validateCheckInDateTimeLocation(String userID, String concatenatedString, String selectedAddress) {
		return _contTrace.checkInDateTimeLocation(userID, concatenatedString, selectedAddress);
	}
}
