package main.traceUnited.controller;

import java.util.ArrayList;

import main.traceUnited.entity.Business;
import main.traceUnited.entity.PublicUser;
import main.traceUnited.entity.Alert;

public class HealthOrganisationReportController {
	// Declare instances of Business and PublicUser Classes
	Business business = new Business();
	PublicUser publicUser = new PublicUser();
	Alert alert = new Alert();
	
	public ArrayList<Double> getPercentages() {
		// call the entity methods which return doubles which are then assigned to the arraylist that is returned
		ArrayList<Double> percentages = new ArrayList<Double>();
		
		double totalPublicUsersCount = publicUser.retrieveTotalPublicUsersCount();
		double totalVaccinatedPublicUsersCount = publicUser.retrieveTotalVaccinatedPublicUsersCount();
		
		//calculate percentage
		double percentageOfVaccinatedPopulation = totalVaccinatedPublicUsersCount/totalPublicUsersCount*100;
		
		double totalBusinessesCount = business.retrieveTotalBusinessesCount();
		double totalBusinessesVisitedByPatientCount = alert.retrieveTotalBusinessesVisitedByPatientCount();
		
		//calculate percentage
		double percentageOfAffectedBusinesses = totalBusinessesVisitedByPatientCount/totalBusinessesCount*100;
		
		percentages.add(percentageOfVaccinatedPopulation);
		percentages.add(percentageOfAffectedBusinesses);
		return percentages;
	}
}
