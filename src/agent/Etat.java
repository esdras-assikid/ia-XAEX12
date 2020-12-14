package agent;

import java.util.ArrayList;



/**
 * Etat est la classe qui fournit au robot une représentation de l'état du terrain.
 * Elle réutilise la classe Point.
 * @author Esdras ASSIKIDANA
 */
public class Etat {
	/**
	 * Tableau contenant les points où le robot doit effectuer ses recherches.
	 */ 
	private Point[] circuit;
	/**
	 * Entier désignant le numéro du point vers lequel le robot doit se diriger.
	 */
	private int idPointActuel;
	/**
	 * Liste contenant les points pour lesquels le robot n'a pas détecté de palet lors de la recherche.
	 */
	public ArrayList<Integer> pointNotFound;

	/**
	 * Constructeur qui initialise les points du circuit avec des valeurs mesurées sur le terrain.
	 */
	public Etat() {
		Point point0 = new Point(30, 0.3,0.5f);
		Point point1 = new Point(130, 0.67, 0.65f);
		Point point2 = new Point(180, 1.35, 0.7f);
		Point point3 = new Point(140, 0.30, 0.65f);
		Point point4 = new Point(180, 0.7, 0.75f);
		Point point5 = new Point(180,1.35,0.7f);
		Point point6 = new Point(200,1.40, 0.7f);
		circuit = new Point[]{point0, point1,point2, point3, point4,point5, point6,/** point7, point8 ,point5, point6, point7, point8**/ };
		idPointActuel = 1;
		pointNotFound = new ArrayList<Integer>() ;
	}

	/**
	 * Permet de modifier la valeur de la distance maximale à laquelle le palet est censé se trouver lors de la recherche.
	 * @param d float type
	 */ 
	public void setPointDistanceMAX(float d) {
		circuit[idPointActuel].setDistanceMax(d);
	}

	/**
	 * Retourne la distance maximale à laquelle le palet est censé se trouver lors de la recherche.
	 * @return la distance maximale à laquelle le palet est censé se trouver lors de la recherche du point actuel
	 */
	public float getDistanceMAX() {
		return circuit[idPointActuel].getDistanceMax();
	}

	/**
	 * Retourne l'ID du point actuel.
	 * @return ID du point actuel.
	 */
	public int getIdPointActuel() {
		return idPointActuel;
	}

	/**
	 * Modifie l'ID du point vers lequel le robot doit se diriger.
	 * @param idPointActuel {@link Etat#idPointActuel}
	 */
	public void setIdPointActuel(int idPointActuel) {
		this.idPointActuel = idPointActuel;
	}

	/**
	 * Retourne le circuit.
	 * @return {@link Etat#circuit}
	 */
	public Point[] getCircuit() {
		return circuit;
	}

	/**
	 * Récupère l'angle de la position actuelle par rapport au prochain point après avoir marqué un point.
	 * @return angle
	 */
	public double getAngleFromPointMarquage() {
		if(idPointActuel <9) {
			return circuit[idPointActuel].getAngle();
		}	
		return 0;	
	}

	/**
	 * Récupère la distance de la position actuelle par rapport au prochain point (dans le cas où le palet n'a pas été trouvé).
	 * @return distance
	 */
	public double getDistanceToPoint() {
		if(idPointActuel <9) {
			return circuit[idPointActuel].getDistance();
		}	
		return 0;	
	}
}