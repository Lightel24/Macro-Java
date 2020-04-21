package core;

public class KeyAction extends Action {
	
	public static final String KEY_PRESSED = "KEY_PRESSED";
	public static final String KEY_RELEASED = "KEY_RELEASED";
	
	private int key;
	
	public KeyAction(long timestamp,String type,int key) {
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

	@Override
	protected String getInfoAsString() {
		return "\"key\"=\""+key+"\"";
	}	
}
