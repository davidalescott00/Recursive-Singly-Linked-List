/**
	 * David Scott
	 * Completed 2/5/2021
	 */
/***************************************************************
 * A basic singly linked list implementation: LinkedList<Item>
 * 
 * Basic Operations:
 *     LinkedList()
 *     isEmpty()
 *     size()
 *     push_front(Item)
 *     peek_front()
 *     pop_front()
 * 
 ***************************************************************/
import java.util.NoSuchElementException;

public class LinkedList<Item> {
	private int _size; // number of elements on list
	private Node _head; // sentinel before first item
	private Node _tail; // sentinel after last item

	//
	// Node helper class facilitating forward-directional pointing
	//
	private class Node {
		private Item _item;
		private Node _next;

		public Node(Item item, Node next) {
			_item = item;
			_next = next;
		}
	}

	public LinkedList() {
		_tail = new Node(null, null);
		_head = new Node(null, _tail);
	}

	public boolean isEmpty() {
		return _head._next == _tail;
	}

	public int size() {
		return _size;
	}

	/**
	 * Insert item between left and right nodes
	 * 
	 * @param left  -- a node
	 * @param item  -- a user-provided item
	 * @param right -- a node
	 */
	private void insert(Node left, Item item, Node right) {
		left._next = new Node(item, right);
		_size++;
	}

	/**
	 * Add an element to the tail of the linked list: O(1) operation
	 * 
	 * @param item
	 */
	public void push_front(Item item) {
		insert(_head, item, _head._next);
	}

	/**
	 * @return the first element in the list.
	 */
	public Item peek_front() {
		return _head._next._item;
	}

	/**
	 * Remove the first element and return it
	 * 
	 * @return an item
	 * @throws NoSuchElementException if the list is empty
	 */
	public Item pop_front() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();

		Item item = _head._next._item;

		// Delete the first valid node containing data; pass previous node to
		// deleteNextNode
		deleteNextNode(_head);

		return item;
	}
	
	/**
	 * Checks if LL contains target item
	 * @return true if contains target
	 * @param target that we are looking for
	 */
	public boolean contains(Item target) {
		return containsHelper(target, _head._next);
	}

	private boolean containsHelper(Item target, Node n) {
		if (atEnd(n))
			return false;

		if (n._item == target) {
			return true;
		}

		return containsHelper(target, n._next);
	}
	/**
	 * Removes all elements from list
	 *
	 */
	public void clear() {
		if (isEmpty())
			return;

		pop_front();
		clear();
	}

	/**
	 * Returns the element in the middle of the list. 
	 * For an even length list, the ‘left’ middle value will be returned
	 * @return generic item found at middle of the list
	 * @throws NoSuchElementException if list is empty
	 */
	public Item middle() throws NoSuchElementException {
		Node middleNode = middleHelper(_head, _head);
		return middleNode._item;
	}

	private Node middleHelper(Node n, Node m) throws NoSuchElementException {
		if (n._next.equals(_tail)) {
			throw new NoSuchElementException();
		}
		if (atEnd(m) || atNextToEnd(m)) {
			return n;
		}
		return middleHelper(n._next, m._next._next);
	}

	/**
	 * Recursive way to reverse LL
	 * After helper method reverses, head and tails are fixed
	 * @return String representation of the contents of the list
	 */
	public void reverse() {
		reverseHelper(_head);
		Node temp = _tail;
		_tail = _head;
		_head = temp;
	}
	private Node reverseHelper(Node n) {
		if (n._next == null) {
			return n;
		}
		else {
			Node temp = n._next;
			n._next = null;
			Node restOf = reverseHelper(temp);
			temp._next = n;
			return restOf;
		}
	}

	/**
	 * @return String representation of the contents of the list
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();

		for (Node n = _head._next; n != _tail; n = n._next) {
			s.append(n._item + " ");
		}

		return s.toString();
	}

	// HELPER METHODS - NON RECURSIVE

	/**
	 * Deletes the AFTER the given node (need to pass X.prev to delete X); returns
	 * the same node in the list
	 * 
	 * @param prev -- a node: prev._next is deleted
	 * @return prev
	 * 
	 *         Nodes: ... prev toDelete ... rest of list
	 *
	 */
	private Node deleteNextNode(Node prev) {
		// The given node cannot be the last valid node in the list
		if (atNextToEnd(prev) || atEnd(prev))
			throw new RuntimeException();

		prev._next = prev._next._next;
		_size--;

		return prev;
	}

	private boolean atEnd(Node n) {
		return (n._next == null);
	}

	private boolean atNextToEnd(Node n) {
		return (n._next == _tail);
	}

}