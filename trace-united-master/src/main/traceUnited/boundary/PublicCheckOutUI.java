package main.traceUnited.boundary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import main.traceUnited.controller.PublicCheckOutController;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.toedter.calendar.JDateChooser;
import com.toedter.components.JSpinField;


public class PublicCheckOutUI extends JFrame {
	private JDateChooser dateChooser = new JDateChooser();
	private Calendar currCalendar = Calendar.getInstance();
	private Date myDate = new Date();
	
	SpinnerModel hoursModel = new SpinnerNumberModel(0, 0, 23, 1);
	JSpinner hoursSpinner = new JSpinner(hoursModel);

	SpinnerModel minutesModel = new SpinnerNumberModel(0, 0, 59, 1);
	JSpinner minutesSpinner = new JSpinner(minutesModel);

	SpinnerModel secondsModel = new SpinnerNumberModel(0, 0, 59, 1);
	JSpinner secondsSpinner = new JSpinner(secondsModel);
	
	PublicCheckOutController publicCheckOutController = new PublicCheckOutController();
	
	private String currID, currName, selectedAddress, checkOutDate;
	private String date, hours, minutes, seconds;
	private JComboBox comboBox;
	
	public PublicCheckOutUI(String userID, String name) {
		super("Public Check Out UI");
		getContentPane().setLayout(null);
		currID = userID;
		currName = name;
		
		
		dateChooser.setDate(myDate);
		dateChooser.getJCalendar().setMaxSelectableDate(myDate);
		
		JButton closeBtn = new JButton("Back");
		closeBtn.setBounds(10, 11, 89, 23);
		getContentPane().add(closeBtn);
		BackBtn onCloseBtn = new BackBtn();
		closeBtn.addActionListener(onCloseBtn); 

		JLabel dateTimeLbl = new JLabel("Date & Time ");
		dateTimeLbl.setBounds(165, 31, 104, 23);
		getContentPane().add(dateTimeLbl);

		JLabel lblCheckoutLocation = new JLabel("Check-Out Location :");
		lblCheckoutLocation.setBounds(33, 223, 130, 23);
		getContentPane().add(lblCheckoutLocation);

		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					selectedAddress = e.getItem().toString();
					
				}
			}
		});
		comboBox.setBounds(165, 223, 150, 22);
		getContentPane().add(comboBox);

		JButton checkOutBtn = new JButton("Check Out");
		checkOutBtn.setBounds(139, 304, 130, 23);
		getContentPane().add(checkOutBtn);

		CheckOutBtn onCheckOut = new CheckOutBtn();
		checkOutBtn.addActionListener(onCheckOut);

		try {
			ResultSet rs = publicCheckOutController.getBusinessAddress(userID);
			while (rs.next()) {
				int id = rs.getInt(1);
				String address = rs.getString(2);
				comboBox.addItem(new AddressIdPair(id, address));
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.WARNING_MESSAGE);
		}
		
		dateChooser.setBounds(119, 65, 167, 23);
		getContentPane().add(dateChooser);

		hoursSpinner.setBounds(75, 143, 64, 34);
		getContentPane().add(hoursSpinner);

		minutesSpinner.setBounds(173, 143, 64, 34);
		getContentPane().add(minutesSpinner);

		secondsSpinner.setBounds(264, 143, 64, 34);
		getContentPane().add(secondsSpinner);

		JLabel lblNewLabel = new JLabel("Hours");
		lblNewLabel.setBounds(75, 111, 64, 21);
		getContentPane().add(lblNewLabel);

		JLabel lblMinutes_1 = new JLabel("Minutes");
		lblMinutes_1.setBounds(173, 111, 64, 21);
		getContentPane().add(lblMinutes_1);

		JLabel lblMinutes = new JLabel("Seconds");
		lblMinutes.setBounds(264, 111, 64, 21);
		getContentPane().add(lblMinutes);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(420,430);
		this.setVisible(true);
	}

	private class CheckOutBtn implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			AddressIdPair name = (AddressIdPair) comboBox.getSelectedItem();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			date= df.format(dateChooser.getDate());
			hours = hoursSpinner.getValue().toString();
			minutes = minutesSpinner.getValue().toString();
			seconds = secondsSpinner.getValue().toString();
			
			if (validateUI(hours, minutes, seconds)) {		
				hours = Integer.parseInt(hours) < 10 ? "0"+hours : hours;
				minutes = Integer.parseInt(minutes) < 10 ? "0"+minutes : minutes;
				seconds = Integer.parseInt(seconds) < 10 ? "0"+seconds : seconds;
				
				checkOutDate = (date + " " + hours + ":" + minutes + ":" + seconds);
				if (publicCheckOutController.updateCheckOutDate(checkOutDate, name.getID(), currID, selectedAddress) == true) {
					onSuccess();
				}
				else {
					onFailure();
				}
			}
		}
	}
	private class AddressIdPair {
		private int Id;
		private String name;
		
	    public AddressIdPair(int Id, String name) {
	        this.Id = Id;
	        this.name = name;
	    }

	    public int getID() { return Id; }
	    public String getName() { return name; }
	    
	    @Override
	    public String toString() {
	    	return name;
	    }
	}
	
	private class BackBtn implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			new PublicUserUI(currID, currName);
		}
	}

	private boolean validateUI(String hours, String minutes, String seconds) {	
		if (dateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Date cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void onSuccess() {
		JOptionPane.showMessageDialog(null, "Check out confirmed!", "Checked Out", JOptionPane.INFORMATION_MESSAGE);
	}

	private void onFailure() {
		JOptionPane.showMessageDialog(null, "Check out failed!", "Checked Out", JOptionPane.ERROR_MESSAGE);
	}
}
