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
	public void sendEvent(String msg) {
		if (canSendEvent()){
			try {
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
			} catch (UnknownHostException e) {
				// Si on n'arrive pas à joindre l'host alors on le fait savoir à l'IHM etudiante
				UASurveillanceIHMEtud.Window.getInstance().setErreur_connexion_serveur("Impossible de se connecter à " + Watcher.IP_SERVER + ". Vérifier l'adresse IP.");
			} catch (IOException e) {
				// S'il y a une erreur, on le fait savoir
				UASurveillanceIHMEtud.Window.getInstance().setErreur_connexion_serveur(e.getMessage() + " IP: "+ Watcher.IP_SERVER );
			}
		}
	}
	
	public void sendEventData(int size, byte[] data) {
		try {
			if (canSendEvent()){
				Date current_date = new Date();
				socketEvent = new Socket(IP_SERVER, 3615);
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
		} catch (UnknownHostException e) {
			// Si on n'arrive pas à joindre l'host alors on le fait savoir à l'IHM etudiante
			UASurveillanceIHMEtud.Window.getInstance().setErreur_connexion_serveur("Impossible de se connecter à " + Watcher.IP_SERVER + ". Vérifier l'adresse IP.");
		} catch (IOException e) {
			// S'il y a une erreur, on le fait savoir
			UASurveillanceIHMEtud.Window.getInstance().setErreur_connexion_serveur(e.getMessage() + " IP: "+ Watcher.IP_SERVER );
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

}