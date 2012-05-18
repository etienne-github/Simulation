package simulation.entity;


import sim.engine.SimState;
import sim.engine.Steppable;

public class Grass extends Entity implements Eatable, Steppable {

	private static final long serialVersionUID = 1L;

	public static final double EATING_ENERGY = 8.0;
	public static final int LENGTH_EATEN = 20;
	public static final int MAX_LENGTH = 50;

	private int length;

	public Grass() {
		length = MAX_LENGTH;
	}
	
	public int getLength() {
		return length;
	}

	@Override
	public Double getEatingEnergy() {
		return EATING_ENERGY;
	}

	@Override
	public void step(SimState arg0) {
		if ( length < MAX_LENGTH ) {
			length++;
		}
	}

	@Override
	public void eaten() {
		if (!canBeEaten()) {
			System.out.println("Erreur : Impossible to eat this food");
		}

		length -= LENGTH_EATEN;
	}

	@Override
	public boolean canBeEaten() {
		if (length > LENGTH_EATEN) {
			return true;
		} else {
			return false;
		}
	}

}
