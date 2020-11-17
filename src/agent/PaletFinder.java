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
		d.getPilot().rotate(-angleDeBalayage, true);
		
		while(d.getPilot().isMoving()) {
			Delay.msDelay(50); // 50 mesures par sec
		    us.setCurrentDistance(us.getDistance());
			if (us.getCurrentDistance() < us.getLastDistance() && us.getCurrentDistance() > 0.310) {
				us.setLastDistance(us.getCurrentDistance());
				closestAngle = d.getPilot().getMovement().getAngleTurned(); // retourne l'angle parcouru depuis que la rotation a commencé
			}
		}
		
		
		d.getPilot().rotate(angleDeBalayage+closestAngle);
			
		return us.getLastDistance();
	
	}
	
	public void run() {
		while(true) {
			if(db.getCmd() == DB.SEARCHCMD) {
				float res = rechercherPaletPlusProche(20);
				db.setDistanceToPalet(res);
				db.setCmd(DB.GOTOPALETCMD);
			}
			if(db.getCmd()== DB.GOTOPALETCMD) {
				us.setCurrentDistance(us.getDistance());
				if(us.detectPalet())
					db.setPaletDetected(true);
				us.setLastDistance(us.getCurrentDistance()); 
				Delay.msDelay(50);
				
			}
			if(db.getCmd()==DB.SAISIECMD) {
				d.stop();
				
			}
		}
		
	}

}
