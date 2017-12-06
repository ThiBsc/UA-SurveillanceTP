package UASurveillanceIHMEtud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = -3013085201157704997L;

	private JMenu menu_initialisation;
	private JMenuItem menu_item_initialisation_etudiant;
	
	public MenuBar() {
		menu_initialisation = new JMenu();
		menu_item_initialisation_etudiant = new JMenuItem("Qui suis-je ?");
		
		initUI();
	}
	
	public void initUI(){		 
		menu_initialisation = new JMenu("Initialisation");
		
		menu_item_initialisation_etudiant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FrameInitialisation.getInstance();
			}
		});
		
		
		menu_initialisation.add(menu_item_initialisation_etudiant);
		
		add(menu_initialisation);
	}
	

}
