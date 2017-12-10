package UASurveillanceIHMEtud;

import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;

public abstract class PopUp extends JFrame {
	
	private static final long serialVersionUID = -7082131960501149448L;
	
	// Window components Size
	private final int width = 600;
	private final int horizontal_margin = 10;
	private final int vertical_margin = 10;
	private final int component_height = 25;
	
	// nombre de composants
	private int nb_composants = 0;

	// Constructeur
	public PopUp() {
		this.getContentPane().setLayout(null);
		this.setTitle("Examen");
		
	    // Taille de la frame, on initialise la hauteur de la frame comme s'il y avait un composant
		this.setSize(width, vertical_margin+component_height);
		
	    // Placer au centre de l'ecran
		int pos_x_on_screen = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - width / 2);
		this.setLocation( pos_x_on_screen, 100 );
		
		// Non resizable
		this.setResizable(false);
	}
	
	// Permet d'initialiser les paramètres des composants de la frame (globalement, c'est ici qu'on va définir les ActionListener)
	protected abstract void initComponents();
	// Permet d'initialiser la disposition des composants
	protected abstract void putComponents();
	
	protected void addComponent(JComponent component, boolean moreOffset) {
		
		// On met à jour le nombre de composants pour savoir quel décalage vertical on a besoin de faire
		nb_composants++;
		
		// Si on veut plus de décalage alors on multiplie la margin par 10
		int horizontal_offset = (moreOffset ? 10*horizontal_margin : horizontal_margin);
		
		// On place le composant
		component.setBounds(
			// Decalage à gauche
			horizontal_offset,
			// Décalage en haut: Se décale du nombre de composants * le décalage vertical et de la taille des composants qui le précéde
			nb_composants*vertical_margin + (nb_composants-1)*component_height,
			// Largeur composant: toute la largeur de la fenêtre - les décalages à gauche et à droite
			this.getWidth()- horizontal_offset*2,
			// Hauteur composant
			component_height
		);
		
		// On agrandit la hauteur de la fenêtre de la taille du composant et de la margin qui le sépare du précédent
		this.setSize(width, this.getHeight()+component_height+vertical_margin);
		
		// On ajoute le composant
		this.add(component);
	}
	
}
