package simulation;

import java.beans.PropertyChangeSupport;

import server.StatsManager;
import sim.display.Console;
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
import simulation.entity.Entity;
import simulation.entity.Grass;
import simulation.entity.VegetationManager;
import simulationWindow.SimulationView;

@SuppressWarnings("serial")
public class SimulationModel extends SimState {

	private static final double DEFAULT_STEP_BY_DAY = 4;
	private static final double DEFAULT_METER_BY_CASE = 20;
	
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	private SparseGrid2D yard;
	private SimProperties simProperties;
	private Double stepByDay;
	private Double caseByMeter;
	private Float[][] Vegetation;
	private Grass[][] Grasses;
	private VegetationManager myVegetationManager;
	private AnimalFactory myFactory;
	private StatsManager myManager;

	public SimulationModel(long seed) {
		super(seed);
		stepByDay = DEFAULT_STEP_BY_DAY;
		caseByMeter = DEFAULT_METER_BY_CASE;
		myManager = new StatsManager();
	}

	public PropertyChangeSupport getPropertyChangeSupport() {
		return support;
	}

	public VegetationManager getVegetationManager() {
		return this.myVegetationManager;
	}
	
	public Double getStepByDay() {
		return stepByDay;
	}

	public Double getCaseByMeter() {
		return caseByMeter;
	}

	public Float[][] getVegetation() {
		return Vegetation;
	}

	public float getVegetationAt(int x, int y) {
		//System.out.println("Get vegetation at ("+x+","+y+") when Vegetation is "+Vegetation.length);
		return Vegetation[x][y];
	}

	public float consumeVegetationAt(int x, int y, Double weightToBeConsumed) {
		System.out.println("Vegetation is "+(Vegetation[x][y]*1000)+ "g and an anima wants to eat "+(weightToBeConsumed*1000)+"g.");
		float canConsume = (float) Math.min(Vegetation[x][y], weightToBeConsumed);
		Vegetation[x][y] -= canConsume;
		System.out.println("Vegetation is eaten off of "+(canConsume*1000)+"g and is now of "+(Vegetation[x][y]*1000)+ "g");
		return canConsume;
	}

	public void setProperties(SimProperties properties) {
		simProperties = properties;
	}

	public SparseGrid2D getYard() {
		return yard;
	}

	public void launchView() {
		SimulationView gui = new SimulationView(this,myManager);
		Console console = new Console(gui);
		console.setVisible(true);
		myFactory = new AnimalFactory(this,gui,myManager);
		
	}

	public void start() {
		super.start();

		Vegetation = new Float[simProperties.getGridWidth()][simProperties
				.getGridHeight()];
		yard = new SparseGrid2D(simProperties.getGridWidth(),
				simProperties.getGridHeight());
		myVegetationManager = new VegetationManager(this);
		Entity.setGRID_SIZE_X(simProperties.getGridWidth());
		Entity.setGRID_SIZE_Y(simProperties.getGridHeight());
		schedule.scheduleRepeating(myVegetationManager);
		support.firePropertyChange("model_initialized", null, null);
		myFactory.createAnimalsFromBatch(simProperties.getSpeciesList());	
		support.firePropertyChange("species_initialized", null, null);	
	}
	
}
