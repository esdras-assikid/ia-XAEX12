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
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
import perception.UltrasonicSensor;

public class Test {

	public static void main(String[] args) {
		
		UltrasonicSensor ultra;
		EV3UltrasonicSensor us;
		MovePilot pilot;
		
		Brick b = BrickFinder.getDefault();
		Port s1 = b.getPort("S1");
		us = new EV3UltrasonicSensor(s1);
		ultra = new UltrasonicSensor(us.getMode("Distance"));
		
		
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B,0.056).offset(-0.06075); // 1.5 = diamètre des roues, offset = décalage des roues ??
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 0.056).offset(0.06075);
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		
		pilot.setAngularSpeed(90);
		
		
		
		float currentDistance;
		float lastDistance = 2.5f;
		boolean res = false;
		pilot.forward();
		
		
		while (pilot.isMoving()) {
			Delay.msDelay(10);
			currentDistance = ultra.getDistance();
			res = ultra.detectPalet(currentDistance, lastDistance);
			lastDistance = currentDistance;
			
			if(res) {
				pilot.stop();
				break;
			}
			
			if(Button.ESCAPE.isDown()) {
				pilot.stop();
				us.close();
				System.exit(0);
			}
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

