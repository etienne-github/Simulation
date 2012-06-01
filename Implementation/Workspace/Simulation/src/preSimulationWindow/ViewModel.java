package preSimulationWindow;

import java.util.HashMap;
import java.util.Map;

import simulation.SimulationModel;
import utils.Constants;
import client.RestFacilities;

public class ViewModel {

	private SimulationModel simModel;
	private SimProperties simProperties;
	private RestFacilities servRes;

	public ViewModel(SimulationModel model, RestFacilities serv) {
		simModel = model;
		servRes = serv;
	}

	// TODO randomAnimals
	public int randomAnimals() {
		return (int) Math.round(Math.random() * Constants.MAX_POP);
	}

	public void sendToModel(SimProperties properties) {
		simProperties = properties;
		simModel.setProperties(simProperties);
		simModel.launchView();
	}

	public RestFacilities getRestServer() {
		return servRes;
	}
}
