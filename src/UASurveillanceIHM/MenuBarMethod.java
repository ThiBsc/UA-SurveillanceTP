package UASurveillanceIHM;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	        JLabel daEx = new JLabel("Date Examen (jj-mm-aaaa)");
			JTextField dateExam = new JTextField();
			JLabel ens = new JLabel("ID Enseignant");
			JTextField enseignant = new JTextField();
			JLabel clEx = new JLabel("ID Classe Examen");
			JTextField classeExam = new JTextField();
			JLabel maEx = new JLabel("ID Matière Examen");
			JTextField matiereExam = new JTextField();
			JLabel duEx = new JLabel("Durée Examen (chaine de caractères)");
			JTextField dureeExam = new JTextField();
			dateExam.setColumns(10);
			classeExam.setColumns(20);
			matiereExam.setColumns(20);
			dureeExam.setColumns(10);
	 
			panel.add(daEx);
			panel.add(dateExam);
			panel.add(ens);
			panel.add(enseignant);
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
				int idenseignant = Integer.parseInt(enseignant.getText());
				String dureeExamen = dureeExam.getText();
			
				ConnexionBase.getInstance().addToDB(new Examen(dateExamen, dureeExamen, idenseignant, matiereExamen, classeExamen));
				SplitPane.getInstance().resetDisplay();
			}

			
		}
	}
}