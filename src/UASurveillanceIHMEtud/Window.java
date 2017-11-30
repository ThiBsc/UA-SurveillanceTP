package UASurveillanceIHMEtud;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

public class Window extends JFrame {

	static Window instance;
	
	private String etud_nom;
	private String etud_prenom;
	private String etud_numero;

	private JLabel temoinDenvoie;
	private JLabel messageDenvoie;
	private BorderLayout layout;
	private MenuBar menuBar;
	
	private Window()
    {
		layout = new BorderLayout();
		temoinDenvoie = new JLabel();
		messageDenvoie = new JLabel("Renseignez qui vous êtes dans Initialisation > Qui suis-je ?");
		menuBar = new MenuBar();
        initUI();
    }
	
	private void initUI() {
		setLayout(layout);
		setTitle("UA-SurveillanceTP");
	    // Taille de la frame
	    setSize(600, 200);
	    // Placer au centre de l'ecran
	    setLocationRelativeTo(null);
	    // Resizable ou non
	    setResizable(false);
        add(temoinDenvoie, BorderLayout.WEST);
        add(messageDenvoie, BorderLayout.CENTER);
        add(menuBar, BorderLayout.NORTH);
        // Action a la fermeture (croix)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
	}
	
	public void refreshUI() {
        if (etud_nom != null) {
        	messageDenvoie.setText(etud_nom.toUpperCase() + " " + etud_prenom + " " + etud_numero);
        }
	}
	
	public static Window getInstance() {
		if (instance == null) {
			instance = new Window();
		}
		return instance;		
	}
	
	public String getEtud_nom() {
		return etud_nom;
	}

	public void setEtud_nom(String etud_nom) {
		this.etud_nom = etud_nom;
	}

	public String getEtud_prenom() {
		return etud_prenom;
	}

	public void setEtud_prenom(String etud_prenom) {
		this.etud_prenom = etud_prenom;
	}

	public String getEtud_numero() {
		return etud_numero;
	}

	public void setEtud_numero(String etud_numero) {
		this.etud_numero = etud_numero;
	}
	
    public static void main(String[] args)
    {
        Window window = Window.getInstance();
    }

}
