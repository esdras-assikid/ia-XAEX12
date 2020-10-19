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


public class UltrasonicSensor  {
    
	//Attribut
	
	
	
	private float[] sample; // to store samples
	private  Brick b ; 
	private  Port s1 ;
	private EV3UltrasonicSensor us;
	private SampleProvider source;
	
	private static float currentDistance;
	private static float lastDistance;
	
	
	
	//Constructeur
	
	public UltrasonicSensor() { // source = sensor mode
		
		b = BrickFinder.getDefault();
		s1 = b.getPort("S1");
		us = new EV3UltrasonicSensor(s1);
		source = us.getMode("Distance");
		sample = new float[source.sampleSize()]; 
		currentDistance = this.getDistance();
		lastDistance = 3.0f;
	}
	
	// M�thodes
	
	public float getDistance() {
		source = us.getDistanceMode();
		source.fetchSample(sample, 0); // fetch a sample from the US sensor
		return sample[0];
	}
	
	public float getListen() {
		source = us.getListenMode();
		source.fetchSample(sample, 1); // fetch a sample from the US sensor
		return sample[1];
	}
	
	public float getCurrentDistance() {
		return currentDistance;
	}
	
	public float getLastDistance() {
		return lastDistance;
	}
	
	public void setCurrentDistance(float currentDistance) {
		this.currentDistance = currentDistance;
	}
	
	public void setLastDistance(float lastDistance) {
		this.lastDistance = lastDistance;
	}
	
	// Permet de d�tecter un mur ou le robot adverse
	// Si la distance est inf�rieur � celle � partir de laquelle un palet n'est plus d�tect�, alors c'est ou bien un mur
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
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B,0.056).offset(-0.06075); // 0.056 = diam�tre des roues, offset = d�calage des roues ??
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 0.056).offset(0.06075);
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		ultra = new UltrasonicSensor();
		
		
		
		boolean res = false;
		lastDistance = 2.5f;
		pilot.forward();
		while (true) {
			
			
			Delay.msDelay(100);
			currentDistance = ultra.getDistance();
			res = ultra.detectWall();
			lastDistance = currentDistance;
			
			System.out.println(res);
			System.out.println(currentDistance);
			System.out.println(ultra.getDistance());
			
			if(res) {
				pilot.stop();
				System.out.print(res);
			}
			
			if(Button.ESCAPE.isDown()) {
				ultra.us.close();
				System.exit(0);
			}
		}
	}
}
