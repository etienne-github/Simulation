package main;

import preSimulationWindow.View;
import preSimulationWindow.ViewModel;
import simulation.SimulationModel;

public class Main {

	public static void main(String[] args) {
		SimulationModel simModel = new SimulationModel(0);
		ViewModel viewModel = new ViewModel(simModel);
		View v = new View(viewModel);
		
		
		v.setVisible(true);

	}

}
