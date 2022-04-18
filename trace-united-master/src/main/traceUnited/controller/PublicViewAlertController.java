package main.traceUnited.controller;

import java.sql.ResultSet;

import main.traceUnited.entity.Alert;

public class PublicViewAlertController {
	private Alert pubAlert = new Alert();
	
	public boolean updateAlertAcknowledgement(String isAcknowledged, String acknowledgeDateTime, String id) {
		return pubAlert.updateAlert(isAcknowledged, acknowledgeDateTime, id);
	}
	public ResultSet displayPublicAlertAcknowledgement(String userID) {
		return pubAlert.displayPublicAlert(userID);
	}
}
