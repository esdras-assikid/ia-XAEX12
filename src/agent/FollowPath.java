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
		System.out.println("POINT ");
		d.turnRight(e.getAngleFromPointMarquage());
		while(d.getPilot().isMoving()) {
			//
		}
		if(e.getDistanceToPoint() !=0) {
			d.avancer(e.getDistanceToPoint());
			while(d.getPilot().isMoving()) {
				//
			}
		}
		
		db.setCmd(DB.SEARCHCMD);
		
	}
	private void marquerPoint() {
		d.turnRight(d.getPosition());
		while(d.getPilot().isMoving()) {
			
		}
		d.avancer();
	}
	public void seRepositionnerIfNotFound() {
		if(e.getIdPointActuel() == 0) {
			d.turnLeft(45);
			while(d.getPilot().isMoving()) {
				
			}
			d.avancer(0.1);
			while(d.getPilot().isMoving()) {
				
			}
			e.pointNotFound.add(e.getCurrentPoint());
			e.setIdPointActuel(1);
			db.setDistanceMAX(0.70f);
			db.setCmd(DB.SEARCHCMD);
			
		}
		if(e.getIdPointActuel() == 1) {
			e.pointNotFound.add(e.getCurrentPoint());
			d.avancer(0.4);
			while(d.getPilot().isMoving()) {
				
			}
			db.setCmd(DB.SEARCHCMD);
			e.setIdPointActuel(2);

			
		}
		if(e.getIdPointActuel() ==2) {
			e.pointNotFound.add(e.getCurrentPoint());
			d.gotoPosition180();
			while(d.getPilot().isMoving()) {
				
			}
			d.avancer(0.4);
			while(d.getPilot().isMoving()) {
				
			}
			e.setIdPointActuel(3);
			d.turnLeft(20);
			db.setCmd(DB.SEARCHCMD);
			
		}
		
	}
	
	@Override
	public void run() {
		boolean b =true;
		while(true) {
			if(db.getCmd() == DB.POINTCMD) {
				allerAuPointSvt();
				b=true;
			}
			if(db.getCmd()==DB.GOTOPALETCMD && b) {
				System.out.print(db.getDistanceToPalet());
				if(db.getDistanceToPalet() > e.getDistanceMAX()) {
					//this.seRepositionnerIfNotFound();
				}else {
					//System.out.println(db.getDistanceToPalet());
					d.avancer(db.getDistanceToPalet()+0.05);
					while(d.getPilot().isMoving()) {
						
					}
					if(db.isPaletDetected())
					b=false;
				}
				
				
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
				e.setIdPointActuel(e.getIdPointActuel()+1);
				while(d.getPilot().isMoving()) {
					
				}
				db.setCmd(DB.GOTOBUTCMD);
			}
			/**if(db.getCmd() == DB.GOTOBUTCMD) {
				d.avancer();
				Delay.msDelay(50);
				if(db.getCmd()==DB.BUTCMD) {
				d.stop();
				
				
			}
			}**/
			
		}
		
	}

}
