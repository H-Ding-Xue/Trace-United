package main.traceUnited.boundary;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.traceUnited.controller.HealthOrganisationReportController;


public class HealthOrganisationReportUI extends JFrame{
	private JButton BackButton = new JButton("Back");
	private JButton selectVaccinationCertificateButton = new JButton("Generate Report");
	private final JLabel Vpercent = new JLabel("");
	private final JLabel Bpercent = new JLabel("");
	private String userName;
	private final JLabel lblBusiness = new JLabel("<html>Percentage of businesses that have been visited by Covid positive individuals </html>");
	private final JLabel vaclabel = new JLabel("Percentage of population vaccinated");

	private HealthOrganisationReportController healthOrganisationReportController = new HealthOrganisationReportController();

	public HealthOrganisationReportUI(String name){
		super("Health Organisation Report UI");
		userName = name;
		getContentPane().setLayout(null);
		BackButton.setBounds(10, 11, 70, 23);
		getContentPane().add(BackButton);
		selectVaccinationCertificateButton.setBounds(154, 259, 234, 45);
		getContentPane().add(selectVaccinationCertificateButton);
		Back onBack = new Back();
		BackButton.addActionListener(onBack);
		OnGenerateReport onGenerateReport = new OnGenerateReport();
		selectVaccinationCertificateButton.addActionListener(onGenerateReport);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,400);
		this.setVisible(true);
		Vpercent.setBounds(308, 79, 103, 14);
		getContentPane().add(Vpercent);
		Bpercent.setBounds(308, 147, 103, 14);
		getContentPane().add(Bpercent);
		vaclabel.setBounds(62, 79, 210, 14);
		getContentPane().add(vaclabel);
		lblBusiness.setBounds(63, 126, 186, 45);
		getContentPane().add(lblBusiness);
		Vpercent.setVisible(false);
		Bpercent.setVisible(false);
		vaclabel.setVisible(false);
		lblBusiness.setVisible(false);
	}

	private class Back implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				new HealthOrganisationUI(userName);
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private class OnGenerateReport implements ActionListener{
		// declaring attributes to calculate and store information
		ArrayList<Double> percentages = new ArrayList<Double>();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// calling controller method which will run through the entity and assign return values to the Percentages arraylist
			percentages = healthOrganisationReportController.getPercentages();
			//Assign values from arraylist to string variables while formatting them before assigning them to their respective Jlabels and making them visible to user
			if(percentages != null && percentages.size()>0) {
				onSuccess(percentages);
			}
			else {
				onFailure();
			}
		}
	}
	
	private void onSuccess(ArrayList<Double> percentages) {
		double vaccinepercent;
		double affectedbusinesspercent;
		String svaccinepercent;
		String saffectedbusinesspercent;
		
		vaccinepercent = percentages.get(0);
		affectedbusinesspercent = percentages.get(1);
		svaccinepercent = String.format("%.2f", vaccinepercent);
		saffectedbusinesspercent = String.format("%.2f", affectedbusinesspercent);
		svaccinepercent = svaccinepercent + " %";
		saffectedbusinesspercent = saffectedbusinesspercent + " %";
		Vpercent.setText(""+svaccinepercent);
		Bpercent.setText(""+saffectedbusinesspercent);
		vaclabel.setVisible(true);
		lblBusiness.setVisible(true);
		Vpercent.setVisible(true);
		Bpercent.setVisible(true);
	}
	
	private void onFailure() {
		JOptionPane.showMessageDialog(null, "Error in generating report", "Error in generating report", JOptionPane.WARNING_MESSAGE);
	}
}
