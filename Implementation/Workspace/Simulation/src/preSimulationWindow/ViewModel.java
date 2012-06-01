package preSimulationWindow;

import java.util.Map;

import simulation.SimProperties;
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

	public int randomAnimals() {
		return (int) Math.round(Math.random() * Constants.MAX_POP);
	}

	// TODO
	public void sendToModel(Map<String, Integer> speciesList,
			SimProperties properties) {
		simProperties = properties;

		// TODO : remplir le ArrayList<SpeciesPop> de simProperties

		// Premiere etape : boucler pour recuperer les stats
		
		// Deuxieme etape : ajouter la liste a simProperties
		
		// Troisieme etape : verification avec des affichages console

		simModel.setProperties(simProperties);
		simModel.launchView();
	}

	public RestFacilities getRestServer() {
		return servRes;
	}
}
