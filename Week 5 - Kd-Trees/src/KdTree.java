
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;


// Point2D compareTo --> check y first, break ties by x
// first level = 0
// odd level --> below/above
// even level --> left/right

// right --> upper or right
// left --> below or left0

public class KdTree {
	private Node root;
	private int size;

	public KdTree() {
		root = null;
	}

	public boolean isEmpty() {
		return (root == null);
	}
	public int size() {
		return size;
	}
	public void insert(Point2D point) {
		if(point == null) throw new IllegalArgumentException();
		if (root == null) {
			root = new Node(point);
			this.size++;
		} else root = insertion(root, point, 0);

	}
	private Node insertion(Node node, Point2D point, int level) {
		if (node == null) {
			this.size++;
			return new Node(point);
		}
		int compare;
		if(level%2 == 0) compare = compareX(node.point, point);
		else compare = compareY(node.point, point);
		if (compare < 0)
			node.right = insertion(node.right, point, level + 1);
		else if (compare > 0)
			node.left = insertion(node.left, point, level + 1);
		else if (!node.point.equals(point)) node.right = insertion(node.right, point, level+1);
		return node;

	}
	
	public boolean contains(Point2D p) {
		if(p == null) throw new IllegalArgumentException();
		int level = 0;
		Node iterator = this.root;
		while(true) {
			if(iterator == null) return false;
			int compare;
			if(level%2 == 0) compare = compareX(iterator.point, p);
			else compare = compareY(iterator.point, p);
			if (compare < 0) {
				iterator = iterator.right;
				level++;
			}
			else if (compare > 0) {
				iterator = iterator.left;
				level++;
			}
			else if(iterator.point.x() == p.x() && iterator.point.y() == p.y()) return true;
			else {
				iterator = iterator.right;
				level++;
			}
			
		}
		
	}
	
	public void draw() {
		if(root == null) return;
		draw(root, 0, 1, 0, 1, 0);
		
	}
	// x and y are boundaries
	private void draw(Node node, double x_left, double x_right, double y_below, double y_upper, int level) {
		// Draw Point
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		StdDraw.point(node.point.x(), node.point.y());
		// Draw Splitting Lines
		StdDraw.setPenRadius();
		if(level%2 == 0) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(node.point.x(), y_below, node.point.x(), y_upper);  	
		} else {
			 StdDraw.setPenColor(StdDraw.BLUE);
			 StdDraw.line(x_left, node.point.y(), x_right, node.point.y());
		}
		if (node.left != null) {
			if(level % 2 == 0) draw(node.left, x_left, node.point.x(), y_below, y_upper, level+1);
			else draw(node.left, x_left, x_right, y_below, node.point.y(), level + 1);
		}
		if (node.right != null) {
			if (level % 2==0) draw(node.right, node.point.x(), x_right, y_below, y_upper , level+1);
			else draw(node.right, x_left, x_right, node.point.y(), y_upper, level+1);
		}
	}
	// Must be modified
	
	
	public Iterable<Point2D> range(RectHV rect) {
		if(rect == null) throw new IllegalArgumentException(); 
		Queue<Point2D> iterable = new Queue<>();
		Node iterator = root;
		range(0, rect, iterator, iterable);  
		return iterable;
		// to be continued
		
	}
	private void range(int level, RectHV rect, Node node, Queue<Point2D> iterable) {
		if(node == null) return;
		if(rect.contains(node.point)) {
			iterable.enqueue(node.point);
				range(level+1, rect, node.right, iterable);
				range(level+1, rect, node.left, iterable);
		} else {
			if(level%2 == 0) { // left or right or both
				// right
				if(rect.xmin() >= node.point.x()) {
					range(level+1, rect, node.right, iterable);
				} else if(rect.xmax() < node.point.x()) {  // left
					range(level+1, rect, node.left, iterable);
				} else { // both
					range(level+1, rect, node.right, iterable);
					range(level+1, rect, node.left, iterable);
				}
			} else {   // upper or below or both
				// upper
				if(rect.ymin() >= node.point.y()) {
					range(level+1, rect, node.right, iterable);
				} else if(rect.ymax() < node.point.y()) { // below
					range(level + 1, rect, node.left, iterable);
				} else {  // both
					range(level+1, rect, node.right, iterable);
					range(level + 1, rect, node.left, iterable);
				}
				
			}
		}
	}
	
	public Point2D nearest(Point2D p) {
		if(p==null) throw new IllegalArgumentException();
		if(root == null) return null;
		Point2D nearest = nearest(null, p, this.root, new RectHV(0,0,1,1), 0, Double.POSITIVE_INFINITY);
		return nearest;
	}
	
    // When performing nearest neighbor search, we organize the recursive method so that when 
	// there are two possible subtrees to go down, 
	// we always choose the subtree that is on the same side of the splitting line as
	// the query point as the first subtree to explore.
	
	// MUST BE MODIFIED
	private Point2D nearest(Point2D nearest, Point2D p, Node node, RectHV rectangle, int level, double distance) {
		if(node == null) return nearest;
		double dist = node.point.distanceSquaredTo(p);
		if(distance > dist) {
			distance = dist;
			nearest = node.point;
		}
		if(level%2==0) {
			RectHV left = new RectHV(rectangle.xmin(), rectangle.ymin(), node.point.x(), rectangle.ymax());
			RectHV right = new RectHV(node.point.x(), rectangle.ymin(), rectangle.xmax(), rectangle.ymax());
			if(p.x() < node.point.x()) {
				nearest = nearest(nearest, p, node.left, left, level+1, distance);
				if(p.distanceSquaredTo(nearest) >= right.distanceSquaredTo(p)) nearest = nearest(nearest, p, node.right, right, level+1, p.distanceSquaredTo(nearest));
			} else {
				nearest = nearest(nearest, p, node.right, right, level+1, distance);
				if(p.distanceSquaredTo(nearest) >= left.distanceSquaredTo(p)) nearest = nearest(nearest, p, node.left, left, level+1, p.distanceSquaredTo(nearest));
			} 
			
		} else {
			RectHV below = new RectHV(rectangle.xmin(), rectangle.ymin(), rectangle.xmax(), node.point.y());
			RectHV above = new RectHV(rectangle.xmin(), node.point.y(), rectangle.xmax(), rectangle.ymax());
			if(p.y() < node.point.y()) {
				nearest = nearest(nearest, p, node.left, below, level+1, distance);
				if(p.distanceSquaredTo(nearest) >= above.distanceSquaredTo(p)) nearest= nearest(nearest, p, node.right, above, level+1, p.distanceSquaredTo(nearest));
			} else {
				nearest = nearest(nearest, p, node.right, above, level+1, distance);
				if(p.distanceSquaredTo(nearest) >= below.distanceSquaredTo(p)) nearest = nearest(nearest, p, node.left, below, level+1, p.distanceSquaredTo(nearest));
				
			}
			
		}
		return nearest;
	}
	
	private static class Node {
		Point2D point;
		Node left;
		Node right;
		
		Node(Point2D point) {
			this.point = point;

		}

		public String toString() {

			return "(" +point.x() + ", " + point.y() + ")";
		}
	}
	private int compareY(Point2D o1, Point2D o2) {
		if(o1.y() > o2.y()) return 1;
		else if(o1.y() < o2.y()) return -1;
		else return 0;
	}
	private int compareX(Point2D o1, Point2D o2) {
		if(o1.x() > o2.x()) return 1;
		else if(o1.x() < o2.x()) return -1;
		else return 0;
	}
	public static void main(String[] args) {
		KdTree tree = new KdTree();
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
		for( Point2D points: tree.range(new RectHV(0.1, 0.1, 0.9, 0.7))) {
			System.out.println(points);
		}
		System.out.println("--");
		System.out.println(tree.nearest(new Point2D(0.5,0.1)));
		System.out.println(tree.nearest(new Point2D(0.3,0.9)));
		System.out.println(tree.nearest(new Point2D(0.4,0.6)));
		System.out.println(tree.nearest(new Point2D(0.6,0.2)));
		System.out.println(tree.nearest(new Point2D(0.9,0.2)));
	}


}
