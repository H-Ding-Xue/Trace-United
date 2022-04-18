package main.traceUnited.boundary;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import main.traceUnited.controller.*;

public class PublicUserUI extends JFrame{
	private JButton logoutButton = new JButton("Logout");
	private JButton viewVaccinationCertificateButton = new JButton("View Vaccination Certificate");
	private JLabel idLabel = new JLabel("Hi, ");
	private JLabel idField = new JLabel("New label");
	private String currentIdField;
	private String userName;

	private final JButton viewAlertBtn = new JButton("View Alert");
	private final JButton checkInBtn = new JButton("Check In");
	private final JButton checkOutBtn = new JButton("Check Out");
	private final JButton btnContactTracing = new JButton("View Contact Traces");


	private final JLabel contactTraceLabel = new JLabel("<html>To view the historical data of your check - in/out records, click the view contact traces button below</html>", SwingConstants.CENTER);
	private final JLabel viewStatusLabel = new JLabel("<html>To view personal vaccination certification, Please click the button below</html>");
	private final JLabel checkOutLabel = new JLabel("To SafeEntry Check - Out, Please click the button below");
	private final JLabel checkInLabel = new JLabel("To SafeEntry Check - In, Please click the button below");
	private final JLabel lblToViewAlert = new JLabel("To view alert, Please click the button below");

	public PublicUserUI(String userId, String name){
		super("Public User UI");

		getContentPane().setLayout(null);
		logoutButton.setBounds(474, 7, 95, 23);
		getContentPane().add(logoutButton);
		viewVaccinationCertificateButton.setBounds(182, 430, 199, 38);
		getContentPane().add(viewVaccinationCertificateButton);
		idLabel.setBounds(29, 9, 21, 19);
		getContentPane().add(idLabel);



		// displaying current user 
		idField.setBounds(48, 7, 122, 23);		
		currentIdField = userId;
		userName = name;
		idField.setText(userName);
		getContentPane().add(idField);
		viewAlertBtn.setBounds(231, 68, 95, 38);

		getContentPane().add(viewAlertBtn);
		checkInBtn.setBounds(231, 151, 95, 38);

		getContentPane().add(checkInBtn);
		checkOutBtn.setBounds(231, 234, 95, 38);

		getContentPane().add(checkOutBtn);
		btnContactTracing.setBounds(182, 339, 199, 38);

		getContentPane().add(btnContactTracing);
		contactTraceLabel.setBounds(91, 283, 401, 45);

		getContentPane().add(contactTraceLabel);
		viewStatusLabel.setBounds(91, 388, 429, 31);

		getContentPane().add(viewStatusLabel);
		checkOutLabel.setBounds(139, 200, 334, 23);

		getContentPane().add(checkOutLabel);
		checkInLabel.setBounds(129, 117, 321, 23);

		getContentPane().add(checkInLabel);
		lblToViewAlert.setBounds(139, 34, 311, 23);

		getContentPane().add(lblToViewAlert);

		OnLogout onLogout = new OnLogout();
		logoutButton.addActionListener(onLogout);
		
		OnViewAlert onViewAlert = new OnViewAlert();
		viewAlertBtn.addActionListener(onViewAlert);
		
		OnDisplayCert onDisplayCert = new OnDisplayCert();
		viewVaccinationCertificateButton.addActionListener(onDisplayCert);

		OnContactTracesButton onContactTracesButton = new OnContactTracesButton();
		btnContactTracing.addActionListener(onContactTracesButton);

		OnCheckInBtn onCheckInBtn = new OnCheckInBtn();
		checkInBtn.addActionListener(onCheckInBtn);

		OnCheckOutBtn onCheckoutBtn = new OnCheckOutBtn();
		checkOutBtn.addActionListener(onCheckoutBtn);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(595,549);
		this.setVisible(true);
	}
	
	public class OnViewAlert implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			new PublicViewAlertUI(currentIdField, userName);
		}
		
	}
	public class OnCheckInBtn implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			new PublicCheckInUI(currentIdField, userName);
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
	private class OnDisplayCert implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			new PublicViewVaccinationCertificateUI(currentIdField);
		}
	}

	private class OnContactTracesButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			new PublicViewContactTracesUI(currentIdField, userName);
		}
	}
	
	public class OnCheckOutBtn implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			new PublicCheckOutUI(currentIdField, userName);
		}		
	}
}
