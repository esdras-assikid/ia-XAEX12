package agent;

import java.io.FileNotFoundException;

import action.Deplacement;
import action.Pince;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import perception.ColorSensor;
import perception.TouchSensor;
import perception.UltrasonicSensor;

public class TestMPPoint {

	// Attributs
	
		static AgentPierre ap;
	
		// Instance du capteur d'ultrasons
			UltrasonicSensor us; 
		// Instance du capteur de couleurs
			ColorSensor cs; 
		// Instance du capteur de toucher
			TouchSensor ts; 
		// Instance pour la pince
			Pince p; 
			
			Deplacement d = new Deplacement();
		
		public TestMPPoint() throws FileNotFoundException {
			us = new UltrasonicSensor();
			d = new Deplacement();
			ts = new TouchSensor();
			p = new Pince();
			cs = new ColorSensor();
			ap = new AgentPierre();
		}

	public static void main(String[] args) {

		Deplacement d = new Deplacement();
		TouchSensor tsa = new TouchSensor();
		Pince p = new Pince();
		ColorSensor cs = new ColorSensor() ;
		UltrasonicSensor us = new UltrasonicSensor();
		AgentPierre ap = new AgentPierre();
		
		if (ap.marquerPremierPoint()==true) {
			System.out.println("Le premier point a été marqué");
			
		}
		
		if (ap.marquerPremierPoint()==false && cs.getCurrentColor() != "WHITE" && tsa.isPressed()== true) {
			System.out.println("Le palet est dans les pinces mais n'a pas passé la ligne d'enbut");
			
		} 
		
		if (ap.marquerPremierPoint()==false && tsa.isPressed()==false) {
			System.out.println("Le palet n'est pas dans les pinces"); 
			
		} 

	}

}
