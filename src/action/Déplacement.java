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
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B,0.056).offset(-0.06075); // 1.5 = diamï¿½tre des roues, offset = dï¿½calage des roues ??
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 0.056).offset(0.06075);
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);

		pilot.setAngularSpeed(90);

	}

	private MovePilot pilot;
	int position = 0;

	// MÃ‰THODES //
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

}


