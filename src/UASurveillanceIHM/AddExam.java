package UASurveillanceIHM;

import java.awt.*;
import javax.swing.*;

public class AddExam extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1771694053323326124L;

	public AddExam(){
		super();
		
		buildContentPane();
	}

	private JPanel buildContentPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
 
		JLabel daEx = new JLabel("Date Examen");
		JTextField dateExam = new JTextField();
		JLabel clEx = new JLabel("Classe Examen");
		JTextField classeExam = new JTextField();
		JLabel maEx = new JLabel("Matière Examen");
		JTextField matiereExam = new JTextField();
		JLabel duEx = new JLabel("Durée Examen");
		JTextField dureeExam = new JTextField();
		dateExam.setColumns(10);
		classeExam.setColumns(20);
		matiereExam.setColumns(20);
		dureeExam.setColumns(20);
 
		panel.add(daEx);
		panel.add(dateExam);
		panel.add(clEx);
		panel.add(classeExam);
		panel.add(maEx);
		panel.add(matiereExam);
		panel.add(duEx);
		panel.add(dureeExam);
		
		// Taille de la frame
	    panel.setSize(800, 600);
	    // Placer au centre de l'ecran
	    setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        setVisible(true);
 
		return panel;
	}
	
	public static void main(String[] args) {
		new AddExam();
	}
	
}
