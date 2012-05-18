package simulation.entity;

public class Entity {

	private static int GRID_SIZE_X;
	private static int GRID_SIZE_Y;
	
	protected int x;
	protected int y;

	public Entity() {
		x = 0;
		y = 0;
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	protected boolean isSameLocation(Entity entity) {
		if (entity.x == x && entity.y == y)
			return true;
		else
			return false;
	}

	protected int getXShortestDirection(Entity entity) {
		int xa = this.x;
		int xb = entity.x;

		int minAbsDx = Math.min(Math.abs(xb - xa),
				GRID_SIZE_X - Math.abs(xb - xa));
		if (minAbsDx == Math.abs(xb - xa)) {
			return xb - xa;
		} else {
			return GRID_SIZE_X - (xb - xa);
		}
	}

	protected int getYShortestDirection(Entity entity) {
		int ya = this.y;
		int yb = entity.y;

		int minAbsDy = Math.min(Math.abs(yb - ya),
				GRID_SIZE_Y - Math.abs(yb - ya));
		if (minAbsDy == Math.abs(yb - ya)) {
			return yb - ya;
		} else {
			return GRID_SIZE_Y - (yb - ya);
		}
	}

	protected double getShortestDistance(Entity entity) {
		/** Dx = min(abs(xb-xa) , X - abs(xb-xa)) avec X largeur de la grille **/
		/** Dy = min(abs(yb-ya) , X - abs(yb-ya)) avec Y hauteur de la grille **/
		/** Distance finale : entier le plus proche de sqrt(Dx^2 + Dy^2) **/
		int xa = this.x;
		int xb = entity.x;
		int ya = this.y;
		int yb = entity.y;

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
}
