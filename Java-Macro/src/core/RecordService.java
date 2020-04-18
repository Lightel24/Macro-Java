package core;

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
	private int mouseFrequency;
	
	public RecordService() {
		init();
		GlobalScreen.setEventDispatcher(new SwingDispatchService());
	}
	
	public void startRecording() {
		try {
			GlobalScreen.registerNativeHook();
			GlobalScreen.addNativeKeyListener(this);
			GlobalScreen.addNativeMouseListener(this);
			GlobalScreen.addNativeMouseMotionListener(this);
			GlobalScreen.addNativeMouseWheelListener(this);
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
	}
	
	public void setMouseFrequency(int mouseFrequency) {
		this.mouseFrequency = mouseFrequency;
	}
	
	private void init() {
		mouseFrequency = DEFAULT_MOUSE_FREQUENCY;
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);

		// Don't forget to disable the parent handlers.
		logger.setUseParentHandlers(false);
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		
	}

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
