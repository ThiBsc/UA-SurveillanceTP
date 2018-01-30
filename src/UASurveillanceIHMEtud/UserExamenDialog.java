package UASurveillanceIHMEtud;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class UserExamenDialog extends JOptionPane {
	
	private static final long serialVersionUID = 1L;
	
	private GridLayout pLayout;
	private JPanel panel;
	private JTextField prenom, nom, exam, ip;
	private JLabel lblPrenom, lblNom, lblIdExam, lblIp;
	private ImageIcon icoPrenom, icoNom, icoExam, icoIp;

	public UserExamenDialog() {
		// ctor
		icoPrenom = icoNom = new ImageIcon(getClass().getResource("/icons/People.png"));
		icoExam = new ImageIcon(getClass().getResource("/icons/Calendar.png"));
		icoIp = new ImageIcon(getClass().getResource("/icons/Network connection.png"));
		
		pLayout = new GridLayout(4, 2, 3, 5);
		panel = new JPanel();
		panel.setLayout(pLayout);
		
		prenom = new JTextField();
		nom = new JTextField();
		exam = new JTextField();
		ip = new JTextField();
		
		lblPrenom = new JLabel("Prénom:");
		lblNom = new JLabel("Nom:");
		lblIdExam = new JLabel("ID examen:");
		lblIp = new JLabel("IP serveur:");
		
		lblPrenom.setIcon(icoPrenom);
		panel.add(lblPrenom);
		panel.add(prenom);
		
		lblNom.setIcon(icoNom);
		panel.add(lblNom);
		panel.add(nom);
		
		lblIdExam.setIcon(icoExam);
		panel.add(lblIdExam);
		panel.add(exam);
		
		lblIp.setIcon(icoIp);
		panel.add(lblIp);
		panel.add(ip);
	}

	public int showConfirmDialog(){
		int ret = -1;
		do {
			ret = super.showConfirmDialog(null, panel, "Connexion étudiant", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (ret == JOptionPane.OK_OPTION){
				if ( !(prenom.getText().matches("\\w+")
						&& nom.getText().matches("\\w+")
						&& exam.getText().matches("\\d+")) ){
					JOptionPane.showMessageDialog(null, "Le formulaire n'est pas valide", "Erreur", JOptionPane.ERROR_MESSAGE);
					ret = -1;
				}
			}
		} while (ret != JOptionPane.OK_OPTION && ret != JOptionPane.CANCEL_OPTION);
		return ret;
	}
	
	public String getPrenom(){
		return prenom.getText();
	}
	public String getNom(){
		return nom.getText();
	}
	public int getIdExam(){
		return Integer.parseInt(exam.getText());
	}
	public String getIp(){
		return ip.getText();
	}
}
