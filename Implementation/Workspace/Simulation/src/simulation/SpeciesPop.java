package simulation;

import server.SpeciesStats;

public class SpeciesPop {

	SpeciesStats stats;
	int population;
	
	public SpeciesPop() {
		
	}

	public SpeciesStats getStats() {
		return stats;
	}

	public void setStats(SpeciesStats stats) {
		this.stats = stats;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}
	
}
