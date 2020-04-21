package core;

import gui.LogMessage;

public interface ManagerObserver {

	void saveCreationFailed();

	void log(LogMessage logMessage);

	void macroListUpddated();

}
