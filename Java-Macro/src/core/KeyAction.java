package core;

public class KeyAction extends Action {
	
	public static final int KEY_PRESSED = 0;
	public static final int KEY_RELEASED = 1;
	
	private int type;
	private int key;
	
	public KeyAction(long timestamp,int type,int key) {
		this.key = key;
		this.timestamp = timestamp;
		this.type = type;
	}
	
	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}	
}
