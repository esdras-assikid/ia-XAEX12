package agent;

import action.Deplacement;
import lejos.utility.Delay;

public class FollowPath extends Thread {
	private Etat e;
	private Deplacement d;
	private DB db;
	private int countSearch =0;
	public FollowPath(Etat e, Deplacement d, DB db) {
		// TODO Auto-generated constructor stub
		this.e = e;
		this.d =d;
		this.db = db;
		
		
		
	}
	private void allerAuPointSvt() {
		System.out.println("POINT ");
		//d.turnRight(e.getAngleFromPointMarquage());
		d.turnLeft(e.getAngleFromPointMarquage());
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
	private void seRepositonnerIfPaletNotTouched() {
		d.reculer(0.5);
		db.setPaletDetected(false);
		while(d.getPilot().isMoving()) {
			
		}
		d.gotoPosition(e.getAngleFromPointMarquage());
		db.setCmd(DB.SEARCHCMD);
	}
	
	public void seRepositionnerIfNotFound() {
		if(e.getIdPointActuel() == 1) {
			//d.gotoPosition(170);
			d.gotoPosition(-170);
			while(d.getPilot().isMoving()) {

			}
			db.setDistanceMAX(0.70f);
			db.setCmd(DB.SEARCHCMD);
		}
		
		if(e.getIdPointActuel() ==3) {
			//d.gotoPosition(160);
			d.gotoPosition(-160);
			while(d.getPilot().isMoving()) {

			}
			e.pointNotFound.add(e.getIdPointActuel());

			e.setIdPointActuel(4);
			e.setPointDistanceMAX(0.75f);
			d.avancer(0.62);
			while(d.getPilot().isMoving()) {

			}
			db.setDistanceMAX(0.70f);
			db.setCmd(DB.SEARCHCMD);
		}
		
		

	}
	
	
	@Override
	public void run() {
		boolean b =true;
		while(true) {
			if(db.getCmd()== DB.FIRSTPOINTCMD) {
				d.avancer();
				while(db.getCmd() == DB.FIRSTPOINTCMD) {
					
				}
				d.avancer(0.05);
			}
			if(db.getCmd()== DB.FIRSTDIRECTIONCMD) {
				//d.turnLeft(40);
				d.turnRight(40);
				while(d.getPilot().isMoving()) {
					
				}
				d.avancer(0.36);
				while(d.getPilot().isMoving()) {
					
				}
				//d.turnRight(40);
				d.turnLeft(40);
				while(d.getPilot().isMoving()) {
					
				}
				d.avancer();
				while(db.getCmd()==DB.FIRSTDIRECTIONCMD) {
					
				}
				d.stop();
				
			}
			if(db.getCmd() == DB.POINTCMD) {
				allerAuPointSvt();
				b=true;
			}
			if(db.getCmd()==DB.GOTOPALETCMD && b) {
				System.out.println(db.getDistanceToPalet());
				if(db.getDistanceToPalet() > e.getDistanceMAX()) {
					this.seRepositionnerIfNotFound();
				}else {
					//System.out.println(db.getDistanceToPalet());
					d.avancer(db.getDistanceToPalet()+0.07);
					while(d.getPilot().isMoving()) {

					}
					
					if(db.isPaletDetected()) {
						b=false;
						System.out.println("PALET DETECTED");
						if(db.getCmd() == DB.GOTOPALETCMD) {
							db.setCmd(DB.AFTEROPENPINCECMD);
						}
						
					} else {
						System.out.println("PALET NOT TOUCHED");
						db.setCmd(DB.PALETNOTTOUCHEDCMD);
					}
							
				}
				
				
			}
			if(db.getCmd()==DB.AFTEROPENPINCECMD) {
				d.avancer();
				System.out.println("OPEN PINCE");
				while(db.getCmd()== DB.AFTEROPENPINCECMD) {
					Delay.msDelay(100); 
					if(db.getCmd() == DB.AFTEROPENPINCECMD) {
						System.out.println("OPEN PALET NOT TOUCHED");
						 db.setCmd(DB.PALETNOTTOUCHEDCMD);
					}
					
					/*
					 * 
					 * b=true;
					 * 
					 * 
					 * }
					 */
				}
				
			}
			if(db.getCmd() == DB.PALETNOTTOUCHEDCMD) {
				if(countSearch ==2) {
					db.setCmd(DB.SAISIECMD);
					countSearch =0;
				}else {
					d.stop();
					b = true;
					this.seRepositonnerIfPaletNotTouched();
					countSearch +=1;
				}
				
			}
			
			if(db.getCmd() == DB.DIRECTIONBUTCMD) {
				//System.out.println(d.getPosition());
				db.setPaletDetected(false);
				if(e.getIdPointActuel() ==0) {
					
				}else {
					d.retourPositionInitial();
					if(e.getIdPointActuel() == 4) {
						if(!e.pointNotFound.contains(3)) {
							e.setIdPointActuel(6);
							e.getCircuit()[6] =new Point(155, 0.75, 0.8f, Etat.RIGHT);
						}else {
							e.setIdPointActuel(e.getIdPointActuel()+1);
						}
					}else {
						e.setIdPointActuel(e.getIdPointActuel()+1);
					}
					
					while(d.getPilot().isMoving()) {
						
					}
					db.setCmd(DB.GOTOBUTCMD);
				}
				
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
