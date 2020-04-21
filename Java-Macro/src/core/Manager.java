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
	private ManagerObserver observer;
	private Thread IOThread;
	private String macroName;
	
	public Manager(){
		fileService = new FileService();
		recordService = new RecordService();
		recordService.setObserver(this);
		macroName = "";
		removeObserver(); //Eviter un nullpointer
	}
	
	public void startRecording(String macroName) {
		try {
			observer.log(new LogMessage("Début de l'enregistrement.",5));
			recordService.startRecording();
			this.macroName = macroName;
		} catch (ObserverNotSetException e) {
			observer.log(new LogMessage("L'opération a échoué veuillez réessayer.",5));
			recordService.setObserver(this);
			e.printStackTrace();
		} catch (ServiceNotReadyException e) {
			observer.log(new LogMessage(e.getMessage(),10));
		}
	}
	
	public void stopRecording() {
		recordService.stopRecording();
		macroName = "";
	}
	
	public void benchmark() {
		
	}
	
	public void loadFromFile(File file, boolean replace){
		if(IOThread == null || !IOThread.isAlive()) {
			IOThread = new Thread("IO-Thread") {
				@Override
				public void run() {
					try {
						observer.log(new LogMessage("Chargement et traitement des données...",5));
						macroMap = fileService.loadnParse(file);
						observer.log(new LogMessage("Chargement et traitement des données terminé.",5));
					} catch (InvalidDataFile e) {
						observer.log(new LogMessage("",1));
						observer.log(new LogMessage(e.getMessage(),-1));
						createToFile(new File(DATA_FILE));
						e.printStackTrace();
					}
				}
			};
			IOThread.start();
		}else {
			observer.log(new LogMessage("Impossible d'acceder au fichier de sauvegarde pour l'instant.",5));
		}
		
	}
	
	public void createToFile(File file) {
		boolean success = fileService.create(file);
		if(!success) {
			observer.saveCreationFailed();
		}else {
			observer.log(new LogMessage("Fichier de sauvegarde crée.",0));
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
	}

	public String[] getMacroNames() {
		return macroMap.keySet().toArray(new String[macroMap.size()]);
	}
	
	public Macro getMacroByName(String name) {
		Macro mac = macroMap.get(name);
		return mac;
	}
}
