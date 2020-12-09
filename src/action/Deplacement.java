package action;

import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

/**
 * Déplacement est la classe qui permet de gérer les mouvements du robot.
 * @author Khadidja MEHDI
 */

public class Deplacement {

	/**
	 * Instance du pilote MovePilot du robot.
	 * @see Deplacement#getPilot()
	 */
	private MovePilot pilot;
	/**
	 * L'angle de position du robot. Il est modifié à chaque rotation du robot.
	 * Il peut y avoir des erreurs d'inexactitude.
	 * La valeur zéro correspond au robot tourné vers le nord, sur la ligne de départ, face à la ligne adverse.
	 * @see Deplacement#getPosition()
	 */
	private double position = 0;


	/**
	 * Constructeur Deplacement.
	 * À l'appel de ce constructeur, le robot est instancié et calibré.
	 * @see Deplacement#pilot
	 */
	public Deplacement() {
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B,0.056).offset(-0.06075); // 1.5 = diam�tre des roues, offset = d�calage des roues ??
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 0.056).offset(0.06075);
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		pilot.setAngularSpeed(45);
		
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(double position) {
		this.position = position;
	}

	/**
	 * Retourne le pilot.
	 * @return une instance de MovePilot, qui est l'attribut du robot.
	 */
	public MovePilot getPilot() {
		return pilot;
	}
	/**
	 * Retourne la position.
	 * @return la position de type entier.
	 */
	public double getPosition() {
		return position;
	}
	/**
	 * @param distance est la distance à parcourir.
	 * Le robot avance d'une certaine distance
	 * @see Deplacement#pilot
	 */
	public void avancer(double distance) {
		pilot.travel(distance);
	}

	/**
	 * Le robot avance et continue d'avancer tant qu'il n'est pas arr�t�
	 * @see Deplacement#pilot
	 */
	public void avancer() {
		pilot.forward();
	}
	/**
	 * @param distance est la distance à parcourir.
	 * Le robot recule d'une certaine distance.
	 * @see Deplacement#pilot
	 */
	public void reculer(double distance) {
		pilot.travel(-distance);
	}

	/**
	 *  * Le robot recule et continue de reculer tant qu'il n'est pas arr�t�.
	 * @see Deplacement#pilot
	 */
	public void reculer() {
		pilot.backward();
	}
	/**
	 * Le robot arrête tout mouvement.
	 * @see Deplacement#pilot
	 */
	public void stop(){
		pilot.stop();
	}
	/**
	 * Le robot tourne à gauche.
	 * L'attribut position est actualisé à chaque rotation du robot.
	 * @param angle est l'angle de rotation du robot.
	 * @see Deplacement#position
	 */
	public void turnLeft(double angle) {
		pilot.rotate(-angle,true);
		modifierPosition(-angle);
	}
	/**
	 * Le robot tourne à droite.
	 * L'attribut position est actualisé à chaque rotation du robot.
	 * @param angle
	 * @see Deplacement#position
	 */
	public void turnRight(double angle) {
		pilot.rotate(angle,true);
		modifierPosition(angle);
	}
	/**
	 * Méthode qui actualise modifie l'attribution de position du robot selon l'angle.
	 * @param angle est l'angle de rotation.
	 * @see Deplacement#position
	 */
	public void modifierPosition(double angle) {
		position+=angle;
		if (position>=360)
			position-=360;
		if (position<0)
			position=360+position;
	}
	
	// Se remet en direction de la ligne d'en-but adverse
	public void retourPositionInitial() {
		if (position < 230) {
			turnLeft(position);

		}else {
			turnRight(360-position);
		}
	}
	
	
	// Se met en direction d'une position donn�e
	public void gotoPosition(double angle) {
		if(position < angle) {
			turnRight(angle-position);
		}else {
			turnLeft(position-angle);
		}
	}
	
	
	// tests
	public static void main(String[] args) {
		Deplacement d = new Deplacement();
		d.turnLeft(140);
		while(d.getPilot().isMoving()) {
			
		}
		d.gotoPosition(360-160);
		while(d.getPilot().isMoving()){
			
		}
		
	}
}


