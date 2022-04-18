package main.traceUnited.controller;

import java.sql.ResultSet;

import main.traceUnited.entity.ContactTrace;

public class PublicViewContactTracesController {
	private ContactTrace contactTrace = new ContactTrace();
	
	public ResultSet retrieveContactTraces(String userID) {
		return contactTrace.viewContactTraces(userID);
	}
}
