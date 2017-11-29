package serverEvent;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EventReceiver extends Thread {

	/**
	 * @param args
	 */
	
	private ServerSocket ss;
	private boolean running;

	public EventReceiver(){
	}
	
	@Override
	public void run() {
		System.out.println("EventAgregator is waiting for suspicious event...");
		running = true;
		try {
			ss = new ServerSocket(3615);
			while (running){
				Socket clientSocket = ss.accept();
				DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
				while (dis.available() != 0) {
					// "TYPE|etu_name|etu_prenom|data|(?other)"
					// get line info
					int line_length = dis.readInt();
					byte[] line_byte = new byte[line_length];
					dis.read(line_byte);
					String line = new String(line_byte, "UTF-8");
					// treatment
					if (line.startsWith("USB")){
						System.out.println(line);
					} else if (line.startsWith("SCREEN")){
						// "SCREEN|etudiantname"
						int available = dis.readInt();
						String etudiant = line.split("\\|")[1];
						byte[] data = new byte[available];
						FileOutputStream fos = new FileOutputStream(new File("/tmp/test_server_"+etudiant+".avi"), true);
						dis.read(data);
						fos.write(data);
						fos.close();
					} else if (line.startsWith("DIRECTORY")){
						System.out.println(line);
					} else if (line.startsWith("NETWORK")){
						System.out.println(line);
					} else {
						System.err.println("Protocol not recognized.");
					}						
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public static void main(String[] args) {
		EventReceiver ea = new EventReceiver();
		ea.start();
	}

}
