package action;


import lejos.hardware.motor.Motor;

public class Pince {
	
	//quand on démarre la pince est fermée et le robot n'a pas de palet
	
	//ATTRIBUTS
	
	private boolean etat; // true=ouvert VS false=fermé
	private boolean aPalet; // true=le robot tient le palet VS false
	
	//CONSTRUCTEUR
	
	public Pince() {		
	}
	
	//METHODES
	
	//les pinces du robot se referment sans qu'il n'y ait de palet à l'intérieur
	//l'attribut etat est donc false=fermé
	//(l'attribut apalet est false)
	public void serrer() {		
		Motor.A.rotate(-950);
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
		Pince pince = new Pince();
		pince.deserrer();
		pince.saisiePalet();
		pince.lachePalet();
		pince.serrer();
	}
	
	


}
