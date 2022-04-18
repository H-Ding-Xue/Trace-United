package main.traceUnited.boundary;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.BorderLayout;

public class HealthOrganisationUI extends JFrame{
	private JButton logoutButton = new JButton("Logout");
	
	private JPanel mainPanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel logoutPanel = new JPanel();
	private JPanel manageUserButtonTextPanel = new JPanel();
	private JPanel manageUserButtonPanel = new JPanel();
	private JPanel reportButtonTextPanel = new JPanel();
	private JPanel reportButtonPanel = new JPanel();
	
	private Border healthOrganisationPanelPadding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
	private Border topPanelPadding = BorderFactory.createEmptyBorder(10, 20, 0, 20);	
	
	private JLabel nameLabel = new JLabel("");
	private JLabel emptyLabel = new JLabel("");
	private JLabel manageUserLabel = new JLabel("To create, update or suspend user accounts, please click the buttons below:");
	private JLabel reportLabel = new JLabel("To view the reports and statistics, please click the view report button below:");
	
	private JButton createUserButton = new JButton("Create User");
	private JButton updateUserButton = new JButton("Update User");
	private JButton suspendUserButton = new JButton("Suspend User");
	private JButton reportsButton = new JButton("View Reports");
	
	private String userName;
	
	public HealthOrganisationUI(String name){
		super("Health Organisation UI");
		userName = name;
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.setLayout(new GridLayout(4,1));
		mainPanel.setBorder(healthOrganisationPanelPadding);
		
		topPanel.setLayout(new GridLayout(1,2));
		topPanel.setBorder(topPanelPadding);
		topPanel.add(nameLabel);
		nameLabel.setText("Hi, " + name);
		
		logoutPanel.setLayout(new GridLayout(1,2));
		logoutPanel.add(emptyLabel);
		logoutPanel.add(logoutButton);
		topPanel.add(logoutPanel);
		
		manageUserButtonTextPanel.add(manageUserLabel);
		manageUserButtonPanel.setLayout(new FlowLayout());
		manageUserButtonPanel.add(createUserButton);
		manageUserButtonPanel.add(updateUserButton);
		manageUserButtonPanel.add(suspendUserButton);
		reportButtonTextPanel.add(reportLabel);
		reportButtonPanel.add(reportsButton);
		
		mainPanel.add(manageUserButtonTextPanel);
		mainPanel.add(manageUserButtonPanel);
		mainPanel.add(reportButtonTextPanel);
		mainPanel.add(reportButtonPanel);
		
		OnLogout onLogout = new OnLogout();
		logoutButton.addActionListener(onLogout);
		
		OnCreateButton onCreateButton = new OnCreateButton();
		createUserButton.addActionListener(onCreateButton);
		
		OnSuspendButton onSuspendButton = new OnSuspendButton();
		suspendUserButton.addActionListener(onSuspendButton);
		
		OnUpdateButton onUpdateButton = new OnUpdateButton();
		updateUserButton.addActionListener(onUpdateButton);
		
		OnReportButton onReportButton = new OnReportButton();
		reportsButton.addActionListener(onReportButton);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,400);
		this.setVisible(true);
	}
	
	private class OnLogout implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new UserLoginUI();
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private class OnCreateButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new CreateUserUI(userName);
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private class OnSuspendButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new SuspendUserUI(userName);
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private class OnUpdateButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new UpdateUserUI(userName);
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private class OnReportButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new HealthOrganisationReportUI(userName);
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}