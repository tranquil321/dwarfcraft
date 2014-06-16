package services.input;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashSet;

public class KeyboardInputService implements InputService, KeyEventDispatcher {

	boolean hasNewInput = false;
	KeyEvent lastEvent = null;
	Collection<Integer> keyMap = new HashSet<Integer>(145);
	
	
	public KeyboardInputService() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(this);
	}

	public boolean hasNewInput() {
		return hasNewInput;
	}

	public Collection<Integer> getNewInput() {
		hasNewInput = false;
		return keyMap;
	}
	
	public KeyEvent getLastEvent(){
		return lastEvent;
	}

	public Collection<Integer> peekNewInput() {
		return keyMap;
	}
	
	public boolean isKeyPressed(int keyCode){
		return keyMap.contains(keyCode);
	}
	
	public boolean isKeyReleased(int keyCode){
		return !isKeyPressed(keyCode);
	}

	public boolean dispatchKeyEvent(KeyEvent e) {
//		System.out.println(e.getID() + ": " + KeyEvent.getKeyText(e.getKeyCode()));
		hasNewInput = hasNewInput || keyMap.add(e.getKeyCode());
		return false;
	}
}
