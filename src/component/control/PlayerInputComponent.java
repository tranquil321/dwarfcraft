package component.control;

import java.awt.event.KeyEvent;

import services.ServiceLocator;
import services.input.InputService;

public class PlayerInputComponent extends InputComponent {

	private InputService kb;
	int speed = 6;

	public PlayerInputComponent() {
		kb = ServiceLocator.getInput();
	}

	public void update() {
		if(kb.hasNewInput()){
			
			if (kb.isKeyReleased(KeyEvent.VK_UP)
					&& kb.isKeyReleased(KeyEvent.VK_DOWN)) {
				parent.getPhysicsComponent().setVelocityNorth(0);
			} else {
				if(kb.isKeyPressed(KeyEvent.VK_UP)){
					parent.getPhysicsComponent().setVelocityNorth(-1*speed);
				} 
				
				if(kb.isKeyPressed(KeyEvent.VK_DOWN)){
					parent.getPhysicsComponent().setVelocityNorth(speed);
				}
			}
			
			if(kb.isKeyReleased(KeyEvent.VK_LEFT) && kb.isKeyReleased(KeyEvent.VK_RIGHT)){
				parent.getPhysicsComponent().setVelocityEast(0);
			} else{
				if(kb.isKeyPressed(KeyEvent.VK_LEFT)){
					parent.getPhysicsComponent().setVelocityEast(-1*speed);
				}
				
				if(kb.isKeyPressed(KeyEvent.VK_RIGHT)){
					parent.getPhysicsComponent().setVelocityEast(speed);
				}
			}
		}
	}


}
