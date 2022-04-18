package main.traceUnited.boundary;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import main.traceUnited.controller.CreateUserController;
import main.traceUnited.controller.UserLoginController;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CreateUserUI extends JFrame{
	private JPanel createUserPanel = new JPanel();
	private JPanel labelPanel = new JPanel();
	private JPanel fieldPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JPanel topPanel = new JPanel();
	
	private Border createUserPanelPadding = BorderFactory.createEmptyBorder(0, 20, 20, 20);
	
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
	
	private JButton createButton= new JButton("Create");
	private JButton backButton= new JButton("Back");
	
	private String userName;
	
	private CreateUserController createUserController = new CreateUserController();
	
	public CreateUserUI(String name) {
		super("Create User UI");
		
		userName = name;
		
		getContentPane().add(createUserPanel);
		
		createUserPanel.setLayout(new BorderLayout());
		createUserPanel.setBorder(createUserPanelPadding);
		createUserPanel.add(topPanel, BorderLayout.NORTH);
		createUserPanel.add(labelPanel, BorderLayout.WEST);
		createUserPanel.add(fieldPanel, BorderLayout.CENTER);
		createUserPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(backButton);
		
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
		
		fieldPanel.setLayout(new GridLayout(9,1));
		
		userTypeComboBox.addItem("Public");
		userTypeComboBox.addItem("Health Staff");
		userTypeComboBox.addItem("Health Organisation");
		userTypeComboBox.addItem("Business");
		
		UserTypeChange userTypeChange = new UserTypeChange();
		userTypeComboBox.addItemListener(userTypeChange);
		
		fieldPanel.add(userTypeComboBox);
		fieldPanel.add(userIDTextField);
		fieldPanel.add(passwordTextField);
		fieldPanel.add(nricTextField);
		fieldPanel.add(nameTextField);
		fieldPanel.add(phoneNumberTextField);
		fieldPanel.add(emailAddressTextField);
		fieldPanel.add(businessNameTextField);
		fieldPanel.add(businessAddressTextField);
		
		businessNameTextField.setEnabled(false);
		businessAddressTextField.setEnabled(false);
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(createButton);
		
		OnSubmit onSubmit = new OnSubmit();
		createButton.addActionListener(onSubmit);
		
		Back back = new Back();
		backButton.addActionListener(back);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,400);
		this.setVisible(true);
	}
	
	private class OnSubmit implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String userType = String.valueOf(userTypeComboBox.getSelectedItem());
				String userID = userIDTextField.getText();
				String password = passwordTextField.getText();
				String nric = nricTextField.getText();
				String name = nameTextField.getText();
				String phoneNumber = phoneNumberTextField.getText();
				String emailAddress = emailAddressTextField.getText();
				String businessName = businessNameTextField.getText();
				String businessAddress = businessAddressTextField.getText();
				
				if(validateUI(userID, password, nric, name)) {
					if(createUserController.validateCreate(userID, password, userType, nric, name, phoneNumber, emailAddress, businessName, businessAddress)) {
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
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "User Creation failed", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private class UserTypeChange implements ItemListener{
		public void itemStateChanged(ItemEvent e){
            System.out.println(e.getItem() + " " + e.getStateChange() );
            
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
	
	private boolean validateUI(String userID, String password, String nric, String name) {
		return (userID != null && userID.length()>0 && password != null && password.length()>0 && nric != null && nric.length()>0 && name != null && name.length()>0);
	}
	
	private class Back implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new HealthOrganisationUI(userName);
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private void onSuccess() {
		JOptionPane.showMessageDialog(null, "User Created successful", "User Created successful", JOptionPane.WARNING_MESSAGE);
	}
	
	private void onFailure() {
		JOptionPane.showMessageDialog(null, "User Creation failed", "User Creation failed", JOptionPane.WARNING_MESSAGE);
	}
}
