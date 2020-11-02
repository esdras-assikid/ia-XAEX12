package agent;

import java.io.FileNotFoundException;

import action.*;
import lejos.hardware.Button;
import lejos.utility.Delay;
import perception.*;

public class Agent {
	// Attributs
	
	// Instance de l'etat de l'environnement

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
		
	//Instance de deplacement
		Deplacement d;
		
		// Constructeur
		public Agent() throws FileNotFoundException {
			cs = new ColorSensor();
			d = new Deplacement();
			e = new Etat(ColorSensor.BLUE);
			us = new UltrasonicSensor();
			
		}
		
		
		// Méthodes
		
		// Méthode pour marquer le premier point
		private void marquerPremierPoint(){}; 
	// Méthode pour marquer un point (autre que le premier)
		private void marquerUnPoint(){}; 

	// Recherche le palet le plus proche et retourne la distance correspondante
		//private float rechercherPaletPlusProche (){}; 

	// se dirige vers et saisit le palet le plus proche, puis retourne true si un palet a bel et bien été saisi
	// retourne false sinon
	// distance = distance du palet le plus proche lors de la détection
		//private boolean saisirPaletPlusProche (float distance){}; 

	// S'oriente, se dirige vers la ligne adverse et dépose le palet 
		private void deposerPalet (){};

	// évite les palets sur le chemin lors de la phase de dépose
		private void eviterPalets (){};

	

	// évite le robot adverse
		private void eviterRobotAdverse(){
		}

	// se positionne au milieu d'une ligne de couleur après avoir marqué un point
	// (couleur dépend de la ligne sur laquelle se trouvent les palets les plus proches)
		private void positionApresPoint(){
			e.setIdligneActuel();
			d.turnRight(90);
			float distance = us.getDistance();
			if(distance > 1.01f) {
				d.avancer();
				while(distance > 1.02f || distance < 0.98f) {
					Delay.msDelay(50);
					distance = us.getDistance();
					if(Button.ESCAPE.isDown()) {
						//us.close();
						this.cs.colorSensor.close();
						this.d.stop();
						
						System.exit(0);
					}
				}
			}
			if(distance < 0.98f) {
			    d.reculer();
			    while(distance > 1.02f || distance < 0.98f) {
					Delay.msDelay(50);
					distance = us.getDistance();
					if(Button.ESCAPE.isDown()) {
						//UltrasonicSensor.us.close();
						this.cs.colorSensor.close();
						this.d.stop();
						
						System.exit(0);
					}
				}
			    
			}
			
			
			d.stop();
			e.setIdligneActuel(Etat.idLigneMilieu);
			if(e.getIdligneActuel() == Etat.idLigneProche) {
				d.turnRight(90);
				
				while(!cs.changeColor().equals(ColorSensor.WHITE)) {
					d.reculer();
					Delay.msDelay(10);
					
				}
				d.stop();
			}
			
			if(e.getIdligneActuel() == Etat.idLigneMilieu) {
				d.turnRight(90);
				while(!cs.changeColor().equals(e.ligneLoin.getCouleur())) {
					d.avancer();
					Delay.msDelay(30);
				}
				d.stop();
			}
			
		};

	// se repositionne si palet non touché lors de la tentative de saisie

		//private void seRepositionner (){};
		
		private void seRepositionner (){};

	    
	
		
	public static void main(String[] args) {

		try {
			Agent a = new Agent();
			a.positionApresPoint();
			
			if(Button.ESCAPE.isDown()) {
				//UltrasonicSensor.us.close();
				a.cs.colorSensor.close();
				a.d.stop();
				
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
