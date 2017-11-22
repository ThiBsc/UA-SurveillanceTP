package UASurveillanceEngine;


/**
 * 
 */
public abstract class Watcher extends Thread {

	protected volatile boolean isRecording; //synchronized non autorisé
	
	/**
	 * Default constructor
	 */
	public Watcher() {
	}

	/**
	 * 
	 */
	private String type;

	/**
	 * 
	 */
	protected DatabaseSingleton db;


	/**
	 * @param msg 
	 * @return
	 */
	public boolean sendEvent(String msg) {
		// TODO implement here
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