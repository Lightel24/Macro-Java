package gui;

public class LogMessage {

	public static final int LOG = 0;
	public static final int WARNING = 1;
	public static final int ERREUR = 2;
	public static final int ERREUR_CRITIQUE = 3;
	
	private String message;
	private int duration;
	private int level;
	/**
	 * @param message
	 * @param duration
	 */
	public LogMessage(String message, int duration, int severite) {
		this.message = message;
		this.duration = duration;
		level = severite;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	public int getLevel() {
		return level;
	}
}
