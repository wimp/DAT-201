package sim.structures;

import java.util.Vector;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiList;

/**
 * A class representing a single-linked list
 *
 */
public class SingleLinkedList {
	private GuiList gui;
	private Vector<Node> v = new Vector<Node>();
	
	public GuiElement getGuiElement(){
		return gui;
	}
	
	public SingleLinkedList(){
		v.add(null);
	}
	
	public void insertBeforeElement(Node element, Object value){
		Node n = new Node(v.indexOf(element),element.getPrevious(),element, value);
		element.getPrevious().setNext(n);
		v.insertElementAt(n, v.indexOf(element));
		for(int i = 0;i<v.size();i++){
			Node el = v.get(i);
			if(el != null)
				el.setIndex(v.indexOf(el));
		}
		element.setPrevious(n);
	}
	
	public void insertAfterElement(Node element, Object value){
		Node n = new Node(v.indexOf(element),element,element.getNext(), value);
		element.getNext().setPrevious(n);
		v.insertElementAt(n, element.getNext().getIndex());
		
		for(int i = 0;i<v.size();i++){
			Node el = v.get(i);
			if(el != null)
				el.setIndex(v.indexOf(el));
		}
		
		element.setNext(n);
	}
	
	/**
	 * The Node class is an inner class of {@link SingleLinkedList} that contains information about where an 
	 * element is in relationship to other elements in a list
	 *
	 */
	public class Node{
	// Class variables
		private Object 	value;
		private int 	index;
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
		
		public int getIndex(){
			return index;
		}
		
		public void setIndex(int index){
			this.index = index;
		}
		
		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	
	// Class constructor
		public Node(int index, Node previous, Node next, Object value){
			this.index 		= index;
			this.previous 	= previous;
			this.next 		= next;
			this.value		= value;
		}
	}
}