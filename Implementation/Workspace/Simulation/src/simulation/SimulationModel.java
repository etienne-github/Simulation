package simulation;

import java.beans.PropertyChangeSupport;

import sim.display.Console;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.grid.SparseGrid2D;
import simulation.entity.Entity;
import simulation.entity.Grass;
import simulation.entity.VegetationManager;
import simulationWindow.SimulationView;

@SuppressWarnings("serial")
public class SimulationModel extends SimState {

	private static final double DEFAULT_STEP_BY_DAY = 4;
	private static final double DEFAULT_METER_BY_CASE = 40;
	
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	private SparseGrid2D yard;
	private SimProperties simProperties;
	private Double stepByDay;
	private Double meterByCase;
	private Float[][] Vegetation;
	private Grass[][] Grasses;
	private VegetationManager myVegetationManager;
	private AnimalFactory myFactory;
	private StatsManager myManager;

	public SimulationModel(long seed) {
		super(seed);
		stepByDay = DEFAULT_STEP_BY_DAY;
		meterByCase = DEFAULT_METER_BY_CASE;
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

	public Double getMeterByCase() {
		return meterByCase;
	}

	public Float[][] getVegetation() {
		return Vegetation;
	}

	public float getVegetationAt(int x, int y) {
		//System.out.println("Get vegetation at ("+x+","+y+") when Vegetation is "+Vegetation.length);
		return Vegetation[x][y];
	}

	public float consumeVegetationAt(int x, int y, Double weightToBeConsumed) {
		//System.out.println("Vegetation is "+(Vegetation[x][y]*1000)+ "g and an anima wants to eat "+(weightToBeConsumed*1000)+"g.");
		float canConsume = (float) Math.min(Vegetation[x][y], weightToBeConsumed);
		Vegetation[x][y] -= canConsume;
		//System.out.println("Vegetation is eaten off of "+(canConsume*1000)+"g and is now of "+(Vegetation[x][y]*1000)+ "g");
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
		
		
		//manage species renew in accordance with birthrate
		schedule.scheduleRepeating(new Steppable() {
			
			@Override
			public void step(SimState arg0) {
				myFactory.renewSpecies();
				
			}
		});

	}
	
	public Double DayToStep(Double days){
		
		//1 Day = 4 steps
		//2 days => 2*4 steps
		
		return days*stepByDay;
	}
	
	public Double StepToDay(Double steps){
		
		//1 Day = 4 steps
		//8 steps => 8/4 2 days
		
		return steps/stepByDay;
	}
	
	public Double ValueByStepToValueByDay(Double valueByStep){
		
		//1 Day = 4 steps
		//100gr eaten by step => 100*4 gr eaten by day
		
		return valueByStep*stepByDay;
	}
	
	public Double ValueByDayToValueByStep(Double valueByDay){
		
		//1 Day = 4 steps
		//400gr eaten by Days => 400/4gr eaten by step
		
		return valueByDay/stepByDay;
	}
	
	
	//-----------------------
public Double CaseToMeters(Double cases){
		
		//1 case = 20 meters
		//2 cases => 2*20 meters
		
		return cases*meterByCase;
	}
	
	public Double MeterToCase(Double meters){
		
		//1 case = 20 meters
		//40 meters => 2 cases
		
		return meters/meterByCase;
	}
	
	public Double ValueByMetersToValueByCase(Double valueByMeters){
		
		//1 case = 20 meters
		//2 s/meters = 40 s/case 
		
		return valueByMeters*stepByDay;
	}
	
	public Double ValueByCaseToValueByMeters(Double valueByCase){
		
		//1 case = 20 meters
		//40 s/case = 2 s/meters   		
		return valueByCase/stepByDay;
	}
	

	
}
