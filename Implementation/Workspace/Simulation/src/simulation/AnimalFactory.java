package simulation;

import server.SpeciesStats;
import simulation.entity.Animal;
import simulation.entity.Carnivorious;
import simulation.entity.Herbivorious;

public class AnimalFactory {

	private SimulationModel simModel;
	
	public AnimalFactory(SimulationModel simModel) {
		this.simModel = simModel;
	}
	
	public Animal createAnimal(SpeciesStats speciesStats) {
		Animal animal;
		if (speciesStats.getIsHerbivorious()) {
			animal = new Herbivorious(speciesStats.getType(), simModel);
		} else {
			animal = new Carnivorious(speciesStats.getType(), simModel);
		}
		
		animal.setStepByDay(simModel.getStepByDay());
		animal.setCaseByMeter(simModel.getCaseByMeter());
		
		animal.setSmellPoint(speciesStats.getSmellPoint());
		animal.setVisionPoint(speciesStats.getVisionPoint());
		animal.setMovePoint(speciesStats.getMovePoint());
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
		
		return animal;
	}
	
}
