package serverEvent;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
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
	public static Vector<String> blackList = new Vector<String>();
	public static String video_path = "";

	public EventReceiver(){
		running = false;
	}

	@Override
	public void run() {
		DatabaseSingleton bdd = DatabaseSingleton.getInstance();
		// prepare
		try {
			bdd.connect("127.0.0.1", "UA-user", "ua_surveillance", "ua-user");
			ResultSet rset = bdd.query("select nom, path from VIDEO_PATH;");
			System.out.println("Choose where to save the movie");
			Vector<String> paths = new Vector<String>();
			int idx = 0;
			while (rset.next()){
				System.out.println(idx++ +". "+rset.getString("nom")+": "+rset.getString("path"));
				paths.add(rset.getString("path"));
			}
			idx = 0;
			Scanner sc = new Scanner(System.in);
			idx = sc.nextInt();
			video_path = paths.get(idx);
			sc.close();
			rset.close();
			ss = new ServerSocket(3615);
			running = true;
			System.out.println("EventReceiver is waiting for suspicious event...");
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
		EventReceiver ea = new EventReceiver();
		if (args.length != 0){
			try {
				BufferedReader br = new BufferedReader(new FileReader(args[0]));
				String line = "";
				while ( (line = br.readLine()) != null) {
					EventReceiver.blackList.add(line);
				}
				br.close();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		ea.start();
	}

}
