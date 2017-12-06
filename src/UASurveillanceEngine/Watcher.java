package UASurveillanceEngine;

import java.awt.Toolkit;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Vector;

import UASurveillanceIHM.DatabaseSingleton;


/**
 * 
 */
public abstract class Watcher extends Thread {

	public static int EXAMEN_id = -1;
	public static String ETU_NOM = null;
	public static String ETU_PRENOM = null;
	public static String IP_SERVER = "127.0.0.1";

	private String type;
	protected volatile boolean isRecording; //synchronized non autorisé
	protected Socket socketEvent;
	protected PrintWriter writer;
	
	/**
	 * Default constructor
	 */
	public Watcher(String type) {
		this.type = type;
		isRecording = false;
	}

	/**
	 * 
	 */
	protected DatabaseSingleton db;

	private boolean canSendEvent(){
		return EXAMEN_id != -1 && ETU_NOM != null && ETU_PRENOM != null;
	}

	/**
	 * @param msg 
	 * @return
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public void sendEvent(String msg) throws UnknownHostException, IOException {
		if (canSendEvent()){
			UASurveillanceIHMEtud.Window.getInstance().displayEventIsSending();
			Date current_date = new Date();
			socketEvent = new Socket(IP_SERVER, 3615);
			DataOutputStream dos = new DataOutputStream(socketEvent.getOutputStream());
			//writer = new PrintWriter(socketEvent.getOutputStream());
			String event_info = type+"|"+EXAMEN_id+"|"+ETU_NOM+"|"+ETU_PRENOM+"|"+current_date.toString()+"|"+msg;
			byte[] event = event_info.getBytes("UTF-8");
			dos.writeInt(event.length);
			dos.write(event);
			dos.flush();
			System.err.println("Sending event: "+event_info);
			dos.close();
			socketEvent.close();
		}
	}
	
	public void sendEventData(int size, byte[] data) throws UnknownHostException, IOException{
		if (canSendEvent()){
			Date current_date = new Date();
			socketEvent = new Socket("127.0.0.1", 3615);
			DataOutputStream dos = new DataOutputStream(socketEvent.getOutputStream());
			//writer = new PrintWriter(socketEvent.getOutputStream());
			String event_info = type+"|"+EXAMEN_id+"|"+ETU_NOM+"|"+ETU_PRENOM+"|"+current_date.toString();
			byte[] event = event_info.getBytes("UTF-8");
			dos.writeInt(event.length);
			dos.write(event);
			dos.writeInt(size);
			dos.write(data);
			dos.flush();
			dos.close();
			socketEvent.close();
		}
	}
	
	/**
	 * Indique si un enregistrement est en cours
	 * @return isRecording
	 */
	public boolean isRecording() {

		return isRecording;

	}
	

	/**
	 * Modifie l'état de l'enregistrement
	 * @param state l'état de l'enregistrement
	 */
	public void setRecording(boolean state) {

		this.isRecording = state;
	}

	/**
	 * 
	 */
	public void stopRecording() {
		this.isRecording=false;
	}
	
	static public void startWatchers() {

		/**
		 * DirectoryWatcher
		 */
		// directoriesToWatch pourra être une variable static final
		Vector <Path> directoriesToWatch = new Vector<Path>();	
//		String home_path = System.getProperty("user.home");
		String home_path = "/home/etudiant";
		// Variables à tester
		
		// Pour tester j'ai mis le dossier du projet et celui de téléchargement
		directoriesToWatch.add( Paths.get(home_path) );
		DirectoryWatcher dw = null;
		try {
			dw = new DirectoryWatcher(directoriesToWatch);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * NetworkWatcher
		 */
		NetworkWatcherHistoryChrome nwhc = new NetworkWatcherHistoryChrome("/home/thibaut/.config/google-chrome/Default/History");
		NetworkWatcherHistoryMozilla nwhm = new NetworkWatcherHistoryMozilla("~/.mozilla/firefox/ugm37j7z.default-1462882570889/places.sqlite");
		NetworkWatcherTCP nwtcp = new NetworkWatcherTCP();
		/**
		 * USBWatcher
		 */
		USBWatcher usbw = new USBWatcher();
		/**
		 * ScreenWatcher
		 */
		ScreenWatcher sw = new ScreenWatcher();
		int screen_w, screen_h;
		screen_w = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		screen_h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		FFMpegRunner ffmpeg = new FFMpegRunner(screen_w, screen_h);
		/**
		 * Lancement des Threads
		 */
		dw.start();
		nwhc.start();
		nwhm.start();
		nwtcp.start();
		sw.start();
		ffmpeg.start();
		usbw.start();
	}

}