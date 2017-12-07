package UASurveillanceIHM;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;


public class SplitPane extends JSplitPane {
	
	private JSplitPane splitPane;
	private JScrollPane scrollPane;
	private Tableau tableauExamen;
	private TableauModelExamen tableauModelExamen;
	private JTabbedPane tabbedPane;
	
	public SplitPane(){
		super(HORIZONTAL_SPLIT);

		tableauModelExamen = new TableauModelExamen();
		tableauModelExamen.addValue(new Examen());
		tableauExamen = new Tableau(tableauModelExamen);
		tableauExamen.setOpaque(false);
		tableauExamen.setBackground(new Color(255, 255, 255, 220));
		
		scrollPane = new JScrollPane(tableauExamen);
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Comportements suspects", null);
		tabbedPane.addTab("Vidéos", null);
		
		setLeftComponent(scrollPane);
		setRightComponent(tabbedPane);
	}

}
