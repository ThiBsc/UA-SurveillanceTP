package UASurveillanceEngine;


import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.imageio.ImageWriter;

import java.awt.AWTException;
import java.awt.Toolkit;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * 
 */
public class ScreenWatcher extends Watcher {

	/**
	 * ffmpeg -f x11grab -s 1920x1080 -i :0.0 -f avi pipe:1 > /tmp/videopipe
	 * ffmpeg -f x11grab -s 1920x1080 -i :0.0 -vf scale=896:504 -f avi pipe:1 > /tmp/videopipe
	 */
	
	private static final int width=640, height=480; // 16*9 -> 768*432

	
	/**
	 * Default constructor
	 */
	public ScreenWatcher() {
		super("SCREEN");
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
				FileOutputStream fos = new FileOutputStream(new File("/tmp/test.avi"));
				isRecording = true;
				while  (isRecording){
					try {
						if (fis.available() != 0){
							int size_available = fis.available();
							byte[] data = new byte[size_available];
							fis.read(data, 0, size_available);
							fos.write(data);
							//System.out.println(new String(data));
							// envoie au server
							//Socket socketEvent = new Socket("172.29.116.105", 3615);
							Socket socketEvent = new Socket("127.0.0.1", 3615);
							DataOutputStream writer = new DataOutputStream(socketEvent.getOutputStream());
							
							// write video part info
							String video_info = "SCREEN|thibaut";
							// Write data
							byte[] info = video_info.getBytes("UTF-8");
							writer.writeInt(info.length);
							writer.write(info);
							// write video part data
							writer.writeInt(size_available);
							writer.write(data);
							writer.flush();
							writer.close();
							socketEvent.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					fis.close();
					fos.flush();
					fos.close();
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