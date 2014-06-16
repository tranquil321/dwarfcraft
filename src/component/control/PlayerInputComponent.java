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
			System.out.println("New Input!");
			
			double vNorth = 0;
			vNorth = kb.isKeyPressed(KeyEvent.VK_UP) ? -1*speed : 0;
			vNorth += kb.isKeyPressed(KeyEvent.VK_DOWN) ? speed : 0;
			
			double vEast = 0;
			vEast = kb.isKeyPressed(KeyEvent.VK_LEFT) ? -1*speed : 0;
			vEast += kb.isKeyPressed(KeyEvent.VK_RIGHT) ? speed : 0;
			
			parent.getPhysicsComponent().setVelocityNorth(vNorth);
			parent.getPhysicsComponent().setVelocityEast(vEast);
			
//			parent.getPhysicsComponent().setVelocityNorth(kb.isKeyPressed(KeyEvent.VK_UP) ? -1*speed : 0);
//			parent.getPhysicsComponent().setVelocityNorth(kb.isKeyPressed(KeyEvent.VK_DOWN) ? speed : 0);
//			parent.getPhysicsComponent().setVelocityEast(kb.isKeyPressed(KeyEvent.VK_LEFT) ? -1*speed : 0);
//			parent.getPhysicsComponent().setVelocityEast(kb.isKeyPressed(KeyEvent.VK_RIGHT) ? speed : 0);
			
		}
	}


}
