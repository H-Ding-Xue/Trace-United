package main.traceUnited.boundary;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.traceUnited.controller.HealthStaffSendAlertToPublicController;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HealthStaffSendAlertToPublicUI extends JFrame{
	private JButton BackButton = new JButton("Back");
	private JLabel NRICLabel = new JLabel("Covid-19 Patient NRIC: ");
	private JTextField NRIC = new JTextField(10);
	private JButton sendalertButton = new JButton("Send Alert");
	private String currentUserId;
	private String userName;
	private final JFrame parent = new JFrame();
	private final JTable table = new JTable();
	private final JScrollPane scrollPane = new JScrollPane();
	JTextArea Alertmsg = new JTextArea();

	private HealthStaffSendAlertToPublicController healthStaffSendAlertToPublicController = new HealthStaffSendAlertToPublicController();

	public HealthStaffSendAlertToPublicUI(String userid, String name){
		super("Health Staff Send Alert To Public UI");
		currentUserId = userid;
		userName = name;
		
		getContentPane().setLayout(null);
		BackButton.setBounds(10, 11, 70, 23);
		getContentPane().add(BackButton);
		NRICLabel.setBounds(35, 54, 129, 14);
		getContentPane().add(NRICLabel);
		NRIC.setBounds(243, 51, 302, 20);
		getContentPane().add(NRIC);
		sendalertButton.setBounds(155, 247, 234, 23);
		getContentPane().add(sendalertButton);
		JLabel lblAlertMessageTo = new JLabel("<html>Alert Message to people who have made contact with Covid-19 patient</html>");
		lblAlertMessageTo.setBounds(35, 115, 146, 55);
		getContentPane().add(lblAlertMessageTo);
		scrollPane.setBounds(45, 307, 483, 443);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(table);	
		Alertmsg.setBounds(243, 82, 302, 154);
		getContentPane().add(Alertmsg);
		JLabel lblListOfPublic = new JLabel("List of Public users to be notified");
		lblListOfPublic.setBounds(189, 281, 226, 15);
		getContentPane().add(lblListOfPublic);
		Back onBack = new Back();
		BackButton.addActionListener(onBack);
		OnSendAlert alert = new OnSendAlert();
		sendalertButton.addActionListener(alert);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,800);
		this.setVisible(true);
	}

	private class Back implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new HealthStaffUI(currentUserId,userName);
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private class OnSendAlert implements ActionListener{
		ArrayList<String> nrics = new ArrayList<String>();
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				// get user input
				String nric = NRIC.getText();
				String message = Alertmsg.getText();
				//validate user input before executing controller method
				if(validateUI(nric, message)) {
					nrics = healthStaffSendAlertToPublicController.validateAlert(nric,message);
					
					if(nrics.get(0).equals("error2")) {
						JOptionPane.showMessageDialog(parent, "No alerts need to be sent");
					}
					
					else if(nrics.get(0).equals("error")) {
						onFailure();
					}
					
					if(!nrics.get(0).equals("error") && !nrics.get(0).equals("error2")) {
						onSuccess(nrics);
					}
				}
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private void onSuccess(ArrayList<String> nrics) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("S/No");
		model.addColumn("NRIC");
		int sn =1;
		for(int i=0; i < nrics.size(); i++){
            model.addRow(new Object[] {
            	sn,	nrics.get(i)
            });
            sn++;
        }
		table.setModel(model);
	}
	
	private void onFailure() {
		JOptionPane.showMessageDialog(parent, "Invalid NRIC");
	}
	
	private boolean validateUI(String nric, String message) {
		return (nric != null && nric.length()>0 && message != null && message.length()>0);
	}
}
