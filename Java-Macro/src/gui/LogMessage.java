package gui;

public class LogMessage {
	private String message;
	private int duration;
	
	/**
	 * @param message
	 * @param duration
	 */
	public LogMessage(String message, int duration) {
		this.message = message;
		this.duration = duration;
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
}
