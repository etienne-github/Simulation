package simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import server.SpeciesStats;
import sim.portrayal.simple.OvalPortrayal2D;
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
	private StatsManager myManager;
	private ArrayList<SpeciesStats> speciesToRenew;
	private HashMap<String,Float> speciesRenewBuffer;
	private HashMap<String,OvalPortrayal2D> speciesPortrayals;
	
	public AnimalFactory(SimulationModel simModel, SimulationView gui, StatsManager myManager) {
		this.simModel = simModel;
		myView = gui;
		this.myManager=myManager;
		speciesToRenew=new ArrayList<SpeciesStats>();
		speciesPortrayals =  new HashMap<String,OvalPortrayal2D>();
		speciesRenewBuffer=new HashMap<String,Float>();

	}
	
	public Animal createAnimal(SpeciesStats speciesStats) {
		Animal animal;
		if (speciesStats.getIsHerbivorious()) {
			//System.out.println("h "+speciesStats.getSmellPoint()+" smellpoints et "+speciesStats.getVisionPoint()+" vision points");
			animal = new Herbivorious(speciesStats.getType(), simModel);
		} else {
			animal = new Carnivorious(speciesStats.getType(), simModel);
			((Carnivorious) animal).setEatableFoodList(speciesStats.getEatableFoodList());
		}
		
		animal.setStepByDay(simModel.getStepByDay());
		animal.setMeterByCase(simModel.getMeterByCase());

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
			myManager.addStatForSpecies(sP.getStats().getType());
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
						a.getSupport().addPropertyChangeListener(myManager);
						simModel.getYard().setObjectLocation(a, location);
						myView.getYard().setPortrayalForObject(a, sP.portrayal);						
						a.setStoppable(simModel.schedule.scheduleRepeating(a));
						a.getSupport().firePropertyChange("was_born", a.getType(), null);
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
			if(!alreadyRenewed(sP.getStats().getType())){
				scheduleRenewOf(sP.getStats());
				speciesPortrayals.put(sP.getStats().getType(), sP.portrayal);
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
	
	
	
	public void RenewFromBatch(ArrayList<SpeciesPop> speciesList){
		Int2D location;
		Iterator<SpeciesPop> it = speciesList.iterator();
		boolean animalSet=false;
		System.out.println("ANIMAL RENEW : Starts treating batch #"+speciesList);
		while(it.hasNext()){
			SpeciesPop sP = it.next();
			System.out.println("ANIMAL RENEW : Starts creating "+sP.getPopulation()+" "+sP.getStats().getNom()+".");
			for(int i=0;i<sP.getPopulation();i++){
				animalSet=false;
				location  = new Int2D(myRandomGen.nextInt(simModel.getYard().getWidth()),myRandomGen.nextInt(simModel.getYard().getHeight()));
				//while we didn't find an empty place the this animal
				while(!animalSet){
					//if the place is free
					if(!checkIfAnimalAtPosition(sP.getStats().getType(), location.x, location.y)){
						//set animal there
						Animal a = this.createAnimal(sP.getStats());
						a.getSupport().addPropertyChangeListener(myManager);
						simModel.getYard().setObjectLocation(a, location);
						myView.getYard().setPortrayalForObject(a, speciesPortrayals.get(sP.getStats().getType()));						
						a.setStoppable(simModel.schedule.scheduleRepeating(a));
						a.getSupport().firePropertyChange("was_born", a.getType(), null);
						System.out.println("ANIMAL RENEW : "+sP.getStats().getNom()+" #"+sP.hashCode()+" created at position "+location.toString());
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
		System.out.println("ANIMAL RENEW : Finished treating a batch.");
		
	}
	
	
	
	public boolean alreadyRenewed(String type){
		boolean retVal=false;
		Iterator<SpeciesStats> it = speciesToRenew.iterator();
		while(it.hasNext()){
			if(it.next().getType().equals(type)){
				retVal=true;
			}
		}
		return retVal;
	}
	
	
	public void scheduleRenewOf(SpeciesStats speciesStats){
		speciesToRenew.add(speciesStats);
		speciesRenewBuffer.put(speciesStats.getType(), 0f);
	}
	
	
	
	
	
	
	
	public void renewSpecies(){
		ArrayList<SpeciesPop> sPL = new ArrayList<SpeciesPop>(); 
		Iterator<SpeciesStats> it = speciesToRenew.iterator();
		int intPart;
		float decPart;
		float buffered;
		double p;
		float bR;
		SpeciesStats s;
		
		while(it.hasNext()){
			s = it.next();
			p = myManager.getPopulationOfSpecies(s.getType());
			bR = (float) (simModel.ValueByDayToValueByStep(s.getBirthRateByDay())*p);
			
			intPart = (int) Math.floor(bR);
			
			decPart = bR-intPart;
			buffered = speciesRenewBuffer.get(s.getType());
			
			System.out.println("pop is "+ p +"bR "+bR+" intP "+intPart+" decPart "+decPart+" buffered "+buffered);
			
			buffered+=decPart;
			
			
			System.out.println("buffered : "+buffered);
			
			if(buffered>=1){
				intPart++;
				buffered-=1;
				System.out.println(" adj intP "+intPart+"  buffered "+buffered);

			}
			
			
			
			speciesRenewBuffer.put(s.getType(),buffered);
			
			if(intPart>0){
				sPL.add(new SpeciesPop(s, intPart));
			}
			
		}
		this.RenewFromBatch(sPL);
	}
	
}
