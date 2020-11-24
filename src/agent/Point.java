package agent;

public class Point {
	//angle en degre pour se mettre dans la bonne position depuis la ligne de but
	private double angle;
	
	
	
	private int orientation;
	
	//la distance minimale à parcourir avant de faire la recherche de palet
	private double distance;
	
	private float distanceMax = 0.49f;
	
	

	public Point(double angle, double distance, float distanceMax, int orientation) {
		// TODO Auto-generated constructor stub
		this.angle = angle;
		this.distance = distance;
		this.orientation = orientation;
		this.distanceMax = distanceMax;
	}
	

	/**
	 * @return the distanceMax
	 */
	public float getDistanceMax() {
		return distanceMax;
	}


	/**
	 * @param distanceMax the distanceMax to set
	 */
	public void setDistanceMax(float distanceMax) {
		this.distanceMax = distanceMax;
	}


	/**
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}


	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}


	public int getOrientation() {
		return orientation;
	}
	
	
	
	

}
