package UASurveillanceEngine;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 */
public class ScreenWatcher extends Watcher {

	/**
	 * ffmpeg -f x11grab -s 1920x1080 -i :0.0 -f avi pipe:1 > /tmp/videopipe
	 * ffmpeg -f x11grab -s 1920x1080 -i :0.0 -vf scale=896:504 -f avi pipe:1 > /tmp/videopipe
	 */	
	private static final int width=640, height=480; // 16*9 -> 768*432

	public ScreenWatcher() {
		super("SCREEN");
		// ctor
	}

	@Override
	public void run() {
		boolean canRun = true;
		System.out.println("Waiting for data...");
		try {
			ProcessBuilder pb = new ProcessBuilder("mkfifo", "/tmp/videopipe");
			try {
				Process p = pb.start();
				p.waitFor();
			} catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
				canRun = false;
			}
			if (canRun){
				System.out.println("Opening videopipe...");
				FileInputStream fis = new FileInputStream(new File("/tmp/videopipe"));
				System.out.println("videopipe opened!");
				//FileOutputStream fos = new FileOutputStream(new File("/tmp/test.avi"));
				isRecording = true;
				while  (isRecording){
					try {
						if (fis.available() > 2048){
							int size_available = fis.available();
							byte[] data = new byte[size_available];
							fis.read(data, 0, size_available);
							sendEventData(size_available, data);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// on envoie tout ce qui reste
				try {
					if (fis.available() != 0){
						int size_available = fis.available();
						byte[] data = new byte[size_available];
						fis.read(data, 0, size_available);
						sendEventData(size_available, data);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					fis.close();
					//fos.flush();
					//fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Stop reading from named pipe. Good bye !");
			File f = new File("/tmp/videopipe");
			f.delete();
		}
	}

}