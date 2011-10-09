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
	
	private boolean circular;
	private boolean doublyLinked;
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
	public LinkedList(Rectangle bounds, boolean doublyLinked, boolean circular){
		
		this.doublyLinked = doublyLinked;
		this.circular = circular;
		
		Node dummy = new Node("dummy");
			
		if(circular) dummy.setNext(dummy);
		if(doublyLinked && circular) dummy.setPrevious(dummy);
		
		v.add(dummy);
		gui = new GuiList(bounds,v);
	}
	/**
	 * Adds a node to the start of the list.
	 * @param value The object (most likely a string of text) that this node is to contain.
	 */
	public void addFirst(Object value){
		insertAfterElement(v.elementAt(0), value);
	}
	public void insertBeforeElement(Node element, Object value){
		Node n = new Node(value);
		n.setPrevious(element.getPrevious());
		n.setNext(element);
		
		element.getPrevious().setNext(n);
		
		v.insertElementAt(n, v.indexOf(element));
		
		element.setPrevious(n);
		
		
		gui.validate();
		gui.repaint();
	}
	public void insertAfterElement(Node element, Object value){
		Node n = new Node(value);
		
		n.setPrevious(element);
		n.setNext(element.getNext());
		
		element.getNext().setPrevious(n);
		
		v.insertElementAt(n, v.indexOf(element)+1);

		element.setNext(n);
		
		gui.validate();
		gui.repaint();
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
		}
	}
}