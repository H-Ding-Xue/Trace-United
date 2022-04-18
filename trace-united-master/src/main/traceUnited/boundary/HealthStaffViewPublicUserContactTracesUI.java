package main.traceUnited.boundary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import main.traceUnited.controller.HealthStaffViewPublicUserContactTracesController;
import main.traceUnited.controller.PublicViewContactTracesController;

public class HealthStaffViewPublicUserContactTracesUI extends JFrame {
	private String currentUserId;
	private String userName;
	
	private JPanel mainPanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	
	private JButton backButton = new JButton("Back");
	
	private JTable table = new JTable();
	
	private Border panelPadding = BorderFactory.createEmptyBorder(0, 20, 20, 20);
	
	private JPanel searchPanel = new JPanel();
	private JLabel nricLabel = new JLabel("Public user's NRIC");
	private JTextField nricTextField = new JTextField(20);
	private JButton retrieveButton = new JButton("Retrieve");
	
	HealthStaffViewPublicUserContactTracesController healthStaffViewPublicUserContactTracesController = new HealthStaffViewPublicUserContactTracesController();
	
	public HealthStaffViewPublicUserContactTracesUI(String userId, String name){
		super("Health Staff View Public User Contact Traces UI");
		currentUserId = userId;
		userName = name;
		add(mainPanel);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(panelPadding);
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(backButton);
		
		searchPanel.setLayout(new FlowLayout());
		searchPanel.add(nricLabel);
		searchPanel.add(nricTextField);
		searchPanel.add(retrieveButton);
		
		centerPanel.add(searchPanel);
		centerPanel.add(new JScrollPane(table));
		
		OnSubmit onSubmit = new OnSubmit();
		retrieveButton.addActionListener(onSubmit);
		
		Back back = new Back();
		backButton.addActionListener(back);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,400);
		this.setVisible(true);
	}
	
	private class OnSubmit implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String nric = nricTextField.getText();
			try {
				if(validateUI(nric)) {
					ResultSet result = healthStaffViewPublicUserContactTracesController.retrievePublicUserContactTraces(nric);
					
					if(result != null) {
						onSuccess(result);
					}
					else {
						onFailure();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "NRIC is invalid", "Invalid NRIC", JOptionPane.WARNING_MESSAGE);
				}
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private class Back implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new HealthStaffUI(currentUserId, userName);
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private boolean validateUI(String nric) {
		return (nric != null && nric.length()>0);
	}
	
	private void onSuccess(ResultSet result) {
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model.addColumn("Check-In Date Time");
		model.addColumn("Check-Out Date Time");
		model.addColumn("Location");
		
		try {
			while(result != null && result.next()) {
				model.addRow(new Object[] {result.getString("CheckInDateTime"), result.getString("CheckOutDateTime"), result.getString("BusinessAddress")});
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
		}
		table.setModel(model);
	}
	
	private void onFailure() {
		JOptionPane.showMessageDialog(null, "No record found", "No record found", JOptionPane.WARNING_MESSAGE);
	}
}
