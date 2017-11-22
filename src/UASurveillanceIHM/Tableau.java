package UASurveillanceIHM;

import javax.swing.JTable;
import javax.swing.table.TableModel;


public class Tableau extends JTable {	
	
	private static final long serialVersionUID = 1L;

	public Tableau() {
		super();
		init();
	}
	public Tableau(TableModel dm) {
		super(dm);
		init();
	}
	
	private void init(){
		//setOpaque(false);
		setAutoCreateRowSorter(true);
	}

}
