// 2 versions, gauche et droite

// Avancer tout droit jusqu'a ce que le touch sensor soit stimulé
// Refermer les pinces 
// Tourner à 90 deg à droite ou à gauche
// Avancer de 30cm
// Tourner de 90 deg dans le sens inverse
// Avancer tout droit jusqu'a color sensor = blanc
// Méthode DeposerPalet (Méthode DeposerPalet)

// A éviter

//Eviter robot -> (Méthode EviterRobot)

package agent;
import perception.*;
import action.*;


public class agentPierre {
	
	// Attributs
	
	// Instance du capteur d'ultrasons
		UltrasonicSensor us; 
	// Instance du capteur de couleurs
		ColorSensor cs; 
	// Instance du capteur de toucher
		TouchSensor ts; 
	// Instance pour la pince
		Pince p; 
		
		Deplacement d = new Deplacement();
	
	public agentPierre() {
		us = new UltrasonicSensor();
		d = new Deplacement();
		ts = new TouchSensor();
		p = new Pince();
		cs = new ColorSensor();
	}

	private boolean marquerPremierPoint() {
		Deplacement d = new Deplacement();
		TouchSensor tsa = new TouchSensor();
		Pince p = new Pince();
		ColorSensor cs = new ColorSensor() ;
		UltrasonicSensor us = new UltrasonicSensor();
			
		while (us.getCurrentDistance() > 0.350) {
			d.avancer();
		}
		
		d.stop();
		p.deserrer();
		
		while(tsa.aEteTouche() == false) {
			d.avancer();
		}
		p.saisiePalet();
		d.turnLeft(90);
		d.avancer(); // 20cm
		d.turnRight(90);
		
		while (cs.getCurrentColor() != "WHITE") {
			d.avancer();
		}
		
		d.stop();
		p.lachePalet();
		
		if (tsa.isPressed()== true) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	public static void main(String[] args) {
		//TODO
	}

}
