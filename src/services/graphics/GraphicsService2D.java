package services.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;

import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import dwarfcraft.entity.Entity;
import dwarfcraft.world.World;
import net.miginfocom.swing.MigLayout;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.JSpinner;

public class GraphicsService2D extends JFrame implements GraphicsService, ChangeListener {
	
	private World world;
	private int blockWidth = 10;
	private int blockHeight = 10;
	
	JPanel panel = new JPanel(){
		@Override
		public void paint(Graphics g){
			// Should draw only from image buffer
			
			int drawWidth = 1000;
			int drawHeight = 1000;
			
			GraphicsService2D.this.blockWidth = (int) (drawWidth / (world.getBlockWidth()+0.0));
			GraphicsService2D.this.blockHeight = (int) (drawHeight / (world.getBlockHeight()+0.0));
			
			int xStart = (int) (this.getWidth()/2.0 - drawWidth/2.0);
			int yStart = (int) (this.getHeight()/2.0 - drawHeight/2.0);
			
			for(int x = 0; x < world.getBlockWidth(); x++){
				for(int y = 0; y < world.getBlockHeight(); y++){
					int block = world.getBlock(x, y);
					Color acolor;
					double peakLevel = slider.getValue()/(slider.getMaximum() + 0.0);
					double treeLevel = slider_2.getValue()/(slider_2.getMaximum() + 0.0);
					double waterLevel = slider_3.getValue()/(slider_3.getMaximum() + 0.0);
					if (block >= peakLevel*256){acolor = Color.WHITE;}
					else if (block > treeLevel*256){acolor = Color.GRAY;}
					else if (block > waterLevel*256){acolor = Color.GREEN;}
					else {acolor = Color.BLUE;}
					g.setColor(acolor);
					
					g.fillRect(x*blockWidth + xStart, y*blockHeight + yStart, blockWidth, blockHeight);
				}
			}
		}
	};
	private JSlider slider;
	private JSlider slider_2;
	private JSlider slider_3;
	private JPanel panel_1;
	private JLabel lblP;
	private JLabel lblL;
	private JLabel lblF;
	private JLabel lblO;
	private JSpinner spinner;
	private JSlider sliderFrequency;
	private JSlider sliderLacunarity;
	private JSlider sliderPersistance;
	private ChangeListener l = new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			world.regen(sliderFrequency.getValue()/100.0f, sliderLacunarity.getValue()/50.0f, sliderPersistance.getValue()/20f, (Integer)spinner.getValue());
			GraphicsService2D.this.panel.repaint();
		}
	};
	
	public GraphicsService2D(World world) {
		super();
		this.world = world;
		
//		this.setBackground(Color.BLACK);
		
		this.setSize(1250, 1000);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[250!,grow,fill][grow,fill]", "[grow,center][grow]"));
		
		slider = new JSlider(0, 100);
		slider.addChangeListener(this);
		getContentPane().add(slider, "flowy,cell 0 0");
		
		slider_2 = new JSlider(0, 100);
		slider_2.addChangeListener(this);
		getContentPane().add(slider_2, "cell 0 0");
		
		slider_3 = new JSlider(0, 100);
		slider_3.addChangeListener(this);
		getContentPane().add(slider_3, "cell 0 0");

		this.getContentPane().add(panel, "cell 1 0 1 2,grow");
		
		panel_1 = new JPanel();
		getContentPane().add(panel_1, "cell 0 1,growx,aligny center");
		panel_1.setLayout(new MigLayout("ins 0", "[20px:n,right][grow,fill]", "[][][][]"));
		
		lblP = new JLabel("P");
		panel_1.add(lblP, "cell 0 0");
		
		sliderPersistance = new JSlider(0, 100);
		sliderPersistance.addChangeListener(l);
		panel_1.add(sliderPersistance, "cell 1 0");
		
		lblL = new JLabel("L");
		panel_1.add(lblL, "cell 0 1");
		
		sliderLacunarity = new JSlider(0, 100);
		sliderLacunarity.addChangeListener(l);
		panel_1.add(sliderLacunarity, "cell 1 1");
		
		lblF = new JLabel("F");
		panel_1.add(lblF, "cell 0 2");
		
		sliderFrequency = new JSlider(0, 100);
		sliderFrequency.addChangeListener(l);
		panel_1.add(sliderFrequency, "cell 1 2");
		
		lblO = new JLabel("O");
		panel_1.add(lblO, "cell 0 3");
		
		spinner = new JSpinner();
		spinner.addChangeListener(l);
		spinner.setModel(new SpinnerNumberModel(1, 1, 8, 1));
		panel_1.add(spinner, "cell 1 3");
		
	}

	public void drawAll() {
		// Should add graphics buffer
	}

	public void drawEntity(Entity e) {
		
	}

	public void drawEntities(Collection<Entity> entities) {
		
	}

	public void stateChanged(ChangeEvent e) {
		panel.repaint();
	}

	public void render() {
		panel.repaint();
	}

	public void stopGraphics() {
		this.setVisible(false);
		this.dispose();
	}
	
	
}
