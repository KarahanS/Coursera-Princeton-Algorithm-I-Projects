import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

	private final Point[] NaturalOrder;
	private final Point[] points;
	private LineSegment[] segments;
	private Point[] aux;
	private int size = -1;

	public FastCollinearPoints(Point[] points) {
		if (points == null)
			throw new IllegalArgumentException();
		this.points = new Point[points.length];

		for (int i = 0; i < points.length; i++) {
			if (points[i] == null)
				throw new IllegalArgumentException();
			this.points[i] = points[i];
		}

		sort(this.points, null, 0, this.points.length - 1);
		NaturalOrder = new Point[points.length];
		System.arraycopy(this.points, 0, NaturalOrder, 0, points.length);

	}

	public int numberOfSegments() {
		if (size != -1)
			return size;
		int counter = 0;
		int extra = 0;

		for (int i = 0; i < points.length; i++) {
			System.arraycopy(NaturalOrder, i, points, i, points.length - i);
			sort(points, points[i].slopeOrder(), i + 1, points.length - 1);

			int adjacents = 1;
			double slope = 0;
			boolean segment = false;
			for (int j = i + 1; j < points.length; j++) {
				if (j == i + 1) {
					slope = points[i].slopeTo(points[j]);
					adjacents++;
				} else if (points[i].slopeTo(points[j]) == slope) {
					adjacents++;
					if (adjacents >= 4)
						segment = true;
				} else {
					if (segment) {
						if (adjacents > 4)
							extra++;
						segment = false;
						counter++;
					}
					slope = points[i].slopeTo(points[j]);
					adjacents = 2;
				}
			}
			if (segment) {
				if (adjacents > 4)
					extra++;
				counter++;
			}

		}

		size = counter - extra;
		return (counter - extra);

	}

	public LineSegment[] segments() {
		if (points.length <= 1)
			return new LineSegment[0];
		if (this.segments != null) {
			LineSegment[] immutable = new LineSegment[segments.length];
			System.arraycopy(segments, 0, immutable, 0, segments.length);
			return immutable;
		}
		if (size == -1)
			size = numberOfSegments();
		this.segments = new LineSegment[size];
		int counter = -1;

		Point ref = points[0];
		for (int i = 0; i < points.length; i++) {

			System.arraycopy(NaturalOrder, 0, points, 0, points.length);

			// Swap
			points[0] = points[i];
			points[i] = ref;

			// Now, we have our origin point at index 0. Other part is going to be sorted.
			sort(points, points[0].slopeOrder(), 1, points.length - 1);

			int adjacents = 1;
			double slope = 0;
			boolean segment = false;
			boolean isMin = false;
			if (less(points[1], points[0]))
				isMin = true;
			for (int j = 1; j < points.length; j++) {
				if (j == 1) {
					slope = points[0].slopeTo(points[j]);
					adjacents++;
				} else if (slope == points[0].slopeTo(points[j])) {
					adjacents++;
					if (adjacents >= 4)
						segment = true;
				} else {
					if (segment && !isMin) {
						segments[++counter] = new LineSegment(points[0], points[j - 1]);
					}
					segment = false;
					slope = points[0].slopeTo(points[j]);
					adjacents = 2;
					if (less(points[j], points[0]))
						isMin = true;
					else
						isMin = false;
				}

			}
			if (segment && !isMin) {
				segments[++counter] = new LineSegment(points[0], points[points.length - 1]);

			}
		}

		LineSegment[] immutable = new LineSegment[segments.length];
		System.arraycopy(segments, 0, immutable, 0, segments.length);
		return immutable;

	}

	private void merge(Point[] a, Point[] aux, int lo, int hi, int mid, Comparator<Point> o) {
		if (lo >= hi)
			return;
		for (int i = lo; i <= hi; i++) {
			aux[i] = a[i];
		}
		int left = lo;
		int right = mid + 1;
		// Left iterates from lo to mid-1
		// Right iterate from mid to hi

		if (o != null) {
			for (int k = lo; k <= hi; k++) {
				if (left > mid)
					a[k] = aux[right++];
				else if (right > hi)
					a[k] = aux[left++];
				else if (less(o, aux[right], aux[left]))
					a[k] = aux[right++];
				else
					a[k] = aux[left++]; // natural order is preserved.
			}
		} else {

			for (int k = lo; k <= hi; k++) {
				if (left > mid)
					a[k] = aux[right++];
				else if (right > hi)
					a[k] = aux[left++];
				else if (less(aux[left], aux[right]))
					a[k] = aux[left++];
				else if (aux[left].compareTo(aux[right]) == 0)
					throw new IllegalArgumentException();
				else
					a[k] = aux[right++];
			}
		}
	}

	private void sort(Point[] a, Point[] aux, int lo, int hi, Comparator<Point> o) {
		if (lo >= hi)
			return;
		int mid = lo + (hi - lo) / 2;

		sort(a, aux, lo, mid, o);
		sort(a, aux, mid + 1, hi, o);
		// It is possible that they are already sorted.
		if (o != null && !less(o, a[mid + 1], a[mid]))
			return;
		if (o == null && less(a[mid], a[mid + 1]))
			return;
		merge(a, aux, lo, hi, mid, o);

	}

	private void sort(Point[] a, Comparator<Point> o, int lo, int hi) {
		aux = new Point[a.length];
		sort(a, aux, lo, hi, o);
	}

	private boolean less(Comparator<Point> o, Point a, Point b) {
		return (o.compare(a, b) < 0);
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
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}

		StdDraw.show();
	}

}
