package agent;

import action.Deplacement;
import lejos.utility.Delay;
/**
 * FollowPath est la classe qui contient les méthodes permettant au robot de suivre le circuit définit dans la classe Etat.
 * Elle utilise les classes Etat, Deplacement et DB.
 * @author Esdras ASSIKIDANA
 */
public class FollowPath extends Thread {
	/**
	 * Instance Etat pour accéder au circuit.
	 */
	private Etat e;
	/**
	 * Instance de Deplacement.
	 */
	private Deplacement d;
	/**
	 * Instance de DB pour modifier les commandes.
	 */
	private DB db;
	/**
	 * Entier correspondant au nombre de fois où le robot doit réessayer de toucher le palet, lorsqu'après avoir détecté le palet et s'être dirigé vers lui, il ne l'a pas touché.
	 */
	private int countSearch =0;

	/**
	 * Constructeur qui initialise les instances.
	 * @param e
	 * @param d
	 * @param db
	 */
	public FollowPath(Etat e, Deplacement d, DB db) {
		this.e = e;
		this.d =d;
		this.db = db;	
	}

	/**
	 * Dirige le robot vers le point suivant du circuit.
	 */
	private void allerAuPointSvt() {
		System.out.println("POINT ");
		d.turnLeft(e.getAngleFromPointMarquage());
		while(d.getPilot().isMoving()) {
		}
		if(e.getDistanceToPoint() !=0) {
			d.avancer(e.getDistanceToPoint());
			while(d.getPilot().isMoving()) {
			}
		}
		db.setCmd(DB.SEARCHCMD);
	}

	/**
	 * Repositionne le robot en reculant dans le cas où il ne touche pas le palet.
	 * Après avoir reculé, il démarre une nouvelle recherche et retente sa chance.
	 */
	private void seRepositonnerIfPaletNotTouched() {
		d.reculer(0.5);
		db.setPaletDetected(false);
		while(d.getPilot().isMoving());
		d.gotoPosition(e.getAngleFromPointMarquage());
		db.setCmd(DB.SEARCHCMD);
	}

	/**
	 * Fait avancer le robot au point suivant dans le cas où il ne trouve pas de palet au point actuel.
	 */
	public void seRepositionnerIfNotFound() {
		if(e.getIdPointActuel() == 1) {
			d.gotoPosition(160);
			while(d.getPilot().isMoving());
			e.pointNotFound.add(e.getIdPointActuel());
			e.setIdPointActuel(2);
			e.setPointDistanceMAX(0.75f);
			d.avancer(0.7);
			while(d.getPilot().isMoving()) {
			}
			db.setDistanceMAX(0.70f);
			db.setCmd(DB.SEARCHCMD);
		}
		else if(e.getIdPointActuel() == 2) {
			d.gotoPosition(70);
			while(d.getPilot().isMoving());
			e.pointNotFound.add(e.getIdPointActuel());
			e.setIdPointActuel(3);
			e.setPointDistanceMAX(0.6f);
			d.avancer(0.1);
			while(d.getPilot().isMoving());
			db.setDistanceMAX(0.70f);
			db.setCmd(DB.SEARCHCMD);
		}
		else if(e.getIdPointActuel() ==3) {
			d.gotoPosition(360-160);
			while(d.getPilot().isMoving());
			e.pointNotFound.add(e.getIdPointActuel());
			e.setIdPointActuel(4);
			e.setPointDistanceMAX(0.75f);
			d.avancer(0.2);
			while(d.getPilot().isMoving());
			db.setDistanceMAX(0.70f);
			db.setCmd(DB.SEARCHCMD);
		}
		else if(e.getIdPointActuel() ==4) {
			if(e.pointNotFound.contains(3)) {
				d.gotoPosition(360-70);
				while(d.getPilot().isMoving());
				e.pointNotFound.add(e.getIdPointActuel());
				e.setIdPointActuel(5);
				e.setPointDistanceMAX(0.6f);
				d.avancer(0.1);
				while(d.getPilot().isMoving());
				db.setDistanceMAX(0.70f);
				db.setCmd(DB.SEARCHCMD);

			} 
			else {
				d.gotoPosition(150);
				while(d.getPilot().isMoving());
				e.pointNotFound.add(e.getIdPointActuel());
				e.setIdPointActuel(6);
				e.setPointDistanceMAX(0.6f);
				d.avancer(0.2);
				while(d.getPilot().isMoving());
				db.setDistanceMAX(0.70f);
				db.setCmd(DB.SEARCHCMD);
			}
		}
	}

	/**
	 * Méthode principale contenant toutes les instructions pour suivre le circuit.
	 * Une fois lancé, le robot suit le circuit en respectant toutes les conditions décrites.
	 */
	public void run() {
		boolean b =true;
		while(true) {
			if(db.getCmd()== DB.FIRSTPOINTCMD) {
				d.avancer();
				while(db.getCmd() == DB.FIRSTPOINTCMD);
				d.avancer(0.05);
			}
			if(db.getCmd()== DB.FIRSTDIRECTIONCMD) {
				d.turnRight(40);
				while(d.getPilot().isMoving());
				d.avancer(0.36);
				while(d.getPilot().isMoving());
				d.turnLeft(40);
				while(d.getPilot().isMoving());
				d.avancer();
				while(db.getCmd()==DB.FIRSTDIRECTIONCMD);
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
				} else {
					d.avancer(db.getDistanceToPalet()+0.07);
					while(d.getPilot().isMoving());
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
				d.avancer(0.10);
				System.out.println("OPEN PINCE");
				while(d.getPilot().isMoving());
				if(db.getCmd() == DB.AFTEROPENPINCECMD) {
					System.out.println("OPEN PALET NOT TOUCHED");
					db.setCmd(DB.PALETNOTTOUCHEDCMD);
				}
			}
			if(db.getCmd() == DB.PALETNOTTOUCHEDCMD) {
				if(countSearch ==2) {
					db.setCmd(DB.SAISIECMD);
					countSearch =0;
				} else {
					d.stop();
					b = true;
					this.seRepositonnerIfPaletNotTouched();
					countSearch +=1;
				}
			}
			if(db.getCmd() == DB.DIRECTIONBUTCMD) {
				db.setPaletDetected(false);
				if(e.getIdPointActuel() ==0) {
				} else {
					d.retourPositionInitial();
					if(e.getIdPointActuel() == 4) {
						if(!e.pointNotFound.contains(3)) {
							e.setIdPointActuel(6);
							e.getCircuit()[6] =new Point(155, 0.75, 0.8f);
						} else {
							e.setIdPointActuel(e.getIdPointActuel()+1);
						}
					} else {
						e.setIdPointActuel(e.getIdPointActuel()+1);
					}
					while(d.getPilot().isMoving());
					db.setCmd(DB.GOTOBUTCMD);
				}
			}
		}
	}
}
