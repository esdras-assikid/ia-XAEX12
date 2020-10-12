package agent;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.Move;
//import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
import perception.UltrasonicSensor;


// recherche le palet le plus proche et se dirige vers lui

public class UltraClosest {
	
	// DifferentialPilot pilot;
	MovePilot pilot;
	UltrasonicSensor ultra;
	float closestAngle = -1.0f;
	float closestDistance = 250.0f;
	EV3UltrasonicSensor us;
	
	public static void main(String[] args) {
		
			new UltraClosest();
		

	}
	
	public UltraClosest() {
		//pilot = new DifferentialPilot(1.5f, 6, Motor.B, Motor.C);
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B,0.056).offset(-0.06075); // 0.056 = diamètre des roues, offset = décalage des roues ??
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 0.056).offset(0.06075);
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		setupUltrasonic();
		runProgram();
		us.close();
	}
	
	public void setupUltrasonic() {
		Brick b = BrickFinder.getDefault();
		Port s1 = b.getPort("S1");
		us = new EV3UltrasonicSensor(s1);
		ultra = new UltrasonicSensor(us.getMode("Distance"));
	}
	
	public void runProgram() {
		//pilot.reset(); // reset count of the motors
		//pilot.setLinearSpeed(pilot.getMaxLinearSpeed());
		pilot.setAngularSpeed(90); // 45° par sec
		pilot.rotate(360,true); // continue le reste du code en même temps que la rotation
		
		
		while(pilot.isMoving()) {
			Delay.msDelay(10); // 50 mesures par sec
		    UltrasonicSensor.currentDistance = ultra.getDistance();
			if (UltrasonicSensor.currentDistance < closestDistance && UltrasonicSensor.currentDistance > 0.320) {
				closestDistance = UltrasonicSensor.currentDistance;
				closestAngle = pilot.getMovement().getAngleTurned(); // retourne l'angle parcouru depuis que la rotation a commencé
			}
		}
		Move movement;
		pilot.setAngularSpeed(45);
		
		if(closestAngle < 180)
			pilot.rotate(closestAngle);
		else
			pilot.rotate(closestAngle - 360);
		
		pilot.forward();
		UltrasonicSensor.currentDistance= ultra.getDistance();
		boolean res = false;
		while (res == false && ultra.detectWall() == false) {
			Delay.msDelay(100);
			UltrasonicSensor.currentDistance = ultra.getDistance();
			res = ultra.detectPalet();
			UltrasonicSensor.lastDistance = UltrasonicSensor.currentDistance;
		}
		
		pilot.stop();
		
		while(true) {

			
			if(Button.ESCAPE.isDown()) {
				pilot.stop();
				us.close();
				System.exit(0);
			}

		}
	}

}
