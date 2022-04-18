package main.traceUnited.controller;

import java.sql.ResultSet;

import main.traceUnited.entity.*;

public class PublicCheckOutController {
	private ContactTrace contTrace = new ContactTrace();
	
	public ResultSet getBusinessAddress(String userID) {
		return contTrace.getBusinessAddress(userID);
	}
	
	public boolean updateCheckOutDate(String checkOutDate, int id, String userID, String businessAddress) {
		return contTrace.updateCheckOutDate(checkOutDate, id, userID, businessAddress);
	}
	
	public String getCheckInDate(String userID, String businessAddress) {
		return contTrace.getCheckInDate(userID, businessAddress);
	}
	
}
