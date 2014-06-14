package services.input;


public interface InputService {

	boolean hasNewInput();
	InputType getNewInput();
	InputType peekNewInput();

}
