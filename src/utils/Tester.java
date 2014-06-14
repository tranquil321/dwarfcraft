package utils;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class Tester extends JFrame{
	float[][] map;
	PerlinNoise doop = new PerlinNoise();
	int Size = 800;
	
	public static void main(String[] args){
		
		Tester test = new Tester();
		test.run();
	}

	private void run() {
		setTitle("Perlin"); 
        setSize(Size, Size); 
        setResizable(false); 
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setVisible(true);
        //SEED, SIZE, OCTAVES, PERSISTENCE, LACUNARITY, FREQUENCY.
        Random r = new Random();
		map = doop.perlin2D(r.nextInt(), Size, 8, .7f, 2f, 1/((float)Size));
		repaint();
	}
	
	public void paint(Graphics g){
		doop.draw(g, map);
	}
}
