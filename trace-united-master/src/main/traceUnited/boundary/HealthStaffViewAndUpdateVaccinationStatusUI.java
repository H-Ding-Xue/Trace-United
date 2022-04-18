package main.traceUnited.boundary;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.traceUnited.controller.HealthStaffViewAndUpdateVaccinationStatusController;
import main.traceUnited.entity.HealthStaff;
import javax.swing.JRadioButton;

public class HealthStaffViewAndUpdateVaccinationStatusUI extends JFrame{
	private JButton BackButton = new JButton("Back");
	private JButton updateVaccinationCertificateButton = new JButton("Submit Vaccination Status");
	private JLabel NRICLabel = new JLabel("NRIC: ");
	private JTextField Id = new JTextField(10);
	private JLabel VaxLabel = new JLabel("Vaccine Status: ");
	private final JLabel ErrorLabel = new JLabel("PLEASE ENTER A NRIC");
	private final JLabel ErrorLabel2 = new JLabel("PLEASE ENTER A STATUS");
	private JButton selectVaccinationCertificateButton = new JButton("Show Current Vaccine Status");
	private JRadioButton Vaccinated = new JRadioButton("Vaccinated");
	private JRadioButton NotVaccinated = new JRadioButton("Not Vaccinated");
	private ButtonGroup group = new ButtonGroup();
	private final JFrame parent = new JFrame();
	private final JLabel IDLabel = new JLabel("NRIC:");
	private final JLabel VLabel = new JLabel("Vaccine Status:");
	private final JLabel IDSelected = new JLabel("");
	private final JLabel SelectedStatus = new JLabel("");
	private String currentUserId;
	private String userName;

	private HealthStaffViewAndUpdateVaccinationStatusController healthStaffViewAndUpdateVaccinationStatusController = new HealthStaffViewAndUpdateVaccinationStatusController();

	public HealthStaffViewAndUpdateVaccinationStatusUI(String userid, String name){
		super("Health Staff View And Update Vaccination Status UI");
		currentUserId = userid;
		userName = name;
		getContentPane().setLayout(null);
		BackButton.setBounds(10, 11, 70, 23);
		getContentPane().add(BackButton);
		updateVaccinationCertificateButton.setBounds(155, 121, 234, 23);
		getContentPane().add(updateVaccinationCertificateButton);
		NRICLabel.setBounds(99, 15, 70, 14);
		getContentPane().add(NRICLabel);
		Id.setBounds(212, 12, 146, 20);
		getContentPane().add(Id);
		VaxLabel.setBounds(99, 40, 103, 14);
		getContentPane().add(VaxLabel);
		ErrorLabel.setBounds(212, 311, 234, 14);
		getContentPane().add(ErrorLabel);
		ErrorLabel.setVisible(false);
		ErrorLabel2.setBounds(212, 336, 234, 14);
		getContentPane().add(ErrorLabel2);
		ErrorLabel2.setVisible(false);
		selectVaccinationCertificateButton.setBounds(155, 155, 234, 23);
		getContentPane().add(selectVaccinationCertificateButton);
		Vaccinated.setBounds(212, 39, 109, 23);
		getContentPane().add(Vaccinated);
		NotVaccinated.setBounds(212, 65, 148, 23);
		getContentPane().add(NotVaccinated);
		Back onBack = new Back();
		BackButton.addActionListener(onBack);
		OnUpdateData onUpdateData = new OnUpdateData();
		updateVaccinationCertificateButton.addActionListener(onUpdateData);
		OnSelectData onSelectData = new OnSelectData();
		selectVaccinationCertificateButton.addActionListener(onSelectData);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,400);
		this.setVisible(true);
		group.add(Vaccinated);
		group.add(NotVaccinated);
		IDLabel.setBounds(155, 208, 41, 14);
		getContentPane().add(IDLabel);
		VLabel.setBounds(99, 234, 103, 14);
		getContentPane().add(VLabel);
		IDSelected.setBounds(205, 208, 103, 14);
		getContentPane().add(IDSelected);
		SelectedStatus.setBounds(205, 234, 103, 14);
		getContentPane().add(SelectedStatus);

		IDLabel.setVisible(false);
		VLabel.setVisible(false);
		IDSelected.setVisible(false);
		SelectedStatus.setVisible(false);
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

	private class OnUpdateData implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// make labels invisible if they were made visible by select button
			IDLabel.setVisible(false);
			VLabel.setVisible(false);
			IDSelected.setVisible(false);
			SelectedStatus.setVisible(false);
			String NRIC = "";
			String Vstatus = "";
			// get user input and validate UI before calling controller methods
			validateUpdateUI();

			if(!Id.getText().equals("")){
				NRIC = Id.getText();
				if(Vaccinated.isSelected() == true) {
					Vstatus = "1";
					if(healthStaffViewAndUpdateVaccinationStatusController.updateVaccinationStatus(NRIC, Vstatus)) {
						onUpdateSuccess();
					}
					else {
						onUpdateFailure();
					}
				}
				else if(NotVaccinated.isSelected() == true) {
					Vstatus = "0";
					if(healthStaffViewAndUpdateVaccinationStatusController.updateVaccinationStatus(NRIC, Vstatus)) {
						onUpdateSuccess();
					}
					else {
						onUpdateFailure();
					}
				}

			}
		}
	}

	private class OnSelectData implements ActionListener{
		String vaxstatus = "";


		@Override
		public void actionPerformed(ActionEvent e) {
			// get user input and validate UI before calling controller methods and also clear radio button slections
			group.clearSelection();
			String nric = "";
			validateUI();

			if(!Id.getText().equals("")) {
				nric = Id.getText();
				vaxstatus = healthStaffViewAndUpdateVaccinationStatusController.retrieveVaccinationStatus(nric);
				if(vaxstatus == "") {
					onFailure();
				}
				else {
					onSuccess(nric, vaxstatus);
				}
			}

		}
	}
	
	private void onSuccess(String nric, String vaxstatus) {
		if(vaxstatus.equals("1")) {
			vaxstatus = "Yes";
		}
		else if(vaxstatus.equals("0")) {
			vaxstatus = "No";
		}
		IDSelected.setText(""+nric);
		SelectedStatus.setText(""+vaxstatus);
		IDLabel.setVisible(true);
		VLabel.setVisible(true);
		IDSelected.setVisible(true);
		SelectedStatus.setVisible(true);
	}
	
	private void onFailure() {
		IDLabel.setVisible(false);
		VLabel.setVisible(false);
		IDSelected.setVisible(false);
		SelectedStatus.setVisible(false);
		JOptionPane.showMessageDialog(parent, "NRIC does not exist");
	}
	
	private void validateUI() {
		if(Id.getText().equals("")) {
			ErrorLabel.setVisible(true);
		}
		else {
			ErrorLabel.setVisible(false);
		}
	}
	
	private void onUpdateSuccess() {
		JOptionPane.showMessageDialog(parent, "Record Updated");
	}
	
	private void onUpdateFailure() {
		JOptionPane.showMessageDialog(parent, "NRIC does not exist");
	}
	
	private void validateUpdateUI() {
		if(Id.getText().equals("")) {
			ErrorLabel.setVisible(true);
		}
		else {
			ErrorLabel.setVisible(false);
		}

		if(Vaccinated.isSelected() == false && NotVaccinated.isSelected() == false){
			ErrorLabel2.setVisible(true);
		}
		else {
			ErrorLabel2.setVisible(false);
		}
	}
}
