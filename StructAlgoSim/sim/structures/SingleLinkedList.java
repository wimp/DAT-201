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
	
	public void insertBeforeElement(Node element){
		element.getPrevious();
//		Node n = new Node(v.)
	}
	
	public class Node{
		private Object value;
		private int index;
		private Node next;
		private Node previous;

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
		
		public Node(int index, Node previous, Node next){
			
		}
	}
}