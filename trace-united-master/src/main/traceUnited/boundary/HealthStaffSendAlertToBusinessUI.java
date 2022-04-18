package main.traceUnited.boundary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import main.traceUnited.controller.HealthStaffSendAlertToBusinessController;

public class HealthStaffSendAlertToBusinessUI extends JFrame {
	private String currentUserId;
	private String userName;
	
	private JButton backButton = new JButton("Back");
	private JButton sendAlertButton = new JButton("Send Alert");
	
	private JLabel nricLabel = new JLabel("Covid - 19 Patient NRIC: ");
	private JLabel messageLabel = new JLabel("<html>Alert Message to businesses who have covid - 19 patients visited their premises: </html>");
	private JLabel tableLabel = new JLabel("List of people that has been alerted:");
	
	private Border panelPadding = BorderFactory.createEmptyBorder(0, 20, 20, 20);
	private Border centerPanelPadding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
	private Border messageInputPanelPadding = BorderFactory.createEmptyBorder(20, 0, 20, 0);
	
	private JTextField nricField = new JTextField(15);
	private JTextArea messageArea = new JTextArea();
	
	private JPanel mainPanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	private JPanel nricInputPanel = new JPanel();
	private JPanel messageInputPanel = new JPanel();
	private JPanel alertButtonPanel = new JPanel();
	
	private JTable table = new JTable();
	
	private HealthStaffSendAlertToBusinessController healthStaffSendAlertToBusinessController = new HealthStaffSendAlertToBusinessController();
	
	public HealthStaffSendAlertToBusinessUI(String userId, String name) {
		super("Health Staff Send Alert To Business UI");
		currentUserId = userId;
		userName = name;
		
		add(mainPanel);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		mainPanel.setBorder(panelPadding);
		
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(backButton);
		
		centerPanel.setLayout(new BorderLayout());
		nricInputPanel.setLayout(new GridLayout(1,2));
		messageInputPanel.setLayout(new GridLayout(1,2));
		messageInputPanel.setBorder(messageInputPanelPadding);
		nricInputPanel.add(nricLabel);
		nricInputPanel.add(nricField);
		messageInputPanel.add(messageLabel);
		messageInputPanel.add(messageArea);
		
		centerPanel.setBorder(centerPanelPadding);
		centerPanel.add(nricInputPanel, BorderLayout.NORTH);
		centerPanel.add(messageInputPanel, BorderLayout.CENTER);
		centerPanel.add(alertButtonPanel, BorderLayout.SOUTH);
		
		alertButtonPanel.setLayout(new FlowLayout());
		alertButtonPanel.add(sendAlertButton);
		
		southPanel.setLayout(new FlowLayout());
		southPanel.add(tableLabel);
		southPanel.add(new JScrollPane(table));
		
		OnSubmit onSubmit = new OnSubmit();
		sendAlertButton.addActionListener(onSubmit);
		
		Back back = new Back();
		backButton.addActionListener(back);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,800);
		this.setVisible(true);
	}
	
	private class OnSubmit implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String patientNRIC = nricField.getText();
				String message = messageArea.getText();
				
				if(validateUI(patientNRIC, message)) {
					
					ArrayList<String> nrics = healthStaffSendAlertToBusinessController.validateAlert(patientNRIC, message);
					
					if(nrics != null && nrics.size()>0){
						onSuccess(nrics);
					}
					else {
						onFailure();
					}
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
	
	private boolean validateUI(String nric, String message) {
		return (nric != null && nric.length()>0 && message != null && message.length()>0);
	}
	
	private void onSuccess(ArrayList<String> nrics) {
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model.addColumn("S/No");
		model.addColumn("NRICs");
		int i = 1;
		
		for(String nric: nrics) {
			model.addRow(new Object[] {i, nric});
			
			i++;
		}
		table.setModel(model);
	}
	
	private void onFailure() {
		JOptionPane.showMessageDialog(null, "No contact traces found", "No contact traces found", JOptionPane.WARNING_MESSAGE);
	}
}
