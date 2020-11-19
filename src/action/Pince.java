package action;


import agent.DB;
import lejos.hardware.motor.Motor;

public class Pince extends Thread {
	
	//quand on démarre la pince est fermée et le robot n'a pas de palet
	
	//ATTRIBUTS
	
	private boolean etat; // true=ouvert VS false=fermé
	private boolean aPalet; // true=le robot tient le palet VS false
	
	private DB db;
	//CONSTRUCTEUR
	public Pince(DB db) {
		this.db = db;
		etat = false;
		aPalet = false;
		
	}
	
	public void run() {
		while(true) {
			if(db.getCmd() == DB.SEARCHCMD) {
				if(!etat) {
					this.deserrer();
				}
				if(db.isPaletDetected()) {
					db.setCmd(DB.AFTEROPENPINCECMD);
				}
				
			}
			if(db.getCmd() == DB.GOTOPALETCMD) {
				if(db.isPaletDetected()) {
					db.setCmd(DB.AFTEROPENPINCECMD);
				}
			}
			if(db.getCmd()==DB.POINTCMD && etat) {
				this.serrer();
			}
			if(db.getCmd() ==DB.SAISIECMD && !aPalet) {
				this.saisiePalet();
				db.setCmd(DB.DIRECTIONBUTCMD);
			}
			if(db.getCmd() == DB.BUTCMD) {
				this.lachePalet();
				db.setCmd(DB.POINTCMD);
				//System.out.print(db.getCmd());
			}
		}
	}
	
	public boolean isEtat() {
		return etat;
	}

	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	public boolean isaPalet() {
		return aPalet;
	}

	public void setaPalet(boolean aPalet) {
		this.aPalet = aPalet;
	}

	
	
	//METHODES
	
	//les pinces du robot se referment sans qu'il n'y ait de palet à l'intérieur
	//l'attribut etat est donc false=fermé
	//(l'attribut apalet est false)
	public void serrer() {		
		Motor.A.rotate(-900);
		etat=false;	
	}
	
	//les pinces du robot s'ouvrent sans qu'il n'y ait de palet à l'intérieur
	//l'attribut etat est donc true=ouvert
	//(l'attribut apalet est false)
	public void deserrer() {		
		Motor.A.rotate(900);
		etat=true;
	}
	
	//les pinces du robot se referment partiellement pour emprisonner le palet
	//l'attribut apalet est donc true
	//l'attribut etat n'est ni true ni false car la pince est entre-ouverte
	public void saisiePalet() {
		if (etat==true) {
			Motor.A.rotate(-900);
			aPalet=true;	
		}
	}
	
	//les pinces du robot s'ouvrent pour lacher le palet
	//l'attribut apalet est donc false
	//l'attribut etat est donc false car la pince est fermée
	public void lachePalet() {
		if (aPalet==true) {
			Motor.A.rotate(900);
			aPalet=false;
			etat=true;
		}
	}
	
	//TESTS
	
	public static void main(String[] args) {
		Pince pince = new Pince(new DB());
		pince.deserrer();
		pince.saisiePalet();
		pince.lachePalet();
		pince.serrer();
	}
	
	


}
