package action;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class Déplacement {
	
	public Déplacement() {
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B,0.056).offset(-0.06075); // 1.5 = diam�tre des roues, offset = d�calage des roues ??
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 0.056).offset(0.06075);
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		
		pilot.setAngularSpeed(90);
		
	}
 
	private MovePilot pilot;
	int position = 0;

	// MÉTHODES //
	public void avancer() {
		pilot.forward();
	}

	public void reculer() {
		pilot.backward();
	}

	public void stop(){
		pilot.stop();
	}

	// Rotation dans le sens des aiguilles d'une montre

	public void turnLeft(int angle) {
		pilot.rotate(-angle);
		modifierPosition(-angle);
	}

	public void turnRight(int angle) {
		pilot.rotate(angle);
		modifierPosition(angle);
	}

	public void modifierPosition(int angle) {
		position+=angle;
		if (position>=360)
			position-=360;
		if (position<0)
			position=360+position;

	}
	
	// Méthodes qui permettent de repositionner le robot vers les sens prédéfinies
	/* public void nord() {
		if (position<=180) 
			turnLeft(position);
		else if (position>180)
			turnRight(360-position);
	}

	public void sud() {
		if (position<=180) {
			turnRight(180-position);
		}
		else if (position>180)
			turnLeft(position-180);
	}
	
	public void est() {
		if (position<=90) {
			turnRight(90-position);
		}
		else if (position>90)
			turnLeft(position-90);
	}
	
	public void ouest() {
		if (position<=270) {
			turnRight(270-position);
		}
		else if (position>270)
			turnLeft(position-270);
	*/ }
	
	

