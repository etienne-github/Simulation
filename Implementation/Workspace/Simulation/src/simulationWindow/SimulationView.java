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
		mainWindow.setTitle("Simulation IA04");
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

		display.reset();
		display.setBackdrop(Color.WHITE);
		display.repaint();
	}

}
