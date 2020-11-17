package agent;

import action.Deplacement;
import lejos.utility.Delay;

public class FollowPath extends Thread {
	private Etat e;
	private Deplacement d;
	private DB db;
	public FollowPath(Etat e, Deplacement d, DB db) {
		// TODO Auto-generated constructor stub
		this.e = e;
		this.d =d;
		this.db = db;
		
		
	}
	private void allerAuPointSvt() {
		d.turnRight(e.getAngleFromPointMarquage());
		while(d.getPilot().isMoving()) {
			
		}
		d.avancer(e.getDistanceToPoint());
		db.setCmd(1);
		
	}
	private void marquerPoint() {
		d.turnRight(d.getPosition());
		while(d.getPilot().isMoving()) {
			
		}
		d.avancer();
	}
	
	@Override
	public void run() {
		boolean b =true;
		while(true) {
			if(db.getCmd() == DB.POINTCMD) {
				allerAuPointSvt();
				
			}
			if(db.getCmd()==DB.GOTOPALETCMD && b) {
				d.avancer(db.getDistanceToPalet());
				while(d.getPilot().isMoving()) {
					
				}
				b=false;
				
			}
			if(db.getCmd()==DB.SAISIECMD) {
				d.stop();
				
			}
			if(db.getCmd() == DB.DIRECTIONBUTCMD) {
				d.turnRight(e.getAngleFromPointMarquage());
				while(d.getPilot().isMoving()) {
					
				}
				db.setCmd(DB.GOTOBUTCMD);
			}
			if(db.getCmd() == DB.GOTOBUTCMD) {
				d.avancer();
				Delay.msDelay(50);
			}
			if(db.getCmd()==DB.BUTCMD) {
				d.stop();
			}
		}
		
	}

}
