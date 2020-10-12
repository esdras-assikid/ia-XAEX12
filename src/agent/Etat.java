package agent;

import perception.ColorSensor;

public class Etat {
	

	// Désignent le nombre de points probablement possible de marquer pour chaque ligne
	// La proximité des lignes est définie par rapport à la ligne adverse : ligneProche = ligne proche de la ligne adverse
		final static int idLigneProche = 1;
		final static int idLigneLoin = 2;
		final static int idLigneMilieu = 3;

	// Tableau d'objets contenant 2 valeurs : 
	// 1 String désignant la couleur et 1 int désignant le nombre de points restant pouvant probablement être pris pour chacune des lignes
		Ligne ligneProche ;
		Ligne ligneLoin ;
		Ligne ligneMilieu; 


	public Etat(String couleurLigneProche) {
		ligneProche = new Ligne(couleurLigneProche, idLigneProche);
		ligneMilieu = new Ligne(ColorSensor.BLACK, idLigneMilieu);
		if(couleurLigneProche.equals(ColorSensor.BLUE)) {
			ligneLoin = new Ligne(ColorSensor.GREEN, idLigneLoin);
		}else if(couleurLigneProche.equals(ColorSensor.GREEN)) {
			ligneLoin = new Ligne(ColorSensor.BLUE, idLigneLoin);
		}else {
			throw new IllegalArgumentException("la couleur de la ligne n'est pas bonne");
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

}
