package main;


import gui.MacroGui;

public class Main {
	
	private MacroGui gui;

	public Main() {
		gui = new MacroGui();
		gui.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new Main();
		
	}
}
