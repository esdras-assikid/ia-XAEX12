package action;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;

public class Déplacement {

	private static EV3TouchSensor s1 = new EV3TouchSensor(SensorPort.S1);
	int position = 0;
	int x = 0;
	int y = 0;

	// MÉTHODES //
	public void avancer() {
		Motor.B.forward();
		Motor.C.forward();	
	}

	public void reculer() {
		Motor.B.backward();
		Motor.C.backward();	
	}

	public void stop(){
		Motor.B.stop();
		Motor.C.stop();
	}

	// Rotation dans le sens des aiguilles d'une montre

	public void turnLeft(int angle) {
		Motor.B.rotate(-angle,true);
		Motor.C.rotate(angle,true);
		modifierPosition(-angle);
	}

	public void turnRight(int angle) {
		Motor.B.rotate(angle,true);
		Motor.C.rotate(-angle,true);
		modifierPosition(angle);
	}

	public void modifierPosition(int angle) {
		position+=angle;
		if (position>360)
			position-=360;

	}
	
	// Méthodes qui permettent de repositionner le robot vers les sens prédéfinies
	public void nord() {
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
	}
	
	
}
