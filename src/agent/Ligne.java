package agent;

public class Ligne {
	private String couleur;
	private int nbrPointRestant;
	

	public Ligne(String color, int pointMax) {
		this.couleur = color;
		this.nbrPointRestant = pointMax;
		// TODO Auto-generated constructor stub
	}


	public String getCouleur() {
		return couleur;
	}


	public int getNbrPointRestant() {
		return nbrPointRestant;
	}


	public void setNbrPointRestant(int nbrPointRestant) {
		this.nbrPointRestant = nbrPointRestant;
	}

}
