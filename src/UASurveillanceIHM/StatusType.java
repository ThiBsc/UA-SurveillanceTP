package UASurveillanceIHM;

import java.awt.Color;

public enum StatusType {
	
	INFO(Color.black),
	WARNING(Color.orange),
	ERROR(Color.red);
	
	private Color color;
	
	private StatusType(Color c){
		color = c;
	}
	
	public Color getColor(){
		return color;
	}
}