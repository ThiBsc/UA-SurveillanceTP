package UASurveillanceEngine;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FFMpegRunner extends Thread {
		
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
			String cmd = "ffmpeg -f x11grab -s "+screen_width+"x"+screen_height+" -i :0.0 -vcodec libx264 -vf scale=896:504 -f avi pipe:1";
			ProcessBuilder pb = new ProcessBuilder(cmd.split("\\s+"));
			//pb.redirectError(Redirect.INHERIT);
			pb.redirectOutput(new File("/tmp/videopipe"));
			try {
				System.out.println("Run ffmpeg...");
				ffmpeg = pb.start();
				running = true;
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
	
	public String stop_ffmpeg(){
		String last_duration = "";
		if (ffmpeg != null){
			// last_duration sous la forme "0:00:19.41"
			BufferedOutputStream bos = new BufferedOutputStream(ffmpeg.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()));
			try {
				System.out.println("Sending 'q' to ffmpeg");
				bos.write(new String("q").getBytes());
				bos.flush();
				String seek_duration = "";
				while ( (seek_duration = br.readLine()) != null){
					//System.err.println(seek_duration);
					if (seek_duration.contains("time=")){
						Pattern pattern = Pattern.compile("^frame.*time=(\\d{2}\\:\\d{2}\\:\\d{2}\\.\\d{2})");
						Matcher matcher = pattern.matcher(seek_duration);
						if (matcher.find()){
							last_duration = matcher.group(1);
						}
					}
				}
				br.close();
				bos.close();
				/**
				 * on copy pour la durée du fichier impossible à connaitre avant la fin d'un stream
				 */
				System.out.println(last_duration);
				System.out.println("ffmpeg is correctly closed");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				running = false;
			}
		}
		return last_duration;
	}
}