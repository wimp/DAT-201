package sim.structures;

import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiList;
import sim.structures.elements.Node;

/**
 * A class representing a linked list. Its visual component is the GuiList.
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
	 * @param animated Determines whether insertion and removal will be animated.
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
	/**
	 * Removes an element at a specified index in the list.
	 * @param index The index of the element to be removed.
	 */
	public Object removeElementAt(int index){
		if(index > 0 && index < v.size()){
		v.elementAt(index).setRemoved(true);
		gui.repaint();
		gui.startAnimation();
		return v.elementAt(index).getValue();
		}
		else return null;
	}
	/**
	 * Gets an element at a specified index in the list.
	 * @param index The index of the element to be removed.
	 */
	public String elementAt(int index){
		return v.elementAt(index).getValue().toString();
	}
	/**
	 * Adds a new node before a node in the list.
	 * @param index The index of the element that the new node will be placed before.
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