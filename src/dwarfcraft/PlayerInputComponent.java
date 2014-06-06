package dwarfcraft;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import utils.Locator;
import utils.graphics.GraphicsService;
import utils.input.InputService;
import component.control.InputComponent;

public class PlayerInputComponent implements InputComponent {

	public PlayerInputComponent(){
		
	}
	
	public void update(Entity entity) {
		InputService kb = Locator.getInput();
		
		if(kb.hasNewInput()){
			
		}
	}


}
