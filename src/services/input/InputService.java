package services.input;

import java.util.Collection;


public interface InputService {

	boolean hasNewInput();
	boolean isKeyPressed(int keyCode);
	boolean isKeyReleased(int keyCode);
	Collection<Integer> getNewInput();
	Collection<Integer> peekNewInput();
	
}
