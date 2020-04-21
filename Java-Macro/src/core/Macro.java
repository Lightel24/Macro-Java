package core;

import java.util.ArrayList;
import java.util.LinkedList;

public class Macro {
	String name;
	ArrayList<Action> liste = new ArrayList<Action>();
	
	public Macro() {
		
	}
	
	public Macro(ArrayList<Action> liste, String name) {
		this.liste = liste;
		this.name=name;
	}
		
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the liste
	 */
	public ArrayList<Action> getListe() {
		return liste;
	}

	public void executeMacro() {
		
	}
	
}
