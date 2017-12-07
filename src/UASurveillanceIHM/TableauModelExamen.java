package UASurveillanceIHM;


public class TableauModelExamen extends TableauModel<Examen> {
	
	private static final long serialVersionUID = 2575720868941743197L;
	
	public TableauModelExamen(){
		super(Examen.class);
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return "Date";
			case 1:
				return "Classe";
			case 2:
				return "Matière";
			case 3:
				return "Durée";
			default:
				return "";
		}
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
        case 0:
//            return getColumnName(columnIndex);
            return _dataList.get(rowIndex).getDate();
        case 1:
//            return getColumnName(columnIndex);
            return _dataList.get(rowIndex).getProm_id();
        case 2:
//            return getColumnName(columnIndex);
            return _dataList.get(rowIndex).getMat_id();
        case 3:
//            return getColumnName(columnIndex);
            return _dataList.get(rowIndex).getDuree();
        default:
            return "";
    }
	}
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
