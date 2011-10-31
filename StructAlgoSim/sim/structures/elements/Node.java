package sim.structures.elements;

/**
 * The Node class is an inner class of {@link SingleLinkedList} that contains 
 * information about where an element is in relationship to other elements in a list
 */
public class Node{
// Class variables
	private Object 	value;
	private int index;
	private Node 	next;
	private Node 	previous;
	private boolean added;
	private boolean removed;
	
// Getters and setters
	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isAdded() {
		return added;
	}
	public void setAdded(boolean added) {
		this.added = added;
	}		
	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

// Class constructor
	public Node(Object value, int index){
		this.previous 	= null;
		this.next 		= null;
		this.value		= value;
		this.index 		= index;
		added = true;
		removed = false;
	}
}
