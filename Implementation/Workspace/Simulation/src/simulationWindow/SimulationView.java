package simulationWindow;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.simple.RectanglePortrayal2D;
import sim.util.gui.SimpleColorMap;
import simulation.SimulationModel;
import simulation.StatsManager;
import simulation.entity.Grass;
import utils.Constants;

public class SimulationView extends GUIState implements PropertyChangeListener{

	private static final int FRAME_SIZE = 500;

	private SimulationModel model;

	private Display2D display;
	private SparseGridPortrayal2D yard;
	private JFrame mainWindow;
	private SimpleColorMap colorMap = new SimpleColorMap(0,Constants.VEGETATION_MAX_WEIGHT_PER_CELL,new Color(240,227,181),new Color(78,214,30));
	private StatsManager stats;
	
	public SimulationView(SimulationModel model, StatsManager myManager) {
		super(model);
		this.model = model;
		model.getPropertyChangeSupport().addPropertyChangeListener(this);
		stats=myManager;
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
	

	public SparseGridPortrayal2D getYard() {
		return yard;
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

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if(arg0.getPropertyName().compareTo("grown")==0){
			//System.err.println("grown received !");
			Grass[][] grasses = (Grass[][]) arg0.getNewValue();
			for(int i=0;i<model.getYard().getWidth();i++){
				for(int j=0;j<model.getYard().getHeight();j++){
					
					
					
					
					RectanglePortrayal2D r = new RectanglePortrayal2D();
					r.paint=colorMap.getColor(model.getVegetationAt(i, j));
					r.filled=true;
					r.scale=1;
					
					yard.setPortrayalForObject(grasses[i][j], r);
				}
			}
		}else if(arg0.getPropertyName().compareTo("model_initialized")==0){
			model.getVegetationManager().getPropertyChangeSupport().addPropertyChangeListener(this);
		}else if(arg0.getPropertyName().compareTo("species_initialized")==0){
			
			System.out.println("species_initialized propertychange received !");
			
			scheduleImmediateRepeat(true, new Steppable()
			{
			int mySteps=0;;	
			public void step(SimState state)
			   {

				 double x = model.schedule.time(); 
			   
			 
			   
			   // now add the data
			   if (x >= state.schedule.EPOCH && x < state.schedule.AFTER_SIMULATION){
				   mySteps++;
				   mySteps=(int) (mySteps%model.getStepByDay());
				   if(mySteps==0){
					   stats.updateCharts(model.StepToDay(x));
				   }		       
			   }
			   }
			});
			
			stats.createFrames(controller);
		}
	}

}
