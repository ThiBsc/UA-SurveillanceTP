package UASurveillanceEngine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import serverEvent.DatabaseSingleton;


/**
 * Classe abstraite gérant les différents types d'évènements
 */
public abstract class Watcher extends Thread {

	/**
	 * Variables de connexion à la base
	 */
	public static int EXAMEN_id = -1;
	public static String ETU_NOM = null;
	public static String ETU_PRENOM = null;
	public static String IP_SERVER = "127.0.0.1";
	protected DatabaseSingleton db;

	/**
	 * Variables communes a tous les watchers
	 */
	private String type;
	protected volatile boolean isRecording; //synchronized non autorisé
	protected Socket socketEvent;
	
	public Watcher(String type) {
		// ctor
		this.type = type;
		isRecording = false;
	}

	/**
	 * Pour savoir s'il est possible d'envoyer un évènement au serveur
	 * @return TRUE si c'est possible, sinon FALSE
	 */
	private boolean canSendEvent(){
		return EXAMEN_id != -1 && ETU_NOM != null && ETU_PRENOM != null;
	}

	/**
	 * Envoie au serveur le message d'information
	 * @param msg
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
	
	/**
	 * Envoie au serveur des byte
	 * @param size - La taille de data à lire
	 * @param data - Les data à lire
	 */
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
	 * Permet de stopper l'enregistrement
	 */
	public void stopRecording() {
		this.isRecording=false;
	}

}