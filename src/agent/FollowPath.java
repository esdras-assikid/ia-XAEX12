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
		db.setCmd(DB.SEARCHCMD);
		
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
				d.avancer();
				while(!db.isPaletDetected()) {
					
				}
				b=false;
			}
			if(db.getCmd()==DB.AFTEROPENPINCECMD) {
				d.avancer();
				while(db.getCmd()== DB.AFTEROPENPINCECMD) {
					
				}
				b=false;
			}
			if(db.getCmd()==DB.SAISIECMD) {
				d.stop();
				
			}
			if(db.getCmd() == DB.DIRECTIONBUTCMD) {
				System.out.println(d.getPosition());
				
				d.retourPositionInitial();
				while(d.getPilot().isMoving()) {
					
				}
				db.setCmd(DB.GOTOBUTCMD);
			}
			/**if(db.getCmd() == DB.GOTOBUTCMD) {
				d.avancer();
				Delay.msDelay(50);
			}**/
			if(db.getCmd()==DB.BUTCMD) {
				d.stop();
			}
		}
		
	}

}
