package main.traceUnited.boundary;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import main.traceUnited.controller.UpdateUserController;


public class UpdateUserUI extends JFrame{
	private JPanel updateUserPanel = new JPanel();
	private JPanel labelPanel = new JPanel();
	private JPanel fieldPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	private Border createUserPanelPadding = BorderFactory.createEmptyBorder(20, 20, 20, 20);

	private JLabel userTypeLabel = new JLabel("User Type:");
	private JLabel userIDLabel = new JLabel("User ID:");
	private JLabel passwordLabel = new JLabel("Password:");
	private JLabel nricLabel = new JLabel("NRIC:");
	private JLabel nameLabel = new JLabel("Name:");
	private JLabel phoneNumberLabel = new JLabel("Phone Number:");
	private JLabel emailAddressLabel = new JLabel("Email:");
	private JLabel businessNameLabel = new JLabel("Business Name:");
	private JLabel businessAddressLabel = new JLabel("Business Address:");
	
	private JTextField userIDTextField = new JTextField(20);
	private JPasswordField passwordTextField = new JPasswordField(20);
	private JTextField nricTextField = new JTextField(20);
	private JTextField nameTextField = new JTextField(20);
	private JTextField phoneNumberTextField = new JTextField(20);
	private JTextField emailAddressTextField = new JTextField(20);
	private JTextField businessNameTextField = new JTextField(20);
	private JTextField businessAddressTextField = new JTextField(20);
	
	private JComboBox userTypeComboBox = new JComboBox();
	
	private JButton retrieveButton= new JButton("Retrieve");
	private final JButton updateButton = new JButton("Update");

	private JButton backButton = new JButton("Back");
	private String userName;
	
	private UpdateUserController UpdateUserController = new UpdateUserController();
	
	public UpdateUserUI(String name) {
		super("Update User UI");
		
		userName = name;
		
		getContentPane().add(updateUserPanel);
		updateUserPanel.setBorder(createUserPanelPadding);
		updateUserPanel.setLayout(null);
		labelPanel.setBounds(20, 42, 93, 276);
		updateUserPanel.add(labelPanel);
		fieldPanel.setBounds(110, 42, 454, 276);
		updateUserPanel.add(fieldPanel);
		buttonPanel.setBounds(20, 317, 544, 33);
		updateUserPanel.add(buttonPanel);
		
		labelPanel.setLayout(new GridLayout(9,1));
		labelPanel.add(userTypeLabel);
		labelPanel.add(userIDLabel);
		labelPanel.add(passwordLabel);
		labelPanel.add(nricLabel);
		labelPanel.add(nameLabel);
		labelPanel.add(phoneNumberLabel);
		labelPanel.add(emailAddressLabel);
		labelPanel.add(businessNameLabel);
		labelPanel.add(businessAddressLabel);
		fieldPanel.setLayout(null);
		userIDTextField.setBounds(0, 33, 454, 30);
		fieldPanel.add(userIDTextField);
		passwordTextField.setBounds(0, 63, 454, 30);
		fieldPanel.add(passwordTextField);
		nricTextField.setBounds(0, 93, 454, 30);
		fieldPanel.add(nricTextField);
		nameTextField.setBounds(0, 123, 454, 30);
		fieldPanel.add(nameTextField);
		phoneNumberTextField.setBounds(0, 153, 454, 30);
		fieldPanel.add(phoneNumberTextField);
		emailAddressTextField.setBounds(0, 183, 454, 30);
		fieldPanel.add(emailAddressTextField);
		businessNameTextField.setBounds(0, 213, 454, 30);
		fieldPanel.add(businessNameTextField);
		businessAddressTextField.setBounds(0, 243, 454, 30);
		fieldPanel.add(businessAddressTextField);
		
		userTypeComboBox.addItem("Public");
		userTypeComboBox.addItem("Health Staff");
		userTypeComboBox.addItem("Health Organisation");
		userTypeComboBox.addItem("Business");
		UserTypeChange userTypeChange = new UserTypeChange();
		userTypeComboBox.addItemListener(userTypeChange);
		userTypeComboBox.setBounds(-3, 0, 457, 36);
		fieldPanel.add(userTypeComboBox);
		
		businessNameTextField.setEnabled(false);
		businessAddressTextField.setEnabled(false);
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		buttonPanel.add(updateButton);
		buttonPanel.add(retrieveButton);
		
		JPanel topPanel = new JPanel();
		topPanel.setBounds(20, 0, 544, 44);
		updateUserPanel.add(topPanel);
		topPanel.setLayout(null);
		
		backButton.setBounds(10, 11, 89, 23);
		topPanel.add(backButton);
		
		OnBack onBack = new OnBack();
		backButton.addActionListener(onBack);
		
		OnSubmit onSubmit = new OnSubmit();
		retrieveButton.addActionListener(onSubmit);
		
		OnUpdate onUpdate = new OnUpdate();
		updateButton.addActionListener(onUpdate);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,400);
		this.setVisible(true);
	}
	private class OnBack implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			dispose();
			new HealthOrganisationUI(userName);
		}
		
	}

	private class OnSubmit implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
				try {
					String userType = String.valueOf(userTypeComboBox.getSelectedItem());

					if(userType != "Business") {
						
						String userID = userIDTextField.getText();
						
						ResultSet result = UpdateUserController.validateRetrieve(userID, userType);
						
						if(result.next() == false) {
							JOptionPane.showMessageDialog(null, "UserID is invalid", "Invalid UserID", JOptionPane.WARNING_MESSAGE);
			                
						}else {
							
			                    userIDTextField.setText(result.getString("UserID"));
			                    passwordTextField.setText(result.getString("Password"));
			                    nricTextField.setText(result.getString("NRIC"));
			                    nameTextField.setText(result.getString("Name"));
			                    phoneNumberTextField.setText(result.getString("PhoneNumber"));
			                    emailAddressTextField.setText(result.getString("EmailAddress"));  
						
						}
					}
					else {

						String userID = userIDTextField.getText();
						
						ResultSet result = UpdateUserController.validateRetrieveBusiness(userID);
						if(result.next() == false) {
							JOptionPane.showMessageDialog(null, "UserID is invalid", "Invalid UserID", JOptionPane.WARNING_MESSAGE);
						}else {
			
			                    userIDTextField.setText(result.getString("UserID"));
			                    passwordTextField.setText(result.getString("Password"));
			                    nricTextField.setText(result.getString("NRIC"));
			                    nameTextField.setText(result.getString("Name"));
			                    phoneNumberTextField.setText(result.getString("PhoneNumber"));
			                    emailAddressTextField.setText(result.getString("EmailAddress"));
			                    businessNameTextField.setText(result.getString("BusinessName"));
			                    businessAddressTextField.setText(result.getString("Businessaddress"));
			                
						}
		               
					}
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Retrieve failed", JOptionPane.WARNING_MESSAGE);
				}
			} 
		}

	private class OnUpdate implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String userType = String.valueOf(userTypeComboBox.getSelectedItem());

				if(userType != "Business") {
					UpdateUserController UpdateUserController = new UpdateUserController();
					String userID = userIDTextField.getText();
					String password = passwordTextField.getText();
					String nric = nricTextField.getText();
					String name = nameTextField.getText();
					String phoneNumber = phoneNumberTextField.getText();
					String emailAddress = emailAddressTextField.getText();
					
					if(validateUI(userID, password, nric, name)) {
						if(UpdateUserController.validateUpdate(userID, password, nric, name, phoneNumber, emailAddress)) {
							onSuccess();
						}
						else {
							onFailure();
						}
					}
					else {
						onFailure();
					}
				}
				else {
					UpdateUserController UpdateUserController = new UpdateUserController();
					String userID = userIDTextField.getText();
					String password = passwordTextField.getText();
					String nric = nricTextField.getText();
					String name = nameTextField.getText();
					String phoneNumber = phoneNumberTextField.getText();
					String emailAddress = emailAddressTextField.getText();
					String businessName = businessNameTextField.getText();
                    String businessAddress = businessAddressTextField.getText();
					
                    if(validateUI(userID, password, nric, name)) {
						if(UpdateUserController.validateUpdateBusiness(userID, password, nric, name, phoneNumber, emailAddress, businessName, businessAddress)) {
							onSuccess();
						}
						else {
							onFailure();
						}
                    }
					else {
						onFailure();
					}
				}
				
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Update failed", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private boolean validateUI(String userID, String password, String nric, String name) {
		return (userID != null && userID.length()>0 && password != null && password.length()>0 && nric != null && nric.length()>0 && name != null && name.length()>0);
	}
	
	private void onSuccess() {
		JOptionPane.showMessageDialog(null, "Information updated successfully", "Information updated successfully", JOptionPane.WARNING_MESSAGE);
	}
	
	private void onFailure() {
		JOptionPane.showMessageDialog(null, "Update failed", "Update failed", JOptionPane.WARNING_MESSAGE);
	}
	
	private class UserTypeChange implements ItemListener{
		public void itemStateChanged(ItemEvent e){
//            System.out.println(e.getItem() + " " + e.getStateChange() );
            
            if(e.getStateChange() == 1) {
            	if(e.getItem() == "Business") {
            		businessNameTextField.setEnabled(true);
            		businessAddressTextField.setEnabled(true);
            	}
            	else {
            		businessNameTextField.setEnabled(false);
            		businessNameTextField.setText("");
            		businessAddressTextField.setEnabled(false);
            		businessAddressTextField.setText("");
            	}
            }
        }
	}
}
