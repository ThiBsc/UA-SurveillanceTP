package UASurveillanceIHM;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;


public class StatusBar extends JPanel {
	
	private JLabel status;

	public StatusBar(){
		status = new JLabel();
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(status);
	}
	
	public void setStatus(StatusType type, String msg){
		status.setText(msg);
		status.setForeground(type.getColor());
	}

}
