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

	public static int EXAMEN_id = -1;
	public static String ETU_NOM = null;
	public static String ETU_PRENOM = null;

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
			Date current_date = new Date();
			socketEvent = new Socket("127.0.0.1", 3615);
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

}