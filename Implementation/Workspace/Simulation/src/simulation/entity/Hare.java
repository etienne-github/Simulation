package simulation.entity;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.field.grid.SparseGrid2D;
import simulation.SimulationModel;

@SuppressWarnings("serial")
public class Hare extends Animal implements Eatable, Steppable {

	public static final double MAX_ENERGY = 80;
	public static final double ENERGY_TO_BE_HUNGRY = 60 ;
	public static final double SMELL_POINT_BASE = 3;
	public static final double SMELL_POINT_BONUS = 2;
	public static final double VISION_POINT_BASE = 7;
	public static final double VISION_POINT_BONUS = 2;
	public static final double MOVE_POINT_BASE = 10;
	public static final double MOVE_POINT_BONUS = 5;
	
	private Stoppable stoppable;

	public Hare() {
		setEnergy(MAX_ENERGY);
		setSmellPoint(SMELL_POINT_BASE + (Math.random()*SMELL_POINT_BONUS));
		setVisionPoint(VISION_POINT_BASE + (Math.random()*VISION_POINT_BONUS));
		setMovePoint(MOVE_POINT_BASE + (Math.random()*MOVE_POINT_BONUS));
	}

	@Override
	public void step(SimState simState) {
		SimulationModel model = (SimulationModel) simState;
		SparseGrid2D yard = model.getYard();
		double energySpend = 1;
		if (getEnergy() < 0) {
			die(yard);
			System.out.println("Mort du lapin à " + simState.schedule.getSteps() + " : "
					+ this);
		} else {
			detectFood(yard);
			Grass nearbyFood = (Grass) getNearbyFood();
			if (getEnergy() < ENERGY_TO_BE_HUNGRY) {
				if (nearbyFood != null && isSameLocation(nearbyFood)) {
					// Eat nearby food
					eat(nearbyFood);
				} else if (nearbyFood != null) {
					// Move to food
					energySpend += moveTo(yard, nearbyFood);
				} else {
					energySpend += moveRandom(yard);
				}
			} else {
				energySpend += moveRandom(yard);
			}
			setEnergy(getEnergy() - energySpend);
		}
	}

	private void die(SparseGrid2D yard) {
		yard.remove(this);
		stoppable.stop();
	}

	public void setStoppable(Stoppable s) {
		stoppable = s;
	}

	@Override
	public Double getEatingEnergy() {
		// TODO Améliorer eatingEnergy pour le Lapin ?
		return getEnergy();
	}

	@Override
	protected boolean canEat(Object food) {
		if (food instanceof Grass) {
			Grass grass = (Grass) food;
			if (grass.canBeEaten()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canBeEaten() {
		return true;
	}

	@Override
	public void eaten() {
		setEnergy(0.0);
	}

	public String toString() {
		return "Rabbit "+super.toString();
	}

}
