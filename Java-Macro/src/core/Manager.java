package core;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import gui.LogMessage;

public class Manager implements RecordServiceObserver{
	
	private static final String DATA_FILE = "data.xml";
	
	private HashMap<String,Macro> macroMap = new HashMap<String,Macro>();
	private FileService fileService;
	private RecordService recordService;
	private RobotService robotService;
	private ManagerObserver observer;
	private Thread IOThread;
	private Thread PlayingThread;
	private String macroName;
	
	public Manager(){
		fileService = new FileService();
		recordService = new RecordService();
		recordService.setObserver(this);
		robotService = new RobotService();
		macroName = "";
		removeObserver(); //Eviter un nullpointer
	}
	

	public void setMouseFrequency(int value) {
		recordService.setMouseFrequency(value);
	} 
	
	public void startRecording(String macroName) {
		try {
			observer.log(new LogMessage("D\u00e9but de l'enregistrement. (Echap pour arreter)",-1, LogMessage.LOG));
			recordService.startRecording();
			this.macroName = macroName;
		} catch (ObserverNotSetException e) {
			observer.log(new LogMessage("L'op\u00e9ration a \u00e9chou\u00e9 veuillez r\u00e9essayer.",5, LogMessage.ERREUR));
			recordService.setObserver(this);
			e.printStackTrace();
		} catch (ServiceNotReadyException e) {
			observer.log(new LogMessage(e.getMessage(),5, LogMessage.ERREUR));
		}
	}
	
	public void stopRecording() {
		recordService.stopRecording();
		macroName = "";
	}
	
	public void startPlaying(String macroName) {
		Macro macro = macroMap.get(macroName);
		if(macro!=null) {
			if(PlayingThread == null || !PlayingThread.isAlive()) {
				PlayingThread = new Thread("Playing-Thread") {
					@Override
					public void run() {
						try {
							observer.log(new LogMessage("Replay de la macro : "+macroName,-1, LogMessage.LOG));
							robotService.exec(macro);
							observer.log(new LogMessage("Opération terminee",2, LogMessage.LOG));
						} catch (ServiceNotReadyException e) {
							observer.log(new LogMessage(e.getMessage(),5, LogMessage.ERREUR));
						}
					}
				};
				PlayingThread.start();
			}else {
				observer.log(new LogMessage("Une macro est deja en cours d'execution!",5, LogMessage.ERREUR));
			} 
		}else {
			observer.log(new LogMessage("[ERREUR] Aucune données associées à "+macroName,5,LogMessage.ERREUR));
		}
	}
	
	public boolean isRecording() {
		return recordService.isRecording();
	}
	
	public void benchmark() {
		
	}
	
	public void loadFromFile(File file, boolean replace){
		if(IOThread == null || !IOThread.isAlive()) {
			IOThread = new Thread("IO-Thread") {
				@Override
				public void run() {
					try {
						observer.log(new LogMessage("Chargement et traitement des donn\u00e9es...",-1, LogMessage.LOG));
						macroMap = fileService.loadnParse(file);
						observer.log(new LogMessage("Chargement et traitement des donn\u00e9es termin\u00e9.",5, LogMessage.LOG));
						observer.macroListUpddated();
					} catch (InvalidDataFile e) {
						observer.log(new LogMessage(e.getMessage(),-1, LogMessage.ERREUR));
						createToFile(new File(DATA_FILE));
						e.printStackTrace();
					}
				}
			};
			IOThread.start();
		}else {
			observer.log(new LogMessage("Impossible d'acceder au fichier de sauvegarde pour l'instant.",5, LogMessage.ERREUR));
		} 
		
	}
	
	public void createToFile(File file) {
		boolean success = fileService.create(file);
		if(!success) {
			observer.saveCreationFailed();
		}else {
			observer.log(new LogMessage("Fichier de sauvegarde cr\u00e9e.",3, LogMessage.LOG));
		}
	}
	
	public void setObserver(ManagerObserver observer) {
		this.observer = observer;
	}
	
	public void removeObserver() {
		observer = new ManagerObserver() {//Does nothing
			@Override
			public void saveCreationFailed() {}

			@Override
			public void log(LogMessage logMessage) {}

			@Override
			public void macroListUpddated() {}
		};
	}
	
	/*
	 * Evenement qui passe l'enregistrement effectué.
	 * 
	 * */
	
	@Override
	public void receiveRecordResult(ArrayList<Action> enregistrement) {
		macroMap.put(macroName, new Macro(enregistrement, macroName));
		observer.macroListUpddated();
		observer.log(new LogMessage("L'enregistrement est termin\u00E9",3, LogMessage.LOG));
	}

	public String[] getMacroNames() {
		return macroMap.keySet().toArray(new String[macroMap.size()]);
	}
	
	public Macro getMacroByName(String name) {
		Macro mac = macroMap.get(name);
		return mac;
	}
	/*
	 * Supprime une macro et avertit l'observer
	 */
	public void delete(String selection) {
		macroMap.remove(selection);
		observer.macroListUpddated();
	}


	public void setMouseRecordingState(boolean bol) {
		recordService.setMouse(bol);
	}
	
	public void setKeyboardRecordingState(boolean bol) {
		recordService.setKeyboard(bol);
	}
	
	public boolean isKeyboardEnabled() {
		return recordService.isKeyboardEnabled();
	}

	public boolean isMouseEnabled() {
		return recordService.isMouseEnabled();
	}


	public void setPlaybackSpeed(int value) {
		robotService.setPlaybackSpeed(value);
		
	}

	public int getPlaybackSpeed() {
		return robotService.getPlaybackSpeed();
	}
}
