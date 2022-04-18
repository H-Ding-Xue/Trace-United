package main.traceUnited.boundary;

import java.awt.BorderLayout;
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
import javax.swing.border.Border;

public class BusinessUI extends JFrame {
	private JPanel mainPanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel logoutPanel = new JPanel();
	private JPanel viewAlertButtonPanel = new JPanel();
	private JPanel contractTracingLabelPanel = new JPanel();
	private JPanel viewContactTracingButtonPanel = new JPanel();
	
	private Border mainPanelPadding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
	private Border topPanelPadding = BorderFactory.createEmptyBorder(10, 20, 0, 20);
	
	private JLabel nameLabel = new JLabel("");
	private JLabel emptyLabel = new JLabel("");
	
	private JLabel viewAlertLabel = new JLabel("To view alert, please click the button below:");
	private JLabel contractTracingLabel = new JLabel("<html>To view the historical data of visited NRICs by date, click the view contact traces button below:</html>");
	
	private JButton logoutButton = new JButton("Logout");
	private JButton viewAlertButton = new JButton("View Alert");
	private JButton viewContactTracingButton = new JButton("View Visited NRICs");
	
	private String userName;
	private String currentIdField;
	
	public BusinessUI(String userId, String name){
		super("Business UI");
		
		currentIdField = userId;
		userName = name;
		
		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		
		topPanel.setLayout(new GridLayout(1,2));
		topPanel.setBorder(topPanelPadding);
		topPanel.add(nameLabel);
		nameLabel.setText("Hi, " + name);
		
		logoutPanel.setLayout(new GridLayout(1,2));
		logoutPanel.add(emptyLabel);
		logoutPanel.add(logoutButton);
		topPanel.add(logoutPanel);

		mainPanel.setLayout(new GridLayout(4,1));
		mainPanel.setBorder(mainPanelPadding);
		mainPanel.add(viewAlertLabel);
		viewAlertButtonPanel.add(viewAlertButton);
		mainPanel.add(viewAlertButtonPanel);
		mainPanel.add(contractTracingLabel);
		viewContactTracingButtonPanel.add(viewContactTracingButton);
		mainPanel.add(viewContactTracingButtonPanel);
		
		OnLogout onLogout = new OnLogout();
		logoutButton.addActionListener(onLogout);
		
		OnClick onContactPg = new OnClick();
		viewContactTracingButton.addActionListener(onContactPg);
		
		OnViewAlert onViewAlert = new OnViewAlert();
		viewAlertButton.addActionListener(onViewAlert);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,400);
		this.setVisible(true);
	}
	
	private class OnClick implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new BusinessViewNRICsVisitedByDateUI(currentIdField, userName);
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private class OnViewAlert implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new BusinessViewAlertUI(currentIdField, userName);
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
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
}
