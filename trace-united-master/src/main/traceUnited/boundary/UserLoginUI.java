package main.traceUnited.boundary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

import main.traceUnited.controller.UserLoginController;

public class UserLoginUI extends JFrame{
	
	private JPanel loginPanel = new JPanel();
	private JPanel loginDetailsPanel = new JPanel();
	
	private JLabel idTypeLabel = new JLabel("Login As :");
	private JLabel idLabel = new JLabel("ID:");
	public JTextField idField = new JTextField(20);
	private JLabel passwordLabel = new JLabel("Password:");
	public JPasswordField passwordField = new JPasswordField(20);
	public JButton loginButton = new JButton("Login");
	
	private JComboBox idTypeComboBox = new JComboBox();
	
	public String name;
	
	static public JDialog dialog;
	
	public boolean isUnitTest = false;
	
	public UserLoginUI() {
		super("User Login UI");
		setLayout(new FlowLayout());
		
		Border loginPanelPadding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		Border loginDetailsPanelPadding = BorderFactory.createEmptyBorder(0, 0, 20, 0);
		
		loginPanel.setLayout(new BorderLayout());
		loginPanel.setBorder(loginPanelPadding);
		
		loginDetailsPanel.setBorder(loginDetailsPanelPadding);
		loginDetailsPanel.setLayout(new GridLayout(6,1));
		loginDetailsPanel.add(idTypeLabel);
		idTypeComboBox.addItem("Public");
		idTypeComboBox.addItem("Health Staff");
		idTypeComboBox.addItem("Health Organisation");
		idTypeComboBox.addItem("Business");
		loginDetailsPanel.add(idTypeComboBox);
		loginDetailsPanel.add(idLabel);
		loginDetailsPanel.add(idField);
		loginDetailsPanel.add(passwordLabel);
		loginDetailsPanel.add(passwordField);
		
		loginPanel.add(loginDetailsPanel, BorderLayout.CENTER);
		loginPanel.add(loginButton, BorderLayout.PAGE_END);
		
		add(loginPanel);
		OnSubmit onSubmit = new OnSubmit();
		loginButton.addActionListener(onSubmit);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,400);
		this.setVisible(true);
	}
	
	private class OnSubmit implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String idType = String.valueOf(idTypeComboBox.getSelectedItem());
				String id = idField.getText();
				String password = passwordField.getText();
				
				if(validateUI(id, password)) {
					UserLoginController userLoginController = new UserLoginController();
					name = userLoginController.validateLogin(idType, id, password);
					if(name.length() > 0) {
						dispose();
						onSuccess(id, idType, name);
					}
					else {
						onFailure();
					}
				}
				else {
					onFailure();
				}
				
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private boolean validateUI(String id, String password) {
		return (id != null && id.length()>0 && password != null && password.length()>0);
	}
	
	private void onSuccess(String id, String idType, String name) {
		if(idType == "Public") {
			new PublicUserUI(id, name);
		}
		else if(idType == "Health Staff") {
			new HealthStaffUI(id, name);
		}
		else if(idType == "Health Organisation") {
			new HealthOrganisationUI(name);
		}
		else if(idType == "Business") {
			new BusinessUI(id, name);
		}
	}
	
	private void onFailure() {
		JOptionPane jop = new JOptionPane();
		jop.setMessageType(JOptionPane.WARNING_MESSAGE);
		jop.setMessage("Login failed, please ensure the Login As, ID and Password combination is correct");
		dialog = jop.createDialog(null, "Login failed");
		
		if(isUnitTest) {
			disposeDialog(dialog);
		}
		
		dialog.setVisible(true);
	}
	
	public void disposeDialog(JDialog dialog) {
		new SwingWorker<Void, Void>() {
	         @Override
	         protected Void doInBackground() throws Exception {
	            Thread.sleep(1000); 

	            return null;
	         }

	         protected void done() {
	            dialog.dispose();
	         };
	      }.execute();
	}
}
