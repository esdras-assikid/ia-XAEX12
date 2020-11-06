package action;


import lejos.hardware.motor.Motor;

public class Pince {
	
	//quand on démarre la pince est fermée et le robot n'a pas de palet
	
	Deplacement d;
	
	//ATTRIBUTS
	
	private boolean etat; // true=ouvert VS false=fermé
	private boolean aPalet; // true=le robot tient le palet VS false
	
	//CONSTRUCTEUR

	public Pince() {
		d= new Deplacement();
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
			Motor.A.rotate(-900);  // si c'est trop, commencer par -600 et dimimuer jusqu'à ce que
			// le palet soit bien prisonnier des pinces mais pas écrasé
			aPalet=true;	
		}
	}
	
	//Le robot doit avancer jusqu'à toucher le palet, puis fermer les pinces
	public void saisirPalet() {
		deserrer();
		d.avancer();
		saisiePalet();
	}
	
	
	//les pinces du robot s'ouvrent pour lacher le palet
	//l'attribut apalet est donc false
	//l'attribut etat est donc false car la pince est fermée
	public void lachePalet() {
		if (aPalet==true) {
			Motor.A.rotate(900); // idem ici, mettre la valeur positive de celle choisie
			// dans saisiePalet()
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
