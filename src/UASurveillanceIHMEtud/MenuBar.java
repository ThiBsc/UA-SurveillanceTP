package UASurveillanceIHMEtud;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = -3013085201157704997L;

	private JMenu menu_initialisation;
	private JMenuItem menu_item_initialisation;
	private InitialisationUser init_user;
	
	public MenuBar(){
		super();
		menu_initialisation = new JMenu();
		menu_item_initialisation = new JMenuItem();
		init_user = new InitialisationUser();
		initUI();
	}
	
	public void initUI(){		 
		menu_initialisation = new JMenu("Initialisation");
		
		menu_item_initialisation = new JMenuItem("Qui suis-je ?");
		menu_item_initialisation.addActionListener(init_user);
		menu_item_initialisation.setActionCommand("init_user");
		
		menu_initialisation.add(menu_item_initialisation);
		
		add(menu_initialisation);
	}
	

}
