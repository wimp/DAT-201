package sim.structures;

import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiList;
import sim.structures.elements.Node;

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
	public LinkedList(Rectangle bounds, boolean animated){

		Node dummy = new Node("dummy");
		
		dummy.setNext(dummy);
		dummy.setPrevious(dummy);
		dummy.setAdded(false);
		v.add(dummy);
		gui = new GuiList(bounds,v, animated);
	}
	/**
	 * Constructor. 
	 * @param bounds The size of the graphical element.
	 */
	public LinkedList(Rectangle bounds){
		this(bounds, true);
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
	public Object removeElementAt(int index){
		if(index > 0 && index < v.size()){
		v.elementAt(index).setRemoved(true);
		gui.repaint();
		gui.getAnimation().start();
		return v.elementAt(index).getValue();
		}
		else return null;
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
		if(index>0 && v.size()>=2){
		Node n = new Node(value);
		v.elementAt(index).getPrevious().setNext(n);
		n.setPrevious(v.elementAt(index).getPrevious());
		
		v.elementAt(index).setPrevious(n);
		n.setNext(v.elementAt(index));
		
		v.insertElementAt(n, index);
		}
		else {
			Node n = new Node(value);
			v.elementAt(0).getPrevious().setNext(n);
			n.setPrevious(v.elementAt(0).getPrevious());
			
			v.elementAt(0).setPrevious(n);
			n.setNext(v.elementAt(0));
			
			v.add(n);
			

		}
		gui.repaint();
		gui.getAnimation().start();
	}

}