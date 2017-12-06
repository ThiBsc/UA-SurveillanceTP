package UASurveillanceIHMEtud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FrameInitialisation extends PopUp {
	
	private static final long serialVersionUID = -3236410975838581458L;

	// Singleton
	static FrameInitialisation instance;
	
	private JLabel label_etudiant_firstname;
	private JLabel label_etudiant_lastname;
	private JLabel label_exam_id;
	private JLabel label_ip_serveur;
	private JTextField field_etudiant_firstname;
	private JTextField field_etudiant_lastname;
	private JTextField field_exam_id;
	private JTextField field_ip_serveur;
	private JButton button_submit;
	
	private FrameInitialisation() {
		super(9);
		
		label_etudiant_firstname = new JLabel("Prénom");
		label_etudiant_lastname = new JLabel("Nom");
		label_ip_serveur = new JLabel("IP du serveur distant");
		field_etudiant_firstname = new JTextField();
		field_etudiant_lastname = new JTextField();
		field_ip_serveur = new JTextField();
		button_submit = new JButton("Valider");
		label_exam_id = new JLabel("ID de l'examen");
		field_exam_id = new JTextField();
		button_submit = new JButton("Valider");
		
		initFrame();
		initForm();
		
		this.setVisible(true);
	}

	protected void initFrame() {

		button_submit.addActionListener(new ActionListener(){ 
			  public void actionPerformed(ActionEvent e) {
				  
				  // Si les champs sont valides
				  if ( formulaireValide() ) {
					  Window window = Window.getInstance();
					  
					  // On met à jour les champs
					  window.setEtud_prenom(field_etudiant_firstname.getText());
					  window.setEtud_nom(field_etudiant_lastname.getText());
					  window.setExam_id(field_exam_id.getText());
					  
					  String ip = field_ip_serveur.getText();
					  if ( ip != null ) {
						  UASurveillanceEngine.Watcher.IP_SERVER = ip;
					  }
					  
					  // On rend l'UI visible
					  window.refreshUI();
					  window.setVisible(true);
					  
					  // On start les watchers
					  UASurveillanceEngine.Watcher.startWatchers();
					  
					  // On ferme la fenêtre
					  FrameInitialisation.getInstance().dispose();
				  } else {
					  // Message d'erreur
					  JOptionPane.showMessageDialog(null, "Le formulaire n'est pas valide", "Erreur", JOptionPane.ERROR_MESSAGE);
				  }
			  } 
		});
		
	}
	
	protected void initForm() {
		
		label_etudiant_firstname.setLabelFor(field_etudiant_firstname);
		label_etudiant_lastname.setLabelFor(field_etudiant_lastname);
		label_exam_id.setLabelFor(field_exam_id);
		label_ip_serveur.setLabelFor(field_ip_serveur);
		
		addComponent(label_etudiant_firstname, 1, false);
		addComponent(field_etudiant_firstname, 2, true);
		addComponent(label_etudiant_lastname, 3, false);
		addComponent(field_etudiant_lastname, 4, true);
		addComponent(label_exam_id, 5, false);
		addComponent(field_exam_id, 6, true);
		addComponent(label_ip_serveur, 7, false);
		addComponent(field_ip_serveur, 8, true);
		addComponent(button_submit, 9, true);
		
	}
	
	public static FrameInitialisation getInstance() {
		if ( instance == null ) {
			instance = new FrameInitialisation();
		} else {
			instance.setVisible(true);
		}
		return instance;
	}
	
	private boolean formulaireValide() {
		return
				field_etudiant_firstname.getText().matches("\\w+") &&
				field_etudiant_lastname.getText().matches("\\w+") &&
				field_exam_id.getText().matches("\\d+")
		;
	}
	
}
