package UASurveillanceEngine;

import java.sql.SQLException;

public class NetworkWatcherHistoryMozilla extends NetworkWatcherHistory {

	public NetworkWatcherHistoryMozilla(String historyPath) {
		super(historyPath);
	}
	
	// Contrairement à Chrome
	// On peut se connecter directement sur le fichier qui contient l'historique
	// même quand Mozilla tourne
	protected boolean query() {
		try {
			if(connect()){
				_sqlQuery = "SELECT datetime(last_visit_date/1000000, 'unixepoch','localtime') AS visit_date, url FROM moz_places ORDER BY last_visit_date DESC LIMIT 1;";
				_stmt = _connection.prepareStatement(_sqlQuery);
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	public void run() {
		if(query()){
			try {
				String lastDate = _stmt.executeQuery().getString("visit_date");
				isRecording = true;
				while(isRecording){
					_rs = _stmt.executeQuery();
					
					String date = _rs.getString("visit_date");
					String url = _rs.getString("url");
					
					if(!date.equalsIgnoreCase(lastDate)){
						
						// Résultat de la requête
						System.out.println("Mozilla Firefox : "+date+" : "+url);
						sendEvent(url);
						lastDate = date;
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						System.out.println(e);
					}
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		} else {
			System.out.println(_historyPath);
			System.out.println("Unable to connect database : Mozilla");
		}

	}

}
