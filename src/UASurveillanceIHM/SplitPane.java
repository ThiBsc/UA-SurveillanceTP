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
		
		tableauExamen.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if(e.getButton()== MouseEvent.BUTTON1){
					System.out.println("la ligne sélectionné est: "+tableauExamen.getSelectedRow());
					// quand on cliqu sur un exam
					// on fai la requte pour savoir ses comportements suspects
					// puis on les ajoute dans l'examen (l'objet)
					// et on les affiche dans le tableauModelComportement
					tableauModelComportementSuspect.resetmodel();
					int id = tableauModelExamen.getObjectTAt(tableauExamen.getSelectedRow()).getId();
					ArrayList<ComportementSuspect> c = conn.allSuspect(id);
					
//					ArrayList<ComportementSuspect> c = tableauModelExamen.getObjectTAt(tableauExamen.getSelectedRow()).getComportementsSuspects();
					for(ComportementSuspect i: c){
						tableauModelComportementSuspect.addValue(i);
					}
					if (tableauModelComportementSuspect.getRowCount() > 0)
						tableauModelComportementSuspect.fireTableRowsInserted(0, tableauModelComportementSuspect.getRowCount()-1);
					else
						tableauModelComportementSuspect.fireTableStructureChanged();											
				}
				else if(e.getButton()== MouseEvent.BUTTON3){
			}
			}
		});
		
		tableauModelExamen.addValue(conn.allExam()); 		
		
	}
	
	public static SplitPane getInstance(){
		if(SplitPane.m_splitpane == null){
			synchronized(SplitPane.class){
				SplitPane.m_splitpane = new SplitPane();
				SplitPane.m_splitpane.setResizeWeight(0.75); //Pour la répartition de la taille des 2 panneaux
			}
		}
		return SplitPane.m_splitpane;
	}
	
	public void resetDisplay(){
		tableauModelExamen.resetmodel();
		tableauModelExamen.addValue(conn.allExam());
		tableauModelExamen.fireTableDataChanged();
	}
	public void ajouterLigneExamen(Examen exam){
		//tableauModelExamen.addValue(exam);
		tableauModelExamen.fireTableStructureChanged();
		System.out.println(tableauExamen.getSelectedRow());
	}

}
