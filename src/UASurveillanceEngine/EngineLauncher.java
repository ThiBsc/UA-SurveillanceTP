package UASurveillanceEngine;

import java.awt.Toolkit;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
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
		if (args.length < 3){
			System.out.println("Usage: program exam_id nom prenom (ip_server)");			
		} else {
			Watcher.EXAMEN_id = Integer.parseInt(args[0]);
			Watcher.ETU_NOM = args[1];
			Watcher.ETU_PRENOM = args[2];
			if (args.length == 4)
				Watcher.IP_SERVER = args[3];
			/**
			 * DirectoryWatcher
			 */
			String home = System.getProperty("user.home");
			// directoriesToWatch pourra être une variable static final
			Vector <Path> directoriesToWatch = new Vector<Path>();
			// Variables à tester
			
			// Pour tester j'ai mis le dossier du projet et celui de téléchargement
			directoriesToWatch.add( Paths.get(home + "/Documents/") );
			//directoriesToWatch.add( Paths.get("/home/"+ usernameEtudiant +"/Téléchargements/") );
			DirectoryWatcher dw = null;
			try {
				dw = new DirectoryWatcher(directoriesToWatch);
			} catch (IOException e) {
				e.printStackTrace();
			}
			dw.start();
			/**
			 * NetworkWatcher
			 */
			NetworkWatcherHistoryChrome nwhc = new NetworkWatcherHistoryChrome(home+"/.config/google-chrome/Default/History");
			//NetworkWatcherHistoryMozilla nwhm = new NetworkWatcherHistoryMozilla("~/.mozilla/firefox/ugm37j7z.default-1462882570889/places.sqlite");
			//NetworkWatcherTCP nwtcp = new NetworkWatcherTCP();
			nwhc.start();
			//nwhm.start();
			//nwtcp.start();
			/**
			 * USBWatcher
			 */
			USBWatcher usbw = new USBWatcher();
			usbw.start();
			/**
			 * ScreenWatcher
			 */
			ScreenWatcher sw = new ScreenWatcher();
			int screen_w, screen_h;
			screen_w = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			screen_h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			FFMpegRunner ffmpeg = new FFMpegRunner(screen_w, screen_h);
			sw.start();
			ffmpeg.start();
			/**
			 * Lancement des Threads
			 */
			/*if (dw != null)
				dw.start();
			nwhc.start();
			nwhm.start();
			nwtcp.start();
			sw.start();
			ffmpeg.start();*/
			Scanner sc = new Scanner(System.in);
			String quit = "";
			System.out.println("Pour quitter, entrez 'q'");
			while (!quit.equals("q"))
				quit = sc.next();
			
			dw.stopRecording();
			nwhc.stopRecording();
			usbw.stopRecording();
			ffmpeg.stop_ffmpeg();
			sw.stopRecording();
		}
	}

}
