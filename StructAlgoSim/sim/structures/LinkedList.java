package sim.structures;

import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiList;

/**
 * A class representing a single-linked list
 *
 */
public class LinkedList {

	public enum ListType{
		CIRCULARSINGLE,
		NONCIRCULARSINGLE,
		CIRCULARDOUBLE,
		NONCIRCULARDOUBLE
	}
	
	private GuiList gui;
	private Vector<Node> v = new Vector<Node>();
	
	public GuiList getGuiElement(){
		return gui;
	}
	
	public LinkedList(Rectangle bounds, ListType type){

		Node n0 = new Node(1, "head");
		Node n1 = new Node(2, "foo");
		n0.setNext(n1);
		Node n2 = new Node(3, "bar");
		n1.setNext(n2);
		n2.setPrevious(n1);
		Node n3 = new Node(4, "boo");
		n2.setNext(n3);
		n3.setPrevious(n2);
		Node n4 = new Node(5, "bar");
		n3.setNext(n4);
		n4.setPrevious(n3);
		
		n4.setNext(n1);
		n1.setPrevious(n4);
		
		v.add(n0);
		v.add(n1);
		v.add(n2);
		v.add(n3);
		v.add(n4);
		
		gui = new GuiList(bounds,v);
	}
	
	public void insertBeforeElement(Node element, Object value){
		Node n = new Node(v.indexOf(element), value);
		n.setPrevious(element.getPrevious());
		n.setNext(element);
		
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
		Node n = new Node(v.indexOf(element), value);
		
		n.setPrevious(element);
		n.setNext(element.getNext());
		
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
	 * The Node class is an inner class of {@link SingleLinkedList} that contains 
	 * information about where an element is in relationship to other elements in a list
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
		public Node(int index, Object value){
			this.index 		= index;
			this.previous 	= null;
			this.next 		= null;
			this.value		= value;
		}
	}
}