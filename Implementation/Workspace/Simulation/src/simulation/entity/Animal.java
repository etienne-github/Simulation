package simulation.entity;

import java.util.ArrayList;
import java.util.Iterator;

import sim.field.grid.SparseGrid2D;
import sim.util.Bag;

public abstract class Animal extends Entity {

	public static final int SEX_F = 1;
	public static final int SEX_M = 2;

	private Double smellPoint;
	private Double visionPoint;
	private Double movePoint;
	private Double reproductionPoint;
	private Double lifetime;

	private Integer sex;
	private Double age;
	private Double energy;

	private ArrayList<Entity> detectedFoodList;
	private Entity randomDestination;

	public Animal() {
		detectedFoodList = new ArrayList<Entity>();
	}

	// Get and Set

	protected Double getSmellPoint() {
		return smellPoint;
	}

	protected void setSmellPoint(Double smellPoint) {
		this.smellPoint = smellPoint;
	}

	protected Double getVisionPoint() {
		return visionPoint;
	}

	protected void setVisionPoint(Double visionPoint) {
		this.visionPoint = visionPoint;
	}

	protected Double getMovePoint() {
		return movePoint;
	}

	protected void setMovePoint(Double movePoint) {
		this.movePoint = movePoint;
	}

	protected Double getReproductionPoint() {
		return reproductionPoint;
	}

	protected void setReproductionPoint(Double reproductionPoint) {
		this.reproductionPoint = reproductionPoint;
	}

	protected Double getLifetime() {
		return lifetime;
	}

	protected void setLifetime(Double lifetime) {
		this.lifetime = lifetime;
	}

	protected Integer getSex() {
		return sex;
	}

	protected void setSex(Integer sex) {
		this.sex = sex;
	}

	protected Double getAge() {
		return age;
	}

	protected void setAge(Double age) {
		this.age = age;
	}

	public Double getEnergy() {
		return energy;
	}

	public void setEnergy(Double energy) {
		this.energy = energy;
	}

	protected ArrayList<Entity> getDetectedFoodList() {
		return detectedFoodList;
	}

	protected Entity getRandomDestination() {
		return randomDestination;
	}

	protected void setRandomDestination(Entity randomDestination) {
		this.randomDestination = randomDestination;
	}

	// Smell and vision

	@SuppressWarnings("rawtypes")
	protected void detectFood(SparseGrid2D yard) {
		Double perceptionPoint = Math.max(smellPoint, visionPoint);

		// Remove old detected Food
		Iterator<Entity> detectedFoodIterator = detectedFoodList.iterator();
		while (detectedFoodIterator.hasNext()) {
			Entity food = detectedFoodIterator.next();
			if (this.getShortestDistance(food) < perceptionPoint) {
				detectedFoodIterator.remove();
			}
		}

		// Add new detected Food
		Bag result = new Bag();
		// TODO vérifier le fonctionnement de la méthode : NeighborsMaxDistance
		yard.getNeighborsMaxDistance(getX(), getY(),
				(int) Math.round(perceptionPoint), true, result, null, null);
		Iterator resultIterator = result.iterator();
		while (resultIterator.hasNext()) {
			Object objectResult = resultIterator.next();
			if (canEat(objectResult)) {
				detectedFoodList.add((Entity) objectResult);
			}
		}
	}

	protected Entity getNearbyFood() {
		Entity nearbyFood = null;
		double distanceNearbyFood = 0;
		Iterator<Entity> detectedFoodIterator = detectedFoodList.iterator();
		while (detectedFoodIterator.hasNext()) {
			Entity foodResult = detectedFoodIterator.next();
			if (nearbyFood == null
					|| distanceNearbyFood > this
							.getShortestDistance(foodResult)) {
				nearbyFood = foodResult;
				distanceNearbyFood = this.getShortestDistance(nearbyFood);
			}
		}

		return nearbyFood;
	}

	// Move

	protected Double moveTo(SparseGrid2D yard, Entity destination) {
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
		return ( movePoint - move );
	}

	protected Double moveRandom(SparseGrid2D yard) {
		if (randomDestination == null || randomDestination.isSameLocation(this)) {
			randomDestination = new Entity();
			randomDestination.setX((int) Math.round(Math.random()
					* Entity.getGRID_SIZE_X()));
			randomDestination.setY((int) Math.round(Math.random()
					* Entity.getGRID_SIZE_Y()));
		}
		return moveTo(yard, randomDestination);
	}

	// Eat

	protected void eat(Eatable food) {
		energy += food.getEatingEnergy();
		food.eaten();
	}

	protected abstract boolean canEat(Object food);

	// Reproduction
	
	//TODO faire la reproduction des animaux
	
	
	// Reproduction
	
	// TODO Gerer la durée de vie et la mort des animaux

	// Other

	public String toString() {
		return "(x: " + getX() + ", y: " + getY() + ", age: " + age + ")";
	}

}
