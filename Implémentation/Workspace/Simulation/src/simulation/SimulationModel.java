package simulation;
import java.util.HashMap;
import java.util.Map;

import sim.engine.SimState;


@SuppressWarnings("serial")
public class SimulationModel extends SimState {

	Map<String, Object> simProperties;
	
	public SimulationModel(long seed) {
		super(seed);
		simProperties = new HashMap<String, Object>();
	}
	
	public Object getProperty(String key) {
		Object obj = simProperties.get(key);
		
		return obj;
	}
	
	public Map<String, Object> getProperties() {
		return simProperties;
	}

	public void setProperties(Map<String, Object> properties) {
		System.out.println("Simulation properties : ");
		for (String key : properties.keySet()) {
			simProperties.put(key, properties.get(key));
			System.out.println(key + " : " + simProperties.get(key));
		}
	}
}
