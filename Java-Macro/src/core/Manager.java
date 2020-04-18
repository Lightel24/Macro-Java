package core;

import java.io.File;
import java.util.HashMap;

import gui.LogMessage;

public class Manager{
	
	private HashMap<Integer,Macro> macroMap = new HashMap<Integer,Macro>();
	private FileService fileService;
	private RecordService recordService;
	private ManagerObserver observer;
	private Thread IOThread;
	
	public Manager(){
		fileService = new FileService();
		recordService = new RecordService();
		removeObserver(); //Eviter un nullpointer
	}
	
	public void startRecording() {
		recordService.startRecording();	//Faire �a en async cot� vue
	}
	
	public void stopRecording() {

	}
	
	public void loadFromFile(File file){ //Faire �a en async cot� vue
		if(IOThread == null || !IOThread.isAlive()) {
			IOThread = new Thread("IO-Thread") {
				@Override
				public void run() {
					try {
						observer.log(new LogMessage("Chargement et traitement des donn�es...",-1));
						macroMap = fileService.loadnParse(file);
						observer.log(new LogMessage("Chargement et traitement des donn�es termin�.",10));
					} catch (InvalidDataFile e) {
						observer.log(new LogMessage("Cr�ation d'un fichier de sauvegarde.",-1));
						createToFile(file);
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
			observer.log(new LogMessage("Fichier de sauvegarde cr�e.",0));
		}
	}
	
	
	public void setObserver(ManagerObserver observer) {
		this.observer = observer;
	}
	
	public void removeObserver() {
		observer = new ManagerObserver() {
			@Override
			public void saveCreationFailed() {}//Does nothing

			@Override
			public void log(LogMessage logMessage) {}
		};
	}
}
