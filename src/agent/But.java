package agent;

import lejos.utility.Delay;

// Cette classe ex�cute les actions � r�aliser lorsque le robot franchit la ligne blanche adverse
// Elle utilise les classes ColorSensor et DB
import perception.ColorSensor;

/**
 * But est la classe qui gère les points à marquer.
 * @author Esdras ASSIKIDANA
 */
public class But extends Thread{
	/**
	 * Instance du ColorSensor pour détecter la ligne blanche.
	 */
	ColorSensor cs;
	/**
	 * Instance DB pour passer la commande actuelle à BUTCMD.
	 */
	DB db;

	/**
	 * Constructeur qui initialise les instances ColorSensor et DB.
	 * @param cs ColorSensor type
	 * @param db DB type
	 */
	public But(ColorSensor cs, DB db) {
		this.cs = cs;
		this.db = db;
	}

	/**
	 * Méthode principale qui fait en sorte que le robot s'arrête et lâche le palet détenu entre les pinces, après avoir détecté une ligne blanche.
	 */
	public void run() {
		while(true) {
			if(db.getCmd() == DB.FIRSTDIRECTIONCMD) {
				while(!cs.changeColor().equals(ColorSensor.WHITE)) { 
					Delay.msDelay(5);
				}
				db.setCmd(DB.BUTCMD);
			}
			if(db.getCmd()==DB.GOTOBUTCMD) {
				while(!cs.changeColor().equals(ColorSensor.WHITE)) {
					Delay.msDelay(5);
				}
				db.setCmd(DB.BUTCMD);
			}	
		}
	}
}