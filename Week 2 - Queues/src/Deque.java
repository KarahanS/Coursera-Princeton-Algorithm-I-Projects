

import java.util.Iterator;

/*
 * Linked-List implementation is utilized for this part.
 */
public class Deque<Item> implements Iterable<Item> {
	private int size;
	private Node head;
	private Node tail;

	// construct and empty deque
	public Deque() {
		head = null;
		tail = null;
		size = 0;
	}

	public boolean isEmpty() {
		return head == null;
	}

	public int size() {
		return size;
	}

	public void addFirst(Item value) {
		if (value == null)
			throw new IllegalArgumentException();
		Node node = new Node(value);
		if (head == null) {
			head = node;
			tail = node;
		} else {
			node.next = head;
			head.prev = node;
			head = node;
		}
		size++;
	}

	public void addLast(Item value) {
		if (value == null)
			throw new IllegalArgumentException();
		Node node = new Node(value);
		if (head == null) {
			head = node;
			tail = node;
		} else {
			tail.next = node;
			node.prev = tail;
			tail = node;
		}
		size++;
	}

	public Item removeFirst() {
		if (head == null)
			throw new java.util.NoSuchElementException();
		Item value = head.value;
		if (head.next != null) {
			head = head.next;
			head.prev = null;
		} else {
			head = null;
			tail = null;
		}
		size--;
		return value;
	}

	public Item removeLast() {
		if (tail == null)
			throw new java.util.NoSuchElementException();
		Item value = tail.value;
		if (tail.prev != null) {
			tail = tail.prev;
			tail.next = null;
		} else {
			tail = null;
			head = null;
		}
		size--;
		return value;
	}

	@Override
	public Iterator<Item> iterator() {
		return new CustomIterator();
	}

	private class Node {
		private Item value;
		private Node next;
		private Node prev;

		Node(Item value) {
			this.value = value;
			this.next = null;
			this.prev = null;
		}
	}

	private class CustomIterator implements Iterator<Item> {
		private Node current = head;

		@Override
		public boolean hasNext() {
			return (current!=null);
		}

		@Override
		public Item next() {
			if(current == null) throw new java.util.NoSuchElementException();
			Item item = current.value;
			current = (current.next == null) ? (null) : (current.next);
			return item;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	public static void main(String[] args) {
		Deque<String> deck = new Deque<>();
		deck.addFirst("Macbeth");
		deck.addFirst("The Death on the Nile");
		deck.addLast("Brave New World");
		deck.addLast("Things Fall Apart");
		System.out.println(deck.isEmpty());
		System.out.println(deck.size());
		System.out.println(deck.removeFirst());
		System.out.println(deck.removeLast());
		Iterator<String> iterate = deck.iterator();
		while(iterate.hasNext()) {
			System.out.println(iterate.next());
		}
		iterate.remove();
		
	}

}

