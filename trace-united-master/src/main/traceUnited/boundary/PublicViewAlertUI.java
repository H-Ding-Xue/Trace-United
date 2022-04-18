package main.traceUnited.boundary;

import javax.swing.JFrame;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Label;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.JScrollPane;
import main.traceUnited.controller.*;

public class PublicViewAlertUI extends JFrame{
	private JTable table;
	private String acknowledgeStatus;
	private String currID, currName;
	PublicViewAlertController publicViewAlertController = new PublicViewAlertController();
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Calendar currCalendar = Calendar.getInstance();
	private String acknowledgeDateTime;
	public PublicViewAlertUI(String userID, String name) {
		super("Public View Alert UI");
		getContentPane().setLayout(null);
		currID = userID;
		currName = name;

		ResultSet rs = publicViewAlertController.displayPublicAlertAcknowledgement(userID);
		acknowledgeDateTime = df.format(currCalendar.getTime());
		JButton backBtn = new JButton("Back");
		backBtn.setBounds(27, 21, 89, 23);
		getContentPane().add(backBtn);
		OnBackBtn onBackBtn = new OnBackBtn();
		backBtn.addActionListener(onBackBtn);

		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 5; // last col editable
			}
		};
		table = new JTable(model);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setViewportView(table);
		scrollPane.setBounds(27, 130, 796, 270);
		getContentPane().add(scrollPane);
		
		table.getTableHeader().setReorderingAllowed(false);
		JTableHeader th = table.getTableHeader();		
		th.setPreferredSize(new Dimension(0,35));
		
		String[] cols = {"S/NO","Alert Date & Time","Alert Message","<html><center>Has been <br>acknowledge","<html><center>Acknowledged <br>Date & Time","Acknowledge"}; 
		model.setColumnIdentifiers(cols);

		try {
			while(rs != null && rs.next()) {
				acknowledgeStatus = Integer.parseInt(rs.getString(5)) == 0 ? "No" : "Yes";
				model.addRow(new Object[] {
						rs.getString(1),rs.getString(4),rs.getString(3),acknowledgeStatus,rs.getString(6),"Acknowledge",
				});
			}
			table.setModel(model);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		table.getColumn("Acknowledge").setCellRenderer(new ButtonRenderer());
		table.getColumn("Acknowledge").setCellEditor(new ButtonEditor(new JCheckBox()));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(865,439);
		this.setVisible(true);
	}


	private class ButtonRenderer extends JButton implements TableCellRenderer{

		public ButtonRenderer() {
			setOpaque(true);
		}
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// TODO Auto-generated method stub
			if (table.getValueAt(row, 3).equals("Yes")) {
				setText((String) (value = "Confirmed"));
			}
			if (column == 5 && isSelected && hasFocus) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
				table.setValueAt("Yes", row, 3);
				table.setValueAt(acknowledgeDateTime, row, 4);
				String currTableID = (String) table.getValueAt(row, 0);
				publicViewAlertController.updateAlertAcknowledgement("True", acknowledgeDateTime, currTableID);
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
			new PublicUserUI(currID,currName);
		}
	}

}
