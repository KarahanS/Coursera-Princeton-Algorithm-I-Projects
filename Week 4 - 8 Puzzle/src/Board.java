
import java.util.Arrays;
import java.util.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
	private final int[][] tiles;
	private Board twin;

	public Board(int[][] tiles) {
		this.tiles = new int[tiles.length][tiles.length];
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				this.tiles[i][j] = tiles[i][j];
			}
		}
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(tiles.length + "\n");
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				str.append(" "+tiles[i][j]);
			}
			str.append("\n");
		}
		return str.toString();

	}

	public int dimension() {
		return tiles.length;
	}

	// number of tiles out of place
	// The Hamming distance between a board and the goal board is the number
	// of tiles in the wrong position.
	public int hamming() {
		int count = 0;
		int supposedToBe = 1;
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				if (tiles[i][j] != supposedToBe && tiles[i][j] != 0)
					count++;
				supposedToBe = ((supposedToBe+1) == (tiles.length*tiles.length)) ? (0) : (supposedToBe + 1);
			}
		}
		return count;
	}

	// sum of Manhattan distances between tiles and goal
	// The Manhattan distance between a board and the goal board is the sum
	// of the Manhattan distances (sum of the vertical and horizontal distance)
	// from the tiles to their goal positions.
	public int manhattan() {
		int count = 0;
		int supposedToBe = 1;
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				if (tiles[i][j] != supposedToBe && tiles[i][j] != 0) {
					int label = tiles[i][j];
					int actualRow = (label % tiles.length == 0) ? (label / tiles.length - 1) : (label / tiles.length);
					int actualColumn = (label % tiles.length == 0) ? (tiles.length - 1) : (label % tiles.length - 1);
					count += Math.abs(actualRow - i) + Math.abs(actualColumn - j);
				}
				supposedToBe = (supposedToBe+1 == (tiles.length*tiles.length)) ? (0) : (supposedToBe + 1);
			}
		}
		return count;

	}

	// is this board the goal board?
	public boolean isGoal() {
		boolean cond = true;
		int supposedToBe = 1;
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				if (tiles[i][j] != supposedToBe) {
					cond = false;
					break;
				}
				supposedToBe = (supposedToBe+1 == (tiles.length*tiles.length)) ? (0) : (supposedToBe + 1);
			}
			if (!cond)
				break;
		}
		return cond;

	}

	// does this board equal y?
	public boolean equals(Object y) {
		if(y == null) return false;
		if (y.getClass() == this.getClass()) {
			Board b = (Board) y;
			return (Arrays.deepEquals(b.tiles, this.tiles));
		} else
			return false;

	}

	// all neighboring boards
	// Returns any kind of collection that implements iterable.
	// For this implementation, I choose stack.
	public Iterable<Board> neighbors() {
		Stack<Board> itr = new Stack<Board>();

		int row = -1, column = -1;
		boolean condition = false;
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				if (tiles[i][j] == 0) {
					row = i;
					column = j;
					condition = true;
					break;
				}
			}
			if (condition)
				break;
		}
		// left
		if (column - 1 >= 0) {
			int[][] left = copy(tiles);

			// swap
			left[row][column] = left[row][column - 1];
			left[row][column - 1] = 0;
			itr.push(new Board(left));
		}
		// right
		if ((column + 1) <= (tiles.length - 1)) {
			int[][] right = copy(tiles);

			// swap
			right[row][column] = right[row][column + 1];
			right[row][column + 1] = 0;
			itr.push(new Board(right));
		}
		// above
		if (row - 1 >= 0) {
			int[][] above = copy(tiles);

			// swap
			above[row][column] = above[row - 1][column];
			above[row - 1][column] = 0;
			itr.push(new Board(above));

		}
		// below
		if (row + 1 <= tiles.length - 1) {
			int[][] below = copy(tiles);

			// swap
			below[row][column] = below[row + 1][column];
			below[row + 1][column] = 0;
			itr.push(new Board(below));

		}
		return itr;

	}

	private int[][] copy(int[][] source) {
		int[][] imitate = new int[source.length][source.length];
		for (int i = 0; i < source.length; i++) {
			for (int j = 0; j < source.length; j++) {
				imitate[i][j] = source[i][j];
			}
		}
		return imitate;
	}

	public Board twin() {
		if(twin != null) return twin;
		int i1, j1;
		int i2, j2;


		i1 = StdRandom.uniform(tiles.length);
		j1 = StdRandom.uniform(tiles.length);
		i2 = StdRandom.uniform(tiles.length);
		j2 = StdRandom.uniform(tiles.length);

		while (tiles[i1][j1] == 0 || tiles[i2][j2] == 0 || tiles[i1][j1] == tiles[i2][j2]) {
			if (tiles[i1][j1] == 0) {
				i1 = StdRandom.uniform(tiles.length);
				j1 = StdRandom.uniform(tiles.length);
			}
			if (tiles[i2][j2] == 0) {
				i2 = StdRandom.uniform(tiles.length);
				j2 = StdRandom.uniform(tiles.length);
			}
			if (tiles[i1][j1] == tiles[i2][j2]) {
				i1 = StdRandom.uniform(tiles.length);
				j1 = StdRandom.uniform(tiles.length);
			}
		}

		// Copy
		int[][] copy = copy(tiles);

		// Swap
		int ref = copy[i1][j1];
		copy[i1][j1] = copy[i2][j2];
		copy[i2][j2] = ref;

		twin = new Board(copy);
		
		int[][] immutability = new int[copy.length][copy.length];
		System.arraycopy(copy, 0, immutability, 0, copy.length);
		return new Board(immutability);
		
	}

	public static void main(String[] args) {
		int[][] array = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
		Board board = new Board(array);
		System.out.println(board);
		System.out.println(board.dimension());
		System.out.println(board.hamming());
		System.out.println(board.manhattan());
		System.out.println(board.isGoal());
		Iterable<Board> itr = board.neighbors();
		for (Board neighbor : itr) {
			System.out.println(neighbor);
			System.out.println(neighbor.equals(board));
		}

	}
}
