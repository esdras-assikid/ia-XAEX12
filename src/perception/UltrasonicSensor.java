package perception;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
/**
 * UltrasonicSensor est la classe qui exploite le capteur d'ultrasons du robot.
 * @author Marouan BOULLI
 */
public class UltrasonicSensor  {
	/**
	 * Tableau contenant les valeurs des distances captées.
	 */
	private float[] sample; 
	/**
	 * Instance de la brick EV3.
	 */
	public Brick b ;
	/**
	 * Instance du port du capteur d'ultrasons.
	 */
	public Port s1 ;
	/**
	 * Instance du capteur d'ultrasons.
	 */
	public  EV3UltrasonicSensor us ;
	/**
	 * Instance qui fournit le nombre d'éléments à mettre dans le tableau sample en fonction du mode du capteur choisi.
	 */
	private SampleProvider source;
	/**
	 * Distance actuellement mesurée.
	 */
	private float currentDistance;
	/**
	 * Dernière distance mesurée.
	 */
	private float lastDistance;

	/**
	 * Constructeur qui initialise tous les attributs.
	 * Le mode du capteur est le "distance" mode, permettant de mesurer les distances.
	 */ 
	public UltrasonicSensor() { 
		b = BrickFinder.getDefault();
		s1 = b.getPort("S1");
		us = new EV3UltrasonicSensor(s1);
		source = us.getMode("Distance");
		sample = new float[source.sampleSize()]; 
		currentDistance = this.getDistance();
		lastDistance = 3.0f;
	}

	/**
	 *Retourne la distance actuellement mesurée.
	 * @return {@link UltrasonicSensor#sample}[0]
	 */
	public float getDistance() {
		source = us.getDistanceMode();
		source.fetchSample(sample, 0); 
		return sample[0];
	}

	/**
	 * Retourne l'attribut correspondant à la distance actuellement mesurée.
	 * @return {@link UltrasonicSensor#currentDistance}
	 */
	public float getCurrentDistance() {
		return currentDistance;
	}

	/**
	 * Retourne la dernière distance mesurée.
	 * @return {@link UltrasonicSensor#lastDistance}
	 */
	public float getLastDistance() {
		return lastDistance;
	}

	/**
	 * Modifie la distance actuellement mesurée.
	 * @param currentDistance est la nouvelle distance actuellement mesurée.
	 */
	public void setCurrentDistance(float currentDistance) {
		this.currentDistance = currentDistance;
	}

	/**
	 * Modifie la dernière distance mesurée.
	 * @param lastDistance est la nouvelle dernière distance mesurée.
	 */
	public void setLastDistance(float lastDistance) {
		this.lastDistance = lastDistance;
	}

	/**
	 * Méthode qui permet de détecter la présence d'un mur.
	 * @return true si le robot est face à un mur, false sinon.
	 */
	public boolean detectWall() {
		if(getDistance() < 0.3) {
			return true;
		}
		return false;
	}

	/**
	 * Méthode qui permet de détecter la présence d'un palet.
	 * @return true si le robot a détecté un palet, false sinon.
	 */
	public boolean detectPalet() { 
		if(currentDistance > lastDistance  && lastDistance <= 0.4) { 
			return true;
		}
		return false;
	}

}
