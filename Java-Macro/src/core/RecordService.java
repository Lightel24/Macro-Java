package core;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class RecordService extends Service implements  NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener{
	
	public static final int DEFAULT_MOUSE_FREQUENCY = 5;
	private static boolean isRunning;

	private ArrayList<Action> enregistrement = new ArrayList<Action>();
	private int mouseFrequency;
	private long lastMouseMoveRegistered;
	private long start;
	private RecordServiceObserver observer;
	
	private boolean mouse; 
	private boolean Keyboard; 
	
	public RecordService() {
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);

		// Don't forget to disable the parent handlers.
		logger.setUseParentHandlers(false);
		reset();
		GlobalScreen.setEventDispatcher(new SwingDispatchService());
		mouse = true;
		Keyboard = true;
	}
	
	public void startRecording(int secondes) throws ObserverNotSetException, ServiceNotReadyException{
			startRecording();
			Timer t = new Timer();	//On prévoit de stop l'enregistrement dans x secondes
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					stopRecording();
				}

			}, secondes*1000);
	}
	
	public void startRecording()  throws ObserverNotSetException, ServiceNotReadyException {
		if(observer!=null) {
			if(isRunning) {
				throw new ServiceNotReadyException("Un enregistrement est deja en cours");
			}else {
				start = System.currentTimeMillis();
				isRunning = true;
				try {
					GlobalScreen.registerNativeHook();
					GlobalScreen.addNativeKeyListener(this);
					if(mouse) {
						GlobalScreen.addNativeMouseListener(this);
						GlobalScreen.addNativeMouseMotionListener(this);
						GlobalScreen.addNativeMouseWheelListener(this);
					}
				} catch (NativeHookException e) {
					e.printStackTrace();
				}
			}
			
		}else {
			throw new ObserverNotSetException("Les données ne peuvent être récupérées! ");
		}
	}
	
	public void stopRecording() {
		if(isRunning) {
			observer.receiveRecordResult((ArrayList<Action>) enregistrement.clone());
			reset();
			try {
				GlobalScreen.unregisterNativeHook();
				GlobalScreen.removeNativeKeyListener(this);
				GlobalScreen.removeNativeMouseListener(this);
				GlobalScreen.removeNativeMouseMotionListener(this);
				GlobalScreen.removeNativeMouseWheelListener(this);
			} catch (NativeHookException e) {
				e.printStackTrace();
			}
		}
	}

	
	private void reset() {
		enregistrement.clear();
		lastMouseMoveRegistered = 0;;
		isRunning = false;
	}

	public void setMouseFrequency(int mouseFrequency) {
		if(mouseFrequency>500 || mouseFrequency<1) {
			System.err.println("La fréquence doit être comprise dans [1,500]");
			mouseFrequency = DEFAULT_MOUSE_FREQUENCY;
		}else {
			this.mouseFrequency = mouseFrequency;
		}
	}
	
	public boolean isMouseEnabled() {
		return mouse;
	}
	
	public boolean isKeyboardEnabled() {
		return Keyboard;
	}
	
	public void setMouse(boolean mouse) {
		this.mouse = mouse;
	}
	
	public void setKeyboard(boolean Keyboard) {
		this.Keyboard = Keyboard;
	}
	
	private long now() {
		return System.currentTimeMillis();
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		if(arg0.getButton() == NativeMouseEvent.BUTTON1) {
			enregistrement.add(new MouseAction(now()-start, MouseAction.LEFT_MOUSE_PRESSED, arg0.getPoint()));
		}else if(arg0.getButton() == NativeMouseEvent.BUTTON2) {
			enregistrement.add(new MouseAction(now()-start, MouseAction.RIGHT_MOUSE_PRESSED, arg0.getPoint()));
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		if(arg0.getButton() == NativeMouseEvent.BUTTON1) {
			enregistrement.add(new MouseAction(now()-start, MouseAction.LEFT_MOUSE_RELEASED, arg0.getPoint()));
		}else if(arg0.getButton() == NativeMouseEvent.BUTTON2) {
			enregistrement.add(new MouseAction(now()-start, MouseAction.RIGHT_MOUSE_RELEASED, arg0.getPoint()));
		}
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {
		if(System.currentTimeMillis() - lastMouseMoveRegistered > (1000/mouseFrequency)) {
			lastMouseMoveRegistered = System.currentTimeMillis();
			enregistrement.add(new MouseAction(now()-start, MouseAction.MOUSE_MOVED, arg0.getPoint()));
		}
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		if(System.currentTimeMillis() - lastMouseMoveRegistered > (1000/mouseFrequency)) {
			lastMouseMoveRegistered = System.currentTimeMillis();
			enregistrement.add(new MouseAction(now()-start, MouseAction.MOUSE_MOVED, arg0.getPoint()));
		}
	}

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent arg0) {}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		if(arg0.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
			stopRecording();
		}else {
			if(Keyboard) {
				enregistrement.add(new KeyAction(now()-start,KeyAction.KEY_PRESSED,arg0.getKeyCode(),arg0.getRawCode()));			
			}
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		if(Keyboard) {
			enregistrement.add(new KeyAction(now()-start,KeyAction.KEY_RELEASED,arg0.getKeyCode(), arg0.getRawCode()));
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {}

	
	public void setObserver(RecordServiceObserver observer) {
		this.observer = observer;
	}
	
	public void removeObserver() {
		this.observer = null;
	}

	public boolean isRecording() {
		return isRunning;
	}
	
}
