package dwarfcraft;

import java.util.Date;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import utils.Locator;
import utils.input.InputService;
import utils.input.KeyboardInputService;

public class DwarfCraft {

//	private static Entity createPlayer() {
//		return new Entity(new PlayerInputComponent(), new MobPhysicsComponent(),
//				new MobGraphicsComponent());
//	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Testing!");
				frame.setSize(800, 600);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
			}
		});
		
		int worldWidth = 19;
		int worldHeight = 19;
		
		Graphics graphics = new ConsoleGraphics();
		Locator.setInput(new KeyboardInputService());
		InputService kb = Locator.getInput();
		World world = new World();
		
//		Entity player = DwarfCraft.createPlayer();
//		player.x = worldWidth/2;
//		player.y = worldHeight/2;
		
		//The game loop
		long previous, current;
		long elapsed;
		previous = (new Date()).getTime();
		
		int FRAMES_PER_SECOND = 30;
		int MS_PER_UPDATE = 50;
		double lag = 0.0;
		
		boolean RUNNING = true;
			
		while (RUNNING) {
			current = (new Date()).getTime();
			elapsed = (current - previous);
			previous = current;
			lag += elapsed;
			
//			if (kb.getKey() == '\n'){
//				RUNNING = false;
//			}	
//
//			while (lag >= MS_PER_UPDATE) {
//				world->update(graphics);
//				Player->update(world, graphics);
//				lag -= MS_PER_UPDATE;
//			}
//			
//			graphics.render();
		}
		
//		graphics->stopGraphics();	
//		world->~World();
	}
}
