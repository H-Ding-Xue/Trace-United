package main.traceUnited.controller;

import java.sql.ResultSet;

import main.traceUnited.entity.*;

public class BusinessViewAlertController {
	private Business business = new Business();
	private Alert alert = new Alert();
	
	public ResultSet getBusinessAlert(String userID) {
		return business.getBusinessAlert(userID);
	}
	
	public boolean updateBusinessAlertAcknowledgement(String isAcknowledged, String acknowledgeDateTime, String id) {
		return alert.updateAlert(isAcknowledged, acknowledgeDateTime, id);
	}
}
