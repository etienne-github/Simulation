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
	@SuppressWarnings("unused")
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
		if (weight < (minimumWeightToDeath + weightConsumeByDay * maxNbDaySafe)) {
			// Hungry
			System.out.println(this.getType()+" #"+this.hashCode()+" is hungry");
			if (nearbyFood != null && isSameLocation(nearbyFood)) {
				// Eat nearby food
				attack(nearbyFood);
				System.out.println(this.getType()+" #"+this.hashCode()+" and is on food");
			} else if (nearbyFood != null) {
				// Move to food
				System.out.println(this.getType()+" #"+this.hashCode()+" and sees food");
				Integer[] coor = new Integer[2];
				coor[0]=(int) nearbyFood.getX();
				coor[1]=(int) nearbyFood.getY();
				
				//if close move and attack
				if(getShortestDistanceToPoint(coor[0], coor[1])<(MeterToCase(movePoint*0.01))){
					System.out.println(this.getType()+" #"+this.hashCode()+" food is close enough ("+MeterToCase(movePoint*0.0005)+") to attack");
					moveTo(yard, coor);
					attack(nearbyFood);
				}else{
					//just move
					moveTo(yard, coor);
				}
				
			} else {
				System.out.println(this.getType()+" #"+this.hashCode()+" and doesn't see food");
				moveRandom(yard);
			}
		} else {
			// Not Hungry
			//System.out.println(this.getType()+" #"+this.hashCode()+" is not hungry");
			moveRandom(yard);
		}
	}

	// Smell and vision

	@SuppressWarnings("rawtypes")
	private void detectFood(SparseGrid2D yard) {
		Double perceptionPoint = (double) MeterToCase(Math.max(smellPoint, visionPoint));

		detectedFoodList.clear();
		
		/*
		// Remove old detected Food
		Iterator<Animal> detectedFoodIterator = detectedFoodList.iterator();
		while (detectedFoodIterator.hasNext()) {
			Entity food = detectedFoodIterator.next();
			if (this.getShortestDistanceToEntity(food) < perceptionPoint) {
				detectedFoodIterator.remove();
			}
		}*/

		// Add new detected Food
		Bag result = new Bag();
		// TODO vérifier le fonctionnement de la méthode NeighborsMaxDistance
		yard.getNeighborsMaxDistance((int)getX(), (int)getY(),
				(int) Math.round(perceptionPoint), true, result, null, null);
		Iterator resultIterator = result.iterator();
		while (resultIterator.hasNext()) {
			Object objectResult = resultIterator.next();
			if(objectResult instanceof Animal){
				if (canEat((Animal) objectResult)) {
					detectedFoodList.add((Animal) objectResult);
				}
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

	private Double moveRandom(SparseGrid2D yard) {
		//System.out.println(this.getType()+" #"+this.hashCode()+" moves randomly");
		Integer[] randomDestination = new Integer[2];

			randomDestination[0]=(int) Math.round(Math.random()
					* Entity.getGRID_SIZE_X());
			randomDestination[1]=(int) Math.round(Math.random()
					* Entity.getGRID_SIZE_Y());
		return moveTo(yard, randomDestination);
	}

	// Attack and Eat
	
	private void attack(Animal food) {
		System.out.println(this.getType()+" #"+this.hashCode()+" attacks "+food.getType()+" #"+food.hashCode());
		Double attack = (double)60/*this.attackPoint - food.defendPoint*/;
		Double randomAttack = simModel.random.nextDouble() * 100;
		
		if ( randomAttack < attack ) {
			eat(food);
		} else {
			food.attacked();
		}

	}

	private void eat(Animal food) {
		System.out.println(this.getType()+" #"+this.hashCode()+" eats "+food.getType()+" #"+food.hashCode());
		this.getSupport().firePropertyChange("ate",this.getType(), food.getEatingEnergy());
		weight += food.getEatingEnergy();
		this.detectedFoodList.remove(food);
		food.eaten();
	}

	protected boolean canEat(Animal animal) {
		return (eatableFoodList.contains(animal.type) && animal.canBeEaten());
	}

	public void setEatableFoodList(ArrayList<String> eatableFoodList) {
		this.eatableFoodList=eatableFoodList;
	}

}
