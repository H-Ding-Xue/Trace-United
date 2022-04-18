package test.traceUnited.boundaryTest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.traceUnited.boundary.UserLoginUI;

public class UserLoginUITest {
	private UserLoginUI userLoginUI;
	
	@Before
	public void setUp() throws Exception {
		this.userLoginUI = new UserLoginUI();
	}
	
	@After
	public void tearDown() throws Exception {
		userLoginUI = null;
	}

	@Test
	public void testLoginFailedWithInvalidUserId() {
		try {
			userLoginUI.isUnitTest = true;
			Thread.sleep(1000);
			userLoginUI.idField.setText("TestP00");
			Thread.sleep(1000);
			userLoginUI.passwordField.setText("PassP001");
			Thread.sleep(1000);
			userLoginUI.loginButton.doClick();
			Thread.sleep(1000);
			assertEquals("testLoginFailedWithInvalidUserName", "", userLoginUI.name);
			Thread.sleep(1000);
			userLoginUI.dispose();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLoginFailedWithInvalidPassword() {
		try {
			userLoginUI.isUnitTest = true;
			Thread.sleep(1000);
			userLoginUI.idField.setText("TestP01");
			Thread.sleep(1000);
			userLoginUI.passwordField.setText("PassP000");
			Thread.sleep(1000);
			userLoginUI.loginButton.doClick();
			Thread.sleep(1000);
			assertEquals("testLoginFailedWithInvalidPassword", "", userLoginUI.name);
			Thread.sleep(1000);
			userLoginUI.dispose();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLoginFailedWithEmptyUserId() {
		try {
			userLoginUI.isUnitTest = true;
			Thread.sleep(1000);
			userLoginUI.idField.setText("");
			Thread.sleep(1000);
			userLoginUI.passwordField.setText("PassP001");
			Thread.sleep(1000);
			userLoginUI.loginButton.doClick();
			Thread.sleep(1000);
			assertEquals("testLoginFailedWithEmptyUserName", null, userLoginUI.name);
			Thread.sleep(1000);
			userLoginUI.dispose();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLoginFailedWithEmptyPassword() {
		try {
			userLoginUI.isUnitTest = true;
			Thread.sleep(1000);
			userLoginUI.idField.setText("TestP01");
			Thread.sleep(1000);
			userLoginUI.passwordField.setText("");
			Thread.sleep(1000);
			userLoginUI.loginButton.doClick();
			Thread.sleep(1000);
			assertEquals("testLoginFailedWithEmptyPassword", null, userLoginUI.name);
			Thread.sleep(1000);
			userLoginUI.dispose();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLoginSuccess() {
		try {
			userLoginUI.idField.setText("TestP01");
			Thread.sleep(1000);
			userLoginUI.passwordField.setText("PassP001");
			Thread.sleep(1000);
			userLoginUI.loginButton.doClick();
			Thread.sleep(1000);
			assertEquals("testLoginSuccess", "Tester Jon", userLoginUI.name);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
