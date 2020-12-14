package agent;

/**
 * DB est la classe qui sert à décrire les différents états dans lesquels le robot se trouve (DB = Data Base).
 * @author Esdras ASSIKIDANA
 */
public class DB {
	/**
	 * Booléen qui renseigne si le robot a détecté un palet dans son état actuel.
	 */
	private boolean paletDetected = false;
	/**
	 * Distance séparant le robot du palet repéré lors de la recherche.
	 */
	private float distanceToPalet = 0f;
	/**
	 * Distance maximale à laquelle le palet est censé se trouver lors de la recherche.
	 */
	private float distanceMAX = 0.45f;
	/**
	 * Dans cet état, le robot se dirige vers le point suivant.
	 */
	public static int POINTCMD = 0;
	/**
	 * Dans cet état, le robot démarre une recherche (= balayage) du palet.
	 */
	public static int SEARCHCMD = 1;
	/**
	 *  Dans cet état, le robot, après avoir effectué la recherche, ouvre les pinces et se dirige vers le palet.
	 */
	public static int GOTOPALETCMD = 2;
	/**
	 * Dans cet état, le robot enclenche la saisie du palet.
	 */
	public static int SAISIECMD = 3;
	/**
	 * Dans cet état, après avoir saisi le palet, le robot se remet en direction de la ligne d'en-but adverse.
	 */
	public static int DIRECTIONBUTCMD = 4;
	/**
	 * Dans cet état, après s'être mis en direction du but adverse, le robot se dirige vers le but adverse et ne s'arrête pas tant qu'il n'a pas détecté la ligne blanche.
	 */
	public static int GOTOBUTCMD = 5;
	/**
	 * Dans cet état, après avoir atteint la ligne d'en-but adverse, le robot lache le palet.
	 */
	public static int BUTCMD = 6;
	/**
	 * Dans cet état, le robot a les pinces ouvertes, s'est dirigé vers le palet pour le récupérer mais ne l'a pas touché.
	 * Il avance donc encore un peu afin de le saisir, au cas où il y aurait une marge d'erreur.
	 */
	public static int AFTEROPENPINCECMD = 7;
	/**
	 * Dans cet état, après avoir déposé le palet, le robot calibre sa position en s'aidant du mur se trouvant en face.
	 */
	public static int CALIBRATECMD = 9;
	/**
	 * Dans cet état, après s'être dirigé vers le palet détecté et avoir parcouru la distance correspondante à sa position sans l'avoir touché,
	 * le robot recule, refait une recherche et retente sa chance.
	 */
	public static int PALETNOTTOUCHEDCMD = 10;
	/**
	 * Cet état correspond au démarrage du robot (en début de partie ou après un temps mort), il se dirige vers le premier point se situant face à lui.
	 */ 
	public static int FIRSTPOINTCMD = 11;
	/**
	 * Dans cet état, le robot saisit le premier palet.
	 */
	public static int FIRSTSAISIECMD = 12;
	/**
	 * Dans cet état, après avoir saisi le premier palet, le robot se décale de la ligne sur laquelle il se trouve pour éviter d'éventuels palets en chemin
	 * et se remet en direction de la ligne adverse pour aller marquer le point.
	 */
	public static int FIRSTDIRECTIONCMD = 13;
	/**
	 * Valeur de la commande correspondant à un des états décrits ci-dessus.
	 */
	private int cmd;

	/**
	 * Constructeur qui initialise la commande au premier point à récupérer (en début de partie ou après un temps mort).
	 */
	public DB() {
		cmd = FIRSTPOINTCMD;
	}

	/**
	 * Renvoie true si le robot est dans l'état où il a détecté un palet.
	 * @return {@link DB#isPaletDetected()}
	 */
	public boolean isPaletDetected() {
		return paletDetected;
	}

	/**
	 * Modifie l'état de l'attribut {@link DB#paletDetected}
	 * @param paletDetected boolean type
	 */
	public void setPaletDetected(boolean paletDetected) {
		this.paletDetected = paletDetected;
	}

	/**
	 * Retourne l'entier correspondant à la commande actuelle.
	 * @return {@link DB#cmd}
	 */
	public int getCmd() {
		return cmd;
	}

	/**
	 * Modifie la commande actuelle.
	 * @param cmd int type
	 */
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	/**
	 * Retourne la distance séparant le robot du palet repéré lors de la recherche.
	 * @return {@link DB#getDistanceToPalet()}
	 */
	public float getDistanceToPalet() {
		return distanceToPalet;
	}

	/**
	 * Modifie la distance séparant le robot du palet repéré lors de la recherche.
	 * @param distanceToPalet float type
	 */
	public void setDistanceToPalet(float distanceToPalet) {
		this.distanceToPalet = distanceToPalet;
	}

	/**
	 * Retourne la distance maximale à laquelle le palet est censé se trouver lors de la recherche.
	 * @return {@link DB#distanceMAX}
	 */ 
	public float getDistanceMAX() {
		return distanceMAX;
	}

	/**
	 * Modifie la distance maximale à laquelle le palet est censé se trouver lors de la recherche.
	 * @param distanceMAX float type
	 */
	public void setDistanceMAX(float distanceMAX) {
		this.distanceMAX = distanceMAX;
	}
}