package main.traceUnited.boundary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import main.traceUnited.controller.SuspendUserController;

public class SuspendUserUI extends JFrame{
	private JPanel mainPanel = new JPanel();
	private JPanel suspendUserPanel = new JPanel();
	private JPanel labelPanel = new JPanel();
	private JPanel fieldPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JPanel topPanel = new JPanel();
	
	private JLabel userIDLabel = new JLabel("User ID:");
	
	private JTextField userIDTextField = new JTextField(20);
	
	private Border mainPanelPadding = BorderFactory.createEmptyBorder(0, 20, 20, 20);
	private Border suspendUserPanelPadding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
	
	private JButton suspendButton= new JButton("Suspend");
	private JButton backButton= new JButton("Back");
	
	private String userName;
    private SuspendUserController suspendUserController = new SuspendUserController();
    
	public SuspendUserUI(String name) {
		super("Suspend User UI");
		
		userName = name;
		
		add(mainPanel);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(suspendUserPanel, BorderLayout.CENTER);
		mainPanel.setBorder(mainPanelPadding);
		
		suspendUserPanel.setBorder(suspendUserPanelPadding);
		suspendUserPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(backButton);
		
		suspendUserPanel.add(labelPanel);
		suspendUserPanel.add(fieldPanel);
		suspendUserPanel.add(buttonPanel);
		
		labelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		labelPanel.add(userIDLabel);
		
		fieldPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		fieldPanel.add(userIDTextField);
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(suspendButton);
		
		OnSubmit onSubmit = new OnSubmit();
		suspendButton.addActionListener(onSubmit);
		
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
				String userID = userIDTextField.getText();
				if(validateUI(userID)){
					
					if(suspendUserController.validateSuspend(userID)) {
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
				JOptionPane.showMessageDialog(null, ex.getMessage(), "User suspension failed", JOptionPane.WARNING_MESSAGE);
			}
		}
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
	
	private boolean validateUI(String userID) {
		return (userID != null && userID.length()>0);
	}
	
	private void onSuccess() {
		JOptionPane.showMessageDialog(null, "User suspended successfully", "User suspended successfully", JOptionPane.WARNING_MESSAGE);
	}
	
	private void onFailure() {
		JOptionPane.showMessageDialog(null, "User suspension failed", "User suspension failed", JOptionPane.WARNING_MESSAGE);
	}
}
