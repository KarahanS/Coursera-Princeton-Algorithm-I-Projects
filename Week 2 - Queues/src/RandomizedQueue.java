

import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/*
 * Resizing array implementation
 * When array reaches to its length, array is resized.
 * When array diminishes to its 1/4 length, array is resized.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
	// adds new item to the last part of the array
	private Item[] array;
	private int index; 

	public RandomizedQueue() {
		array = (Item[]) new Object[1];
		index = 0;
	}

	public boolean isEmpty() {
		return index == 0;
	}

	public int size() {
		return index;
	}

	public void enqueue(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		array[index++] = item;
		if (index == array.length) {
			Resize(array.length * 2);
		}
	}

	// Needs modification (constant time?) now it takes O(n) (worst case)
	public Item dequeue() {
		if(index == 0) throw new java.util.NoSuchElementException();
		int indice = StdRandom.uniform(index);
		Item i = array[indice];
		// swap
		array[indice] = array[--index];
		array[index] = null;
		if (index>0 && index == array.length/4) {
			Resize(array.length/2);
		}
		return i;	
	}

	public Item sample() {
		if(index == 0) throw new java.util.NoSuchElementException();
		return array[StdRandom.uniform(index)];
	}

	private void Resize(int length) {
		Item[] arr = (Item[]) new Object[length];
		for (int i = 0; i < index; i++) {
			arr[i] = array[i];
		}
		array = arr;
	}

	@Override
	public Iterator<Item> iterator() {
		return new CustomIterator();
	}

	private class CustomIterator implements Iterator<Item> {
		private Item[] arr;
		private int n;
		
	
		private CustomIterator() {
			arr = (Item[]) new Object[index];
			 for(int i=0; i<index; i++) {
				 arr[i] = array[i];
			 }
			 n = index;
			 StdRandom.shuffle(arr); // O(n) linear time complexity
			
		}

		@Override
		public boolean hasNext() {
			return n > 0;
		}

		@Override
		public Item next() {
			if(n==0) throw new java.util.NoSuchElementException();
			Item item = arr[--n]; 
			arr[n] = null;
			return item;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public static void main(String[] args) {
		int n = 5;
		RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
		for (int i = 0; i < n; i++)
		    queue.enqueue(i);
		for (int a : queue) {
		    for (int b : queue)
		        StdOut.print(a + "-" + b + " ");
		    StdOut.println();
		}
		
		
		RandomizedQueue<String> deck = new RandomizedQueue<>();
		deck.enqueue("Macbeth");
		deck.enqueue("The Death on the Nile");
		deck.enqueue("Brave New World");
		deck.enqueue("Things Fall Apart");
		System.out.println(deck.isEmpty());
		System.out.println(deck.dequeue());
		System.out.println(deck.dequeue());
		System.out.println(deck.sample());
	
	
		Iterator<String> iterate = deck.iterator();
		while(iterate.hasNext()) {
			System.out.println(iterate.next());
		}
		iterate.remove();
	}
}
