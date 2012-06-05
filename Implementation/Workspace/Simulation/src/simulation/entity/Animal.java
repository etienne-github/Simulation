package simulation.entity;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import simulation.SimulationModel;


public abstract class Animal extends Entity implements Eatable {

	private static final long serialVersionUID = 1L;
	
	private static final double FLEE_MOVE_COEF = 1.5;
	private static final Double MAX_ATTACK_AND_DEFEND = 100.0;
	
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
		super(simModel);
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double toStep(Double value) {
		return value / stepByDay;
	}
	
	public int toCase(Double value) {
		return (int) Math.round(value / caseByMeter);
	}
	
	// Main action

	public void step(SimState arg0) {
		x = simModel.getYard().getObjectLocation(this).x;
		y = simModel.getYard().getObjectLocation(this).y;
		
		isHidden = false;
		
		//System.out.println("myY  :" +getY());
		
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
		System.out.println(this.getType()+" #"+this.hashCode()+" dies !");
		simModel.getYard().remove(this);
		stoppable.stop();
	}
	
	
	// Eat and Attacked
	
	public void attacked() {
		System.out.println(this.getType()+" #"+this.hashCode()+" is attacked !");
		if(isUseHiddenDefense) {
			isHidden = true;
		} else {
			flee();
		}
	}
	
	private void flee() {
		System.out.println(this.getType()+" #"+this.hashCode()+" flees aways !");
		double random = Math.random() * 1000;
		double destinationX = this.x * (Math.cos(random) * toCase(movePoint) * FLEE_MOVE_COEF);
		double destinationY = this.x * (Math.sin(random) * toCase(movePoint) * FLEE_MOVE_COEF);
		
		setX((int) Math.round(destinationX));
		setY((int) Math.round(destinationY));
		simModel.getYard().setObjectLocation(this, (int)this.x, (int)this.y);
		
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
		System.out.println(this.getType()+" #"+this.hashCode()+" is eaten !");
		die();
	}
	
	// Other

	public String toString() {
		return "(x: " + getX() + ", y: " + getY() + ", age: " + age + ")";
	}
	
}
