package action;


import agent.DB;
import lejos.hardware.motor.Motor;

// Cette classe décrit les pinces du robot et contient toutes les actions à réaliser avec ces pinces
// Elle utilise la classe DB

public class Pince extends Thread {
	
	// Décrit l'état actuel des pinces : ouvertes = true  vs  fermées = false
	private boolean etat; 
	
	// Précise si le robot tient un palet entre ses pinces ou non : tient le palet = true  vs  n'a pas de palet = false
	private boolean aPalet; 
	
	// Instance de DB pour modifier les commandes
	private DB db;
	
	
	// Initialise les attributs
	// Au début de la partie, les pinces sont fermées et le robot ne tient pas de palet
	public Pince(DB db) {
		this.db = db;
		etat = false;
		aPalet = false;
		
	}
	
	
	// Retourne l'état des pinces : fermées ou ouvertes
	public boolean isEtat() {
		return etat;
	}
	
	// Modifie l'état des pinces
	public void setEtat(boolean etat) {
		this.etat = etat;
	}
	
	// Retourne true si le robot détient un palet, false sinon
	public boolean isaPalet() {
		return aPalet;
	}
	
	// Modifie si le robot détient un palet ou non
	public void setaPalet(boolean aPalet) {
		this.aPalet = aPalet;
	}

	
	// Ferme les pinces et modifie leur état (fermées = false)
	public void serrer() {		
		Motor.A.rotate(-900);
		etat=false;	
	}
	
	// Desserre les pinces et modifie leur état (ouvertes = true)
	public void deserrer() {		
		Motor.A.rotate(900);
		etat=true;
	}
	
	// Saisie le palet si les pinces sont bien ouvertes et met à jour aPalet (détient un palet = true)
	public void saisiePalet() {
		if (etat==true) {
			Motor.A.rotate(-900);
			aPalet=true;	
		}
	}
	
	// Lache le palet si le robot détient un palet et met à jour aPalet (ne détient pas de palet = false) et l'état des pinces (ouvertes = true)
	public void lachePalet() {
		if (aPalet==true) {
			Motor.A.rotate(900);
			aPalet=false;
			etat=true;
		}
	}
	
	
	// Méthode principale contenant toutes les actions à réaliser avec les pinces en respectant toutes les conditions décrites
	public void run() {
		while(true) {
			if(db.getCmd() == DB.FIRSTPOINTCMD) {
				if(!etat) {
					this.deserrer();
				}
			}
			if(db.getCmd() ==DB.FIRSTSAISIECMD) {
				this.saisiePalet();
				db.setCmd(DB.FIRSTDIRECTIONCMD);
			}
			if(db.getCmd() == DB.SEARCHCMD) {
				if(!etat) {
					this.deserrer();
				}
				
				
			}
			if(db.getCmd() == DB.GOTOPALETCMD) {
				
			}
			if(db.getCmd()==DB.POINTCMD && etat) {
				this.serrer();
			}
			if(db.getCmd() ==DB.SAISIECMD && !aPalet) {
				this.saisiePalet();
				db.setCmd(DB.DIRECTIONBUTCMD);
			}else if(db.getCmd() == DB.SAISIECMD && aPalet){
				db.setCmd(DB.DIRECTIONBUTCMD);
			}
			if(db.getCmd() == DB.BUTCMD) {
				this.lachePalet();
				while(Motor.A.isMoving()) {
					
				}
				db.setCmd(DB.CALIBRATECMD);
				//System.out.print(db.getCmd());
			}
		}
	}
	
	
	


}
