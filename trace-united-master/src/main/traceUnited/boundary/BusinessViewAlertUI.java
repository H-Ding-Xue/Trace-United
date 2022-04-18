package main.traceUnited.boundary;

import javax.swing.JFrame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import main.traceUnited.controller.BusinessViewAlertController;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class BusinessViewAlertUI extends JFrame {
	private JTable table;
	private String currID, currUserName, acknowledgeStatus, acknowledgeDateTime;
	
	private BusinessViewAlertController businessViewAlertController = new BusinessViewAlertController();
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Calendar currCalendar = Calendar.getInstance();
	public BusinessViewAlertUI(String userID, String userName){
		super("Business View Alert UI");
		getContentPane().setLayout(null);
		currID = userID;
		currUserName = userName;
		
		JButton backBtn = new JButton("Back");
		backBtn.setBounds(28, 25, 89, 23);
		getContentPane().add(backBtn);
		
		OnBackBtn onBackBtn = new OnBackBtn();
		backBtn.addActionListener(onBackBtn);
		acknowledgeDateTime = df.format(currCalendar.getTime());
		
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 5;
			}
		};

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 95, 698, 219);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		
		table.getTableHeader().setReorderingAllowed(false);
		JTableHeader th = table.getTableHeader(); 
		th.setPreferredSize(new Dimension(0,35));
		
		String[] cols  = {"S/No","Alert Message", "<html><center>Alert & Date Time</html>","<html><center>Acknowledged <br>Date Time", "<html><center>Has been <br>Acknowledged</html>", "Acknowledge"};
		model.setColumnIdentifiers(cols);

		try {
			ResultSet rs = businessViewAlertController.getBusinessAlert(currID);
			while(rs != null && rs.next()) {
				acknowledgeStatus = Integer.parseInt(rs.getString(5)) == 0 ? "No" : "Yes";
				model.addRow(new Object[] {
						rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4),acknowledgeStatus,"Acknowledge"
				});
			}
			table.setModel(model);

		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.WARNING_MESSAGE);
		}
		table.getColumn("Acknowledge").setCellRenderer(new ButtonRenderer());
		table.getColumn("Acknowledge").setCellEditor(new ButtonEditor(new JCheckBox()));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(734,439);
		this.setVisible(true);
	}
	private class ButtonRenderer extends JButton implements TableCellRenderer{

		public ButtonRenderer() {
			setOpaque(true);
		}
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {			
			if (table.getValueAt(row, 4).equals("Yes")) {
				setText((String) (value = "Confirmed"));
			}
			if (column == 5 && isSelected && hasFocus) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
				String currTableID = (String) table.getValueAt(row, 0);
				table.setValueAt("Yes", row, 4);
				table.setValueAt(acknowledgeDateTime, row, 3);
				businessViewAlertController.updateBusinessAlertAcknowledgement("True", acknowledgeDateTime, currTableID);
			} 
			else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == "Acknowledge") ? "Acknowledge" : "Confirmed");

			return this;				
		}

	}
	private class ButtonEditor extends DefaultCellEditor {

		protected JButton button;
		private String label;
		private boolean isPushed;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}
	}

	private class OnBackBtn implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			new BusinessUI(currID, currUserName);
		}
	}
}
