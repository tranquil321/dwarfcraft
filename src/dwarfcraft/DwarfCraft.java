package dwarfcraft;

import java.awt.BorderLayout;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import component.graphics.GraphicsComponent;
import services.ServiceLocator;
import services.graphics.GraphicsService2D;
import services.input.InputService;
import services.input.KeyboardInputService;

public class DwarfCraft {

//	private static Entity createPlayer() {
//		return new Entity(new PlayerInputComponent(), new MobPhysicsComponent(),
//				new MobGraphicsComponent());
//	}

	public static void main(String[] args) {
		
		int worldWidth = 19;
		int worldHeight = 19;
		
		ServiceLocator.setInput(new KeyboardInputService());
		final InputService kb = ServiceLocator.getInput();
		final World3D world = new World3D(worldWidth, worldHeight);
		final GraphicsService2D graphics = new GraphicsService2D(world);
		
//		Entity player = DwarfCraft.createPlayer();
//		player.x = worldWidth/2;
//		player.y = worldHeight/2;

		
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Testing!");
				frame.setSize(800, 600);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setLayout(new BorderLayout());
				frame.getContentPane().add(graphics, BorderLayout.CENTER);
				frame.setVisible(true);
			}
		});
		
		
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
