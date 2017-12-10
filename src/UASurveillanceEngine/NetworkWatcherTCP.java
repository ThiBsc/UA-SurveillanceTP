package UASurveillanceEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetworkWatcherTCP extends NetworkWatcher {
	
	public NetworkWatcherTCP(){
		super();
	}
	
	public void run() {
		/*
		File index = new File("SortiTCPdump");
		if (!index.exists()) {
		    index.mkdir();
		} else {
			String[]entries = index.list();
			for(String s: entries){
			    File fichierSelection = new File(index.getPath(),s);
			    fichierSelection.delete();
			}
		    index.delete();
		    if (!index.exists()) {
		        index.mkdir();
		    }
		}
		int increment = 1;
		String fileName ="SortiTCPdump/tcpdump_"+increment+".txt";
		*/
		String tcpdumpCmdResponse = "";
		ProcessBuilder constructionProcess = null;
		
		constructionProcess = new ProcessBuilder("tcpdump","-c 25","udp", "-tttt");
		
 
		constructionProcess.redirectErrorStream(true);
 
		try {
			isRecording = true;
			while(isRecording)
			{	
				Process process = constructionProcess.start();
				InputStream recupFluxDonnee = process.getInputStream();
				tcpdumpCmdResponse = this.getStringFromStream(recupFluxDonnee);
				//BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
				//out.write(tcpdumpCmdResponse);
				recupFluxDonnee.close();
				//out.close();
				//increment++;
				//fileName ="SortiTCPdump/tcpdump_"+increment+".txt";
			}
 
		} catch (Exception e) {
			System.out.println("Error Executing tcpdump command" + e);
		}
		
	}
	
	/*
	 * méthodes étant utiliser dans runTCPDUmp pour convertir le flux d'informations
	 * reçu en une chaine de caractère pouvant être utilisé dans le programme ou bien
	 * redirigé vers un fichier.
	 */
	private String getStringFromStream(InputStream recupFluxDonnee) throws IOException {
		String renvoi="";
		
		if (recupFluxDonnee != null) {
			
			try (BufferedReader br = new BufferedReader(new InputStreamReader(recupFluxDonnee))) {
			    String line = null;
			    int numeroLigne = 1;
			    while((line = br.readLine()) != null) {
			    	String[] ligneSplit = line.split(" ");
			    	if(ligneSplit.length == 10){
			    		if(numeroLigne > 1){
//					    	String value ="";
//					    	value ="Date : " +  ligneSplit[0] + " Heure: " + ligneSplit[1] + " Site: " + ligneSplit[8];
					    	sendEvent(ligneSplit[8]);
//					    	renvoi = renvoi + value;
			    		}
			    		numeroLigne++;
			    	}
			    	
			        
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}
		
		return renvoi;
	}
}
