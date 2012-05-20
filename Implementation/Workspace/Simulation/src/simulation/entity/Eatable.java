package simulation.entity;

public interface Eatable {

	public Double getEatingEnergy();
	
	public boolean canBeEaten();
	
	public void eaten();
}
