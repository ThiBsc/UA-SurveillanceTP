package UASurveillanceIHMEtud;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public abstract class PopUp extends JFrame {
	
	private static final long serialVersionUID = -7082131960501149448L;
	
	// Window components Size
	private final int width = 600;
	private final int base_height = 0;
	private final int horizontal_margin = 10;
	private final int vertical_margin = 10;
	private final int component_height = 25;

	public PopUp(int nbComponents) {
		this.getContentPane().setLayout(null);
		this.setTitle("Examen");
		
	    // Taille de la frame
		int height = base_height+ component_height*(nbComponents+2) + vertical_margin*(nbComponents+1);
		this.setSize(width, height);
		
	    // Placer au centre de l'ecran
		this.setLocationRelativeTo(null);
		
		// Non resizable
		this.setResizable(false);
		
		// On interdit la fermeture de la fenêtre
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
	
	protected abstract void initFrame();
	protected abstract void initForm();
	
	protected void addComponent(JComponent component, int position, boolean moreOffset) {
		// Si on veut plus de décalage alors on multiplie la margin par 10
		int horizontal_offset = (moreOffset ? 10*horizontal_margin : horizontal_margin);
		
		// On place le composant
		component.setBounds(
			// Decalage à gauche
			horizontal_offset,
			// Décalage en haut: Se décale de position*le décalage en haut et de la taille des composants qui le précéde
			position*vertical_margin + (position-1)*component_height,
			// Largeur composant: toute la largeur de la fenêtre - les décalages à gauche et à droite
			this.getWidth()- horizontal_offset*2,
			// Hauteur composant
			component_height
		);
		
		// On ajoute le composant
		this.add(component);
	}
	
}
