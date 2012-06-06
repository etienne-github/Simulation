package simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import server.SpeciesStats;
import sim.util.Bag;
import sim.util.Int2D;
import simulation.entity.Animal;
import simulation.entity.Carnivorious;
import simulation.entity.Herbivorious;
import simulationWindow.SimulationView;

public class AnimalFactory {
	

	private SimulationModel simModel;
	Random myRandomGen = new Random();
	private SimulationView myView;
	
	public AnimalFactory(SimulationModel simModel, SimulationView gui) {
		this.simModel = simModel;
		myView = gui;

	}
	
	public Animal createAnimal(SpeciesStats speciesStats) {
		Animal animal;
		if (speciesStats.getIsHerbivorious()) {
			//System.out.println("h "+speciesStats.getSmellPoint()+" smellpoints et "+speciesStats.getVisionPoint()+" vision points");
			animal = new Herbivorious(speciesStats.getType(), simModel);
		} else {
			animal = new Carnivorious(speciesStats.getType(), simModel);
		}
		
		animal.setStepByDay(simModel.getStepByDay());
		animal.setCaseByMeter(simModel.getCaseByMeter());

		animal.setSmellPoint(speciesStats.getSmellPoint());
		animal.setVisionPoint(speciesStats.getVisionPoint());
		animal.setMovePoint(speciesStats.getMovePoint());
		System.out.println("MovePoint is "+speciesStats.getMovePoint());
		animal.setMaxLifetime(speciesStats.getMaxLifetime());
		animal.setMinimumWeightToDeath(speciesStats.getMinimumWeightToDeath());
		animal.setWeightConsumeByDay(speciesStats.getWeightConsumeByDay());
		animal.setMaxNbDaySafe(speciesStats.getMaxNbDaySafe());
		animal.setAttackPoint(speciesStats.getAttackPoint());
		animal.setDefendPoint(speciesStats.getDefendPoint());
		animal.setIsUseHiddenDefense(speciesStats.getIsUseHiddenDefense());
		
		animal.setAge(speciesStats.getInitAge());
		animal.setWeight(speciesStats.getInitWeight());
		animal.setIsHidden(false);
		
		
		if(speciesStats.getIsHerbivorious()){
			((Herbivorious)animal).initializePerception();
		}
		
		return animal;
	}
	
	public void createAnimalsFromBatch(ArrayList<SpeciesPop> speciesList){
		Int2D location;
		Iterator<SpeciesPop> it = speciesList.iterator();
		boolean animalSet=false;
		System.out.println("ANIMAL FACTORY : Starts treating batch #"+speciesList);
		while(it.hasNext()){
			SpeciesPop sP = it.next();
			System.out.println("ANIMAL FACTORY : Starts creating "+sP.getPopulation()+" "+sP.getStats().getNom()+".");
			for(int i=0;i<sP.getPopulation();i++){
				animalSet=false;
				location  = new Int2D(myRandomGen.nextInt(simModel.getYard().getWidth()),myRandomGen.nextInt(simModel.getYard().getHeight()));
				//while we didn't find an empty place the this animal
				while(!animalSet){
					//if the place is free
					if(!checkIfAnimalAtPosition(sP.getStats().getType(), location.x, location.y)){
						//set animal there
						Animal a = this.createAnimal(sP.getStats());
						simModel.getYard().setObjectLocation(a, location);
						myView.getYard().setPortrayalForObject(a, sP.portrayal);						
						a.setStoppable(simModel.schedule.scheduleRepeating(a));
						System.out.println("ANIMAL FACTORY : "+sP.getStats().getNom()+" #"+sP.hashCode()+" created at position "+location.toString());
						animalSet=true;
						
					//else check another position	
					}else{
						System.out.println("ANIMAL FACTORY : location "+location+" already occupied by species "+sP.getStats().getNom()+" looking now for another.");

						//check another location
						location= new Int2D(myRandomGen.nextInt(simModel.getYard().getWidth()),myRandomGen.nextInt(simModel.getYard().getHeight()));

					}
				}

			}
			
		}
		System.out.println("ANIMAL FACTORY : Finished treating a batch.");
		
	}
	
	public boolean checkIfAnimalAtPosition(String AnimalSoughtType, int x, int y){
		//initialize return value
		boolean found=false;
		
		//get object at position
		Bag b = simModel.getYard().getObjectsAtLocation(x,y);
		
		//parse them an search for the sought species
		Iterator it = b.iterator();
		while(it.hasNext()&&(!found)){
			Object o = it.next();
			if(o instanceof Animal){
				if(((Animal)o).getType().equals(AnimalSoughtType)){
					found = true;
				}
			}
		}
		return found;
	}
	
}
