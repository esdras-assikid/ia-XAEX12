package action;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MoveController;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import perception.TouchSensor;
import perception.UltrasonicSensor;

public class Navigate {
	Navigator na;
	Path chemin;

	public Navigate() {
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B,0.056).offset(-0.06075); // 1.5 = diam�tre des roues, offset = d�calage des roues ??
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 0.056).offset(0.06075);
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		pilot.setAngularSpeed(90);
		
		na = new Navigator((MoveController) pilot);
		chemin = new Path();
		chemin.add((new Waypoint(0, 0.6)));
		chemin.add((new Waypoint(-0.10, 2.4)));
		chemin.add((new Waypoint(0, 1.8)));
		chemin.add((new Waypoint(0, 2.4)));
		chemin.add((new Waypoint(-0.5, 1.8)));
		na.setPath(chemin);
		na.singleStep(true);
		na.followPath();
		na.goTo(0, 0.6f);
		
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		
		Navigator na;
		Path chemin;
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B,0.056).offset(-0.06075); // 1.5 = diam�tre des roues, offset = d�calage des roues ??
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 0.056).offset(0.06075);
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = ((new MovePilot(chassis)));
		pilot.setAngularSpeed(90);
		na = new Navigator(pilot);
		chemin = new Path();
		chemin.add((new Waypoint(0.60, 0.50)));
		chemin.add((new Waypoint( 2.40, 0.10)));
		chemin.add((new Waypoint( 1.80, 0.80)));
		
		
		//
		
		 /* chemin.add((new Waypoint(-10, 240))); chemin.add((new Waypoint(0, 180)));
		 * chemin.add((new Waypoint(0, 240))); chemin.add((new Waypoint(-50, 180)));
		 */
		
		boolean v = true;
		na.setPath(chemin);
		
		System.out.println(na.getPath().toString());
		na.followPath();
		
		
		while(v) {
			
			v = na.waitForStop();
			
			
			
			if(Button.ESCAPE.isDown()) {
				
				
				System.exit(0);
			}
		}
		
	}

}
