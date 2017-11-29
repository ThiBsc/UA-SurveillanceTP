package UASurveillanceEngine;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

class FFMpegRunner extends Thread {
		
	private Process ffmpeg;		
	private boolean running;
	private int screen_width, screen_height;
	
	public FFMpegRunner(int w, int h){
		ffmpeg = null;
		screen_width = w;
		screen_height = h;
	}
	
	@Override
	public void run(){
		try {
			String cmd = "ffmpeg -f x11grab -s "+screen_width+"x"+screen_height+" -i :0.0 -vf scale=896:504 -f avi pipe:1";
			ProcessBuilder pb = new ProcessBuilder(cmd.split("\\s+"));
			//pb.redirectError(Redirect.INHERIT);
			pb.redirectOutput(new File("/tmp/videopipe"));
			try {
				System.out.println("Run ffmpeg...");
				ffmpeg = pb.start();
				System.out.println("ffmpeg is running!");
				ffmpeg.waitFor();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Leave ffmpeg run()");
		}
	}
	
	public void stop_ffmpeg(){
		if (ffmpeg != null){
			BufferedOutputStream bos = new BufferedOutputStream(ffmpeg.getOutputStream());
			try {
				System.out.println("Sending 'q' to ffmpeg");
				bos.write(new String("q").getBytes());
				bos.flush();
				bos.close();
				System.out.println("ffmpeg is correctly closed");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}