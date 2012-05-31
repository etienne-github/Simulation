package simulation.entity;

import java.util.ArrayList;
import java.util.Iterator;

import sim.field.grid.SparseGrid2D;
import sim.util.Bag;
import simulation.SimulationModel;

public class Carnivorious extends Animal {

	private static final long serialVersionUID = 1L;

	private ArrayList<String> eatableFoodList;
	private ArrayList<Animal> detectedFoodList;
	private Entity randomDestination;

	public Carnivorious(String type, SimulationModel simModel) {
		super(type, simModel);
		eatableFoodList = new ArrayList<String>();
		detectedFoodList = new ArrayList<Animal>();
	}

	// Main action

	public void action() {
		SparseGrid2D yard = simModel.getYard();
		detectFood(yard);
		Animal nearbyFood = (Animal) getNearbyFood();
		if (weight < weightConsumeByStep * maxNbStepSafe) {
			// Hungry
			if (nearbyFood != null && isSameLocation(nearbyFood)) {
				// Eat nearby food
				attack(nearbyFood);
			} else if (nearbyFood != null) {
				// Move to food
				moveTo(yard, nearbyFood);
			} else {
				moveRandom(yard);
			}
		} else {
			// Not Hungry
			moveRandom(yard);
		}
	}

	// Smell and vision

	@SuppressWarnings("rawtypes")
	private void detectFood(SparseGrid2D yard) {
		Double perceptionPoint = Math.max(smellPoint, visionPoint);

		// Remove old detected Food
		Iterator<Animal> detectedFoodIterator = detectedFoodList.iterator();
		while (detectedFoodIterator.hasNext()) {
			Entity food = detectedFoodIterator.next();
			if (this.getShortestDistanceToEntity(food) < perceptionPoint) {
				detectedFoodIterator.remove();
			}
		}

		// Add new detected Food
		Bag result = new Bag();
		// TODO vérifier le fonctionnement de la méthode NeighborsMaxDistance
		yard.getNeighborsMaxDistance(getX(), getY(),
				(int) Math.round(perceptionPoint), true, result, null, null);
		Iterator resultIterator = result.iterator();
		while (resultIterator.hasNext()) {
			Object objectResult = resultIterator.next();
			if (canEat((Animal) objectResult)) {
				detectedFoodList.add((Animal) objectResult);
			}
		}
	}

	private Entity getNearbyFood() {
		Entity nearbyFood = null;
		double distanceNearbyFood = 0;
		Iterator<Animal> detectedFoodIterator = detectedFoodList.iterator();
		while (detectedFoodIterator.hasNext()) {
			Entity foodResult = detectedFoodIterator.next();
			if (nearbyFood == null
					|| distanceNearbyFood > this
							.getShortestDistanceToEntity(foodResult)) {
				nearbyFood = foodResult;
				distanceNearbyFood = this.getShortestDistanceToEntity(nearbyFood);
			}
		}

		return nearbyFood;
	}

	// Move

	private Double moveTo(SparseGrid2D yard, Entity destination) {
		Double move = movePoint;
		while (move > 0 && !isSameLocation(destination)) {
			int dX = getXShortestDirection(destination);
			if (dX > 0)
				setX(getX() + 1);
			else if (dX < 0)
				setX(getX() - 1);
			setX(getX() % Entity.getGRID_SIZE_X());

			int dY = getYShortestDirection(destination);
			if (dY > 0)
				setY(getY() + 1);
			else if (dY < 0)
				setY(getY() - 1);
			setY(getY() % Entity.getGRID_SIZE_Y());

			yard.setObjectLocation(this, getX(), getY());
			move--;
		}
		return (movePoint - move);
	}

	private Double moveRandom(SparseGrid2D yard) {
		if (randomDestination == null || randomDestination.isSameLocation(this)) {
			randomDestination = new Entity();
			randomDestination.setX((int) Math.round(Math.random()
					* Entity.getGRID_SIZE_X()));
			randomDestination.setY((int) Math.round(Math.random()
					* Entity.getGRID_SIZE_Y()));
		}
		return moveTo(yard, randomDestination);
	}

	// Attack and Eat
	
	private void attack(Animal food) {
		Double attack = this.attackPoint - food.defendPoint;
		Double randomAttack = Math.random() * 100;
		if ( randomAttack > attack ) {
			eat(food);
		} else {
			attacked();
		}
	}

	private void eat(Animal food) {
		weight += food.getEatingEnergy();
		food.eaten();
	}

	protected boolean canEat(Animal animal) {
		return (eatableFoodList.contains(animal.type) && animal.canBeEaten());
	}

}
