package preSimulationWindow;

import java.util.HashMap;
import java.util.Map;

import simulation.SimulationModel;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

public class ViewModel {

	SimulationModel simModel;
	Map<String, Object> simProperties;
	
	public ViewModel(SimulationModel model) {
		simModel = model;
		simProperties = new HashMap<String, Object>();
	}


	public IntegerArray randomAnimals() {
		IntegerArray array = new IntegerArray(2);
		array.add((int) Math.ceil(Math.random() * Constants.MAX_LAPINS
				* Constants.COEF_LOUPS_LAPINS));
		array.add((int) Math.round(Math.random() * Constants.MAX_LAPINS));

		return array;
	}

	public void setProperties(Map<String, Object> map) {
		for (String key : map.keySet()) {
			simProperties.put(key, map.get(key));
		}
	}
	
	public void setProperty(String key, Object obj) {
		simProperties.put(key, obj);
	}
	
	public void sendToModel(Map<String, Object> properties) {
		setProperties(properties);
		simModel.setProperties(simProperties);
	}
}
