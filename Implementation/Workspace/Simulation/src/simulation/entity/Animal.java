package simulation.entity;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
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
	protected Boolean isUseHiddenDefense;
	
	protected Double age;
	protected Double weight;
	protected Boolean isHidden;
	protected Stoppable stoppable;
	
	public Animal(String type, SimulationModel simModel) {
		this.type = type;
	}
	
	// Main action
	
	@Override
	public void step(SimState arg0) {
		isHidden = false;
		
		action();
		
		weight -= weightConsumeByStep;
		age++;
		checkAlive();
	}
	
	public abstract void action();
	
	
	public void checkAlive() {
		if (weight < minimumWeightToDeath || age > maxLifetime) {
			die();
		}
	}
	
	protected void die() {
		simModel.getYard().remove(this);
		stoppable.stop();
	}
	
	
	// Eat and Attacked
	
	public void attacked() {
		if(isUseHiddenDefense) {
			isHidden = true;
		} else {
			flee();
		}
	}
	
	private void flee() {
		double random = Math.random() * 1000;
		double destinationX = getX() * (Math.cos(random) * movePoint * 1.5);
		double destinationY = getY() * (Math.sin(random) * movePoint * 1.5);
		
		setX((int) Math.round(destinationX));
		setY((int) Math.round(destinationY));
		simModel.getYard().setObjectLocation(this, getX(), getY());
		
		weight -= weightConsumeByStep;
	}
	
	public Double getEatingEnergy() {
		return weight;
	}
	
	public boolean canBeEaten() {
		return !isHidden;
	}
	
	@Override
	public void eaten() {
		die();
	}
	
	// Other

	public String toString() {
		return "(x: " + getX() + ", y: " + getY() + ", age: " + age + ")";
	}
	
}
