package services.input;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class KeyboardInputService implements InputService, KeyEventDispatcher {

	public KeyboardInputService() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(this);
	}

	public boolean hasNewInput() {
		return false;
	}

	public KeyboardInputType getNewInput() {
		return null;
	}

	public KeyboardInputType peekNewInput() {
		return null;
	}

	public boolean dispatchKeyEvent(KeyEvent e) {
		System.out.println(e.getID());
		e.getKeyChar();
//		if (e.getID() == KeyEvent.KEY_PRESSED) {
//			System.out.println(e);
//		}
		return true;
	}
}
