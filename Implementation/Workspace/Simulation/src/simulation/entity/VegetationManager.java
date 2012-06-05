package simulation.entity;


import java.beans.PropertyChangeSupport;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Int2D;
import simulation.SimulationModel;
import utils.Constants;

public class VegetationManager extends Entity implements Steppable {

	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	private static final long serialVersionUID = 1L;
	private SimulationModel myModel;
	private Grass[][] Grasses;

	public PropertyChangeSupport getPropertyChangeSupport(){
		return support;
	}

	public VegetationManager(SimulationModel simModel) {
		super(simModel);
		myModel = simModel;
		Grasses= new Grass[myModel.getYard().getWidth()][myModel.getYard().getHeight()];
				
		System.out.println("Init Vegetation\n\n");
		Float[][] Vegetation = myModel.getVegetation();
		
		
		for(int i=0;i<myModel.getYard().getWidth();i++){
			for(int j=0;j<myModel.getYard().getHeight();j++){
				Vegetation[i][j]=(float) (/*Math.random()**/Constants.VEGETATION_MAX_WEIGHT_PER_CELL);
				//	System.out.print(Vegetation[i][j]+"|");
				Grass g = new Grass(simModel);
				Grasses[i][j]=g;
				myModel.schedule.scheduleRepeating(g);
				myModel.getYard().setObjectLocation(g, new Int2D(i,j));
				
			}
			//System.out.print("\n");
		}
		
		//System.out.print("\n\n");
		support.firePropertyChange("grown",null,Grasses);
	}

	public void growVegetation(){

		
		System.out.println("Grow Vegetation !\n\n");
		Float[][] Vegetation = myModel.getVegetation();
		
		for(int i=0;i<myModel.getYard().getWidth();i++){
			for(int j=0;j<myModel.getYard().getHeight();j++){
				
				float grownWeight = Vegetation[i][j]+Constants.VEGETATION_WEIGHT_GAINED_PER_STEP;
				if(grownWeight>=Constants.VEGETATION_MAX_WEIGHT_PER_CELL){
					grownWeight=Constants.VEGETATION_MAX_WEIGHT_PER_CELL;
				}
				Vegetation[i][j]=grownWeight;
				//System.out.print(Vegetation[i][j]+"|");
			}
			//System.out.println("");
		}
		
		//System.out.println("\n\n");
		support.firePropertyChange("grown",null,Grasses);
	}
	

	@Override
	public void step(SimState simState) {
		growVegetation();
	}

	

}
