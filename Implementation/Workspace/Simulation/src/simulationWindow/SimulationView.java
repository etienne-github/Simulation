package simulationWindow;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;
import sim.portrayal.simple.RectanglePortrayal2D;
import simulation.SimulationModel;
import simulation.entity.Grass;
import simulation.entity.Hare;

public class SimulationView extends GUIState {

	private static final int FRAME_SIZE = 500;

	private SimulationModel model;

	private Display2D display;
	private SparseGridPortrayal2D yard;
	private JFrame mainWindow;

	public SimulationView(SimulationModel model) {
		super(model);
		this.model = model;
	}

	public void init(Controller c) {
		super.init(c);
		display = new Display2D(FRAME_SIZE, FRAME_SIZE, this);
		display.setClipping(false);
		mainWindow = display.createFrame();
		mainWindow.setTitle("Lapin IA04");
		c.registerFrame(mainWindow);
		mainWindow.setVisible(true);
		yard = new SparseGridPortrayal2D();
		display.attach(yard, "Yard");
	}

	public void start() {
		super.start();
		setupPortrayals();
	}

	private void setupPortrayals() {
		yard.setField(model.getYard());
		yard.setPortrayalForClass(Hare.class, new RabbitPortrayal());
		yard.setPortrayalForClass(Grass.class, new GrassPortrayal());

		display.reset();
		display.setBackdrop(Color.WHITE);
		display.repaint();
	}

	private class RabbitPortrayal extends OvalPortrayal2D {
		private static final long serialVersionUID = 1L;

		public RabbitPortrayal() {
			super();
			paint = Color.BLUE;
			filled = true;
		}

		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
			Hare rabbit = (Hare) object;
			if (rabbit.getEnergy() < Hare.ENERGY_TO_BE_HUNGRY) {
				paint = Color.RED;
			} else {
				paint = Color.BLUE;
			}

			super.draw(object, graphics, info);
		}
	}

	private class GrassPortrayal extends RectanglePortrayal2D {
		private static final long serialVersionUID = 1L;

		public GrassPortrayal() {
			super();
			paint = Color.BLUE;
			filled = true;
		}

		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
			Grass grass = (Grass) object;
			if (grass.getLength() < Grass.LENGTH_EATEN) {
				paint = Color.BLACK;
			} else if (grass.getLength() < Grass.MAX_LENGTH/2) {
				paint = Color.DARK_GRAY;
			} else {
				paint = Color.GREEN;
			}

			super.draw(object, graphics, info);
		}
	}

}
