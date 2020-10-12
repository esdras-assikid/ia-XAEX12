package agent;

import action.*;
import perception.*;
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
			
		}
		
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	// Méthodes
	

// Recherche le palet le plus proche et retourne la distance correspondante
	private float rechercherPaletPlusProche (){
		return 0;
	}





// évite le robot adverse
	private void eviterRobotAdverse(){
		boolean turnRight = false;
		int r = (int) Math.random()*2 + 1;
		if(r == 0)
			turnRight = true;
		else
			turnRight = false;
		
		if( p.aPalet()) {
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
		
		else if(p.aPalet() == false) {
			
		}
		
	}



}
