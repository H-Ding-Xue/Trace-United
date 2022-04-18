package main.traceUnited.boundary;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;

import main.traceUnited.controller.BusinessViewNRICsVisitedByDateController;
import main.traceUnited.controller.PublicCheckInController;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class BusinessViewNRICsVisitedByDateUI extends JFrame{

	private JPanel topPanel = new JPanel();

	private JButton backButton = new JButton("Back");

	private JPanel mainPanel = new JPanel();
	
	private JDateChooser dateChooser = new JDateChooser();

	private JLabel locationLbl = new JLabel("");

	private JLabel DateLbl = new JLabel("Date:");

	private JButton RetrieveBtn = new JButton("Retrieve");
	
	private JLabel idLbl = new JLabel("");
	
	private String userName, currentIdField, selectedAddress;
	
	private final JLabel locaLbl = new JLabel("Location:");
	
	private JTable table;
	
	private DefaultTableModel model = new DefaultTableModel();
	
	private BusinessViewNRICsVisitedByDateController businessViewNRICsVisitedByDateController = new BusinessViewNRICsVisitedByDateController();
	
	public BusinessViewNRICsVisitedByDateUI(String userId, String name) {
		super("Business View NRICs Visited By Date UI");
		currentIdField = userId;
		userName = name;
		
		getContentPane().setLayout(null);
		
		topPanel.setBounds(0, 0, 585, 46);
		getContentPane().add(topPanel);
		topPanel.setLayout(null);
		
		backButton.setBounds(10, 11, 89, 23);
		topPanel.add(backButton);
		OnBack onBack = new OnBack();
		backButton.addActionListener(onBack);
		
		idLbl.setBounds(348, 15, 208, 19);
		topPanel.add(idLbl);
		idLbl.setText(name);
		
		mainPanel.setBounds(0, 45, 585, 323);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		dateChooser.setBounds(57, 22, 139, 23);
		mainPanel.add(dateChooser);
		locationLbl.setBounds(98, 56, 437, 23);
		mainPanel.add(locationLbl);
		DateLbl.setBounds(28, 22, 35, 23);
		mainPanel.add(DateLbl);
		RetrieveBtn.setBounds(464, 22, 89, 23);
		mainPanel.add(RetrieveBtn);
		RetrieveButton onRetrieve = new RetrieveButton();
		RetrieveBtn.addActionListener(onRetrieve);
		locaLbl.setBounds(28, 56, 60, 23);
		mainPanel.add(locaLbl);
		
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(27, 90, 171, 222);
		mainPanel.add(scrollPane);
		scrollPane.setViewportView(table);
		JTableHeader tablehead = table.getTableHeader();
		tablehead.setPreferredSize(new Dimension(0,35));
		String[] cols = {"NRIC"}; 
		model.setColumnIdentifiers(cols);
		try {
			ResultSet rs = businessViewNRICsVisitedByDateController.validateRetrieveLocation(currentIdField);
			while (rs.next()) {
				String address = rs.getString(1);
				locationLbl.setText(address);
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.WARNING_MESSAGE);
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,400);
		this.setVisible(true);
		
	}
	private class OnBack implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			dispose();
			new BusinessUI(currentIdField, userName);
		}
		
	}
	private class RetrieveButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				
				String date= df.format(dateChooser.getDate());
				String checkinDate = (date + "%");
				String businessAddress = locationLbl.getText();
				
				if(validateUI(date, businessAddress)){
					ArrayList<String> results = businessViewNRICsVisitedByDateController.validateRetrieveNric(checkinDate, businessAddress);
					if(results != null && results.size()>0) {
						onSuccess(results);
					}
					else {
						onFailure();
					}
				}
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private boolean validateUI(String date, String businessAddress) {
		return (date != null && date.length()>0 && businessAddress != null && businessAddress.length()>0);
	}
	
	private void onSuccess(ArrayList<String> results) {
		model = new DefaultTableModel();
		model.addColumn("NRIC");
		for(String result: results) {
			model.addRow(new Object[] {result});
		}
		
		table.setModel(model);
	}
	
	private void onFailure() {
		JOptionPane.showMessageDialog(null, "No record found", "No record found", JOptionPane.WARNING_MESSAGE);
	}
}
