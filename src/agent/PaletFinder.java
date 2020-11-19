package agent;

import action.Deplacement;
import lejos.utility.Delay;
import perception.UltrasonicSensor;

public class PaletFinder extends Thread {
	private UltrasonicSensor us;
	private Deplacement d;
	private DB db;

	public PaletFinder(UltrasonicSensor us, Deplacement d, DB db) {
		// TODO Auto-generated constructor stub
		this.us = us;
		this.d = d;
		this.db = db;
	}
	
	private float rechercherPaletPlusProche (int angleDeBalayage){
		float closestAngle = -1.0f;
		d.getPilot().setAngularSpeed(45);
		d.getPilot().rotate(angleDeBalayage / 2);
		d.getPilot().rotate(-angleDeBalayage,true);
		
		float lastDistance = 3.0f;
		
		while(d.getPilot().isMoving()) {
			Delay.msDelay(50); // 50 mesures par sec
		    us.setCurrentDistance(us.getDistance());
			if (us.getCurrentDistance() < lastDistance && us.getCurrentDistance() > 0.310) {
				lastDistance = us.getCurrentDistance();
				closestAngle = d.getPilot().getMovement().getAngleTurned(); // retourne l'angle parcouru depuis que la rotation a commencé
			}
		}
		
		
		d.getPilot().rotate(angleDeBalayage+closestAngle);
		d.modifierPosition( Math.round(angleDeBalayage+closestAngle));
		
		
		return lastDistance;
	
	}
	
	private void calibratePosition (){
		float closestAngle = -1.0f;
		d.getPilot().setAngularSpeed(45);
		d.getPilot().rotate(45 / 2);
		d.getPilot().rotate(-45,true);
		float lastDistance = 3.0f;
		
		while(d.getPilot().isMoving()) {
			Delay.msDelay(40); // 50 mesures par sec
		    us.setCurrentDistance(us.getDistance());
			if (us.getCurrentDistance() < lastDistance) {
				lastDistance = us.getCurrentDistance();
				closestAngle = d.getPilot().getMovement().getAngleTurned(); // retourne l'angle parcouru depuis que la rotation a commencé
			}
		}
		
		
		d.getPilot().rotate(45+closestAngle);
		d.setPosition(0);
		
		
		;
	
	}
	
	public void run() {
		while(true) {
			if(db.getCmd() == DB.SEARCHCMD) {
				float res = rechercherPaletPlusProche(25);
				System.out.println(res);
				db.setDistanceToPalet(res);
				db.setCmd(DB.GOTOPALETCMD);
			}
			if(db.getCmd()== DB.GOTOPALETCMD) {
				us.setCurrentDistance(us.getDistance());
				if(us.detectPalet()) {
					db.setPaletDetected(true);
					//d.stop();
				}
				
				us.setLastDistance(us.getCurrentDistance()); 
				Delay.msDelay(30);
				
			}
			
			if(db.getCmd()==DB.GOTOBUTCMD) {
				d.avancer(0.4);
				while(d.getPilot().isMoving()) {
					
				}
				db.setCmd(DB.BUTCMD);
				//d.stop();
				
			}
			/*
			 * if(db.getCmd() == DB.RESETPOSITIONCMD) { calibratePosition();
			 * while(d.getPosition() !=0) {
			 * 
			 * } db.setCmd(DB.POINTCMD); }
			 */
		}
		
	}

}
