package simulation;

import java.util.ArrayList;

import server.SpeciesStats;


public class SimProperties {

	private Integer gridWidth;
	private Integer gridHeight;
	private ArrayList<SpeciesPop> speciesList;
	
	public SimProperties() {
		speciesList = new ArrayList<SpeciesPop>();
	}

	public SimProperties(SimProperties properties) {
		gridWidth = new Integer(properties.getGridWidth());
		gridHeight = new Integer(properties.getGridHeight());
		speciesList = new ArrayList<SpeciesPop>();
	}
	
	public Integer getGridHeight() {
		return gridHeight;
	}
	
	public Integer getGridWidth() {
		return gridWidth;
	}

	public ArrayList<SpeciesPop> getSpeciesList() {
		return speciesList;
	}

	public int getSpeciesPop(int idx) {
		return speciesList.get(idx).getPopulation();
	}
	
	public int getSpeciesPop(String name) {
		for (SpeciesPop pop : speciesList) {
			if (pop.getStats().getNom().equals(name))
			{
				return pop.getPopulation();
			}
		}
		return -1;
	}
	
	public String getSpeciesName(int idx) {
		return speciesList.get(idx).getStats().getNom();
	}
	
	public SpeciesStats getSpeciesStats(int idx) {
		return speciesList.get(idx).getStats();
	}
	
	public SpeciesStats getSpeciesStats(String name) {
		for (SpeciesPop pop : speciesList) {
			if (pop.getStats().getNom().equals(name))
			{
				return pop.getStats();
			}
		}
		return null;
	}
	
	public void setSpeciesList(ArrayList<SpeciesPop> speciesList) {
		this.speciesList = speciesList;
	}

	public void setGridHeight(Integer gridHeight) {
		this.gridHeight = gridHeight;
	}
	
	public void setGridWidth(Integer gridWidth) {
		this.gridWidth = gridWidth;
	}
	
	public void addSpecies(SpeciesPop species) {
		speciesList.add(species);
	}
	
	public void addSpecies(ArrayList<SpeciesPop> species) {
		speciesList.addAll(species);
	}
	
	public void addSpecies(SpeciesStats stats, int pop) {
		speciesList.add(new SpeciesPop(stats, pop));
	}
}
