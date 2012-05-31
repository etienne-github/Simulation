package simulation.entity;

import sim.field.grid.SparseGrid2D;
import simulation.SimulationModel;

public class Herbivorious extends Animal {

	private static final long serialVersionUID = 1L;
	private PerceptionTableCell[][] perception;
	private float vegetationWeightEatenByStep;

	public Herbivorious(String type, SimulationModel simModel) {
		super(type, simModel);
		int perceptionPoint = (int) Math.max(smellPoint, visionPoint);
		
		//Initialize perception table
		perception = new PerceptionTableCell[2*perceptionPoint][2*perceptionPoint];
		for(PerceptionTableCell[] i:perception){
			for(PerceptionTableCell j:i){
				j=new PerceptionTableCell();
			}
		}
	}

	@Override
	public void action() {
		SparseGrid2D yard = simModel.getYard();

	}

	@Override
	public boolean canBeEaten() {
		// TODO retourner si disponible au mangeage
		//FIXME déplacer dans animal
		return true;
	}
	
	private void detectFood(SparseGrid2D yard,Integer[][] Vegetation, SimulationModel simModel) {
		
		//Parse surrounding to get vegetation
		int perceptionPoint = (int) Math.max(smellPoint, visionPoint);
		for(int dx=perceptionPoint*-1;dx<=perceptionPoint;dx++){
			for(int dy=perceptionPoint*-1;dy<=perceptionPoint;dy++){
				int x = yard.stx(getX()+dx);
				int y = yard.sty(getY()+dx);
				
				//update perception matrix
				perception[dx+perceptionPoint][dy+perceptionPoint].setX(x);
				perception[dx+perceptionPoint][dy+perceptionPoint].setY(y);
				perception[dx+perceptionPoint][dy+perceptionPoint].setAmount(simModel.getVegetationAt(x, y));				
			}
		}
		
	}
	
	private Integer[] getNearbyFood() {
		
		Integer[] neareastFoodCoordinate = new Integer[2];
		neareastFoodCoordinate[0]=-1; //the x
		neareastFoodCoordinate[1]=-1; //the y
		double minDistance=movePoint;
		
		double currentDistance;
		for(PerceptionTableCell[] i:perception){
			for(PerceptionTableCell j:i){
				currentDistance = getShortestDistanceToPoint(j.getX(), j.getY());
				if(currentDistance<=minDistance){ //if nearer food found
					minDistance = currentDistance; //update min distance
					neareastFoodCoordinate[0]=j.getX(); //update the x
					neareastFoodCoordinate[1]=j.getY(); //update the y
					
				}
			}
		}
		
		return neareastFoodCoordinate;
	}
	
	private Double moveTo(SparseGrid2D yard, Integer[] coordinates) {
		Double move = movePoint;
		
		if((coordinates[0]!=-1)&&(coordinates[1]!=-1)){//if valid coordinates
			while (move > 0 && !isSameLocation(coordinates[0],coordinates[1])) {
				int dX = getXShortestDirection(coordinates[0]);
				if (dX > 0)
					setX(getX() + 1);
				else if (dX < 0)
					setX(getX() - 1);
				setX(getX() % Entity.getGRID_SIZE_X());

				int dY = getYShortestDirection(coordinates[1]);
				if (dY > 0)
					setY(getY() + 1);
				else if (dY < 0)
					setY(getY() - 1);
				setY(getY() % Entity.getGRID_SIZE_Y());

				yard.setObjectLocation(this, getX(), getY());
				move--;
			}
			return (movePoint - move);
		}else{
			return movePoint;
		}
		

	}
	
	
	private Double moveRandom(SparseGrid2D yard) {
		float randomSign = (float) (Math.random()*2);
		float randomAngle = (float) Math.random();
		if(randomSign<=1){
			randomAngle*=-1;
		}
		
		//maximum distance toward random direction 
		float dX = (float) (Math.cos(randomAngle)*movePoint);
		float dY = (float) (Math.sin(randomAngle)*movePoint);
		
		//Avoid null move
		if((dX<1)&&(dY<1)){
			if(dX>dY){
				dX=1;
				dY=0;
			}else{
				dY=1;
				dX=0;
			}
		}

		//destination is
		int X = yard.stx((int) (getX()+dX));
		int Y = yard.sty((int) (getY()+dY));
		
		yard.setObjectLocation(this, getX(), getY());
		return (double) 0;
	
	}
	
	private void eat(int VegetationX, int VegetationY, Integer[][] vegetation, SimulationModel SimModel) {
		//consume vegetationand update weight
		weight+=SimModel.consumeVegetationAt(VegetationX, VegetationY, vegetationWeightEatenByStep);

	}
	
	
	class PerceptionTableCell {
		private float amount;
		private int X;
		private int Y;
		
		public PerceptionTableCell(){
			amount=-1;
			X=-1;
			Y=-1;
		}
		
		
		public float getAmount() {
			return amount;
		}
		public void setAmount(float amount) {
			this.amount = amount;
		}
		public int getX() {
			return X;
		}
		public void setX(int x) {
			X = x;
		}
		public int getY() {
			return Y;
		}
		public void setY(int y) {
			Y = y;
		}		
	}

}
