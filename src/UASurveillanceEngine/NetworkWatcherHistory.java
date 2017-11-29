package UASurveillanceEngine;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class NetworkWatcherHistory extends NetworkWatcher {
	protected String _historyPath;
	protected Connection _connection;
	protected String _sqlQuery;
	protected PreparedStatement _stmt;
	protected ResultSet _rs;
	
	
	public NetworkWatcherHistory(String historyPath) {
		super();
		_historyPath = historyPath;
	}
	
	// On se "connecte" sur le fichier base de donnée
	// qui contient l'historique du navigateur
	protected boolean connect() throws SQLException{
		File history = new File(_historyPath);
		
		if(history.exists()){
			_connection = DriverManager.getConnection("jdbc:sqlite:"+_historyPath);
			return (_connection != null);
		} else {
			System.out.println("File "+_historyPath+" not detected");
			return false;
		}
	}
	
	protected abstract boolean query();
	
	public abstract void run();
}
