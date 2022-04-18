package main.traceUnited.boundary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import main.traceUnited.controller.PublicCheckInController;
import main.traceUnited.controller.PublicCheckOutController;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.toedter.calendar.JDateChooser;
import com.toedter.components.JSpinField;


public class PublicCheckInUI extends JFrame {
	private JDateChooser dateChooser = new JDateChooser();
	private Calendar currCalendar = Calendar.getInstance();
	private Date myDate = new Date();
	
	SpinnerModel hoursModel = new SpinnerNumberModel(0, 0, 23, 1);
	JSpinner hoursSpinner = new JSpinner(hoursModel);

	SpinnerModel minutesModel = new SpinnerNumberModel(0, 0, 59, 1);
	JSpinner minutesSpinner = new JSpinner(minutesModel);
	
	SpinnerModel secondsModel = new SpinnerNumberModel(0, 0, 59, 1);
	JSpinner secondsSpinner = new JSpinner(secondsModel);
	private String userid, currName, selectedAddress;
	
	private PublicCheckInController publicCheckInController = new PublicCheckInController();
	
	public PublicCheckInUI(String userID, String name) {
		super("Public Check In UI");
		userid = userID;
		currName = name;
	
		getContentPane().setLayout(null);
		
		dateChooser.setDate(myDate);
		dateChooser.getJCalendar().setMaxSelectableDate(myDate);
		
		JButton closeBtn = new JButton("Back");
		closeBtn.setBounds(10, 11, 89, 23);
		getContentPane().add(closeBtn );
		CloseButton onCloseBtn = new CloseButton();
		closeBtn.addActionListener(onCloseBtn); 

		JLabel dateTimeLbl = new JLabel("Date & Time :");
		dateTimeLbl.setBounds(159, 54, 104, 23);
		getContentPane().add(dateTimeLbl);

		JLabel lblCheckInLocation = new JLabel("Check-In Location :");
		lblCheckInLocation.setBounds(10, 232, 118, 23);
		getContentPane().add(lblCheckInLocation);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(159, 232, 153, 22);
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					selectedAddress = e.getItem().toString();
				}
			}
		});
		getContentPane().add(comboBox);

		try {
			ResultSet rs = publicCheckInController.validateRetrieveLocation();
			while (rs.next()) {
				String address = rs.getString(1);
				comboBox.addItem(address);
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.WARNING_MESSAGE);
		}
		
		JButton checkInBtn = new JButton("Check In");
		checkInBtn.setBounds(125, 307, 130, 23);
		getContentPane().add(checkInBtn);


		CheckInBtn onCheckIn = new CheckInBtn();
		checkInBtn.addActionListener(onCheckIn);
		dateChooser.setBounds(105, 88, 164, 23);
		getContentPane().add(dateChooser);
		hoursSpinner.setBounds(64, 165, 63, 37);
		getContentPane().add(hoursSpinner);
		minutesSpinner.setBounds(159, 165, 63, 37);
		getContentPane().add(minutesSpinner);
		secondsSpinner.setBounds(247, 165, 55, 37);
		getContentPane().add(secondsSpinner);
		
		JLabel userIDlbl = new JLabel("");
		userIDlbl.setBounds(31, 63, 46, 14);
		getContentPane().add(userIDlbl);
		
		JLabel HoursLbl = new JLabel("Hours");
		HoursLbl.setBounds(63, 133, 46, 14);
		getContentPane().add(HoursLbl);
		
		JLabel MinutesLbl = new JLabel("Minutes");
		MinutesLbl.setBounds(159, 133, 46, 14);
		getContentPane().add(MinutesLbl);
		
		JLabel SecondsLbl = new JLabel("Seconds");
		SecondsLbl.setBounds(247, 133, 46, 14);
		getContentPane().add(SecondsLbl);



		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(420,430);
		this.setVisible(true);
	}

	private class CheckInBtn implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			String date= df.format(dateChooser.getDate());
			String hours = hoursSpinner.getValue().toString();
			String minutes = minutesSpinner.getValue().toString();
			String seconds = secondsSpinner.getValue().toString();	
			
			if(validateUI(hours, minutes, seconds, selectedAddress)) {
				hours = Integer.parseInt(hours) < 10 ? "0"+hours : hours;
				minutes = Integer.parseInt(minutes) < 10 ? "0"+minutes : minutes;
				seconds = Integer.parseInt(seconds) < 10 ? "0"+seconds : seconds;
				
				String concatenatedString = (date + " " + hours + ":" + minutes + ":" + seconds );
				if(publicCheckInController.validateCheckInDateTimeLocation(userid, concatenatedString, selectedAddress)) {
					onSuccess();
				}
				else {
					onFailure();
				}
			}
		}
	}
	
	private class CloseButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			new PublicUserUI(userid, currName);
		}
	}
	
	private void onSuccess() {
		JOptionPane.showMessageDialog(null, "Checked in successfully", "Checked in successfully", JOptionPane.WARNING_MESSAGE);
	}
	
	private void onFailure() {
		JOptionPane.showMessageDialog(null, "Update failed", "Update failed", JOptionPane.WARNING_MESSAGE);
	}
	
	private boolean validateUI(String hours, String minutes, String seconds, String selectedAddress) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateChooser.getDate());
		calendar.add(Calendar.DATE, -1);
		
		if(dateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Date cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(calendar.getTime().after(currCalendar.getTime())){
			JOptionPane.showMessageDialog(null, "Date selected ahead of today's date. \nPlease select again", "Invalid", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		else if(hours == null  && minutes == null && seconds == null) {
			JOptionPane.showMessageDialog(null, "Time cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(selectedAddress == null && selectedAddress.length() == 0) {
			JOptionPane.showMessageDialog(null, "Please select an Address", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}