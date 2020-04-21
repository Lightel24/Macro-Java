package core;

import java.awt.Point;

public class MouseAction extends Action {
	
	public static final String LEFT_MOUSE_PRESSED = "LEFT_MOUSE_PRESSED";
	public static final String LEFT_MOUSE_RELEASED = "LEFT_MOUSE_RELEASED";
	public static final String RIGHT_MOUSE_PRESSED = "RIGHT_MOUSE_PRESSED";
	public static final String RIGHT_MOUSE_RELEASED = "RIGHT_MOUSE_RELEASED";
	public static final String MOUSE_MOVED = "MOUSE_MOVED";

	private Point loc;
	
	public MouseAction(long timestamp,String type,Point loc) {
		this.timestamp = timestamp;
		this.type=type;
		this.loc=loc;
	}	
	
	/**
	 * @return the loc
	 */
	public Point getLocation() {
		return loc;
	}

	/**
	 * @param loc the loc to set
	 */
	public void setLocation(Point loc) {
		this.loc = loc;
	}
	
	/*
	 * @return a string representation of this object
	 */
	@Override
	protected String getInfoAsString() {
		return "\"loc=\"\""+loc+"\"";
	}
}
