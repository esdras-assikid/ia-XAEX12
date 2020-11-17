package agent;

import perception.ColorSensor;

public class But extends Thread{
	ColorSensor cs;
	DB db;

	public But(ColorSensor cs, DB db) {
		// TODO Auto-generated constructor stub
		this.cs = cs;
		this.db = db;
	}
	
	public void run() {
		while(true) {
			if(db.getCmd()==DB.GOTOBUTCMD) {
				if(cs.changeColor().equals(ColorSensor.WHITE)) {
					db.setCmd(DB.BUTCMD);
				}
			}
		}
	}

}