package UASurveillanceIHM;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

/**
 * 
 * @author thibaut
 * Contient les actions à réaliser en fonction des clics sur la MenuBar
 */
public class MenuBarMethod implements ActionListener {
	
	public MenuBarMethod(){
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("about")){
			String credits = "<html>UA-Surveillance v0.1 !<br>Dev - ..." +
            		"<br>Icons - ..." +
            		"<br>Database - MariaDB</html>";
			JOptionPane.showMessageDialog(null, credits, "About", JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getActionCommand().equals("ajout")){
			
	        JPanel panel = new JPanel(new GridLayout(0, 1));
	        JLabel daEx = new JLabel("Date Examen");
			JTextField dateExam = new JTextField();
			JLabel clEx = new JLabel("Classe Examen");
			JTextField classeExam = new JTextField();
			JLabel maEx = new JLabel("Matière Examen");
			JTextField matiereExam = new JTextField();
			JLabel duEx = new JLabel("Durée Examen");
			JTextField dureeExam = new JTextField();
			dateExam.setColumns(10);
			classeExam.setColumns(20);
			matiereExam.setColumns(20);
			dureeExam.setColumns(10);
	 
			panel.add(daEx);
			panel.add(dateExam);
			panel.add(clEx);
			panel.add(classeExam);
			panel.add(maEx);
			panel.add(matiereExam);
			panel.add(duEx);
			panel.add(dureeExam);

			int input = JOptionPane.showOptionDialog(null, panel, "Ajout d'un examen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if(input == JOptionPane.OK_OPTION)
			{
			   //get infos from the form above to send them to the DB
				String dateExamen = dateExam.getText();
				int classeExamen = Integer.parseInt(classeExam.getText());
				int matiereExamen = Integer.parseInt(matiereExam.getText());
				String dureeExamen = dureeExam.getText();
			
				
			}

			
		}
	}
}