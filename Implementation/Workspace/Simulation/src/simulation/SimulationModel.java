package simulation;
import preSimulationWindow.SimProperties;
import sim.display.Console;
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
import simulation.entity.Entity;
import simulation.entity.Grass;
import simulation.entity.Hare;
import simulationWindow.SimulationView;


@SuppressWarnings("serial")
public class SimulationModel extends SimState {

	private SparseGrid2D yard;
	private SimProperties simProperties;
	
	public SimulationModel(long seed) {
		super(seed);
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
		yard = new SparseGrid2D(simProperties.getGridWidth(), simProperties.getGridHeight());
		Entity.setGRID_SIZE_X(simProperties.getGridWidth());
		Entity.setGRID_SIZE_Y(simProperties.getGridHeight());
		
		//Add Grasses
		for(int i = 0 ; i < simProperties.getGridWidth(); i++){
			for(int j=0 ; j < simProperties.getGridHeight(); j++) {
				Grass grass = new Grass();
				grass.setX(i);
				grass.setY(j);
				yard.setObjectLocation(grass, grass.getX(), grass.getY());
				schedule.scheduleRepeating(grass);
			}
		}
		
		//add Hare
		for(int i = 0 ; i < simProperties.getHareNumber() ; i++) {
			
		}
	}
	
}
