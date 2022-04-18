package test.traceUnited;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import test.traceUnited.boundaryTest.*;

public class TestRunner {
	public static void main(String[] args) {
		Result userLoginUITestResult = JUnitCore.runClasses(UserLoginUITest.class);
		
		System.out.println("Is the UserLoginController test successful?: " + userLoginUITestResult.wasSuccessful());
		
		for(Failure failure : userLoginUITestResult.getFailures()) {
			System.out.println("Failure: " + failure.toString());
		}
	}
}
