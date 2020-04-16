package core;

public abstract class Service {
	ServiceObserver observer;
	
	public void setServiceObserver(ServiceObserver observer) {
		this.observer = observer;
	}
}
