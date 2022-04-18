package main.traceUnited.boundary;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import main.traceUnited.boundary.*;
import main.traceUnited.controller.*;

public class PublicViewVaccinationCertificateUI extends JFrame{
	private JTextField nameTextField;
	private JTextField userIdTextField;
	private JTextField statusTextField;
	
	public static final Color DARK_GREEN = new Color(0,153,0);
	//label naming conventions 
	private JLabel nameLabelUI, userIdLabelUI, statusLabelUI;
	
	// use to store the data taken from sql statement
	private String labelIdUI, labelNameUI, labelStatusUI;
	
	private PublicViewVaccinationCertificateController publicViewVaccinationCertificateController = new PublicViewVaccinationCertificateController();
	
	public PublicViewVaccinationCertificateUI(String currentIdField) {
		super("Public View Vaccination Certificate UI");	
		
		labelIdUI = currentIdField;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(485,484);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Certificate for Covid-19 Vaccination");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(112, 11, 237, 57);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Beneficiary Details");
		lblNewLabel_1.setBounds(68, 89, 127, 38);
		getContentPane().add(lblNewLabel_1);
		
		JLabel nameLabel = new JLabel("Name : ");
		nameLabel.setBounds(164, 138, 62, 31);
		getContentPane().add(nameLabel);
		
		JLabel userIDLabel = new JLabel("User ID : ");
		userIDLabel.setBounds(164, 186, 62, 31);
		getContentPane().add(userIDLabel);
		
		JLabel lblNewLabel_4 = new JLabel("Vaccination Details");
		lblNewLabel_4.setBounds(68, 245, 127, 38);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Vaccinated Status :");
		lblNewLabel_5.setBounds(112, 294, 112, 31);
		getContentPane().add(lblNewLabel_5);
		
		
		JButton closeBtn = new JButton("Close");
		closeBtn.setBounds(180, 387, 89, 23);		
		getContentPane().add(closeBtn);
		
		nameLabelUI = new JLabel("<dynamic>");
		nameLabelUI.setFont(new Font("Arial", Font.PLAIN, 12));
		nameLabelUI.setBounds(235, 138, 86, 31);
		getContentPane().add(nameLabelUI);
		
		userIdLabelUI = new JLabel(currentIdField);
		userIdLabelUI.setFont(new Font("Arial", Font.PLAIN, 12));
		userIdLabelUI.setBounds(236, 186, 85, 31);
		getContentPane().add(userIdLabelUI);
		
		statusLabelUI = new JLabel("<dynamic>");
		statusLabelUI.setBounds(234, 298, 87, 23);
		getContentPane().add(statusLabelUI);

		
		CloseBtn onCloseBtn = new CloseBtn();
		closeBtn.addActionListener(onCloseBtn);

		displayCert();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private class CloseBtn implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	
	private void displayCert() {
		
		ResultSet rs = publicViewVaccinationCertificateController.userGetInfo(userIdLabelUI.getText());
		
		try {
			while(rs.next()) {
				labelIdUI = rs.getString(1);
				labelNameUI = rs.getString(2);
				labelStatusUI = rs.getString(3);
				
				userIdLabelUI.setText(labelIdUI);
				nameLabelUI.setText(labelNameUI);			
				if (Integer.parseInt(labelStatusUI) == 0) {
					statusLabelUI.setText("No");
					statusLabelUI.setForeground(Color.red);
					
				}
				else {
					statusLabelUI.setForeground(DARK_GREEN);
					statusLabelUI.setText("Yes");
				}
				
			}
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
}
