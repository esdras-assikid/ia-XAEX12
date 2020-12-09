package agent;

import java.util.ArrayList;

import perception.ColorSensor;

//Cette donne au robot une représentation de l'état du terrain
// Elle réutilise la classe Point

public class Etat {
	


		// Tableau contenant les points où le robot doit effectuer ses recherches
		private Point[] circuit;
		
		// Entier désignant le numéro du point actuel vers lequel le robot doit se diriger
		private int idPointActuel;
		
		// Liste contenant les points pour lesquels le robot n'a pas détecté de palet lors de la recherche
		public ArrayList<Integer> pointNotFound;
		

		// Initialise les points du circuit avec des valeurs mesurées sur le terrain
		public Etat() {
			
				Point point0 = new Point(30, 0.3,0.5f);
				Point point1 = new Point(130, 0.67, 0.65f);
				Point point2 = new Point(180, 1.35, 0.7f);
				Point point3 = new Point(140, 0.30, 0.65f);
				Point point4 = new Point(180, 0.7, 0.75f);
				Point point5 = new Point(180,1.35,0.7f);
				Point point6 = new Point(200,1.40, 0.7f);
				
				// Tous ces points en commentaires sont utiles en cas de changement de stratégie (début à gauche vs à droite)
				
				//Point point2 = new Point(183, 0.67, 0.7f );
				//Point point3 = new Point(140 , 0.30, 0.75f);
				//Point point4 = new Point( 180, 0.70, 0.80f);
				//Point point3 = new Point(115 , 0.5, 0.75f);
				//Point point4 = new  Point(180, 0.6, 0.75f);
				//Point point5 = new Point(215, 0.72, 0.9f);
				//Point point5 = new Point(140, 0.30, 0.8f);
				//Point point6 = new Point(180, 0.7, 0.75f);
				//Point point7 = new Point(180,1.35,0.7f);
				//Point point8 = new Point(200,1.40, 0.7f);
				
				
				circuit = new Point[]{point0, point1,point2, point3, point4,point5, point6,/** point7, point8 ,point5, point6, point7, point8**/ };
				idPointActuel = 1;
				pointNotFound = new ArrayList<Integer>() ;
			
			
		}
		
		// Permet de modifier la valeur de la distance max à laquelle le palet est censé se trouver lors de la recherche
		public void setPointDistanceMAX(float d) {
			circuit[idPointActuel].setDistanceMax(d);
		}
		
		// retourne la distance max à laquelle le palet est censé se trouver lors de la recherche
		public float getDistanceMAX() {
			return circuit[idPointActuel].getDistanceMax();
		}
		
		// Retourne l'ID du point actuel
		public int getIdPointActuel() {
			return idPointActuel;
		}
		
	
		// Modifie l'ID du point actuel vers lequel le robot doit se diriger
		public void setIdPointActuel(int idPointActuel) {
			this.idPointActuel = idPointActuel;
		}
		
		
		// Retourne le circuit
		public Point[] getCircuit() {
			return circuit;
		}
	
	
	
	
		
		
		/**
		 * Récupère l'angle de la position actuel par rapport au prochain point après avoir
		 * marqué un point
		 */
		public double getAngleFromPointMarquage() {
			if(idPointActuel <9) {
				return circuit[idPointActuel].getAngle();
									
			}	
			return 0;	
		}
		
	
		
		/**
		 * Recupère la distance de la position actuel par rapport au prochain point (dans le cas où le palet n'a pas été trouvé)
		 * 
		 *
		 */
		public double getDistanceToPoint() {
			if(idPointActuel <9) {
				return circuit[idPointActuel].getDistance();
									
			}	
			return 0;	
		}

		
	
	

}
