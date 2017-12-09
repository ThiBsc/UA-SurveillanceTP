package serverEvent;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import UASurveillanceIHM.DatabaseSingleton;

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
		DatabaseSingleton bdd = DatabaseSingleton.getInstance();
		System.out.println("EventReceiver is waiting for suspicious event...");
		try {
			String video_path = "";
			try {
				bdd.connect("127.0.0.1", "UA-user", "ua_surveillance", "ua-user");
				ResultSet rset = bdd.query("select path from VIDEO_PATH where nom like \"Test\";");
				rset.next();
				video_path = rset.getString("path");
				rset.close();
				ss = new ServerSocket(3615);
				running = true;
			} catch (SQLException | ClassNotFoundException e) {
				System.err.println(e.getMessage());
				running = false;
			}
			while (running){
				Socket clientSocket = ss.accept();
				DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
				/**
				 * Waiting for socket data, with 100 attemps max
				 */
				int attempts = 0;
				while (dis.available() == 0 && attempts++ < 100){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// "TYPE|exam_id|etu_name|etu_prenom|date|(?other)"
				// get line info
				int line_length = dis.readInt();
				byte[] line_byte = new byte[line_length];
				dis.read(line_byte);
				
				String line = new String(line_byte, "UTF-8");
				String type = line.split("\\|")[0];
				int exam_id = Integer.parseInt(line.split("\\|")[1]);
				String nom = line.split("\\|")[2];
				String prenom = line.split("\\|")[3];
				
				// treatment
				if (type.equals("USB") || type.equals("DIRECTORY") || type.equals("NETWORK")){
					String other = line.split("\\|")[5];
					
					// values (id_exam, type, nom, prenom, date, other)
					String insert_format = "insert into EXAMEN_has_EVENEMENT " +
							"(EXAMEN_id, EVENEMENT_type, etu_nom, etu_prenom, date, other) " +
							"values (%d, \"%s\", \"%s\", \"%s\", current_timestamp, \"%s\");";
					System.out.println(line);
					try {
						bdd.insert(String.format(insert_format, exam_id, type, nom, prenom, other));
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				} else if (type.equals("SCREEN")){
					// "SCREEN|etudiantname"
					String VIDEO_PATH_nom = video_path+exam_id+"_"+nom+"_"+prenom+".avi";
					String insert_format = "insert into EXAMEN_has_VIDEO_PATH " +
							"(EXAMEN_id, VIDEO_PATH_nom, etu_nom, etu_prenom, create_date, etu_hostname, etu_ip) " +
							"select * from (select %d, \"%s\", \"%s\", \"%s\", current_timestamp, \"\" as host, \"\" as ip) as tab " +
							"where not exists (" +
							"select EXAMEN_id from EXAMEN_has_VIDEO_PATH where EXAMEN_id=%d and VIDEO_PATH_nom like \"%s\");";
					try {
						bdd.insert(String.format(insert_format, exam_id, "Test", nom, prenom, exam_id, "Test"));
					} catch (SQLException e) {
						System.err.println(e.getMessage());
					}
					int available = dis.readInt();
					byte[] data = new byte[available];
					FileOutputStream fos = new FileOutputStream(new File(VIDEO_PATH_nom), true);
					dis.read(data);
					fos.write(data);
					fos.close();
				} else {
					System.err.println("Protocol not recognized.");
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				bdd.disconnect();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
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
