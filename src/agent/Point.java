package agent;

// Cette classe sert à décrire les points contenus dans le circuit que le robot doit suivre robot

public class Point {
	
	//angle en degrés par rapport à la position du robot lorsqu'il est sur la ligne d'en-but adverse face au mur après avoir déposer le palet
	//Grâce à cette valeur, il sait de combien de degrés il doit se tourner pour être face au point suivant (= palet suivant)
	private double angle;
	
	
	
	//la distance à parcourir vers le point avant de faire la recherche de palet
	private double distance;
	
	//la distance max à laquelle le palet est censé se trouver lors de la recherche
	//Si aucune des valeurs captées par l'ultrasonic sensor n'est inférieure à cette valeur lors de la recherche
	//alors il considère qu'il n'y a plus de palet à ce point
	private float distanceMax = 0.49f;
	
	
	//initialise les valeurs des attributs
	
	public Point(double angle, double distance, float distanceMax) {
		// TODO Auto-generated constructor stub
		this.angle = angle;
		this.distance = distance;
		this.distanceMax = distanceMax;
	}
	

	// Retourne la distance max à laquelle le palet est censé se trouver lors de la recherche
	public float getDistanceMax() {
		return distanceMax;
	}


	// Modifie la distance max à laquelle le palet est censé se trouver lors de la recherche
	public void setDistanceMax(float distanceMax) {
		this.distanceMax = distanceMax;
	}


	// Retourne l'angle en degrés par rapport à la position du robot lorsqu'il est sur la ligne d'en-but adverse face au mur après avoir déposer le palet
	public double getAngle() {
		return angle;
	}


	// Retourne la distance à parcourir vers le point avant de faire la recherche de palet
	public double getDistance() {
		return distance;
	}


	
	
	
	
	

}
