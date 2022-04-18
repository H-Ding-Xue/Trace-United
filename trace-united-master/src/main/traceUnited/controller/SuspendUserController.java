package main.traceUnited.controller;

import main.traceUnited.entity.User;

public class SuspendUserController {
	private User _user = new User();
	
	public boolean validateSuspend(String userId) {
		if(userId.matches("^[a-zA-Z0-9]*$"))
			return _user.suspend(userId);
		else {
			throw new IllegalArgumentException("Invalid input format");
		}
	}
}
