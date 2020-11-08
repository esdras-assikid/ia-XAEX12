package agent;

import lejos.robotics.geometry.Point;
import perception.ColorSensor;

public class Etat {
	

	// Désignent le nombre de points probablement possible de marquer pour chaque ligne
	// La proximité des lignes est définie par rapport à la ligne adverse : ligneProche = ligne proche de la ligne adverse
		final static int idLigneProche = 1;
		final static int idLigneLoin = 2;
		final static int idLigneMilieu = 3;
		
		private Point[] circuit;
		private int idPointActuel;

	// Tableau d'objets contenant 2 valeurs : 
	// 1 String désignant la couleur et 1 int désignant le nombre de points restant pouvant probablement être pris pour chacune des lignes
	// on garde ceci pour une seconde stratégie si la première devient obsolète
		public Ligne ligneProche ;
		
		
		public Ligne ligneLoin ;
		public Ligne ligneMilieu; 
		private int idligneActuel;


		public Etat(String couleurLigneProche) {
				Point point1 = new Point(-0.15f, 0.5f);
				Point point2 = new Point(0.4f, 0.5f );
				Point point3 = new Point(1f , 0.5f);
				Point point4 = new Point( 1f, 1.1f);
				Point point5 = new Point(0.5f, 1.1f);
				Point point6 = new Point(0f, 1.1f);
				Point point7 = new Point(0.5f, 1.8f);
				Point point8 = new Point(1f, 1.8f);
				
				
				circuit = new Point[]{point1,point2, point3, point4,point5, point6, point7, point8 };
				idPointActuel = 0;
				
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
		
		/**
		 * @return the idPointActuel
		 */
		public int getIdPointActuel() {
			return idPointActuel;
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
		public int getAngleFromPointMarquage() {
			if(idPointActuel <7) {
				Point lastpoint = circuit[idPointActuel];
				Point nextpoint = circuit[idPointActuel+1];
				if(idPointActuel==0) {
					return  (int) (((nextpoint.angle())*180) / Math.PI) ;
				}
				return (int) (((nextpoint.angleTo(new Point(lastpoint.x, 0f)))*180) / Math.PI);
									
			}	
			return 0;	
		}
		
		/**
		 * Recupère l'angle de la position actuel par rapport au prochain point si le 
		 * palet n'est pas à la position précédente
		 *
		 */
		public int getAngleFromPointNotFound() {
			if(idPointActuel <7) {
				Point lastpoint = circuit[idligneActuel];
				Point nextpoint = circuit[idligneActuel+1];
				if(idligneActuel==0) {
					return  (int) (((nextpoint.angleTo(new Point(0f, 0.6f)))*180) / Math.PI) ;
				}
				return (int) (((nextpoint.angleTo(lastpoint))*180) / Math.PI);
									
			}	
			return 0;	
		}
	
	

}
