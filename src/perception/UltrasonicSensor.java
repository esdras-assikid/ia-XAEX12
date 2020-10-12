package perception;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.filter.AbstractFilter;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;


public class UltrasonicSensor extends AbstractFilter {
    
	//Attribut
	
	
	
	private float[] sample; // to store samples
	private static final Brick b = BrickFinder.getDefault(); ;
	private static final Port s1 = b.getPort("S1");
	private static final EV3UltrasonicSensor us = new EV3UltrasonicSensor(s1);
	
	public static float currentDistance;
	public static float lastDistance;
	
	//Constructeur
	
	public UltrasonicSensor(SampleProvider source) { // source = sensor mode
		super(source); // initialise la source (choix du sensor) et la taille du sample correspondant
		sample = new float[sampleSize]; 
		currentDistance = this.getDistance();
		lastDistance = 3.0f;
	}
	
	// Méthodes
	
	public float getDistance() {
		super.fetchSample(sample, 0); // fetch a sample from the US sensor
		return sample[0];
	}
	
	public float getListen() {
		super.fetchSample(sample, 1); // fetch a sample from the US sensor
		return sample[1];
	}
	
	// Permet de détecter un mur ou le robot adverse
	// Si la distance est inférieur à celle à partir de laquelle un palet n'est plus détecté, alors c'est ou bien un mur
	// ou bien le robot adverse qui est en face
	public boolean detectWall() {
		if(currentDistance < 0.3) {
			return true;
		}
		
		return false;
	}
	
	public boolean detectPalet() { 
	
	
		if(currentDistance > lastDistance  && lastDistance <= 0.33) { 
			
			return true;
			
		}
		
		return false;
	}
	
	
	public boolean facingRobot() {
		
		if(this.getListen() == 1)
			return true;
			
		return false;
	}
	public static void main(String[] args) {

		UltrasonicSensor ultra;
		
		MovePilot pilot;
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B,0.056).offset(-0.06075); // 0.056 = diamètre des roues, offset = décalage des roues ??
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 0.056).offset(0.06075);
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		ultra = new UltrasonicSensor(us.getMode("Distance"));
		
		
		
		boolean res = false;
		lastDistance = 2.5f;
		pilot.forward();
		while (true) {
			
			
			Delay.msDelay(100);
			currentDistance = ultra.getDistance();
			res = ultra.detectPalet();
			lastDistance = currentDistance;
			
			System.out.println(res);
			System.out.println(currentDistance);
			System.out.println(lastDistance);
			
			if(res) {
				pilot.stop();
				System.out.print(res);
			}
			
			if(Button.ESCAPE.isDown()) {
				us.close();
				System.exit(0);
			}
		}
	}
}
