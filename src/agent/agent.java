package agent;

import action.*;
import perception.*;

public class agent {
	// Attributs
	
	// Instance de l’état de l’environnement
		//Etat e; 
	
	// Instance du capteur d'ultrasons
		UltrasonicSensor us; 
	// Instance du capteur de couleurs
		ColorSensor cs; 
	// Instance du capteur de toucher
		TouchSensor ts; 
	// Instance pour la pince
		Pince p; 

		
		// Constructeur
		public agent() {
			
		}
		
		
		// Méthodes
		
		// Méthode pour marquer le premier point
		private void marquerPremierPoint(){}; 
	// Méthode pour marquer un point (autre que le premier)
		private void marquerUnPoint(){}; 

	// Recherche le palet le plus proche et retourne la distance correspondante
		private float rechercherPaletPlusProche (){}; 

	// se dirige vers et saisit le palet le plus proche, puis retourne true si un palet a bel et bien été saisi
	// retourne false sinon
	// distance = distance du palet le plus proche lors de la détection
		private boolean saisirPaletPlusProche (float distance){}; 

	// S'oriente, se dirige vers la ligne adverse et dépose le palet 
		private void deposerPalet (){};

	// évite les palets sur le chemin lors de la phase de dépose
		private void eviterPalets (){};

	// se repositionne si face à un mur
		private void eviterMur(){
			
		};

	// évite le robot adverse
		private void eviterRobotAdverse(){};

	// se positionne au milieu d'une ligne de couleur après avoir marqué un point
	// (couleur dépend de la ligne sur laquelle se trouvent les palets les plus proches)
		private void positionApresPoint(){};

	// se repositionne si palet non touché lors de la tentative de saisie
		private void seRepositionner (){};
<<<<<<< HEAD
	    public static void main(String[] args) {
=======
		
		
	public static void main(String[] args) {
>>>>>>> 0e3a1033b7b7ae9cb3f670912f1044158fc1c088
		// TODO Auto-generated method stub

	}

}
