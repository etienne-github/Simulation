package simulation.entity;

import java.util.ArrayList;

import sim.field.grid.SparseGrid2D;
import sim.util.Int2D;
import simulation.SimulationModel;

public class Herbivorious extends Animal {

	private static final long serialVersionUID = 1L;
	private PerceptionTableCell[][] perception;

	public Herbivorious(String type, SimulationModel simModel) {
		super(type, simModel);
	//	System.out.println(this.getType()+" #"+this.hashCode()+" sees "+visionPoint+" meters away.");
		//System.out.println(this.getType()+" #"+this.hashCode()+" smells "+smellPoint+" meters away.");
	}
	
	public void  initializePerception(){
		//System.out.println(this.getType()+" #"+this.hashCode()+" perception initialized !");

		int perceptionPoint = (int) Math.max(smellPoint, visionPoint);
		
		
		//Initialize perception table
		perception = new PerceptionTableCell[2*perceptionPoint][2*perceptionPoint];
		for(int i = 0; i<2*perceptionPoint;i++){
			for(int j = 0; j<2*perceptionPoint;j++){
				perception[i][j]=new PerceptionTableCell();
			}
		}
		
	}

	@Override
	public void action() {
		SparseGrid2D yard = simModel.getYard();
		detectFood(yard, simModel.getVegetation(), simModel);
		
		Integer[] coor = getNearbyFood();
		if (weight < weightConsumeByDay * maxNbDaySafe) {
			//System.out.println(this.getType()+" #"+this.hashCode()+" is on ("+getX()+","+getY()+") and is hungry !");

			if((coor[0]!=-1)&&(coor[1]!=-1)){
				if(isOnFood()){
					//System.out.println(this.getType()+" #"+this.hashCode()+" and is on food !");
					eat((int)getX(),(int)getY(),simModel.getVegetation(),simModel);
				}else{
					//System.out.println(this.getType()+" #"+this.hashCode()+" and sees food on ("+coor[0]+","+coor[1]+")/("+simModel.getVegetationAt(coor[0], coor[1])+") !");
					moveTo(yard,coor);
				}
			}else{
				if(!isOnFood()){
					//System.out.println(this.getType()+" #"+this.hashCode()+" and doesn't see food !");
					moveRandom(yard);
				}else{
					//System.out.println(this.getType()+" #"+this.hashCode()+" and is on food !");
					eat((int)getX(),(int)getY(),simModel.getVegetation(),simModel);
				}
				

			}
			}else{
				//System.out.println(this.getType()+" #"+this.hashCode()+" is not hungry and is on ("+getX()+","+getY()+") !");
				moveRandom(yard);
			}
}
	
	private void detectFood(SparseGrid2D yard,Float[][] Vegetation, SimulationModel simModel) {
		
		//Parse surrounding to get vegetation
		int perceptionPoint = (int) Math.max(smellPoint, visionPoint);
		

		
		for(int dx=perceptionPoint*-1;dx<perceptionPoint;dx++){
			for(int dy=perceptionPoint*-1;dy<perceptionPoint;dy++){
				

				int x = yard.stx((int)getX()+dx);
				int y = yard.sty((int)getY()+dy);

				//System.out.println("yard : ("+yard.getWidth()+","+yard.getHeight()+")");
				while((Math.abs(x)>Math.abs(yard.getWidth()-1))||(x<0)){
					x=yard.stx(x);
				}
				
				while((Math.abs(y)>Math.abs(yard.getHeight()-1))||(y<0)){
					y=yard.stx(y);
				}
				
				//System.out.println(this.getType()+" #"+this.hashCode()+" myPosition is ("+getX()+","+getY()+") my perception is "+perceptionPoint+" i'm looking at ("+dx+","+dy+") of me, so at ("+x+","+y+")");

				
				
				//update perception matrix
				perception[dx+perceptionPoint][dy+perceptionPoint].setX(x);
				perception[dx+perceptionPoint][dy+perceptionPoint].setY(y);
				perception[dx+perceptionPoint][dy+perceptionPoint].setAmount(simModel.getVegetationAt(x, y));				
			}
		}
		//printPerception();
		
	}
	
	private Integer[] getNearbyFood() {
		ArrayList<Int2D> cases = new ArrayList<Int2D>();

		double minDistance=movePoint;
		
		double currentDistance;
		for(PerceptionTableCell[] i:perception){
			for(PerceptionTableCell j:i){
				if(j.getAmount()>0){
					currentDistance = getShortestDistanceToPoint(j.getX(), j.getY());
					if(currentDistance<minDistance){ //if nearer food found
						cases.clear();
						cases.add(new Int2D(j.getX(),j.getY()));
						minDistance = currentDistance; //update min distance					
					}else if(currentDistance==minDistance){
						cases.add(new Int2D(j.getX(),j.getY()));			
					}
				}

			}
		}
		
		if(cases.size()==0){
			Integer[] i = new Integer[2];
			i[0]=-1;
			i[1]=-1;
			return i;
		}else{
			int ind = (int) (Math.random()*cases.size());
			Integer[] i = new Integer[2];
			Int2D d = cases.get(ind);
			i[0]=d.x;
			i[1]=d.y;
			return i;
		}
	}
	
	private Double moveTo(SparseGrid2D yard, Integer[] coordinates) {
		Double move = movePoint;
		
		//System.out.println(this.getType()+" #"+this.hashCode()+" trying to move to ("+coordinates[0]+","+coordinates[1]+") and movePoint ("+movePoint+")");

		
		
		
		
		if((coordinates[0]!=-1)&&(coordinates[1]!=-1)){//if valid coordinates
		
			while (move > 0 && !isSameLocation(coordinates[0],coordinates[1])) {
				int dX = getXShortestDirection(coordinates[0]);
				if (dX > 0){
					setX(simModel.getYard().stx((int) (this.getX() + 1)));
					//System.out.println("x+1 -> "+this.getX());
					
				}else if (dX < 0){
					setX(simModel.getYard().stx((int) (this.getX() - 1)));
					//System.out.println("x-1 -> "+this.getX());
				}

				int dY = getYShortestDirection(coordinates[1]);
				if (dY > 0){
					setY(simModel.getYard().sty((int)getY() + 1));
					//System.out.println("y+1 -> "+this.getY());
					
				}else if (dY < 0){
					setY(simModel.getYard().sty((int)getY() - 1));
					//System.out.println("y-1 -> "+this.getY());
				}

				move--;
			}
			return (movePoint - move);
		}else{
			return movePoint;
		}
		

	}
	
	
	private Double moveRandom(SparseGrid2D yard) {
		float randomSign = (float) (Math.random()*2);
		float randomAngle = (float) ((float) Math.random()*2*Math.PI);
		if(randomSign<=1){
			randomAngle*=-1;
		}
		
		//maximum distance toward random direction 
		float dX = (float) (Math.cos(randomAngle)*movePoint);
		float dY = (float) (Math.sin(randomAngle)*movePoint);
		
		
		//System.out.println(this.getType()+" #"+this.hashCode()+"  ("+movePoint+") move randomly toward "+Math.toDegrees(randomAngle)+"+ degrees ("+Math.cos(randomAngle)+","+Math.sin(randomAngle)+") to ("+dX+","+dY+") from itself!");

		
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
		
		
		//System.out.println("yard : ("+yard.getWidth()+","+yard.getHeight()+")");
		while((Math.abs(X)>Math.abs(yard.getWidth()-1))||(X<0)){
			X=yard.stx(X);
		}
		
		while((Math.abs(Y)>Math.abs(yard.getHeight()-1))||(Y<0)){
			Y=yard.stx(Y);
		}
		
		
		//System.out.println(this.getType()+" #"+this.hashCode()+" move randomly to ("+X+","+Y+")!");

		yard.setObjectLocation(this, X, Y);
		return (double) 0;
	
	}
	
	private void eat(int VegetationX, int VegetationY, Float[][] floats, SimulationModel SimModel) {
		//consume vegetationand update weight

		float consumes=SimModel.consumeVegetationAt(VegetationX, VegetationY, toStep(weightConsumeByDay));
		
		//System.out.println(this.getType()+" #"+this.hashCode()+" tries to eat "+(toStep(weightConsumeByDay)*1000)+"g of  grass !");

		//System.out.println(this.getType()+" #"+this.hashCode()+" eats "+(consumes*1000)+"g of  grass !");
		weight+=consumes;

	}
	
	public boolean isOnFood(){
		if(simModel.getVegetationAt((int)getX(), (int)getY())>0){
			return true;
		}else{
			return false;
		}
	}
	
	public void printPerception(){
		int perceptionPoint = (int) Math.max(smellPoint, visionPoint);

		for(int i=0;i<perceptionPoint*2;i++){
			for(int j=0;j<perceptionPoint*2;j++){
				if((i==perceptionPoint)&&(j==perceptionPoint)){
					System.out.print("X ");
				}else{
					System.out.print(perception[i][j].getAmount()+" ");	
				}
						
			}
			System.out.print("\n");	
		}
		System.out.print("\n\n");
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
