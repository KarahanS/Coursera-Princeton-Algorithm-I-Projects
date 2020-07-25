import java.util.Comparator;
import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private PriorityObject winner;
	private boolean isSolvable;

	// priority = moves + manhattan
	// if priority is low, it's good.
	// find a solution to the initial board
	public Solver(Board initial) {
		if (initial == null)
			throw new IllegalArgumentException();
		isSolvable = Solve(initial);
	}

	public boolean isSolvable() {
		return isSolvable;
	}

	private boolean Solve(Board root) {
		boolean cond = false;
		PriorityObject init = new PriorityObject(root, 0, null);
		MinPQ<PriorityObject> pq = new MinPQ<PriorityObject>(init.comparator());
		pq.insert(init);
		Board twinBro = root.twin();
		MinPQ<PriorityObject> pqTwin = new MinPQ<PriorityObject>(init.comparator());
		pqTwin.insert(new PriorityObject(twinBro, 0, null));
		PriorityObject searchNode;
		PriorityObject searchNodeTwin;
		Board ref;
		Board refTwin;
		while (true) {
		    searchNode = pq.min();
			searchNodeTwin = pqTwin.min();
			ref = pq.min().board;
			refTwin = pqTwin.min().board;
			pqTwin.delMin();
			pq.delMin();
			if (ref.isGoal()) {
				winner = searchNode;
				cond = true;
				break;
			}
			if (refTwin.isGoal()) {
				winner = null;
				cond = false;
				break;
			}

			for (Board neighbor : ref.neighbors()) {
				if (searchNode.prev == null || !searchNode.prev.board.equals(neighbor)) {
					pq.insert(new PriorityObject(neighbor, searchNode.move + 1, searchNode));
				}

			}
			for (Board neighbor : refTwin.neighbors()) {
				if (searchNodeTwin.prev == null || !searchNodeTwin.prev.board.equals(neighbor)) {
					pqTwin.insert(new PriorityObject(neighbor, searchNodeTwin.move + 1, searchNodeTwin));
				}

			}

		}
		// Memory usage
		searchNode = null;
		searchNodeTwin = null;
		ref = null;
		refTwin = null;
		
		isSolvable = cond;
		return cond;
	}

	public int moves() {
		if (!isSolvable)
			return -1;
		return winner.move;

	}

	public Iterable<Board> solution() {
		if (!isSolvable)
			return null;
		Stack<Board> s = new Stack<Board>();
		s.add(winner.board);
		PriorityObject itr = winner;
		while(itr.prev != null) {
			itr = itr.prev;
			s.add(itr.board);
		}
		Stack<Board> sUp = new Stack<Board>();
		while(!s.isEmpty()) sUp.add(s.pop());
		return sUp;
	}

	private class PriorityObject {
		private final Board board;
		private final int cost;
		private final PriorityObject prev;
		private final int move;

		PriorityObject(Board board, int move, PriorityObject prev) {
			this.move = move;
			this.board = board;
			this.cost = move + board.manhattan();
			this.prev = prev;

		}

		Comparator<PriorityObject> comparator() {
			return new CustomComparator();
		}

		class CustomComparator implements Comparator<PriorityObject> {

			@Override
			public int compare(PriorityObject o1, PriorityObject o2) {
				if (o1.cost > o2.cost)
					return 1;
				else if (o1.cost < o2.cost)
					return -1;
				else
					return 0;
			}
		}
	}

	// test client
	public static void main(String[] args) {

		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] tiles = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				tiles[i][j] = in.readInt();
			}
		Board initial = new Board(tiles);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}
