package simulation.entity;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import simulation.SimulationModel;


public abstract class Animal extends Entity implements Steppable, Eatable {

	private static final long serialVersionUID = 1L;
	
	private static final double FLEE_MOVE_COEF = 1.5;
	private static final Double MAX_ATTACK_AND_DEFEND = 100.0;
	
	protected SimulationModel simModel;
	protected String type;
	protected Double stepByDay;
	protected Double caseByMeter;	
	
	protected Double smellPoint;
	protected Double visionPoint;
	protected Double movePoint;
	protected Double maxLifetime;
	protected Double minimumWeightToDeath;
	protected Double weightConsumeByDay;
	protected Double maxNbDaySafe;
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
	
	//Getter and Setter
	public void setStepByDay(Double stepByDay) {
		this.stepByDay = stepByDay;
	}

	public void setCaseByMeter(Double caseByMeter) {
		this.caseByMeter = caseByMeter;
	}

	public void setSmellPoint(Double smellPoint) {
		this.smellPoint = smellPoint;
	}

	public void setVisionPoint(Double visionPoint) {
		this.visionPoint = visionPoint;
	}

	public void setMovePoint(Double movePoint) {
		this.movePoint = movePoint;
	}

	public void setMaxLifetime(Double maxLifetime) {
		this.maxLifetime = maxLifetime;
	}

	public void setMinimumWeightToDeath(Double minimumWeightToDeath) {
		this.minimumWeightToDeath = minimumWeightToDeath;
	}

	public void setWeightConsumeByDay(Double weightConsumeByDay) {
		this.weightConsumeByDay = weightConsumeByDay;
	}

	public void setMaxNbDaySafe(Double maxNbDaySafe) {
		this.maxNbDaySafe = maxNbDaySafe;
	}

	public void setAttackPoint(Double attackPoint) {
		this.attackPoint = attackPoint;
	}

	public void setDefendPoint(Double defendPoint) {
		this.defendPoint = defendPoint;
	}

	public void setIsUseHiddenDefense(Boolean isUseHiddenDefense) {
		this.isUseHiddenDefense = isUseHiddenDefense;
	}

	public void setAge(Double age) {
		this.age = age;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}

	public void setStoppable(Stoppable stoppable) {
		this.stoppable = stoppable;
	}
	
	//Conversion methods
	
	public Double toStep(Double value) {
		return value / stepByDay;
	}
	
	public int toCase(Double value) {
		return (int) Math.round(value / caseByMeter);
	}
	
	// Main action
	
	@Override
	public void step(SimState arg0) {
		isHidden = false;
		
		action();
		
		weight -= toStep(weightConsumeByDay);
		age += toStep(1.0);
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
		double destinationX = getX() * (Math.cos(random) * toCase(movePoint) * FLEE_MOVE_COEF);
		double destinationY = getY() * (Math.sin(random) * toCase(movePoint) * FLEE_MOVE_COEF);
		
		setX((int) Math.round(destinationX));
		setY((int) Math.round(destinationY));
		simModel.getYard().setObjectLocation(this, getX(), getY());
		
		weight -= toStep(weightConsumeByDay);
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
