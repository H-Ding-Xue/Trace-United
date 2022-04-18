package main.traceUnited.controller;

import main.traceUnited.entity.User;

public class UserLoginController {
	private User _user = new User();
	
	public String validateLogin(String userType, String userId, String password) {
		if(userId.matches("^[a-zA-Z0-9]*$"))
			return _user.login(userType, userId, password);
		else {
			return "";
		}
	}
}
