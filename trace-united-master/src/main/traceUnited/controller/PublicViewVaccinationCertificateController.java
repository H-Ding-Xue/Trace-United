package main.traceUnited.controller;

import java.sql.ResultSet;

import main.traceUnited.entity.PublicUser;

public class PublicViewVaccinationCertificateController {
	private PublicUser pubUser = new PublicUser();
	
	public ResultSet userGetInfo(String userID) {
		return pubUser.userGetInfo(userID);
	}
}