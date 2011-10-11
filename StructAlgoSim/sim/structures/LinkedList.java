package sim.structures;

import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiList;

/**
 * A class representing a linked list. 
 * The attributes, doubly or singly linked and circular or non-circular 
 * are set in the constructor.
 *
 */
public class LinkedList {
	private GuiList gui;
	private Vector<Node> v = new Vector<Node>();
	
	public GuiList getGuiElement(){
		return gui;
	}
	/**
	 * Constructor. 
	 * @param bounds The size of the graphical element.
	 * @param doublyLinked Determines whether nodes will link both forwards and backwards.
	 * @param circular Determines if the last node will link to the first of the list (and vice verse if the list if doubly-linked)
	 */
	public LinkedList(Rectangle bounds, boolean doublyLinked, boolean circular, boolean animated){

		Node dummy = new Node("dummy");
		
		dummy.setNext(dummy);
		dummy.setPrevious(dummy);
		dummy.setAdded(false);
		v.add(dummy);
		gui = new GuiList(bounds,v, doublyLinked, circular, animated);
	}
	/**
	 * Constructor. 
	 * @param bounds The size of the graphical element.
	 * @param doublyLinked Determines whether nodes will link both forwards and backwards.
	 * @param circular Determines if the last node will link to the first of the list (and vice verse if the list if doubly-linked)
	 */
	public LinkedList(Rectangle bounds, boolean doublyLinked, boolean circular){
		this(bounds, doublyLinked, circular, true);
	}
	/**
	 * Adds a new node to the start of the list.
	 * @param value The object (most likely a string of text) that this node is to contain.
	 */
	public void addFirst(Object value){
		insertAt(1, value);
	}
	/**
	 * Adds a new node to the end of the list.
	 * @param value The object (most likely a string of text) that this node is to contain.
	 */
	public void addLast(Object value){
		insertAt(v.size()-1, value);
	}
	public void removeElementAt(int index){
		if(index > 0){
		v.elementAt(index).setRemoved(true);
		gui.repaint();
		gui.getAnimation().start();
		}
	}
	public Node elementAt(int value){
		return v.elementAt(value);
	}
	/**
	 * Adds a new node before a node in the list.
	 * @param element The element that the new node will be placed before.
	 * @param value The object (most likely a string of text) that this node is to contain.
	 */
	public void insertAt( int index, Object value){
		if(index>0){
		Node n = new Node(value);
		v.elementAt(index).getPrevious().setNext(n);
		n.setPrevious(v.elementAt(index).getPrevious());
		
		v.elementAt(index).setPrevious(n);
		n.setNext(v.elementAt(index));
		
		v.insertElementAt(n, index);
		
		gui.repaint();
		gui.getAnimation().start();
		}
	}
	/**
	 * The Node class is an inner class of {@link SingleLinkedList} that contains 
	 * information about where an element is in relationship to other elements in a list
	 */
	public class Node{
	// Class variables
		private Object 	value;
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
		public Node(Object value){
			this.previous 	= null;
			this.next 		= null;
			this.value		= value;
			added = true;
			removed = false;
		}
	}
}