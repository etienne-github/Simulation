package preSimulationWindow;

import java.util.HashMap;
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
	private HashMap<String, String> nameList; // liste des correspondances nom/type

	public ViewModel(SimulationModel model, RestFacilities serv) {
		simModel = model;
		servRes = serv;
		nameList = new HashMap<String, String>();
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
		}
		System.out.println("Envoi de la liste "+ simProperties.getSpeciesList() +" au modele");
		simModel.setProperties(simProperties);
		simModel.launchView();
	}

	public RestFacilities getRestServer() {
		return servRes;
	}
	
	public void setNameList() {
		for (String s : getSpeciesList()) {
			String type = servRes.getSpecies(s).getType();
			nameList.put(s, type);
		}
	}
	
	public HashMap<String, String> getNameList() {
		return nameList;
	}

	public String[] getSpeciesList() {
		String str = servRes.getSpeciesList();
		return str.substring(1, str.length() - 1).split(", ");
		
	}
}
