package action;
import agent.DB;
import lejos.hardware.motor.Motor;

/**
 * Pince est la classe qui permet de gérer les mouvements de la pince du robot.
 * @author Alizée GUYOT
 */
public class Pince extends Thread {

	/**
	 * Attribut booléen décrivant l'état des pinces.
	 * S'il vaut true, les pinces sont ouvertes.
	 * S'il vaut false, les pinces sont fermées.
	 */
	private boolean etat; 
	/**
	 * Attribut booléen précisant si le robot détient un palet.
	 * S'il vaut true, les pinces serrent un palet.
	 * S'il vaut false, les pinces ne serrent pas de palet.
	 */
	private boolean aPalet; 
	/**
	 * Instance de la classe DB pour modifier les commandes.
	 */
	private DB db;

	/**
	 * Constructeur qui instancie les attributs.
	 * Au lancement du programme, le robot a les pinces fermées et ne détient pas de palet.
	 * @param db DB type
	 */
	public Pince(DB db) {
		this.db = db;
		etat = false;
		aPalet = false;
	}

	/**
	 * @return {@link Pince#etat}
	 */
	public boolean isEtat() {
		return etat;
	}

	/**
	 * Modifie l'attribut etat.
	 * @param etat boolean type
	 */
	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	/**
	 * @return {@link Pince#aPalet}
	 */
	public boolean isaPalet() {
		return aPalet;
	}

	/**
	 * Modifie l'attribut aPalet.
	 * @param aPalet boolean type
	 */
	public void setaPalet(boolean aPalet) {
		this.aPalet = aPalet;
	}

	/**
	 * Ferme les pinces et met à jour l'attribut etat à false.
	 */
	public void serrer() {		
		Motor.A.rotate(-900);
		etat=false;	
	}

	/**
	 * Ouvre les pinces et met à jour l'attribut etat à true.
	 */
	public void deserrer() {		
		Motor.A.rotate(900);
		etat=true;
	}

	/**
	 * Saisit un palet si les pinces sont ouvertes et met à jour l'attribut etat à true.
	 */
	public void saisiePalet() {
		if (etat==true) {
			Motor.A.rotate(-900);
			aPalet=true;	
		}
	}

	/**
	 * Lache le palet si le robot détient en détient un et met à jour aPalet.
	 */
	public void lachePalet() {
		if (aPalet==true) {
			Motor.A.rotate(900);
			aPalet=false;
			etat=true;
		}
	}

	/**
	 * Méthode principale contenant toutes les actions à réaliser avec les pinces en respectant toutes les conditions décrites.
	 */
	public void run() {
		while(true) {
			if(db.getCmd() == DB.FIRSTPOINTCMD) 
				if(!etat) 
					this.deserrer();
			if(db.getCmd() ==DB.FIRSTSAISIECMD) {
				this.saisiePalet();
				db.setCmd(DB.FIRSTDIRECTIONCMD);
			}
			if(db.getCmd() == DB.SEARCHCMD) 
				if(!etat) 
					this.deserrer();
			if(db.getCmd() == DB.GOTOPALETCMD);
			if(db.getCmd()==DB.POINTCMD && etat) 
				this.serrer();
			if(db.getCmd() ==DB.SAISIECMD && !aPalet) {
				this.saisiePalet();
				db.setCmd(DB.DIRECTIONBUTCMD);
			} else if(db.getCmd() == DB.SAISIECMD && aPalet){
				db.setCmd(DB.DIRECTIONBUTCMD);
			}
			if(db.getCmd() == DB.BUTCMD) {
				this.lachePalet();
				while(Motor.A.isMoving()) {
				}
				db.setCmd(DB.CALIBRATECMD);
			}
		}
	}
}
