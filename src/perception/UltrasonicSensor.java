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

// Classe qui gère le capteur d'ultrasons du robot 
public class UltrasonicSensor  {
	
	// Tableau contenant les valeurs des distances captées
	private float[] sample; 
	
	// Instance de la Brick
	public Brick b ;
	
	// Instance du port du capteur d'ultrasons
	public Port s1 ;
	
	// Instance du capteur d'ultrasons
	public  EV3UltrasonicSensor us ;
	
	// Fournit le bon nombre d'éléments à mettre dans le tableau sample en fonction du mode du capteur choisi
	private SampleProvider source;
	
	// Distance actuelle mesurée
	private float currentDistance;
	
	// Dernière distance mesurée
	private float lastDistance;
	
	
	
	
	// Initialise tous les attributs
	// Le mode du capteur est le "distance" mode, permettant de mesurer les distances
	public UltrasonicSensor() { 
		
		b = BrickFinder.getDefault();
		s1 = b.getPort("S1");
		us = new EV3UltrasonicSensor(s1);
		source = us.getMode("Distance");
		sample = new float[source.sampleSize()]; 
		currentDistance = this.getDistance();
		lastDistance = 3.0f;
	}
	
	
	// Retourne la distance actuelle mesurée
	public float getDistance() {
		source = us.getDistanceMode();
		source.fetchSample(sample, 0); // fetch a sample from the US sensor
		return sample[0];
	}
	
	
	// Retourne la distance actuelle
	public float getCurrentDistance() {
		return currentDistance;
	}
	
	// Retourne la dernière distance mesurée
	public float getLastDistance() {
		return lastDistance;
	}
	
	// Modifie la distance actuelle
	public void setCurrentDistance(float currentDistance) {
		this.currentDistance = currentDistance;
	}
	
	// Modifie la dernière distance mesurée
	public void setLastDistance(float lastDistance) {
		this.lastDistance = lastDistance;
	}
	
	// Retourne true si le robot est face à un mur, false sinon
	public boolean detectWall() {
		if(getDistance() < 0.3) {
			return true;
		}
		
		return false;
	}
	
	// Retourne true si un palet à été détecté, false sinon
	public boolean detectPalet() { 
	
	
		if(currentDistance > lastDistance  && lastDistance <= 0.4) { 
			
			return true;
			
		}
		
		return false;
	}
	
	
	
	
	
	//Tests
	public static void main(String[] args) {

		UltrasonicSensor ultra;
		
		MovePilot pilot;
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B,0.056).offset(-0.06075); // 0.056 = diametre des roues, offset = decalage des roues ??
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 0.056).offset(0.06075);
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		ultra = new UltrasonicSensor();
		
		
		
		//Test getDistance()
		
		while(true) {
			System.out.println(ultra.getDistance());
			Delay.msDelay(1000);
		if(Button.ESCAPE.isDown()) {
				ultra.us.close();
				System.exit(0);
			}
		}
		
		
		
		/*Test detectPalet()
		pilot.forward();
		while(true) {
			
			currentDistance = ultra.getDistance();
			if(ultra.detectPalet())
				pilot.stop();
			lastDistance = currentDistance;
			Delay.msDelay(50);
			
			if(Button.ESCAPE.isDown()) {
				ultra.us.close();
				System.exit(0);
			}
		}
		*/
		
		/*Test detectWall()
		pilot.forward();
		while(true) {
			//currentDistance = ultra.getDistance();
			if(ultra.detectWall())
				pilot.stop();
			//Delay.msDelay(50);
			
			if(Button.ESCAPE.isDown()) {
				ultra.us.close();
				System.exit(0);
			}
		}
		*/
		
		
		
		/* Test rechercherPalet
		 * 
		 * 
		 * boolean res = false;
		
		pilot.forward();
		while (true) {
			
			
			Delay.msDelay(100);
			currentDistance = ultra.getDistance();
			res = ultra.detectPalet();
			lastDistance = currentDistance;
			
			System.out.println(res);
			System.out.println(currentDistance);
			System.out.println(ultra.getDistance());
			
			if(res) {
				pilot.rotate(90);
				pilot.forward();
				Delay.msDelay(1000);
				pilot.stop();
				System.out.print(res);
			}
			
		
		
			if(Button.ESCAPE.isDown()) {
				ultra.us.close();
				System.exit(0);
			}
		}
		*/
	}
}
