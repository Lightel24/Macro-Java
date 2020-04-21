package main;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import core.Macro;
import core.Manager;
import core.RecordService;
import fr.lightel24.gitupdater.GitUpdater;
import fr.lightel24.gitupdater.UpdateCallback;
import fr.lightel24.gitupdater.UpdateEvent;
import fr.lightel24.gitupdater.VersionCheckException;
import gui.MacroGui;
import gui.UpdateGui;
import gui.UpdateGuiObserver;

public class Main implements UpdateGuiObserver{
	
	public final static String SEMVER = "1.0.0";
	
	private MacroGui gui;
	private UpdateGui update;
	private Manager manager;
	private boolean cancelFlag = false;

	public Main() {
		checkVersion();
	}
	
	
	private void checkVersion() {	//On peut se permettre de bloquer le main.
		update = new UpdateGui();
		update.setVisible(true);
		update.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {}
			@Override
			public void windowClosed(WindowEvent arg0) {
				manager = new Manager();
				gui = new MacroGui(manager);
				manager.loadFromFile(new File("data/data.xml"), true);
				gui.setVisible(true);
			}
			@Override
			public void windowClosing(WindowEvent arg0) {}
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			@Override
			public void windowIconified(WindowEvent arg0) {}
			@Override
			public void windowOpened(WindowEvent arg0) {}
			
		});
		
		GitUpdater up = new GitUpdater();
		up.setRepo("apache", "airflow");
		up.setVersionCheckMethod(GitUpdater.DIFFERENCE_CHECK_TAGNAME);
		
		try {
			if(up.isUpToDate(SEMVER)==GitUpdater.IS_NOT_UP_TO_DATE) {
				update.setEnTete("Mise à jour en cours...");
				update.changeSkip(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						update.dispose();
					}
				});
			}
		} catch (VersionCheckException e) {
			e.printStackTrace();
			update.dispose();
		}
	}


	public static void main(String[] args) {
		new Main();
	}


	@Override
	public void skip() {	//Attention peut bloquer non thread safe
		update.dispose();
		cancelFlag=true;
	}
}
