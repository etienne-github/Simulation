package simulation;
import java.beans.PropertyChangeSupport;

import preSimulationWindow.SimProperties;
import sim.display.Console;
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
import simulation.entity.Entity;
import simulation.entity.Grass;
import simulation.entity.VegetationManager;
import simulationWindow.SimulationView;


@SuppressWarnings("serial")
public class SimulationModel extends SimState  {

	private PropertyChangeSupport support=new PropertyChangeSupport(this);
	private SparseGrid2D yard;
	private SimProperties simProperties;
	private Float[][] Vegetation;
	private Grass[][] Grasses;
	private VegetationManager myVegetationManager;
	
	public PropertyChangeSupport getPropertyChangeSupport(){
		return support;
	}
	
	public VegetationManager getVegetationManager(){
		return this.myVegetationManager;
	}
	
	public SimulationModel(long seed) {
		super(seed);
		 
	}

	public Float[][] getVegetation(){
		return Vegetation;
	}
	
	public float getVegetationAt(int x,int y){
		return Vegetation[x][y];
	}
	
	public float consumeVegetationAt(int x, int y, float weightToBeConsumed){
		float canConsume= Math.min(Vegetation[x][y], weightToBeConsumed);
		Vegetation[x][y]-=canConsume;
		return canConsume;
	}
	
	public void setProperties(SimProperties properties) {
		simProperties = new SimProperties(properties);
	}
	
	public SparseGrid2D getYard() {
		return yard;
	}
	
	public void launchView() {
		SimulationView gui = new SimulationView(this);
		Console console = new Console(gui);
		console.setVisible(true);
	}
	
	public void start() {
		super.start();
		
		Vegetation = new Float[simProperties.getGridWidth()][simProperties.getGridHeight()];
		
		
		
		yard = new SparseGrid2D(simProperties.getGridWidth(), simProperties.getGridHeight());
		myVegetationManager= new VegetationManager(this);
		Entity.setGRID_SIZE_X(simProperties.getGridWidth());
		Entity.setGRID_SIZE_Y(simProperties.getGridHeight());
		
		
		
	
		schedule.scheduleRepeating(myVegetationManager);
		//add Hare
		for(int i = 0 ; i < simProperties.getHareNumber() ; i++) {
			
		}
		support.firePropertyChange("model_initialized", null, null);
	}
	
}
