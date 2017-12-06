package UASurveillanceIHMEtud;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import UASurveillanceEngine.Watcher;

public class Window extends JFrame {

	private static final long serialVersionUID = -5086125816507692170L;

	static Window instance;
	private final Color color_sending = new Color(0, 255, 0);
	private final Color color_waiting = new Color(255, 255, 0);
	
	// Window components Size
	private final int width = 600;
	private final int height = 200;
	private final int horizontal_margin = 10;
	private final int vertical_margin = 10;
	private final int size_temoin_envoie = 100;
	
	private String etud_nom;
	private String etud_prenom;
	// Le numéro étudiant, ça fait encore plus peur aux étudiants
	private String etud_numero;
	private String exam_id;

	private JLabel label_examen;
	private BorderLayout layout;
	
	private JPanel main_panel;
	private JLabel label_etudiant;
	private JPanel temoin_envoie;
	
	private MenuBar menuBar;
	
	private Window()
    {
		layout = new BorderLayout();
		label_examen = new JLabel();
		main_panel = new JPanel();
		temoin_envoie = new JPanel();
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
		main_panel.setLayout(null);
		
		// On place le témoin d'envoie
		temoin_envoie.setBounds(
			// Decalage à gauche: la margin + le décalage qui permet de centrer le temoin
	    	( this.getWidth()/3 - size_temoin_envoie ) /2,
			// Décalage en haut: la margin + le décalage qui permet de centrer le temoin
	    	(this.getHeight() - size_temoin_envoie ) /2,
	    	// Occupe la taille du témoin - les margins à gauche et à droite
	    	size_temoin_envoie - 2*horizontal_margin,
	    	// Occupe toute la hauteur - les margins en haut et en bas
	    	size_temoin_envoie - 2*vertical_margin
	    );
		
		// On met le temoin en couleur d'attente
		temoin_envoie.setBackground(color_waiting);
		
		// On centre le texte du label etudiant
	    label_etudiant.setHorizontalTextPosition(JLabel.CENTER);
	    label_etudiant.setVerticalTextPosition(JLabel.CENTER);
	    
	    // On place le label etudiant
	    label_etudiant.setBounds(
			// Decalage à gauche : se décale du tiers de la largeur
	    	this.getWidth()/3 + horizontal_margin,
			// Décalage en haut
	    	vertical_margin,
	    	// Occupe les deux tiers de la largeur - les margins à gauche et à droite
	    	this.getWidth()*2/3 - 2*horizontal_margin,
	    	// Occupe toute la hauteur - les margins en haut et en bas
	    	this.getHeight() - 2*vertical_margin
	    );
	    
	    // On ajoute les composants au panel
	    main_panel.add(temoin_envoie);
	    main_panel.add(label_etudiant);
	}

	public void refreshUI() {
        label_etudiant.setText( etud_nom.toUpperCase() + " " + etud_prenom + " " + etud_numero );
		label_examen.setText( "Vous participez à l'examen n°" + exam_id );	
	}
	
	public void displayEventIsSending() {
		// On met le fond du témoin en vert pour une demi seconde
		temoin_envoie.setBackground(color_sending);
		System.out.println("Attention, on envoie dans la base ce que tu fais frr");
		// Après 500ms on remet le fond d'attente
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	temoin_envoie.setBackground(color_waiting);
		            }
		        }, 
		        500
		);
	}
	
	/**
	 * GETTERS & SETTERS
	 */
	
	public String getEtud_nom() {
		return etud_nom;
	}

	public void setEtud_nom(String etud_nom) {
		Watcher.ETU_NOM = etud_nom;
		this.etud_nom = etud_nom;
	}

	public String getEtud_prenom() {
		return etud_prenom;
	}

	public void setEtud_prenom(String etud_prenom) {
		this.etud_prenom = etud_prenom;
		Watcher.ETU_PRENOM = etud_prenom;
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
		Watcher.EXAMEN_id = Integer.parseInt(exam_id);
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
