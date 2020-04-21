package core;

public abstract class Action implements Comparable<Action>{
	protected long timestamp;
	protected String type;

	protected abstract String getInfoAsString();

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	
	/*
	 * @return the comparison
	 */
	@Override
    public int compareTo(Action compare) {
       return (int) (compare.getTimestamp() - timestamp);
    }	
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/*
	 * @return a string representation of this object
	 */
	@Override
	public String toString() {
		return "\"type\"=\""+type+"\"	\"timestamp\"=\""+timestamp+"\"		" + getInfoAsString();
	}
}
