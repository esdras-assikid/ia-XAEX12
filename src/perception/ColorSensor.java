package perception;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;

public class ColorSensor {
	public EV3ColorSensor colorSensor;
	public SampleProvider average;
	
	public  float[] blue;
	public  float[] yellow;
	public  float[] grey;
	public  float[] green;
	public  float[] black;
	public  float[] red;
	public  float[] white;

	//Constructor 
	public ColorSensor() throws FileNotFoundException {
		
			File colorFile = new File("CouleurXAEX12.txt");
			Scanner colorFileReader = new Scanner(colorFile);
			while(colorFileReader.hasNextLine()) {
				String line = colorFileReader.nextLine();
				String[] values = line.split(";");
				if(values[0] == "green") {
					
				}else if(values[0] == "green") {
					green = new float[3];
					green[0] = Float.valueOf(values[1]);
					green[1] = Float.valueOf(values[2]);
					green[2] = Float.valueOf(values[3]);
					
				}else if(values[0] == "grey") {
					grey = new float[3];
					grey[0] = Float.valueOf(values[1]);
					grey[1] = Float.valueOf(values[2]);
					grey[2] = Float.valueOf(values[3]);
					
				}else if(values[0] == "yellow") {
					yellow = new float[3];
					yellow[0] = Float.valueOf(values[1]);
					yellow[1] = Float.valueOf(values[2]);
					yellow[2] = Float.valueOf(values[3]);
				}else if(values[0] == "red") {
					red = new float[3];
					red[0] = Float.valueOf(values[1]);
					red[1] = Float.valueOf(values[2]);
					red[2] = Float.valueOf(values[3]);
				}else if(values[0] == "blue") {
					blue = new float[3];
					blue[0] = Float.valueOf(values[1]);
					blue[1] = Float.valueOf(values[2]);
					blue[2] = Float.valueOf(values[3]);
					
				}else if(values[0] == "black") {
					black = new float[3];
					black[0] = Float.valueOf(values[1]);
					black[1] = Float.valueOf(values[2]);
					black[2] = Float.valueOf(values[3]);
					
				}else if(values[0] == "white") {
					white = new float[3];
					white[0] = Float.valueOf(values[1]);
					white[1] = Float.valueOf(values[2]);
					white[2] = Float.valueOf(values[3]);
				}
				
				
			}
			colorFileReader.close();
			
			Port port = LocalEV3.get().getPort("S4");
			colorSensor = new EV3ColorSensor(port);
			average = new MeanFilter(colorSensor.getRGBMode(), 1);
			colorSensor.setFloodlight(Color.WHITE);
		
		
	}
	
	public static double scalaire(float[] v1, float[] v2) {
		return Math.sqrt (Math.pow(v1[0] - v2[0], 2.0) +
				Math.pow(v1[1] - v2[1], 2.0) +
				Math.pow(v1[2] - v2[2], 2.0));
	}
	
	public String currentColor() {
		float[] sample = new float[average.sampleSize()];
		average.fetchSample(sample, 0);
		double minscal = Double.MAX_VALUE;
		
		String color = "";
		
		double scalaire = scalaire(sample, blue);
		if(scalaire < minscal) {
			minscal = scalaire;
			color = "blue";
		}
		
		scalaire = scalaire(sample, green);
		if(scalaire < minscal) {
			minscal = scalaire;
			color = "green";
		}
		
		scalaire = scalaire(sample, red);
		if(scalaire < minscal) {
			minscal = scalaire;
			color = "red";
		}
		
		scalaire = scalaire(sample, grey);
		if(scalaire < minscal) {
			minscal = scalaire;
			color = "grey";
		}
		
		scalaire = scalaire(sample, yellow);
		if(scalaire < minscal) {
			minscal = scalaire;
			color = "yellow";
		}
		
		scalaire = scalaire(sample, black);
		if(scalaire < minscal) {
			minscal = scalaire;
			color = "black";
		}
		
		scalaire = scalaire(sample, white);
		if(scalaire < minscal) {
			minscal = scalaire;
			color = "white";
		}
		
		return color;
		
	}
	
	
}