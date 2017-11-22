package UASurveillanceIHM;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Window extends JFrame {
	
	private static final long serialVersionUID = 8799814155423117081L;
	/** menuBar: la barre de menu permettant des interactions avec le programme */
	private MenuBar menubar;
	private SplitPane splitPane;
	private StatusBar status;
	private BorderLayout layout;
	
	private Window()	
    {
		layout = new BorderLayout();
		menubar = new MenuBar();
		splitPane = new SplitPane();
		status = new StatusBar();
		status.setStatus(StatusType.INFO, "Surveillance TP");
        initUI();
    }
	private void initUI(){
		setLayout(layout);
		setTitle("UA-SurveillanceTP");
	    // Taille de la frame
	    setSize(800, 600);
	    // Placer au centre de l'ecran
	    setLocationRelativeTo(null);
	    // Resizable ou non
	    setResizable(true);
        add(menubar, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);
        // Action a la fermeture (croix)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        setVisible(true);
	}

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Window();
            }
        });
    }

}