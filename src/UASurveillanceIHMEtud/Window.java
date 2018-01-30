package UASurveillanceIHMEtud;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import UASurveillanceEngine.DirectoryWatcher;
import UASurveillanceEngine.FFMpegRunner;
import UASurveillanceEngine.NetworkWatcherHistoryChrome;
import UASurveillanceEngine.NetworkWatcherHistoryMozilla;
import UASurveillanceEngine.NetworkWatcherTCP;
import UASurveillanceEngine.ScreenWatcher;
import UASurveillanceEngine.USBWatcher;
import UASurveillanceEngine.Watcher;

public class Window extends JFrame {

	private static final long serialVersionUID = -5086125816507692170L;

	// Singleton
	static Window instance;
	
	// Permet de récupérer les erreurs de connexion au serveur
	private String erreur_connexion_serveur;

	// Envoie = vert
	private final Color color_sending = new Color(0, 255, 0);
	// Attente = jaune
	private final Color color_waiting = new Color(255, 255, 0);
	
	// Window components Size
	private final int width = 500;
	private final int height = 200;
	private final int horizontal_margin = 10;
	private final int vertical_margin = 10;
	private final int size_temoin_envoie = 130;
	private String etud_nom;
	private String etud_prenom;
	private String exam_id;

	// Components
	private JLabel label_bottom;
	private JPanel main_panel;
	private JLabel label_etudiant;
	private JPanel temoin_envoie;
	private MenuBar menuBar;
	
	// Watchers
	private Vector<Watcher> watchers;
	private FFMpegRunner ffmpeg;
	
	// Constructeur
	public Window()
    {
		// Composants
		label_bottom = new JLabel();
		main_panel = new JPanel();
		temoin_envoie = new JPanel();
		label_etudiant = new JLabel();
		menuBar = new MenuBar();
		watchers = new Vector<Watcher>();
		
        initUI();
        
        setVisible(true);
    }
	
	private void initUI() {
		setLayout(new BorderLayout());
		setTitle("UA-SurveillanceTP");
		
	    // Taille de la frame
	    setSize(width, height);
	    
	    // Placer au centre de l'ecran
	    setLocationRelativeTo(null);
	    
	    // Resizable ou non
	    setResizable(false);
        
        // Avertissement à la fermeture
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    this.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent we)
	        { 
	            String warning_text = "<html>";
		            warning_text += "Êtes-vous sûr de vouloir quitter l'application ?<br>";
		            warning_text += "<i>Le professeur sera averti</i>";
	            warning_text += "</html>";
	            
	            int PromptResult = JOptionPane.showConfirmDialog( null, warning_text, "Vous allez quitter l'application", JOptionPane.WARNING_MESSAGE );
	            
	            if(PromptResult==JOptionPane.YES_OPTION)
	            {
	            	// Si L'utilisateur est sûr de vouloir quitter alors on prévient le serveur et on arrête tout
	            	// sendEvent("APPLICATION"+"|"+EXAMEN_id+"|"+ETU_NOM+"|"+ETU_PRENOM+"|"+current_date.toString()+"|"+"DECONNEXION");
	            	stopWatchers();
	                System.exit(0);
	            }
	        }
	    });

	    label_bottom.setHorizontalAlignment(JLabel.CENTER);
	    
	    initMainPanel();
	    
        add(main_panel, BorderLayout.CENTER);
        add(menuBar, BorderLayout.NORTH);
        add(label_bottom, BorderLayout.SOUTH);
	}
	
	private void initMainPanel() {
		main_panel.setLayout(null);
		
		// On place le témoin d'envoie
		temoin_envoie.setBounds(
			// Decalage à gauche: la margin + le décalage qui permet de centrer le temoin
	    	( this.getWidth()/3 - size_temoin_envoie ) /2,
			// Décalage en haut: la margin + le décalage qui permet de centrer le temoin
	    	vertical_margin,
	    	// Occupe la taille du témoin - les margins à gauche et à droite
	    	size_temoin_envoie - 2*horizontal_margin,
	    	// Occupe la taille du témoin
	    	size_temoin_envoie
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
	    	0,
	    	// Occupe les deux tiers de la largeur - les margins à gauche et à droite
	    	this.getWidth()*2/3 - 2*horizontal_margin,
	    	// Occupe toute la hauteur - les margins en haut et en bas
	    	this.getHeight()
	    );
	    label_etudiant.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 15));
	    
	    // On ajoute les composants au panel
	    main_panel.add(temoin_envoie);
	    main_panel.add(label_etudiant);
	}

	public void refreshUI() {
        label_etudiant.setText(  etud_prenom + " " + etud_nom.toUpperCase() );
		label_bottom.setText( "Vous participez à l'examen n°" + exam_id );	
	}
	
	public void displayEventIsSending() {
		// On met le fond du témoin en vert pour une demi seconde
		temoin_envoie.setBackground(color_sending);
		
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
	 * Pour chaque setters, on met à jour les variables des watchers
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

	public String getExam_id() {
		return exam_id;
	}

	public void setExam_id(String exam_id) {
		Watcher.EXAMEN_id = Integer.parseInt(exam_id);
		this.exam_id = exam_id;
		
	}

	public void setErreur_connexion_serveur(String erreur_connexion_serveur) {
		this.erreur_connexion_serveur = erreur_connexion_serveur;
		label_bottom.setText(this.erreur_connexion_serveur);
	}
	
	public void startWatchers(){
		/**
		 * DirectoryWatcher
		 */
		// directoriesToWatch pourra être une variable static final
		Vector <Path> directoriesToWatch = new Vector<Path>();	
		// String home_path = System.getProperty("user.home");
		String home_path = "/home/etudiant";
		// Variables à tester
		
		// Pour tester j'ai mis le dossier du projet et celui de téléchargement
		directoriesToWatch.add( Paths.get(home_path) );
		DirectoryWatcher dw = null;
		try {
			watchers.add(new DirectoryWatcher(directoriesToWatch));
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * NetworkWatcher
		 */
		watchers.add(new NetworkWatcherHistoryChrome(home_path + "/.config/google-chrome/Default/History"));
		watchers.add(new NetworkWatcherHistoryMozilla(home_path + "/.mozilla/firefox/ugm37j7z.default-1462882570889/places.sqlite"));
		watchers.add(new NetworkWatcherTCP());
		/**
		 * USBWatcher
		 */
		watchers.add(new USBWatcher());
		/**
		 * ScreenWatcher
		 */
		watchers.add(new ScreenWatcher());
		int screen_w, screen_h;
		screen_w = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		screen_h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		ffmpeg = new FFMpegRunner(screen_w, screen_h);
		/**
		 * Lancement des Threads
		dw.start();
		nwhc.start();
		nwhm.start();
		nwtcp.start();
		sw.start();
		ffmpeg.start();
		usbw.start();
		 */
		for (Watcher w : watchers){
			w.start();
		}
		ffmpeg.start();
	}
	
	public void stopWatchers(){
		for (Watcher w : watchers){
			if (w instanceof ScreenWatcher){
				String duration = ffmpeg.stop_ffmpeg();
				w.sendEvent("MOVIE_DURATION;"+duration);
				ffmpeg = null;
			}
			w.stopRecording();
		}
	}
	
    public static void main(String[] args)
    {
    	UserExamenDialog ued = new UserExamenDialog();
    	if (ued.showConfirmDialog() == JOptionPane.OK_OPTION){
    		Window w = new Window();
    		w.setEtud_nom(ued.getNom());
    		w.setEtud_prenom(ued.getPrenom());
    		w.setExam_id(String.valueOf(ued.getIdExam()));
    		w.refreshUI();
    		Watcher.ihm = w;
    	}
    }

}
