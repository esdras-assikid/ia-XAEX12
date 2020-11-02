package agent;

import action.*;
import perception.*;
import lejos.hardware.Button;
import lejos.robotics.navigation.Move;
import lejos.utility.Delay;

public class AgentMarouan {

	// Attributs
	
	// Instance de l’état de l’environnement
		Etat e; 
	
	// Instance du capteur d'ultrasons
		UltrasonicSensor us; 
	// Instance du capteur de couleurs
		ColorSensor cs; 
	// Instance du capteur de toucher
		TouchSensor ts; 
	// Instance pour la pince
		Pince p; 
		
		Deplacement d = new Deplacement();
		
		// Constructeur
		public AgentMarouan() {
			us = new UltrasonicSensor();
			d = new Deplacement();
			ts = new TouchSensor();
			p = new Pince();
			rechercherPaletPlusProche();
		}
		
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AgentMarouan();
	}
	
	
	// Méthodes
	

// Recherche le palet le plus proche et retourne la distance correspondante
	private float rechercherPaletPlusProche (){
		float closestAngle = -1.0f;

		d.getPilot().rotate(50);; 
		d.getPilot().rotate(-100, true);
		
		while(d.getPilot().isMoving()) {
			Delay.msDelay(10); // 50 mesures par sec
		    us.setCurrentDistance(us.getDistance());
			if (us.getCurrentDistance() < us.getLastDistance() && us.getCurrentDistance() > 0.310) {
				us.setLastDistance(us.getCurrentDistance());
				closestAngle = d.getPilot().getMovement().getAngleTurned(); // retourne l'angle parcouru depuis que la rotation a commenc�
			}
		}
		
		d.getPilot().setAngularSpeed(45);
		
		
		d.getPilot().rotate(100+closestAngle);
		
		p.deserrer();
		d.avancer();
		us.setCurrentDistance(us.getDistance()) ;
		boolean res = false;
		while (res == false && us.detectWall() == false) {
			Delay.msDelay(100);
			us.setCurrentDistance(us.getDistance()) ;
			res = ts.aEteTouche();//res = us.detectPalet();
			us.setLastDistance(us.getCurrentDistance()) ;
		}
		p.saisiePalet();
		d.stop();
		
		while(true) {

			
			if(Button.ESCAPE.isDown()) {
				d.stop();
				us.us.close();
				System.exit(0);
			}

		}
	}





// évite le robot adverse
	/**
	 * 
	 */
	private void eviterRobotAdverse(){
		boolean turnRight = false;
		int r = (int) Math.random()*2 + 1;
		if(r == 0)
			turnRight = true;
		else
			turnRight = false;
		
		if( p.isaPalet()) {
			d.stop();
			if(turnRight) {
				d.turnRight(45);
				d.avancer();
				Delay.msDelay(1500);
				d.stop();
				d.turnLeft(45);
				d.avancer();
				//deposerPalet();
			}
			else {
				d.turnLeft(45);
				d.avancer();
				Delay.msDelay(1500);
				d.stop();
				d.turnRight(45);
				d.avancer();
				//deposerPalet();
			}
		}
		
		/*
		 * else if(p.aPalet() == false) {
		 * 
		 * }
		 */
		
	}



}
