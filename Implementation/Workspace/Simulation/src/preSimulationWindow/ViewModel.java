package preSimulationWindow;

import java.util.Map;

import server.SpeciesStats;
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

		for (String key : speciesList.keySet()) {
			SpeciesStats stats = servRes.getSpecies(key);
			properties.addSpecies(stats, speciesList.get(key));
			System.out.println("Ajout de l'espece : " + stats.getNom() + " avec une population de " + speciesList.get(key));
		//	System.out.println("a "+stats.getSmellPoint()+" smellpoints et "+stats.getVisionPoint()+" vision points");
		}
		System.out.println("Envoie de la liste "+simProperties.getSpeciesList()+" au model");
		simModel.setProperties(simProperties);
		simModel.launchView();
	}

	public RestFacilities getRestServer() {
		return servRes;
	}
}
