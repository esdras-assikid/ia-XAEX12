package perception;

import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractFilter;

public class UltrasonicSensor extends AbstractFilter {
    
	//Attribut
	
	float[] sample; // to store samples
	
	//Constructeur
	
	public UltrasonicSensor(SampleProvider source) { // source = sensor mode
		super(source); // initialise la source (choix du sensor) et la taille du sample correspondant
		sample = new float[sampleSize]; 
	}
	
	// Méthodes
	
	public float getDistance() {
		super.fetchSample(sample, 0); // fetch a sample from the US sensor
		return sample[0];
	}
	
	
	
	
}
