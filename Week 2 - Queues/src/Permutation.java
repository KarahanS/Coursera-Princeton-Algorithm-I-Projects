

import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		
		RandomizedQueue<String> q = new RandomizedQueue<>();
		while (!StdIn.isEmpty()) {
			String s = StdIn.readString();
			q.enqueue(s);
		}
		Iterator<String> i = q.iterator();
		while (k>0) {
			k--;
			String s = i.next();
			StdOut.println(s);
		}
		
	}

}
