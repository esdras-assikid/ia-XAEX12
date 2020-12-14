package agent;

import java.io.FileNotFoundException;

import action.*;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import perception.*;

/**
 * Agent est la classe qui permet de lancer le programme complet du robot.
 * Toutes les méthodes run() contenues dans les autres classes sont exécutées en parallèle grâce à la classe Thread.
 * Elle utilise toutes les autres classes sauf Point, AgentDroit et AgentGauche.
 * @author Esdras ASSIKIDANA
 */
public class Agent {

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
	 * Instance du déplacement du robot.
	 */
	Deplacement d;
	/**
	 * Instance de DB pour le contrôle des commandes.
	 */
	DB db;
	/**
	 * Instance de Followpath pour suivre le circuit décrit dans Etat.
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
	 * Constructeur qui instancie toutes les instances.
	 * @throws FileNotFoundException
	 */
	public Agent() throws FileNotFoundException {
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
	 * La méthode start() permet d'appeler les méthodes run() des instances sur lesquelles elle est appelée.
	 * Le programme s'arrête quand le bouton "Escape" est pressé.
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			Agent a = new Agent();
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
