package simulation.entity;

import simulation.SimulationModel;

public class Herbivorious extends Animal {

	private static final long serialVersionUID = 1L;

	public Herbivorious(String type, SimulationModel simModel) {
		super(type, simModel);
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canBeEaten() {
		// TODO retourner si disponible au mangeage
		return true;
	}

}
