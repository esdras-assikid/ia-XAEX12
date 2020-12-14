package perception;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

/**
 * ColorSensor est la classe qui exploite le capteur de couleurs.
 * @author Esdras ASSIKIDANA.
 */
public class ColorSensor {

	/**
	 * Instance du capteur de couleurs.
	 */
	public EV3ColorSensor colorSensor;
	/**
	 * 
	 */
	public SampleProvider average;
	/**
	 * Tableau qui stocke les couleurs détectées.
	 * La dernière couleur détectée se trouve à l'indice 0.
	 */
	public String[] currentColor;
	/**
	 * Tableau contenant les valeurs correspondant à la couleur bleue.
	 */
	public  float[] blue;
	/**
	 * Tableau contenant les valeurs correspondant à la couleur jaune.
	 */
	public  float[] yellow;
	/**
	 * Tableau contenant les valeurs correspondant à la couleur grise.
	 */
	public  float[] grey;
	/**
	 * Tableau contenant les valeurs correspondant à la couleur verte.
	 */
	public  float[] green;
	/**
	 * Tableau contenant les valeurs correspondant à la couleur noire.
	 */
	public  float[] black;
	/**
	 * Tableau contenant les valeurs correspondant à la couleur rouge.
	 */
	public  float[] red;
	/**
	 * Tableau contenant les valeurs correspondant à la couleur blanche.
	 */
	public  float[] white;
	/**
	 * Constante désignant la couleur bleue.
	 */
	public static final String BLUE ="BLUE";
	/**
	 * Constante désignant la couleur jaune.
	 */
	public static final String YELLOW = "YELLOW";
	/**
	 * Constante désignant la couleur grise.
	 */
	public static final String GREY = "GREY";
	/**
	 * Constante désignant la couleur verte.
	 */
	public static final String GREEN = "GREEN";
	/**
	 * Constante désignant la couleur noire.
	 */
	public static final String BLACK = "BLACK";
	/**
	 * Constante désignant la couleur rouge.
	 */
	public static final String RED = "RED";
	/**
	 * Constante désignant la couleur blanche.
	 */
	public static final String WHITE = "WHITE";

	/**
	 * Construceur qui initialise le capteur de couleurs et lit les valeurs correspondantes à chaque couleur dans un fichier crée au préalable contenant les valeurs associées à chaque couleur.
	 * @throws FileNotFoundException
	 */
	public ColorSensor() throws FileNotFoundException {
		File colorFile = new File("CouleurXAEX12.txt");
		Scanner colorFileReader = new Scanner(colorFile);
		while(colorFileReader.hasNextLine()) {
			String line = colorFileReader.nextLine();
			String[] values = line.split(";");
			if(values[0].equals("green")) {
				green = new float[3];
				green[0] = Float.valueOf(values[1]);
				green[1] = Float.valueOf(values[2]);
				green[2] = Float.valueOf(values[3]);
			} else if(values[0].equals("grey")) {
				grey = new float[3];
				grey[0] = Float.valueOf(values[1]);
				grey[1] = Float.valueOf(values[2]);
				grey[2] = Float.valueOf(values[3]);
			} else if(values[0].equals("yellow")) {
				yellow = new float[3];
				yellow[0] = Float.valueOf(values[1]);
				yellow[1] = Float.valueOf(values[2]);
				yellow[2] = Float.valueOf(values[3]);
			} else if(values[0].equals("red")) {
				red = new float[3];
				red[0] = Float.valueOf(values[1]);
				red[1] = Float.valueOf(values[2]);
				red[2] = Float.valueOf(values[3]);
			} else if(values[0].equals("blue")) {
				blue = new float[3];
				blue[0] = Float.valueOf(values[1]);
				blue[1] = Float.valueOf(values[2]);
				blue[2] = Float.valueOf(values[3]);
			} else if(values[0].equals("black")) {
				black = new float[3];
				black[0] = Float.valueOf(values[1]);
				black[1] = Float.valueOf(values[2]);
				black[2] = Float.valueOf(values[3]);
			} else if(values[0].equals("whites")) {
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

		this.currentColor = new String[4]; 
		this.currentColor[0] = this.getCurrentColor(); 

		for(int i=1; i<4; i++) 
			currentColor[i]=ColorSensor.WHITE; 

		System.out.println(this.currentColor[0]);

	}

	/**
	 * Calcule un scalaire et retourne sa valeur.
	 * @param v1
	 * @param v2
	 * @return scalaire
	 */
	public static double scalaire(float[] v1, float[] v2) {
		return Math.sqrt (Math.pow(v1[0] - v2[0], 2.0) +
				Math.pow(v1[1] - v2[1], 2.0) +
				Math.pow(v1[2] - v2[2], 2.0));
	}

	/**
	 * Détecte la couleur actuelle et la renvoie.
	 * @return {@link ColorSensor#currentColor}
	 */
	public String getCurrentColor() {
		float[] sample = new float[average.sampleSize()];
		average.fetchSample(sample, 0);
		double minscal = Double.MAX_VALUE;

		String color = "";

		double scalaire = scalaire(sample, blue);
		if(scalaire < minscal) {
			minscal = scalaire;
			color = ColorSensor.BLUE;
		}

		scalaire = scalaire(sample, green);
		if(scalaire < minscal) {
			minscal = scalaire;
			color = ColorSensor.GREEN;
		}

		scalaire = scalaire(sample, red);
		if(scalaire < minscal) {
			minscal = scalaire;
			color = ColorSensor.RED;
		}

		scalaire = scalaire(sample, grey);
		if(scalaire < minscal) {
			minscal = scalaire;
			color = ColorSensor.GREY;
		}

		scalaire = scalaire(sample, yellow);
		if(scalaire < minscal) {
			minscal = scalaire;
			color = ColorSensor.YELLOW;
		}

		scalaire = scalaire(sample, black);
		if(scalaire < minscal) {
			minscal = scalaire;
			color = ColorSensor.BLACK;
		}

		scalaire = scalaire(sample, white);
		if(scalaire < minscal) {
			minscal = scalaire;
			color = ColorSensor.WHITE;
		}
		return color;
	}

	/**
	 * Met à jour le tableau des couleurs détectées.
	 * @param color est la dernière couleur détectée à ajouter en première position du tableau.
	 */
	public void updateColorTable(String color) {
		for(int i=this.currentColor.length-1; i > 0; i--) {
			currentColor[i]= currentColor[i-1];
		}
		currentColor[0] = color;
	}

	/**
	 * Modifie la couleur actuellement détectée.
	 * @return la dernière couleur détectée par le capteur.
	 */
	public String changeColor() {
		String colorDetected = this.getCurrentColor();
		while(colorDetected == this.currentColor[0]) {
			Delay.msDelay(50);
			colorDetected = this.getCurrentColor();	
		}
		updateColorTable(colorDetected);
		System.out.println(colorDetected);
		return colorDetected;
	}


	/**
	 * Programme de test.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ColorSensor cs = new ColorSensor();
			while(true) {
				cs.changeColor();
				if(Button.ESCAPE.isDown()) {
					cs.colorSensor.close();
					System.exit(0);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
