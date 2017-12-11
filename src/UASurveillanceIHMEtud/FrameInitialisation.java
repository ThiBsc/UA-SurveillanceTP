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
		super();
		
		// Titre de la fenêtre
		this.setTitle("Initialisation");
		
		// Instanciation des objets
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
		
		// Initialisation du ActionListener et construction du formulaire
		initComponents();
		putComponents();
		
		// On rend la frame visible
		this.setVisible(true);
	}

	protected void initComponents() {

		button_submit.addActionListener(new ActionListener(){ 
			  public void actionPerformed(ActionEvent e) {
				  
				  // Si les champs sont valides
				  if ( formulaireValide() ) {
					  
					  // On ferme la fenêtre
					  FrameInitialisation.getInstance().dispose();
					  
					  // On ouvre la fenêtre principale
					  Window window = Window.getInstance();
					  
					  // On met à jour les champs
					  window.setEtud_prenom(field_etudiant_firstname.getText());
					  window.setEtud_nom(field_etudiant_lastname.getText());
					  window.setExam_id(field_exam_id.getText());
					  
					  // On met à jour l'itnerface
					  window.refreshUI();
					  
					  // Si l'ip est vide, on garde l'ip par défaut / pour les test : 127.0.0.1
					  String ip = field_ip_serveur.getText();
					  if ( ip != null ) {
						  UASurveillanceEngine.Watcher.IP_SERVER = ip;
					  }

					  // On prévient la base qu'un étudiant vient de se connecter
					  // sendEvent("APPLICATION"+"|"+EXAMEN_id+"|"+ETU_NOM+"|"+ETU_PRENOM+"|"+current_date.toString()+"|"+"DECONNEXION");
					  
					  // On start les watchers
					  window.startWatchers();
				  } else {
					  // Message d'erreur
					  JOptionPane.showMessageDialog(null, "Le formulaire n'est pas valide", "Erreur", JOptionPane.ERROR_MESSAGE);
				  }
			  } 
		});
		
	}
	
	protected void putComponents() {
		
		// On attribut les labels aux textfields
		label_etudiant_firstname.setLabelFor(field_etudiant_firstname);
		label_etudiant_lastname.setLabelFor(field_etudiant_lastname);
		label_exam_id.setLabelFor(field_exam_id);
		label_ip_serveur.setLabelFor(field_ip_serveur);
		
		// On laisse à la fonction de la classe abstraite le soin de placer les composants
		addComponent(label_etudiant_firstname, false);
		addComponent(field_etudiant_firstname, true);
		addComponent(label_etudiant_lastname, false);
		addComponent(field_etudiant_lastname, true);
		addComponent(label_exam_id, false);
		addComponent(field_exam_id, true);
		addComponent(label_ip_serveur, false);
		addComponent(field_ip_serveur, true);
		addComponent(button_submit, true);
		
	}
	
	public static FrameInitialisation getInstance() {
		// Mécanisme de Singleton
		if ( instance == null ) {
			instance = new FrameInitialisation();
		} else {
			instance.setVisible(true);
		}
		return instance;
	}
	
	private boolean formulaireValide() {
		// Nom prénom -> suite de lettres, espace autorisé mais commence par une lettre
		return
				field_etudiant_firstname.getText().matches("\\w(\\w| )+") &&
				field_etudiant_lastname.getText().matches("\\w(\\w| )+") &&
				field_exam_id.getText().matches("\\d+")
		;
	}
	
}
