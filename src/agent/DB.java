package agent;

public class DB {
	private boolean paletDetected = false;
	private float distanceToPalet = 0f;
	
	private float distanceMAX = 0.45f;
	
	
	public static int POINTCMD = 0;
	public static int SEARCHCMD =1;
	public static int GOTOPALETCMD = 2;
	public static int AFTEROPENPINCECMD = 7;
	public static int SAISIECMD = 3;
	public static int DIRECTIONBUTCMD = 4;
	public static int GOTOBUTCMD = 5;
	public static int BUTCMD = 6;
	public static int RESETPOSITIONCMD = 8;
	public static int CALIBRATECMD = 9;
	
	
	public static int FIRSTPOINTCMD = 11;
	public static int FIRSTSAISIECMD = 12;
	public static int FIRSTDIRECTIONCMD = 13;
	
	
	private int cmd;

	public DB() {
		cmd = FIRSTPOINTCMD;
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

	/**
	 * @return the distanceMAX
	 */
	public float getDistanceMAX() {
		return distanceMAX;
	}

	/**
	 * @param distanceMAX the distanceMAX to set
	 */
	public void setDistanceMAX(float distanceMAX) {
		this.distanceMAX = distanceMAX;
	}

}
