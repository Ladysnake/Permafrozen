package ladysnake.permafrozen.util;

public class GridPoint {
	public GridPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	protected double x;
	protected double y;

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double centreSqrDist(GridPoint other) {
		return this.centreSqrDist(other.x, other.y);
	}

	public double centreSqrDist(double x, double y) {
		double dx = Math.abs(x - this.x);
		double dy = Math.abs(y - this.y);
		return dx * dx + dy * dy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o == null) {
			return false;
		} else if (o instanceof GridPoint) {
			GridPoint cell = (GridPoint) o;
			return cell.x == this.x && cell.y == this.y;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int result = 7;
		result = 29 * result + Double.hashCode(this.x);
		result = 29 * result + Double.hashCode(this.y);
		return result;
	}

	@Override
	public String toString() {
		return "GridPoint(" + this.x
				+ ", " + this.y
				+ ')';
	}
}
