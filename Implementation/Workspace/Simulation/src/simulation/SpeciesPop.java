package simulation;

import java.awt.Color;

import server.SpeciesStats;
import sim.portrayal.simple.OvalPortrayal2D;
import sim.portrayal.simple.RectanglePortrayal2D;

public class SpeciesPop {

	SpeciesStats stats;
	int population;
	OvalPortrayal2D portrayal;
	Color myColor;
	
	public SpeciesPop(SpeciesStats s, int p) {
		stats = s;
		population = p;
		

		

		
		
		//Color c = new Color(ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255));
		this.portrayal = new OvalPortrayal2D();
		myColor = this.getNextRandomColor();		
		portrayal.paint=myColor;
		portrayal.filled=true;
		//portrayal.scale=0.5;
	
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
	
	public String toString(){
		return "["+this.getStats().getType()+","+this.getPopulation()+"]";
	}
	
	public Color getNextRandomColor(){
		float min=60;
		float max=255;
		
		float randR = (float) ((min + (Math.random()*(max-min)))/255f);
		float randG = (float) ((min + (Math.random()*(max-min)))/255f);
		float randB = (float) ((min + (Math.random()*(max-min)))/255f);
		System.out.println("color is ("+randR+","+randG+","+randB+")");
		
		return new Color(randR,randG,randB);
		//return Color.red;
	}
}
