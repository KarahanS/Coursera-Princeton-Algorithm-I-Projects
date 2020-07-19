
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class BruteCollinearPoints {
	private final Point[] points;
	private LineSegment[] segments;

	public BruteCollinearPoints(Point[] points) {
		if (points == null) throw new IllegalArgumentException();
		this.points = new Point[points.length];

		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) throw new IllegalArgumentException();
			this.points[i] = points[i];
		}
		for(int i=0; i<points.length; i++) {
			for(int j=i+1; j<points.length; j++) {
				if(points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
			}
		}
		QuickSort(this.points, 0, this.points.length - 1);

	}

	public int numberOfSegments() {
		int counter = 0;
		for (int i = 0; i < points.length - 3; i++) {
			for (int j = i + 1; j < points.length - 2; j++) {
				double slope = points[i].slopeTo(points[j]);
				for (int n = j + 1; n < points.length - 1; n++) {
					if (points[i].slopeTo(points[n]) != slope)
						continue;
					for (int m = n + 1; m < points.length; m++) {
						if (points[i].slopeTo(points[m]) != slope)
							continue;
						counter++;
					}
				}
			}
		}
		return counter;
	}

	// Collinear points --> 4 points connected together
	public LineSegment[] segments() {
		if(this.segments != null) {
			LineSegment[] immutable = new LineSegment[segments.length];
			System.arraycopy(segments, 0, immutable, 0, segments.length);
			return immutable;
		}
		this.segments = new LineSegment[numberOfSegments()];
		int counter = -1;
		// O(n^4 - 24)
		for (int i = 0; i < points.length - 3; i++) {
			for (int j = i + 1; j < points.length - 2; j++) {
				double slope = points[i].slopeTo(points[j]);
				for (int n = j + 1; n < points.length - 1; n++) {
					if (points[i].slopeTo(points[n]) != slope) continue;
					for (int m = n + 1; m < points.length; m++) {
						if (points[i].slopeTo(points[m]) != slope) continue;		
						segments[++counter] = new LineSegment(points[i], points[m]);
					}
				}
			}
		}
		// To avoid mutability
		LineSegment[] immutable = new LineSegment[segments.length];
		System.arraycopy(segments, 0, immutable, 0, segments.length);
		return immutable;

	}
	private void swap(Point[] a, int i, int j) {
		Point remember = a[j];
		a[j] = a[i];
		a[i] = remember;
		
	}
	// Natural Order
	private void QuickSort(Point[] a, int left, int right) {
		if(left>=right) return;
		int i = left, j = right + 1;
		
		while(true) {
			// Stops when there is a value higher than pivot on left.

			while(less(a[++i], a[left])) { 
				if(i>=right) break;
			}
			// Stops when there is a value less than pivot on right.
			while(less( a[left], a[--j])) if(j<=left) break;
			if(i>=j) break;
			swap(a, i, j);
		}
		swap(a, left, j);
		QuickSort(a, left, j-1);
		QuickSort(a, j+1, right);
		
	}
	private boolean less(Point a, Point b) {
		return a.compareTo(b) < 0;
	}

	

	public static void main(String[] args) {

		// read the n points from a file
		In in = new In(args[0]);
		int n = in.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		// draw the points
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}

}
