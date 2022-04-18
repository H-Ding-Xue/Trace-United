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
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import main.traceUnited.controller.PublicViewContactTracesController;

public class PublicViewContactTracesUI extends JFrame {
	private String currentUserId;
	private String userName;
	
	private JPanel mainPanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	
	private JButton backButton = new JButton("Back");
	
	private JTable table = new JTable();
	
	private JLabel label = new JLabel("Your check-in and check-out date time and location");
	
	private Border panelPadding = BorderFactory.createEmptyBorder(0, 20, 20, 20);
	
	private PublicViewContactTracesController publicViewContactTracesController = new PublicViewContactTracesController();
	
	public PublicViewContactTracesUI(String userId, String name){
		super("Public View Contact Traces UI");
		currentUserId = userId;
		userName = name;
		add(mainPanel);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(panelPadding);
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(backButton);
		
		centerPanel.add(label);
		centerPanel.add(new JScrollPane(table));
		
		ResultSet result = publicViewContactTracesController.retrieveContactTraces(userId);
		
		if(result != null) {
			onSuccess(result);
		}
		else {
			onFailure();
		}
		
		Back back = new Back();
		backButton.addActionListener(back);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,400);
		this.setVisible(true);
	}
	
	private class Back implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new PublicUserUI(currentUserId, userName);
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
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
			table.setModel(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void onFailure() {
		JOptionPane.showMessageDialog(null, "No record found", "No record found", JOptionPane.WARNING_MESSAGE);
	}
}
