package UASurveillanceIHMEtud;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Window extends JFrame {
		
	private JLabel temoinDenvoie;
	private JLabel messageDenvoie;
	private BorderLayout layout;
	
	private Window()
    {
		layout = new BorderLayout();
		temoinDenvoie = new JLabel("AH");
		messageDenvoie = new JLabel("Ouai");
        initUI();
    }
	
	private void initUI() {
		setLayout(layout);
		setTitle("UA-SurveillanceTP");
	    // Taille de la frame
	    setSize(400, 200);
	    // Placer au centre de l'ecran
	    setLocationRelativeTo(null);
	    // Resizable ou non
	    setResizable(false);
        add(temoinDenvoie, BorderLayout.WEST);
        add(messageDenvoie, BorderLayout.CENTER);
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
