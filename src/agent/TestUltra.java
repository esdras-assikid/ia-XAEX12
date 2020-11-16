package agent;

import action.Deplacement;
import action.Pince;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.Move;
//import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
import perception.ColorSensor;
import perception.TouchSensor;
import perception.UltrasonicSensor;


// recherche le palet le plus proche et se dirige vers lui

public class TestUltra {
	
	// Instance de lâ€™Ã©tat de lâ€™environnement
	Etat e; 

	// Instance du capteur d'ultrasons
	UltrasonicSensor us; 
	// Instance du capteur de couleurs
	ColorSensor cs; 
	// Instance du capteur de toucher
	TouchSensor ts; 
	// Instance pour la pince
	Pince p; 
	
	Deplacement d;
	
	public static void main(String[] args) {
		
			new TestUltra();
		

	}
	
	public TestUltra() {
		d = new Deplacement();
		us = new UltrasonicSensor();
		d = new Deplacement();
		ts = new TouchSensor();
		p = new Pince();
		rechercherPaletPlusProche();
	while(true) {
			
			if(Button.ESCAPE.isDown()) {
				d.stop();
				us.us.close();
				System.exit(0);
			}

		}
		
	}
	
	private float rechercherPaletPlusProche (){
		float closestAngle = -1.0f;
		d.getPilot().setAngularSpeed(45);
		d.getPilot().rotate(20);; 
		d.getPilot().rotate(-40, true);
		
		while(d.getPilot().isMoving()) {
			Delay.msDelay(10); // 50 mesures par sec
		    us.setCurrentDistance(us.getDistance());
			if (us.getCurrentDistance() < us.getLastDistance() && us.getCurrentDistance() > 0.310) {
				us.setLastDistance(us.getCurrentDistance());
				closestAngle = d.getPilot().getMovement().getAngleTurned(); // retourne l'angle parcouru depuis que la rotation a commencé
			}
		}
		
		
		d.getPilot().rotate(40+closestAngle);
		
		
		d.getPilot().rotate(10);; 
		d.getPilot().rotate(-20, true);
		
		while(d.getPilot().isMoving()) {
			Delay.msDelay(10); // 50 mesures par sec
		    us.setCurrentDistance(us.getDistance());
			if (us.getCurrentDistance() < us.getLastDistance() && us.getCurrentDistance() > 0.310) {
				us.setLastDistance(us.getCurrentDistance());
				closestAngle = d.getPilot().getMovement().getAngleTurned(); // retourne l'angle parcouru depuis que la rotation a commencé
			}
		}
		
		d.getPilot().rotate(20+closestAngle);
		
		return us.getLastDistance();
	
	}
}


