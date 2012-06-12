package simulation.entity;

import sim.engine.SimState;
import sim.engine.Steppable;
import simulation.SimulationModel;

@SuppressWarnings("serial")
public class Entity implements Steppable {

	private static int GRID_SIZE_X;
	private static int GRID_SIZE_Y;
	
	protected int x;
	protected int y;
	protected SimulationModel simModel;

	public Entity(SimulationModel simModel) {
		this.simModel = simModel;
	}

	public static int getGRID_SIZE_X() {
		return GRID_SIZE_X;
	}

	public static void setGRID_SIZE_X(int gRID_SIZE_X) {
		GRID_SIZE_X = gRID_SIZE_X;
	}

	public static int getGRID_SIZE_Y() {
		return GRID_SIZE_Y;
	}

	public static void setGRID_SIZE_Y(int gRID_SIZE_Y) {
		GRID_SIZE_Y = gRID_SIZE_Y;
	}
	public float getY() {
		return simModel.getYard().getObjectLocation(this).y;
	}

	public float getX() {
		return simModel.getYard().getObjectLocation(this).x;
	}

	public void setX(float x) {
		
		simModel.getYard().setObjectLocation(this, (int) x, (int)getY());
	}

	public void setY(float y) {
		simModel.getYard().setObjectLocation(this, simModel.getYard().getObjectLocation(this).x,(int) y);
	}

	
	protected boolean isSameLocation(int x,int y) {
		if (x ==(int)getX() && y == (int)getY())
			return true;
		else
			return false;
	}

	protected boolean isSameLocation(Entity entity) {
		if (entity.getX() == (int)getX() && entity.getY() == (int)getY())
			return true;
		else
			return false;
	}
	
	protected int getXShortestDirection(int x) {
		int xa = (int) getX();
		int xb = x;

		int minAbsDx = Math.min(Math.abs(xb - xa),
				GRID_SIZE_X - Math.abs(xb - xa));
		if (minAbsDx == Math.abs(xb - xa)) {
			return xb - xa;
		} else {
			return GRID_SIZE_X - (xb - xa);
		}
	}
	

	protected int getXShortestDirection(Entity entity) {
		int xa = (int) getX();
		int xb = (int) entity.getX();

		int minAbsDx = Math.min(Math.abs(xb - xa),
				GRID_SIZE_X - Math.abs(xb - xa));
		if (minAbsDx == Math.abs(xb - xa)) {
			return xb - xa;
		} else {
			return GRID_SIZE_X - (xb - xa);
		}
	}
	
	protected int getYShortestDirection(int y) {
		int ya = (int) getY();
		int yb = y;

		int minAbsDy = Math.min(Math.abs(yb - ya),
				GRID_SIZE_Y - Math.abs(yb - ya));
		if (minAbsDy == Math.abs(yb - ya)) {
			return yb - ya;
		} else {
			return GRID_SIZE_Y - (yb - ya);
		}
	}

	protected int getYShortestDirection(Entity entity) {
		int ya = (int)getY();
		int yb = (int) entity.getY();

		int minAbsDy = Math.min(Math.abs(yb - ya),
				GRID_SIZE_Y - Math.abs(yb - ya));
		if (minAbsDy == Math.abs(yb - ya)) {
			return yb - ya;
		} else {
			return GRID_SIZE_Y - (yb - ya);
		}
	}
	
	
	protected double getShortestDistanceToPoint(int x, int y){
		int xa = (int)getX();
		int xb = x;
		int ya = (int)getY();
		int yb = y;
		if((xa==xb)&&(ya==yb)){
			return 0;
		}else{
			int dX = Math.min(Math.abs(xb - xa),
					GRID_SIZE_X - Math.abs(xb - xa));
			int dY = Math.min(Math.abs(yb - ya),
					GRID_SIZE_Y - Math.abs(yb - ya));
			double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));

			return distance;
		}

	}

	protected double getShortestDistanceToEntity(Entity entity) {
		/** Dx = min(abs(xb-xa) , X - abs(xb-xa)) avec X largeur de la grille **/
		/** Dy = min(abs(yb-ya) , X - abs(yb-ya)) avec Y hauteur de la grille **/
		/** Distance finale : entier le plus proche de sqrt(Dx^2 + Dy^2) **/
		int xa = (int)getX();
		int xb = (int)entity.getX();
		int ya = (int)getY();
		int yb = (int)entity.getY();

		int dX = Math.min(Math.abs(xb - xa),
				GRID_SIZE_X - Math.abs(xb - xa));
		int dY = Math.min(Math.abs(yb - ya),
				GRID_SIZE_Y - Math.abs(yb - ya));
		double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));

		return distance;
	}

	public String toString() {
		return "(x: " + x + ", y: " + y + ")";
	}

	@Override
	public void step(SimState arg0) {
		x = simModel.getYard().getObjectLocation(this).x;
		y = simModel.getYard().getObjectLocation(this).y;	
	}
	
}
