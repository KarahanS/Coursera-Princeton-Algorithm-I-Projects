import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	private final TreeSet<Point2D> set;

	public PointSET() {
		set = new TreeSet<>();
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public int size() {
		return set.size();
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		set.add(p);
	}

	public boolean contains(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		return set.contains(p);
	}

	public void draw() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		for (Point2D point : set) {
			StdDraw.point(point.x(), point.y());
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new IllegalArgumentException();
		Queue<Point2D> iterable = new Queue<>();
		for (Point2D point : set) {
			if (rect.contains(point)) {
				iterable.enqueue(point);
			}
		}
		return iterable;
	}

	// This could be improved - Counting can be stopped
	// after observing that distance is increasing.
	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		double distance = Integer.MAX_VALUE;
		Point2D nearest = null;
		for (Point2D point : set) {
			if (point.distanceSquaredTo(p) < distance) {
				nearest = point;
				distance = point.distanceSquaredTo(p);
			}
		}
		return nearest;

	}

	// Unit testing
	public static void main(String[] args) {
		PointSET tree = new PointSET();
		tree.insert(new Point2D(0.7, 0.2));
		tree.insert(new Point2D(0.5, 0.4));
		tree.insert(new Point2D(0.2, 0.3));
		tree.insert(new Point2D(0.8, 0.7));
		tree.insert(new Point2D(0.9, 0.6));
		tree.insert(new Point2D(0.4, 0.7));
		tree.insert(new Point2D(0.1, 0.9));
		tree.insert(new Point2D(0.3, 0.6));

		System.out.println(tree.size());
		System.out.println(tree.contains(new Point2D(0.9, 0.6)));
		System.out.println(tree.contains(new Point2D(0.5, 0.4)));
		System.out.println(tree.contains(new Point2D(0.4, 0.7)));
		System.out.println(tree.contains(new Point2D(0.2, 0.1)));
		tree.draw();
		for (Point2D points : tree.range(new RectHV(0.1, 0.1, 0.9, 0.7))) {
			System.out.println(points);
		}
		System.out.println();
		System.out.println(tree.nearest(new Point2D(0.5,0.1)));
		System.out.println(tree.nearest(new Point2D(0.3,0.9)));
		System.out.println(tree.nearest(new Point2D(0.4,0.6)));
		System.out.println(tree.nearest(new Point2D(0.6,0.2)));
		System.out.println(tree.nearest(new Point2D(0.9,0.2)));
		
	}

}
