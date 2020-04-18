package core;

import java.awt.Point;

public class MouseAction extends Action {
	
	public static final int LEFT_MOUSE_PRESSED = 0;
	public static final int LEFT_MOUSE_RELEASED = 1;
	public static final int RIGHT_MOUSE_PRESSED = 3;
	public static final int RIGHT_MOUSE_RELEASED = 4;
	
	private int type;
	private Point loc;
	
	public MouseAction(long timestamp,int type,Point loc) {
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
