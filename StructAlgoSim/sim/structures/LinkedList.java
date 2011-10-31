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
	private Node dummy;

	public GuiList getGuiElement(){
		return gui;
	}
	/**
	 * Constructor. 
	 * @param bounds The size of the graphical element.
	 * @param animated Determines whether insertion and removal will be animated.
	 */
	public LinkedList(Rectangle bounds, boolean animated){

		dummy = new Node("dummy", 0);

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
		insertAt(0, value,false );
	}
	/**
	 * Adds a new node to the end of the list.
	 * @param value The object (most likely a string of text) that this node is to contain.
	 */
	public void addLast(Object value){
		insertAt(v.size()-1, value, true);
	}
	/**
	 * Removes an element at a specified index in the list.
	 * @param index The index of the element to be removed.
	 */
	public Object removeElementAt(int index){
		if(index > 0 && index < v.size()){
			gui.stopAnimation();
			gui.repaint();
			gui.setRemoved(v.elementAt(index));
			Object s = v.elementAt(index).getValue();
			v.set(index, null);
			
			gui.startAnimation();
			return s;
		}
		else return null;
	}
	/**
	 * Gets an element at a specified index in the list.
	 * @param index The index of the element to be removed.
	 */
	public String get(int index){
		for(Node n : v) 
			if(n!=null) 
				if(n.getIndex()==index) return n.getValue().toString();
		return null;
	}
	/**
	 * Adds a new node before a node in the list.
	 * @param index The index of the element that the new node will be placed before.
	 * @param value The object (most likely a string of text) that this node is to contain.
	 */
	public void insertAt( int index, Object value, boolean after){

		gui.stopAnimation();
		if(index <0) 
			index = v.size()-1;
		
		Node n = null;
		Node indexNode = null;
		
		if(index>=0 && index<v.size()){
			
			for(Node in : v){
				if(in.getIndex()==index) indexNode = in;
			}
			
			if(after){
				n = new Node(value, index+1);
				indexNode.getNext().setPrevious(n);
				n.setNext(indexNode.getNext());

				indexNode.setNext(n);
				n.setPrevious(indexNode);
			}
			else {
				n = new Node(value, index);
				indexNode.getPrevious().setNext(n);
				n.setPrevious(indexNode.getPrevious());

				indexNode.setPrevious(n);
				n.setNext(indexNode);
			}
			
			if(after && index == v.size()-1){
				n.setIndex(v.size());
				v.add(n);
			}
			else if(!after && index == 0)
			{
				n.setIndex(v.size());
				v.add(n);
			}
			else if(after)
				v.insertElementAt(n, index+1);
			else
				v.insertElementAt(n, index);
		}
		for(Node node : v){
			if(node!=null)
			node.setIndex(v.indexOf(node));
		}
		gui.repaint();
		gui.startAnimation();
		
		if(n!=null)
			n.setAdded(true);
	}
}