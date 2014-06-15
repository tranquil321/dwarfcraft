package dwarfcraft;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import component.control.PlayerInputComponent;
import component.graphics.GraphicsComponent;
import component.graphics.MobGraphicsComponent;
import component.physics.MobPhysicsComponent;
import dwarfcraft.entity.Entity;
import dwarfcraft.world.World2D;
import services.ServiceLocator;
import services.graphics.GraphicsService2D;
import services.input.InputService;
import services.input.KeyboardInputService;

public class DwarfCraft {

	public static void main(String[] args) throws InterruptedException {
		
		int worldSize = 100;
		
		ServiceLocator.setInput(new KeyboardInputService());
		final InputService kb = ServiceLocator.getInput();
		final World2D world = new World2D(worldSize);
		Collection<Entity> entities = new ArrayList<Entity>();
		final GraphicsService2D graphics = new GraphicsService2D(world, entities );
		
		
		Entity player = new Entity.Player("Player");
		player.setLocation(worldSize/2, worldSize/2);
		entities.add(player);

		
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				graphics.setVisible(true);
			}
		});
		
		
		//The game loop
		long previous, current;
		long elapsed;
		long totalTime;
		previous = (new Date()).getTime();
		
		int FRAMES_PER_SECOND = 30;
		int MS_PER_UPDATE = 50;
		int MAX_FRAME_RATE = 60;
		double lag = 0.0;
		
		boolean RUNNING = true;
			
		while (RUNNING) {
			current = (new Date()).getTime();
			elapsed = (current - previous);
			previous = current;
			lag += elapsed;
			
			if (kb.isKeyPressed(KeyEvent.VK_ENTER)){
				RUNNING = false;
			}	

			while (lag >= MS_PER_UPDATE) {
				player.update(world, graphics);
				lag -= MS_PER_UPDATE;
			}
			
			graphics.render();
			
			totalTime = MAX_FRAME_RATE - (new Date()).getTime() + previous;
			if (totalTime > 0){
				Thread.sleep(totalTime);
			}
		}
		
		graphics.stopGraphics();	
	}
}
