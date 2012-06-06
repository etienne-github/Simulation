package main;

import client.RestFacilities;
import preSimulationWindow.View;
import preSimulationWindow.ViewModel;
import simulation.AnimalFactory;
import simulation.SimulationModel;

public class Main {

	public static void main(String[] args) {
		RestFacilities servRes = new RestFacilities();
		SimulationModel simModel = new SimulationModel(0);
		ViewModel viewModel = new ViewModel(simModel, servRes);
		View v = new View(viewModel);
		
		v.setVisible(true);

	}

}
