package UASurveillanceEngine;

import java.awt.Toolkit;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

/**
 * 
 * EngineLauncher pour lancer toutes les threads
 * du moteur et pouvoir effectuer des tests
 *
 */
public class EngineLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * DirectoryWatcher
		 */
		// directoriesToWatch pourra être une variable static final
		Vector <Path> directoriesToWatch = new Vector<Path>();	
		String usernameEtudiant = "etudiant";
		// Variables à tester
		
		// Pour tester j'ai mis le dossier du projet et celui de téléchargement
		directoriesToWatch.add( Paths.get("/home/"+ usernameEtudiant +"/Documents/") );
		directoriesToWatch.add( Paths.get("/home/"+ usernameEtudiant +"/Téléchargements/") );
		DirectoryWatcher dw = null;
		try {
			dw = new DirectoryWatcher(directoriesToWatch);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * NetworkWatcher
		 */
		NetworkWatcherHistoryChrome nwhc = new NetworkWatcherHistoryChrome("/home/etudiant/.config/google-chrome/Default/History");
		NetworkWatcherHistoryMozilla nwhm = new NetworkWatcherHistoryMozilla("/home/etudiant/.mozilla/firefox/ugm37j7z.default-1462882570889/places.sqlite");
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
		if (dw != null)
			dw.start();
		nwhc.start();
		nwhm.start();
		nwtcp.start();
		sw.start();
		ffmpeg.start();
	}

}
