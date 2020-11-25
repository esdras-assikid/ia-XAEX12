package agent;

import java.util.ArrayList;

import perception.ColorSensor;

public class Etat {
	

	// Désignent le nombre de points probablement possible de marquer pour chaque ligne
	// La proximité des lignes est définie par rapport à la ligne adverse : ligneProche = ligne proche de la ligne adverse
		final static int idLigneProche = 1;
		final static int idLigneLoin = 2;
		final static int idLigneMilieu = 3;
		
		private Point[] circuit;
		private int idPointActuel;
		
		public ArrayList<Point> pointNotFound;

	// Tableau d'objets contenant 2 valeurs : 
	// 1 String désignant la couleur et 1 int désignant le nombre de points restant pouvant probablement être pris pour chacune des lignes
	// on garde ceci pour une seconde stratégie si la première devient obsolète
		public Ligne ligneProche ;
		
		
		public Ligne ligneLoin ;
		public Ligne ligneMilieu; 
		private int idligneActuel;
		
		public static int LEFT=-1;
		public static int RIGHT=1;


		public Etat(String couleurLigneProche) {
				Point point0 = new Point(30, 0.3,0.5f, RIGHT);
				Point point1 = new Point(160, 0, 0.65f,  RIGHT);
				Point point2 = new Point(180, 0.50, 1.00f, RIGHT );
				Point point3 = new Point(135 , 0.50, 0.85f, RIGHT);
				Point point4 = new Point( 180, 0.6, 0.45f,RIGHT);
				Point point5 = new Point(180, 0.6, 0.5f, RIGHT);
				Point point6 = new Point(180, 0.6, 0.75f, LEFT);
				//Point point7 = new Point(0.5f, 1.8f);
				//Point point8 = new Point(1f, 1.8f);
				
				
				circuit = new Point[]{point0, point1,point2, point3, point4,point5, point6, /**,point5, point6, point7, point8**/ };
				idPointActuel = 1;
				pointNotFound = new ArrayList<Point>() ;
				ligneProche = new Ligne(couleurLigneProche, idLigneProche);
				ligneMilieu = new Ligne(ColorSensor.BLACK, idLigneMilieu);
				idligneActuel = idLigneProche;
				if(couleurLigneProche.equals(ColorSensor.BLUE)) {
					ligneLoin = new Ligne(ColorSensor.GREEN, idLigneLoin);
				}else if(couleurLigneProche.equals(ColorSensor.GREEN)) {
					ligneLoin = new Ligne(ColorSensor.BLUE, idLigneLoin);
				}else {
					throw new IllegalArgumentException("la couleur de la ligne n'est pas bonne");
				}
			
		}
		
		public void setPointDistanceMAX(float d) {
			circuit[idPointActuel].setDistanceMax(d);
		}
		
		public float getDistanceMAX() {
			return circuit[idPointActuel].getDistanceMax();
		}
		
		/**
		 * @return the idPointActuel
		 */
		public int getIdPointActuel() {
			return idPointActuel;
		}
		
		public Point getCurrentPoint() {
			return circuit[idligneActuel];
		}

		/**
		 * @param idPointActuel the idPointActuel to set
		 */
		public void setIdPointActuel(int idPointActuel) {
			this.idPointActuel = idPointActuel;
		}
		
		/**
		 * @param idligneActuel the idligneActuel to set
		 */
		public void setIdligneActuel(int idligneActuel) {
			this.idligneActuel = idligneActuel;
		}
	
		public Point[] getCircuit() {
			return circuit;
		}
		
		public int getIdligneActuel() {
			return idligneActuel;
		}
	
		public void setIdligneActuel() {
			if(idligneActuel == idLigneProche)
				if(ligneProche.getNbrPointRestant() == 0) {
					this.idligneActuel = idLigneLoin;
				}
			if(idligneActuel == idLigneLoin)
				if(ligneLoin.getNbrPointRestant() == 0) {
					this.idligneActuel = idLigneMilieu;
				}
			if(idligneActuel == idLigneMilieu)
				if(ligneLoin.getNbrPointRestant() == 0) {
					this.idligneActuel = idLigneProche;
				}	
		}

	
		// Met à jour le nombre de points probables restant sur la ligne passée en paramètre
		public void mettreAjour (int idLigne){
			if(idLigne == idLigneLoin)
				ligneLoin.setNbrPointRestant(ligneLoin.getNbrPointRestant()-1);
			if(idLigne == idLigneMilieu)
				ligneMilieu.setNbrPointRestant(ligneMilieu.getNbrPointRestant()-1);
			if(idLigne == idLigneProche)
				ligneProche.setNbrPointRestant(ligneProche.getNbrPointRestant()-1);
		}

		
	
		
		
		/**
		 * Recupère l'angle de la position actuel par rapport au prochain point après avoir
		 * marqué un point
		 */
		public double getAngleFromPointMarquage() {
			if(idPointActuel <4) {
				return circuit[idPointActuel].getAngle();
									
			}	
			return 0;	
		}
		
		public int getCurrentPointOrientation() {
			if(idPointActuel <4) {
				return circuit[idPointActuel].getOrientation();
									
			}	
			return 0;	
		}
		
		/**
		 * Recupère l'angle de la position actuel par rapport au prochain point si le 
		 * palet n'est pas à la position précédente
		 *
		 */
		public double getDistanceToPoint() {
			if(idPointActuel <4) {
				return circuit[idPointActuel].getDistance();
									
			}	
			return 0;	
		}

		
	
	

}
