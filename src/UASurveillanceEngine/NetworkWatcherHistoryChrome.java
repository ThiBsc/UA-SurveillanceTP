package UASurveillanceEngine;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NetworkWatcherHistoryChrome extends NetworkWatcherHistory {

	public NetworkWatcherHistoryChrome(String historyPath) {
		super(historyPath);
	}
	
	// La méthode qui copie l'historique
	private void copyHistory(){
		Path FROM = Paths.get(_historyPath);
		Path TO = Paths.get("/tmp/History.sqlite");
		CopyOption[] options = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
		
		try {
			Files.copy(FROM, TO, options);
		} catch(IOException e) {
			System.out.println(e);
		}
	}
	
	// On peut pas lire le fichier qui contient l'historique de Chrome
	// donc on copie le fichier pour ensuite pouvoir lire dans la copie
	protected boolean connect(){
		File history = new File(_historyPath);
		if(history.exists()){
			copyHistory();
			try {
				_connection = DriverManager.getConnection("jdbc:sqlite:/tmp/History.sqlite");
				return (_connection != null);
			} catch (SQLException e) {
				System.out.println(e);
			}
			return false;
		} else {
			return false;
		}
	}
	
	// Si on a réussi à se connecté
	// on prépare la requête pour pouvoir consulter la base
	protected boolean query() {
		try {
			if(connect()){
				_sqlQuery = "SELECT datetime((last_visit_time/1000000) - 11644473600, 'unixepoch', 'localtime') AS visit_date, url FROM urls ORDER BY last_visit_time DESC LIMIT 1;";
				_stmt = _connection.prepareStatement(_sqlQuery);
				return true;
			} else {
				return false;
			}
		} catch(SQLException e){
			System.out.println(e);
		}
		return false;
	}

	// On consulte la base de donnée ici
	public void run() {
		if(query()){
			// On récupère la date à laquelle la base a été modifiée pour la dernière fois
			File history = new File(_historyPath);
			long lastDate = history.lastModified();
			isRecording = true;
			while(isRecording){
				long date = history.lastModified();
				// On regarde si la date de modifiaction est différente
				// Si c'est la cas ça veut dire qu'une page a été chargée
				if(date != lastDate){
					lastDate = date;
					// On se reconnecte
					// On récupère les données et on les affiche
					try {
						_connection.close();
						if(connect()){
							_stmt = _connection.prepareStatement(_sqlQuery);
							_rs = _stmt.executeQuery();
							
							String dateToString = _rs.getString("visit_date");
							String url = _rs.getString("url");
							
							// Résultat de la requête
							System.out.println("Google Chrome : "+dateToString+" : "+url);
							sendEvent(url);
						} else {
							System.out.println("Unable to connect database : Chrome");
						}
					} catch (SQLException e) {
						System.err.println(e.getMessage());
					}
				}
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			}
		} else {
			System.out.println("Unable to connect database : Chrome");
		}
	}

}
