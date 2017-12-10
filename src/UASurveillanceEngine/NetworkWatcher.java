package UASurveillanceEngine;


public abstract class NetworkWatcher extends Watcher{
	
	public NetworkWatcher() {
		super("NETWORK");
	}

	
	public abstract void run();

	/*
	public static void main(String args[]) throws IOException{
		NetworkWatcherHistoryMozilla mozilla = new NetworkWatcherHistoryMozilla("/home/etudiant/.mozilla/firefox/ugm37j7z.default-1462882570889/places.sqlite");
		NetworkWatcherHistoryChrome chrome = new NetworkWatcherHistoryChrome("/home/etudiant/.config/google-chrome/Default/History");
		NetworkWatcherTCP tcp = new NetworkWatcherTCP();
	}
	*/
	
}