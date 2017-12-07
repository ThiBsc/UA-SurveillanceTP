package UASurveillanceIHM;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class TableauModel<T> extends AbstractTableModel implements TableModel {
	
	private static final long serialVersionUID = 1L;
	
	protected ArrayList<T> _dataList;
	
	public TableauModel(Class<T> type){
		_dataList = new ArrayList<T>();
	}

	public int getColumnCount() {
		return 0;
	}
	public int getRowCount() {
		return _dataList.size();
	}
	public Object getValueAt(int arg0, int arg1) {
		return null;
	}
	@Override
	public String getColumnName(int columnIndex){
		return ""+columnIndex;
	}
	
	public void addValue(T o) {	
		_dataList.add(o);
	}
	
	public T getObjectTAt(int row){
		return _dataList.get(row);
	}
	public void resetmodel(){
		_dataList.clear();
	}

}
