package preSimulationWindow;

import simulation.SimulationModel;
import utils.Constants;

public class ViewModel {

	SimulationModel simModel;
	SimProperties simProperties;
	
	public ViewModel(SimulationModel model) {
		simModel = model;
	}


	public Integer[] randomAnimals() {
		Integer[] array = new Integer[2];
		array[0] = (int) Math.ceil(Math.random() * Constants.MAX_LAPINS
				* Constants.COEF_LOUPS_LAPINS);
		array[1] = (int) Math.round(Math.random() * Constants.MAX_LAPINS);

		return array;
	}
	
	public void sendToModel(SimProperties properties) {
		simProperties = properties;
		simModel.setProperties(simProperties);
		simModel.launchView();
	}
}

