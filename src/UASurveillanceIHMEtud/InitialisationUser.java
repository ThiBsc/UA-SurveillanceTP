package UASurveillanceIHMEtud;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class InitialisationUser implements ActionListener {

	private JFrame frame;
	private JLabel label_user_firstname;
	private JLabel label_user_lastname;
	private JLabel label_user_num;
	private JTextField field_user_firstname;
	private JTextField field_user_lastname;
	private JTextField field_user_num;
	private JButton button_submit;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame = new JFrame();
		label_user_firstname = new JLabel("Prénom");
		label_user_lastname = new JLabel("Nom");
		label_user_num = new JLabel("N° étudiant");
		field_user_firstname = new JTextField();
		field_user_lastname = new JTextField();
		field_user_num = new JTextField();
		button_submit = new JButton("Valider");
		
		button_submit.addActionListener(new ActionListener(){ 
			  public void actionPerformed(ActionEvent e) {
				  Window window = Window.getInstance();
				  window.setEtud_nom(field_user_lastname.getText());
				  window.setEtud_prenom(field_user_firstname.getText());
				  window.setEtud_numero(field_user_num.getText());
				  window.refreshUI();
				  frame.dispose();
			  } 
		});
		
		initFrame();
		
		frame.setVisible(true);
	}

	private void initFrame() {
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setTitle("Qui suis-je ?");
	    // Taille de la frame
		frame.setSize(600, 200);
	    // Placer au centre de l'ecran
		frame.setLocationRelativeTo(null);
		
		initForm();
	}
	
	private void initForm() {
		System.out.println(frame.getLayout());
		label_user_firstname.setLabelFor(field_user_firstname);
		label_user_lastname.setLabelFor(field_user_lastname);
		label_user_num.setLabelFor(field_user_num);
		
		frame.add(label_user_firstname);
		frame.add(field_user_firstname);
		frame.add(label_user_lastname);
		frame.add(field_user_lastname);
		frame.add(label_user_num);
		frame.add(field_user_num);
		frame.add(button_submit);
	}
	
}
