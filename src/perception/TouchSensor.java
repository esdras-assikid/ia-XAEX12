package perception;

import agent.DB;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;


import lejos.robotics.*;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
/**
 * TouchSensor est la classe qui exploite le capteur de toucher.
 * Elle utilise la classe DB.
 * @author Pierre LAURENT-PAOLI.
 */
public class TouchSensor extends Thread {

	/**
	 * Instance du capteur de toucher.
	 */
	private EV3TouchSensor ts;
	/**
	 * Tableau contenant les valeurs renvoyées par le capteur de toucher.
	 */ 
	private float [] sample;
	/**
	 * Instance fournissant le nombre de valeurs à mettre dans le tableau sample en fonction du mode du capteur choisi.
	 * @see TouchSensor#sample
	 */
	private SampleProvider source;
	/**
	 * Instance de la brick EV3.
	 */
	Brick b;
	/**
	 * Instance du port dans lequel est branché le capteur de toucher.
	 */
	Port s3;
	/**
	 * État du capteur de toucher.
	 * S'il est stimulé, il vaut true.
	 * S'il n'est pas stimulé, il vaut false.
	 */
	private boolean etat;
	/**
	 * Instance de DB permettant de modifier les commandes.
	 */
	private DB db;

	/**
	 * Constructeur qui initialise les attributs.
	 * Le mode du capteur est le TouchMode.
	 * L'état est initialisé à false : au début de la partie, le robot a les pinces vides et fermées.
	 * @param db {@link DB} type
	 */
	public TouchSensor(DB db) {
		b = BrickFinder.getDefault();
		s3 = b.getPort("S3");
		ts = new EV3TouchSensor(s3);
		source = ts.getTouchMode();
		sample = new float[source.sampleSize()];
		etat=false;
		this.db = db;
	}

	/**
	 * Méthode booléenne qui retourne true s'il le capteur de toucher est pressé.
	 * @return true si le capteur de toucher est préssé, false sinon.
	 */
	public boolean isPressed() {
		float[] sample = new float[1];
		source.fetchSample(sample, 0);
		return sample[0] != 0;
	}

	/**
	 * @return l'état du capteur.
	 * @see TouchSensor#etat
	 */
	public boolean isEtat() {
		return etat;
	}

	/**
	 * Modifie l'attribut d'état du capteur.
	 * @param etat boolean etat
	 * @see TouchSensor#etat
	 */
	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	/**
	 * @return l'état du capteur.
	 * @see TouchSensor#etat
	 */
	public boolean aEteTouche() {
		while(etat == false) {
			etat = isPressed();
		}
		return etat;
	}

	/**
	 * Méthode principale contenant les instructions à réaliser suivant l'état du capteur et la commande en cours.
	 */ 
	public void run() {
		while(true) {
			if(db.getCmd()==DB.FIRSTPOINTCMD) {
				if(aEteTouche()) {
					System.out.print("CA MARCHE AUSSI");
					db.setPaletDetected(false);
					db.setCmd(DB.FIRSTSAISIECMD);
					this.etat=false;
				}
			}
			if(db.isPaletDetected() && db.getCmd()==DB.GOTOPALETCMD ) {
				if(aEteTouche()) {
					System.out.print("CA MARCHE AUSSI");
					db.setCmd(DB.SAISIECMD);
				}
			}
			if(db.isPaletDetected() && db.getCmd()==DB.AFTEROPENPINCECMD ) {
				if(aEteTouche()) {
					db.setCmd(DB.SAISIECMD);
				}
			}
			if(db.getCmd() == DB.DIRECTIONBUTCMD){
				this.etat=false;
			}
		}
	}

	/**
	 * Programme de test.
	 * @param args {@link String}[] type
	 */
	public static void main(String[] args) {

		TouchSensor ts = new TouchSensor(new DB());
		MovePilot pilot;
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B,0.056).offset(-0.06075); 
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 0.056).offset(0.06075);
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		pilot.forward();
		while(true) {
			if(ts.aEteTouche())
				pilot.stop();
			System.out.println(ts.aEteTouche());
			if(Button.ESCAPE.isDown()) {
				System.exit(0);
			}
		}
	}

}
