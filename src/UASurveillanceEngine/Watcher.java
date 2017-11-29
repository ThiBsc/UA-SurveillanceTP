package UASurveillanceEngine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import UASurveillanceIHM.DatabaseSingleton;


/**
 * 
 */
public abstract class Watcher extends Thread {

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


	/**
	 * @param msg 
	 * @return
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public boolean sendEvent(String msg) throws UnknownHostException, IOException {
		Date current_date = new Date();
		socketEvent = new Socket("127.0.0.1", 3615);
		DataOutputStream dos = new DataOutputStream(socketEvent.getOutputStream());
		//writer = new PrintWriter(socketEvent.getOutputStream());
		String nom, prenom;
		nom = prenom = "";
		String event_info = type+"|"+nom+"|"+prenom+"|"+current_date.toString()+"|"+msg;
		byte[] event = event_info.getBytes("UTF-8");
		dos.writeInt(event.length);
		dos.write(event);
		dos.close();
		socketEvent.close();
		return false;
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