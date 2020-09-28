package agent;


import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import perception.UltrasonicSensor;

public class Test {

	public static void main(String[] args) {
		
		UltrasonicSensor ultra;
		EV3UltrasonicSensor us;
		
		Brick b = BrickFinder.getDefault();
		Port s1 = b.getPort("S1");
		us = new EV3UltrasonicSensor(s1);
		ultra = new UltrasonicSensor(us.getMode("Distance"));
		
		ultra.detectPalet();
		
		
	}

}
