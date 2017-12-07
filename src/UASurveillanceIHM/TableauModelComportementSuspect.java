package UASurveillanceIHM;

public class TableauModelComportementSuspect extends TableauModel<ComportementSuspect> {

	private static final long serialVersionUID = -3004803702982420283L;

	public TableauModelComportementSuspect(){
		super(ComportementSuspect.class);
	}
	
	@Override
	public int getColumnCount() {
		return 3;
	}
	
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return "Nom";
			case 1:
				return "Prénom";
			case 2:
				return "Type";
			default:
				return "";
		}
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0:
				return getObjectTAt(rowIndex).getM_nom();
			case 1:
				return getObjectTAt(rowIndex).getM_prenom();
			case 2:
				return getObjectTAt(rowIndex).getM_type();
			default:
				return "";
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
