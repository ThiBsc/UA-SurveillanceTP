package UASurveillanceIHMEtud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = -3013085201157704997L;

//	private JMenu menu_initialisation;
//	private JMenuItem menu_item_initialisation_etudiant;
	private JMenu menu_about;
	private JMenuItem menu_item_about_surveillance;
	private JMenuItem menu_item_about_help;
	
	public MenuBar() {
//		menu_initialisation = new JMenu("Initialisation");
//		menu_item_initialisation_etudiant = new JMenuItem("Qui suis-je ?");
		this.menu_about = new JMenu("À propos");
		this.menu_item_about_surveillance = new JMenuItem("La surveillance");
		this.menu_item_about_help = new JMenuItem("?");
		
		initUI();
	}
	
	public void initUI() {
		
		// ActionListener à propos de la surveillance
		this.menu_item_about_surveillance.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String text_html = "";
				text_html += "<html>";
					text_html += "Éléments sur lesquels vous êtes surveillés:<br>";
					text_html += "<ul>";
						text_html += "<li>Navigation internet</li>";
						text_html += "<li>Branchement USB</li>";
						text_html += "<li>Mouvement sur les fichiers (Création, modification, suppression)</li>";
					text_html += "</ul>";
					text_html += "<i>Pendant toute la durée de l'examen, votre écran est enregistré.</i>";
				text_html += "</html>";
				// On renseigne sur quoi les étudiants sont surveillés
				JOptionPane.showMessageDialog(null, text_html, "À propos de la surveillance", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		// ActionListener à propos de l'interface
		this.menu_item_about_help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String text_html = "";
				text_html += "<html>";
					text_html += "Lorsque cela est nécessaire, le programme envoie des données relatives à votre activité à un serveur.<br>";
					text_html += "Le voyant devient alors vert.";
				text_html += "</html>";
				// On renseigne sur quoi les étudiants sont surveillés
				JOptionPane.showMessageDialog(null, text_html, "?", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		// On ajoute au menu "À propos" les deux items
		this.menu_about.add(this.menu_item_about_surveillance);
		this.menu_about.add(this.menu_item_about_help);
		
		// On ajoute le menu à la barre de menus
		this.add(this.menu_about);
		
		
//		menu_initialisation = new JMenu("Initialisation");
//		
//		menu_item_initialisation_etudiant.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				FrameInitialisation.getInstance();
//			}
//		});
//		
//		
//		menu_initialisation.add(menu_item_initialisation_etudiant);
//		
//		add(menu_initialisation);
	}
	

}
