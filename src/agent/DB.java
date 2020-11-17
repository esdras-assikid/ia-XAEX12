package agent;

public class DB {
	private boolean paletDetected = false;
	private float distanceToPalet = 0f;
	
	public static int POINTCMD = 0;
	public static int SEARCHCMD =1;
	public static int GOTOPALETCMD = 2;
	public static int AFTEROPENPINCECMD = 7;
	public static int SAISIECMD = 3;
	public static int DIRECTIONBUTCMD = 4;
	public static int GOTOBUTCMD = 5;
	public static int BUTCMD = 6;
	
	private int cmd=0;

	public DB() {
		// TODO Auto-generated constructor stub
	}

	public boolean isPaletDetected() {
		return paletDetected;
	}

	public void setPaletDetected(boolean paletDetected) {
		this.paletDetected = paletDetected;
	}

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public float getDistanceToPalet() {
		return distanceToPalet;
	}

	public void setDistanceToPalet(float distanceToPalet) {
		this.distanceToPalet = distanceToPalet;
	}

}
