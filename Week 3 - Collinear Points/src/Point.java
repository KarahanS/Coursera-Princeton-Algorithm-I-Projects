import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point>{
	private final int x;
	private final int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void draw() {
		StdDraw.point(x, y);
	}
	// draws the line segment from this point to that point
	public void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}
	
	// String representation
	public String toString() {
		return "("+ this.x + ", " + this.y + ")";
	}
	
	@Override
	public int compareTo(Point that) {
		if(this.y < that.y) return -1;
		else if (this.y > that.y) return 1;
		else {
			if (this.x < that.x) return -1;
			else if (this.x > that.x) return 1;
			else return 0;
		}
	}
	
	public double slopeTo(Point that) {
		double Diffy = that.y - this.y;
		double Diffx = that.x - this.x;
		if (Diffx == 0 && Diffy == 0) return Double.NEGATIVE_INFINITY;
		else if (Diffx == 0) return Double.POSITIVE_INFINITY;
		else if (Diffy == 0) return 0;
		else return Diffy/Diffx;
	}
	
	// compares two points by slopes they make with this point
	public Comparator<Point> slopeOrder() {
		return new CustomComparator();
		
	}
	private class CustomComparator implements Comparator<Point> {

		// Class.this.field --> to reach a field of outer class
		// Or use the method directly --> they refer to the same thing
		@Override
		public int compare(Point p1, Point p2) {
			double slopeP1 = slopeTo(p1);
			double slopeP2 = slopeTo(p2);
			// p1 < p2 if slopeP1 < slopeP2
			if (slopeP1 < slopeP2) return -1;
			else if (slopeP1 > slopeP2) return 1;
			else return 0;
		}
		
	}

}
