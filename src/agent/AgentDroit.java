package agent;

import java.io.FileNotFoundException;

import action.*;
import lejos.hardware.Button;
import perception.*;

/**
 * AgentDroit est une classe identique à Agent. Elle est lancée lorsque le robot débute la partie du côté droit.
 * @author Esdras ASSIKIDANA
 */
public class AgentDroit {
	/**
	 * Instance de l'état du terrain.
	 */
	Etat e; 
	/**
	 * Instance du capteur d'ultrasons.
	 */
	UltrasonicSensor us; 
	/**
	 * Instance du capteur de couleurs.
	 */
	ColorSensor cs; 
	/**
	 * Instance du capteur de toucher.
	 */
	TouchSensor ts; 
	/**
	 * Instance de la pince.
	 */
	Pince p; 
	/**
	 * Instance de déplacement.
	 */
	Deplacement d;
	/**
	 * Instance de DB pour le contrôle des commandes.
	 */
	DB db;
	/**
	 * Instance de FollowPath pour suivre le circuit décrit dans Etat.
	 */
	FollowPath fp;
	/**
	 * Instance de But pour les actions à réaliser au moment de marquer un but.
	 */
	But b;
	/**
	 * Instance de PaletFinder pour exécuter les phases de recherche.
	 */
	PaletFinder pf;

	/**
	 * Constructeur qui initialise toutes les instances.
	 * @throws FileNotFoundException si le fichier CouleurXAEX12.txt est absent pour l'instanciation de la classe ColorSensor
	 */
	public AgentDroit() throws FileNotFoundException {
		cs = new ColorSensor();
		d = new Deplacement();
		e = new Etat();
		us = new UltrasonicSensor();
		db = new DB();
		p = new Pince(db);
		fp = new FollowPath(e, d, db);
		b= new But(cs, db);
		pf = new PaletFinder(us, d, db);
		ts = new TouchSensor(db);
	}

	/**
	 * Méthode principale qui instancie Agent et lance le programme dès que le bouton "Enter" est pressé.
	 * La méthode start() permet d'appeler les méthodes run des instances sur lesquelles elle est appelée.
	 * Le programme s'arête quand le bouton "Escape" est pressé.
	 * @param args String[] type
	 */
	public static void main(String[] args) {
		try {
			AgentDroit a = new AgentDroit();
			while(!Button.ENTER.isDown()) {
			}

			a.fp.start();
			a.b.start();
			a.p.start();
			a.pf.start();
			a.ts.start();

			while(!Button.ESCAPE.isDown());
			System.exit(0);
			if(Button.ESCAPE.isDown()) {
				a.cs.colorSensor.close();
				a.d.stop();
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

