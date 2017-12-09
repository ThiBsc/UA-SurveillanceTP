package UASurveillanceIHM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import UASurveillanceEngine.ConnexionBase;


public class SplitPane extends JSplitPane {
	/*
	 * Le SplitPane est un Singleton, on en utilise qu'un seul.
	 * et on peut en avoir besoin à différent moment dans le programme
	 * l'utilisation du Singleton me semblait pertinente
	 * 
	 *Jonathan
	 */
	//private JSplitPane splitPane;
	private JScrollPane scrollPane;
	private Tableau tableauExamen;
	private TableauModelExamen tableauModelExamen;
	private JTabbedPane tabbedPane;
	
	private TableauModelComportementSuspect tableauModelComportementSuspect;
	private JScrollPane scrollComportementSuspect;
	private Tableau tableauComportementSuspect;

	private ConnexionBase conn;
	
	private JPanel p1 = new JPanel();
	
	private static volatile SplitPane m_splitpane = null;
	
	private SplitPane(){
		super(HORIZONTAL_SPLIT);
		conn = ConnexionBase.getInstance();

		tableauModelExamen = new TableauModelExamen();
		tableauExamen = new Tableau(tableauModelExamen);
		tableauExamen.setOpaque(false);
		tableauExamen.setBackground(new Color(255, 255, 255, 220));
		
		scrollPane = new JScrollPane(tableauExamen);
		tabbedPane = new JTabbedPane();
		
		tableauModelComportementSuspect = new TableauModelComportementSuspect();
		tableauComportementSuspect = new Tableau(tableauModelComportementSuspect);
		scrollComportementSuspect = new JScrollPane(tableauComportementSuspect);
		
		tabbedPane.addTab("Comportements suspects", scrollComportementSuspect);
//		tabbedPane.addTab("Vidéos", p1);
		
		
		ChangeListener changeListener = new ChangeListener() {
		      public void stateChanged(ChangeEvent changeEvent) {
		        JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
		        int index = sourceTabbedPane.getSelectedIndex();
		        System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
		        if(sourceTabbedPane.getTitleAt(index).equals("Comportements suspects")){
		        	
		        	scrollComportementSuspect.setVisible(true);
		        	p1.setVisible(false);
		        }
		        else{
		        	scrollComportementSuspect.setVisible(false);
		        	p1.setVisible(true);
		        	
		        }		        	
		        	
		      }
		    };
		
		tabbedPane.addChangeListener(changeListener);
		
		setLeftComponent(scrollPane);
		setRightComponent(tabbedPane);
		
		tableauExamen.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if(e.getButton()== MouseEvent.BUTTON1){
					System.out.println("la ligne sélectionné est: "+tableauExamen.getSelectedRow());
					// quand on clique sur un exam
					// on fait la requete pour savoir ses comportements suspects
					// puis on les ajoute dans l'examen (l'objet)
					// et on les affiche dans le tableauModelComportement
					tableauModelComportementSuspect.resetmodel();
					int id = tableauModelExamen.getObjectTAt(tableauExamen.getSelectedRow()).getId();
					ArrayList<ComportementSuspect> c = conn.allSuspect(id);
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