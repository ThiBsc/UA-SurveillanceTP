package UASurveillanceEngine;

import java.io.File;
import java.util.Date;


/**
 * 
 */
public class USBWatcher extends Watcher {

	/**
	 * Default constructor
	 */
	public USBWatcher() {
		super("USB");
	}
		
	@Override
	public void run() {
		if(System.getProperty("os.name").equalsIgnoreCase("linux")){
			System.out.println("OS détecté : "+System.getProperty("os.name"));
			
			// on initialise un file sur le fichier /dev
			File dev = new File("/dev");
			String[] devices = dev.list();
			int connected_devices = devices.length;		
			
			// On regarde ensuite si le nombre de fichier/dossier dans /dev change
			// si c'est le cas c'est qu'il y a eu un changement au niveau des périphériques connectés
			isRecording = true;
			while(isRecording){
				devices = dev.list();
				Date current_date = new Date();			// pour voir la date quand il y a un changement
				if(devices.length != connected_devices){
					if(devices.length < connected_devices){
						sendEvent("DISCONNECTED");
						connected_devices = devices.length;
					}
					else{
						sendEvent("CONNECTED");
						connected_devices = devices.length;
					}
				}
				
				// on attend un peu avant de refaire une boucle
				try{
					Thread.sleep(400);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
		else{
			System.out.println("Os détecté : "+System.getProperty("os.name"));
			
			// Partie pour regarder les ports usb sur windows
			String[] letters = new String[]{"A","B","C","D","E","F","G","H","I"};
			File[] drives = new File[letters.length];
			boolean[] isDrive = new boolean[letters.length];
			
			/*
			 * On initialise les différents tableaux.
			 * drives : contient les paths vers les périphériques usb.
			 * isDrive : dit si les périphériques usb sont occupés ou non.
			 */
			for(int i=0; i<letters.length; i++){
				drives[i] = new File(letters[i]+":/");
				
				isDrive[i] = drives[i].canRead();
			}
			
			System.out.println("UsbDetector : en attente de périphériques... ");
			
			/*
			 * On fait une boucle infinie pour vérifier si un nouveau périphérique a été connecté sur la machine.
			 */
			isRecording = true;
			while(isRecording){
				// On regarde les périphériques un par un.
				for(int i=0; i<letters.length; i++){
					boolean pluggedIn = drives[i].canRead();
					
					// Si l'état est différent de l'état actuel,
					// on affiche un message.
					if(pluggedIn != isDrive[i]){
						if(pluggedIn){
							System.out.println("Le périphérique "+letters[i]+" est connecté.");
						}
						else{
							System.out.println("Le périphérique "+letters[i]+" est déconnecté.");
						}
						isDrive[i] = pluggedIn;
					}
				}
				
				// On attend un peu avant de boucler
				try{ Thread.sleep(100);}
				catch(InterruptedException e){}
			}
		}
	}

}