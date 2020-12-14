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
	 */
	private MovePilot pilot;
	/**
	 * Angle de position du robot. Il est modifié à chaque rotation du robot.
	 * Il peut y avoir des erreurs d'inexactitude.
	 * La valeur zéro correspond au robot tourné vers le nord, sur la ligne de départ, face à la ligne adverse.
	 * @see Deplacement#getPosition()
	 */
	private double position = 0;

	/**
	 * Constructeur Deplacement.
	 * À l'appel de ce constructeur, le robot est instancié et calibré.
	 */
	public Deplacement() {
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B,0.056).offset(-0.06075); // 1.5 = diam�tre des roues, offset = d�calage des roues ??
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 0.056).offset(0.06075);
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		pilot.setAngularSpeed(45);
	}

	/**
	 * Modifie l'attribut position.
	 * @param the position to set
	 */
	public void setPosition(double position) {
		this.position = position;
	}

	/**
	 * Retourne le pilot.
	 * @return {@link Deplacement#pilot}
	 */
	public MovePilot getPilot() {
		return pilot;
	}

	/**
	 * Retourne la position.
	 * @return {@link Deplacement#position}
	 */
	public double getPosition() {
		return position;
	}

	/**
	 * Fait avancer le robot d'une certaine distance.
	 * @param distance est la distance à parcourir
	 */
	public void avancer(double distance) {
		pilot.travel(distance);
	}

	/**
	 * Fait avancer le robot tant qu'il n'est pas arrêté.
	 */
	public void avancer() {
		pilot.forward();
	}

	/**
	 * Fait reculer le robot d'une certaine distance.
	 * @param distance est la distance à parcourir.
	 */
	public void reculer(double distance) {
		pilot.travel(-distance);
	}

	/**
	 * Fait reculer le robot tant qu'il n'est pas arrêté.
	 */
	public void reculer() {
		pilot.backward();
	}

	/**
	 * Arrête tout mouvement du robot.
	 */
	public void stop(){
		pilot.stop();
	}

	/**
	 * Fait tourner le robot à gauche.
	 * L'attribut position est actualisé à chaque rotation du robot.
	 * @param angle est l'angle de rotation du robot.
	 */
	public void turnLeft(double angle) {
		pilot.rotate(-angle,true);
		modifierPosition(-angle);
	}

	/**
	 * Faut tourner le robot à droite.
	 * L'attribut position est actualisé à chaque rotation du robot.
	 * @param angle est l'angle de rotation du robot.
	 */
	public void turnRight(double angle) {
		pilot.rotate(angle,true);
		modifierPosition(angle);
	}

	/**
	 * Actualise l'attribut de position du robot selon l'angle.
	 * @param angle est l'angle de rotation.
	 */
	public void modifierPosition(double angle) {
		position+=angle;
		if (position>=360)
			position-=360;
		if (position<0)
			position=360+position;
	}

	/**
	 * Remet le robot en direction de la ligne d'en-but adverse.
	 */
	public void retourPositionInitial() {
		if (position < 230) {
			turnLeft(position);
		} else {
			turnRight(360-position);
		}
	}

	/**
	 * Met le robot en direction d'une position de donné.
	 * @param angle de position souhaité
	 */
	public void gotoPosition(double angle) {
		if(position < angle) {
			turnRight(angle-position);
		} else {
			turnLeft(position-angle);
		}
	}

}