package serverEvent;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Classe du serveur recevant tous les évènements pour les répartir correctement
 */
public class EventReceiver extends Thread {

	/**
	 * Variables du serveur
	 */
	private ServerSocket ss;
	private boolean running;
	public static Vector<String> blackList;
	public static String video_path = "";

	public EventReceiver(){
		blackList = new Vector<String>();
		running = false;
	}
	
	@Override
	public void run() {
		DatabaseSingleton bdd = DatabaseSingleton.getInstance();
		System.out.println("EventReceiver is waiting for suspicious event...");
		// prepare
		try {
			bdd.connect("127.0.0.1", "UA-user", "ua_surveillance", "ua-user");
			ResultSet rset = bdd.query("select path from VIDEO_PATH where nom like \"Test\";");
			rset.next();
			video_path = rset.getString("path");
			rset.close();
			ss = new ServerSocket(3615);
			running = true;
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		// listening
		while (running){
			try {
				Socket clientSocket = ss.accept();
				EventThread et = new EventThread(clientSocket);
				et.start();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		// leaving
		try {
			bdd.disconnect();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Méthode pour savoir si le serveur est en écoute
	 * @return TRUE si oui, sinon FALSE
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Permet de mettre fin à la thread du serveur
	 */
	public void stopRunning() {
		this.running = false;
	}
	
	public static void main(String[] args) {
		// String blacklistFile = args[0];
		
		EventReceiver ea = new EventReceiver();
		ea.blackList.add("docs google");
		ea.blackList.add("facebook");
		ea.start();
	}

}
