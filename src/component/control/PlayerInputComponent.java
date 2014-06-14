package component.control;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import services.ServiceLocator;
import services.graphics.GraphicsService;
import services.input.InputService;
import dwarfcraft.Entity;

public class PlayerInputComponent extends InputComponent {

	public PlayerInputComponent(){
		
	}
	
	public void update(Entity entity) {
		InputService kb = ServiceLocator.getInput();
		
		if(kb.hasNewInput()){
			
		}
	}


}
