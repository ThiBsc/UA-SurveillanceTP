package UASurveillanceIHMEtud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class FrameInitialisationExamen extends PopUp {

	private static final long serialVersionUID = -3170639246962101168L;

	// Singleton
	static FrameInitialisationExamen instance;
	
	private JLabel label_exam_id;
	private JTextField field_exam_id;
	private JButton button_submit;
	
	private FrameInitialisationExamen() {
		super();
		
		label_exam_id = new JLabel("ID de l'examen");
		field_exam_id = new JTextField();
		button_submit = new JButton("Valider");
		
		initComponents();
		putComponents();		
		
		this.setVisible(true);
	}
	
	protected void initComponents() {

		button_submit.addActionListener(new ActionListener(){ 
			  public void actionPerformed(ActionEvent e) {
				  Window window = Window.getInstance();
				  window.setExam_id(field_exam_id.getText());
				  window.refreshUI();
				  getInstance().dispose();
			  } 
		});
		
	}
	
	protected void putComponents() {
		label_exam_id.setLabelFor(field_exam_id);
		
		addComponent(label_exam_id, false);
		addComponent(field_exam_id, true);
		addComponent(button_submit, true);
	}
	
	public static FrameInitialisationExamen getInstance() {
		if ( instance == null ) {
			instance = new FrameInitialisationExamen();
		} else {
			instance.setVisible(true);
		}
		return instance;
	}
	
}
