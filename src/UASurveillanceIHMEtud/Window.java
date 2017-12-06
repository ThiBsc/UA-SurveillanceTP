package UASurveillanceIHMEtud;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Window extends JFrame {

	private static final long serialVersionUID = -5086125816507692170L;

	static Window instance;
	
	// Window components Size
	private final int width = 600;
	private final int height = 200;
	private final int horizontal_margin = 10;
	private final int vertical_margin = 10;
	
	private String etud_nom;
	private String etud_prenom;
	private String etud_numero;
	private String exam_id;

	private JLabel label_examen;
	private BorderLayout layout;
	
	private JPanel main_panel;
	private JLabel temoin_envoie;
	private JLabel label_etudiant;
	private MenuBar menuBar;
	
	private Window()
    {
		layout = new BorderLayout();
		label_examen = new JLabel();
		main_panel = new JPanel();
		temoin_envoie = new JLabel();
		label_etudiant = new JLabel();
		menuBar = new MenuBar();
		
		// On demande l'id de l'examen à l'ouverture
		FrameInitialisation.getInstance();
		
        initUI();
    }
	
	private void initUI() {
		setLayout(layout);
		setTitle("UA-SurveillanceTP");
	    // Taille de la frame
	    setSize(width, height);
	    // Placer au centre de l'ecran
	    setLocationRelativeTo(null);
	    // Resizable ou non
	    setResizable(false);

	    label_examen.setHorizontalAlignment(JLabel.CENTER);
	    
	    initMainPanel();
	    
        add(main_panel, BorderLayout.CENTER);
//        add(menuBar, BorderLayout.NORTH);
        add(label_examen, BorderLayout.SOUTH);
        
        // Action a la fermeture (croix)
        // TODO
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initMainPanel() {
		temoin_envoie.setBounds(
			// Decalage à gauche
	    	horizontal_margin,
			// Décalage en haut
	    	vertical_margin,
	    	// Prend le tiers de la largeur - les margins à gauche et à droite
	    	this.getWidth()/3 - 2*horizontal_margin,
	    	// Prend toute la hauteur - les margins en haut et en bas
	    	this.getHeight() - 2*vertical_margin
	    );
		
	    label_etudiant.setHorizontalTextPosition(JLabel.CENTER);
	    label_etudiant.setVerticalTextPosition(JLabel.CENTER);
	    label_etudiant.setBounds(
			// Decalage à gauche : se décale du tiers de la largeur
	    	this.getWidth()/3 + horizontal_margin,
			// Décalage en haut
	    	vertical_margin,
	    	// Prend les deux tiers de la largeur - les margins à gauche et à droite
	    	this.getWidth()*2/3 - 2*horizontal_margin,
	    	// Prend toute la hauteur - les margins en haut et en bas
	    	this.getHeight() - 2*vertical_margin
	    );
	    
	    main_panel.add(temoin_envoie);
	    main_panel.add(label_etudiant);
	}

	public void refreshUI() {
        label_etudiant.setText( etud_nom.toUpperCase() + " " + etud_prenom + " " + etud_numero );
		label_examen.setText( "Vous participez à l'examen n°" + exam_id );
		setVisible(true);
	}
	
	public void displayEventIsSending() {
		// TODO
		System.out.println("Attention, on envoie dans la base ce que tu fais frr");
	}
	
	/**
	 * GETTERS & SETTERS
	 */
	
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

	public String getExam_id() {
		return exam_id;
	}

	public void setExam_id(String exam_id) {
		this.exam_id = exam_id;
		
	}
	
    public static void main(String[] args)
    {
        Window window = Window.getInstance();
    }
	
	public static Window getInstance() {
		if (instance == null) {
			instance = new Window();
		}
		return instance;		
	}

}
