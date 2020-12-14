package agent;

/**
 * Agent est la classe qui sert à décrire les points contenus dans le circuit, et auxquelles le robot doit se diriger.
 * @author Esdras ASSIKIDANA
 */
public class Point {
	/**
	 * Angle en degrés par rapport à la position du robot lorsqu'il est sur la ligne d'en-but adverse face au mur après avoir déposer le palet.
	 * Permet au robot de savoir de combien de degrés il doit se tourner pour être face au point suivant (et donc au palet suivant).
	 */
	private double angle;
	/**
	 * Distance à parcour vers le point avant d'effectuer une recherche de palet.
	 */
	private double distance;
	/**
	 * Distance maximale à laquelle le palet est censé se trouver lors de la recherche.
	 * Si aucune des valeurs captées par l'UltrasonicSensor n'est inférieure à cette valeur lors de la recherche, alors il considère qu'il n'y a plus de palet à ce point.
	 */
	private float distanceMax = 0.49f;


	/**
	 * Constructeur qui initialise les attributs.
	 * @param angle
	 * @param distance
	 * @param distanceMax
	 */
	public Point(double angle, double distance, float distanceMax) {
		this.angle = angle;
		this.distance = distance;
		this.distanceMax = distanceMax;
	}

	/**
	 * Retourne la distance maximale à laquelle le palet est censé se trouver lors de la recherche.
	 * @return {@link Point#distanceMax}
	 */
	public float getDistanceMax() {
		return distanceMax;
	}

	/**
	 * Modifie la distance max à laquelle le palet est censé se trouver lors de la recherche.
	 * @param distanceMax
	 */
	public void setDistanceMax(float distanceMax) {
		this.distanceMax = distanceMax;
	}

	/**
	 * Retourne l'angle en degrés par rapport à la position du robot lorsqu'il est sur la ligne d'en-but adverse face au mur après avoir déposer le palet.
	 * @return {@link Point#angle}
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Retourne la distance à parcourir vers le point avant de faire la recherche de palet.
	 * @return {@link Point#distance}
	 */
	public double getDistance() {
		return distance;
	}
}