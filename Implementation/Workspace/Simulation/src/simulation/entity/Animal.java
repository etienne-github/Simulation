package simulation.entity;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.field.grid.SparseGrid2D;
import simulation.SimulationModel;


public abstract class Animal extends Entity implements Steppable, Eatable {

	private static final long serialVersionUID = 1L;
	
	private static final Double MAX_ATTACK_AND_DEFEND = 100.0;
	
	protected SimulationModel simModel;
	protected String type;

	protected Double smellPoint;
	protected Double visionPoint;
	protected Double movePoint;
	protected Double maxLifetime;
	protected Double minimumWeightToDeath;
	protected Double weightConsumeByStep;
	protected Double maxNbStepSafe;
	protected Double attackPoint;
	protected Double defendPoint;
	protected Boolean defendIsHidden;
	
	protected Double age;
	protected Double weight;
	protected Stoppable stoppable;
	
	public Animal(String type, SimulationModel simModel) {
		this.type = type;
	}
	
	// Main action
	
	@Override
	public void step(SimState arg0) {
		action();
	}
	
	public abstract void action();
	
	
	// TODO Gerer la durée de vie et la mort des animaux
	
	protected void die() {
		simModel.getYard().remove(this);
		stoppable.stop();
	}
	
	
	// Eat and Attacked
	
	public void attacked() {
		// TODO fuir ou se cacher
	}
	
	public Double getEatingEnergy() {
		return weight;
	}
	
	public abstract boolean canBeEaten();
	
	@Override
	public void eaten() {
		die();
	}
	
	public void hide(){
		//TODO implémentation
	}
	
	public void flee(){
		//TODO implémentation
	}
	
}
