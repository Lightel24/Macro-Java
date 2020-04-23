package core;

public class KeyAction extends Action {
	
	public static final String KEY_PRESSED = "KEY_PRESSED";
	public static final String KEY_RELEASED = "KEY_RELEASED";
	
	private int key;
	private int rawCode;
	
	public KeyAction(long timestamp,String type,int key, int rawCode) {
		this.key = key;
		this.timestamp = timestamp;
		this.type = type;
		this.rawCode = rawCode;
	}
	
	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}
	
	/**
	 * @return the rawCode
	 */
	public int getRawCode() {
		return rawCode;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(int key) {
		this.key = key;
	}

	@Override
	protected String getInfoAsString() {
		return "\"key\"=\""+key+"\"	\"rawCode\"=\""+rawCode+"\"";
	}	
}
