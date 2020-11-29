package agent;

import java.io.FileNotFoundException;

import action.*;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import perception.*;

public class AgentGauche {
	// Attributs
	
	// Instance de l'etat de l'environnement

	// Instance de l’état de l’environnement

		Etat e; 
	
	// Instance du capteur d'ultrasons
		UltrasonicSensor us; 
	// Instance du capteur de couleurs
		ColorSensor cs; 
	// Instance du capteur de toucher
		TouchSensor ts; 
	// Instance pour la pince
		Pince p; 
		
	//Instance de deplacement
		Deplacement d;
		DB db;
		FollowPath fp;
		But b;
		PaletFinder pf;
		// Constructeur
		public AgentGauche() throws FileNotFoundException {
			cs = new ColorSensor();
			d = new Deplacement();
			e = new Etat(ColorSensor.BLUE);
			us = new UltrasonicSensor();
			db = new DB();
			p = new Pince(db);
			fp = new FollowPath(e, d, db);
			b= new But(cs, db);
			pf = new PaletFinder(us, d, db);
			ts = new TouchSensor(db);
			
		}
		
	
	    
	
		
	public static void main(String[] args) {
		
		

		try {
			AgentGauche a = new AgentGauche();
			while(!Button.ENTER.isDown()) {
				
			}
			
			//a.marquerPremierPoint();
			a.fp.start();
			a.b.start();
			a.p.start();
			a.pf.start();
			a.ts.start();
			
			while(!Button.ESCAPE.isDown()) {
				
			}
			System.exit(0);
			/**boolean t= true;
			while(t) {
				a.testEtaT();
				t=false;
				if(Button.ESCAPE.isDown()) {
					//UltrasonicSensor.us.close();
					a.cs.colorSensor.close();
					a.d.stop();
					
					
				}
			}**/
			
			if(Button.ESCAPE.isDown()) {
				//UltrasonicSensor.us.close();
				a.cs.colorSensor.close();
				a.d.stop();
				
				System.exit(0);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
