package UASurveillanceEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Watcher détectant les branchement de clé usb
 */
public class USBWatcher extends Watcher {

	public USBWatcher() {
		super("USB");
		// ctor
	}
		
	@Override
	public void run() {
		isRecording = true;
		// initialisation des infos de départ
		String cmd = "lsusb -t | grep Class=Mass | wc -l";
		ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", cmd);
		//pb.inheritIO();
		int n_usb = -1;
		while (isRecording) {
			try {
				Process usb = pb.start();
				usb.waitFor();
				BufferedReader reader = new BufferedReader(new InputStreamReader(usb.getInputStream()));
				StringBuilder builder = new StringBuilder();
				String line = null;
				while ( (line = reader.readLine()) != null) {
				   builder.append(line);
				}
				int result = Integer.parseInt(builder.toString());
				if (n_usb == -1){
					n_usb = result;
				} else {
					if (n_usb < result){
						//System.out.println("Connexion");
						sendEvent("CONNECTED");
					} else if (n_usb > result) {
						//System.out.println("Deconnexion");
						sendEvent("DISCONNECTED");
					}
					n_usb = result;
				}
				Thread.sleep(500);
			} catch (IOException | InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	}

}