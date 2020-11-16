package action;

import lejos.utility.Delay;

public class TestsPince {
	
	public static void main(String[] args) {
		Pince pince = new Pince();
		Deplacement d = new Deplacement();
		
		// A- test serrer-deserrer a realiser 5-10 fois
		// depart pinces fermees
		pince.deserrer(); // mesurer l'amplitude des pinces une fois ouvertes
		pince.serrer(); // mesurer l'amplitude des pinces une fois fermees 
		
		// B- test saisiePalet-lachePalet a realiser 5-10 fois
		// depart pinces ouvertes
		pince.deserrer();
		// puis mettre un palet entre les pinces
		
			// 1- s'assurer que le palet n'est pas ecrase
			pince.saisiePalet(); // mesurer l'ecartement des pinces
			pince.lachePalet(); // idem
		
			// 2- s'assurer que le palet est correctement emprisonne entre les pinces
			// pendant les deplacements du robot
			pince.saisiePalet();
			d.avancer();
			Delay.msDelay(50);
			d.turnLeft(360);
			d.turnRight(360);
			d.reculer();
			Delay.msDelay(50);
			pince.lachePalet();
			
		/* si 2 est un echec apres une reussite de 1, recalibrer le nombre de tours
		 * pour que la saisiePalet soit plus serree
		*/
		
		// C- test saisirPalet
		// depart pinces fermees, palet face au robot a 15cm - 50cm a realiser 5 fois chacun
		pince.saisirPalet();
		// mesurer le nombre de reussites et d'echecs
		
		// D- tests generaux pour verifier le calibrage de la pince apres les tests precedents
		// depart pinces fermees
		pince.deserrer();
		pince.saisiePalet(); // en mettant le palet entre ses pinces
		pince.lachePalet();
		pince.serrer();
		
	}
}
