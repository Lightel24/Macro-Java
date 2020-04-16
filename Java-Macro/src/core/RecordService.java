package core;

public class RecordService extends Service{
	
	private KeyLogger keyLogger;
	private MouseLogger mouseLogger;
	
	public RecordService() {
		init();
	}
	
	private void init() {
		keyLogger = new KeyLogger();
		mouseLogger = new MouseLogger();
	}
	
}
