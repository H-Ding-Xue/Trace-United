package main.traceUnited.boundary;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class HealthStaffUI extends JFrame{
	private JButton logoutButton = new JButton("Logout");
	private JButton TraceButton = new JButton("View Public Contact Traces");
	private JLabel HiLabel = new JLabel("Hi,");
	private JLabel NameLabel = new JLabel("");
	private JButton AlertPublicButton = new JButton("Send Alert To Public");
	private JButton AlertBusinessButton = new JButton("Send Alert To Business");
	private JLabel VStatusLabel = new JLabel("<html> To view the historical data of public user check - in/out records, click the view public contact traces button below <html>");
	private JButton VStatusButton = new JButton("View/Update Vaccine Status");
	private String currentUserId;
	private String userName;

	public HealthStaffUI(String userId, String name){
		super("Health Staff UI");
		currentUserId = userId;
		userName = name;
		getContentPane().setLayout(null);
		logoutButton.setBounds(291, 11, 83, 23);
		getContentPane().add(logoutButton);
		TraceButton.setBounds(76, 112, 240, 32);
		getContentPane().add(TraceButton);
		HiLabel.setBounds(10, 15, 22, 14);
		getContentPane().add(HiLabel);
		NameLabel.setBounds(25, 15, 103, 14);
		getContentPane().add(NameLabel);
		NameLabel.setText(userName);
		AlertPublicButton.setBounds(10, 208, 173, 32);
		getContentPane().add(AlertPublicButton);
		OnLogout onLogout = new OnLogout();
		logoutButton.addActionListener(onLogout);
		OnViewUpdateData onViewUpdateData = new OnViewUpdateData();
		VStatusButton.addActionListener(onViewUpdateData);
		ViewContactTraces viewContactTraces = new ViewContactTraces();
		TraceButton.addActionListener(viewContactTraces);
		SendAlertToBusiness sendAlertToBusiness = new SendAlertToBusiness();
		AlertBusinessButton.addActionListener(sendAlertToBusiness);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400,500);
		this.setVisible(true);
		VStatusLabel.setBounds(27, 55, 333, 55);
		getContentPane().add(VStatusLabel);
		JLabel lblToSendA = new JLabel("<html> To send a alert to a member of public who has come accross a covid-19 patient, please click the button below<html>");
		lblToSendA.setBounds(25, 155, 333, 55);
		getContentPane().add(lblToSendA);
		JLabel lblToViewOr = new JLabel("<html> To view or update a patient's vaccination certification status, please click the button below<html>");
		lblToViewOr.setBounds(25, 251, 333, 55);
		getContentPane().add(lblToViewOr);
		VStatusButton.setBounds(76, 308, 240, 32);
		getContentPane().add(VStatusButton);
		AlertBusinessButton.setBounds(191, 208, 183, 32);
		getContentPane().add(AlertBusinessButton);
		SendAlertToPublic sendAlertToPublic = new SendAlertToPublic();
		AlertPublicButton.addActionListener(sendAlertToPublic);
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

	private class OnViewUpdateData implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				dispose();
				new HealthStaffViewAndUpdateVaccinationStatusUI(currentUserId, userName);
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private class ViewContactTraces implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new HealthStaffViewPublicUserContactTracesUI(currentUserId, userName);
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private class SendAlertToBusiness implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new HealthStaffSendAlertToBusinessUI(currentUserId, userName);
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private class SendAlertToPublic implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new HealthStaffSendAlertToPublicUI(currentUserId, userName);
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
