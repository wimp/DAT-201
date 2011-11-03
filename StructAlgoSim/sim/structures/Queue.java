package sim.structures;


import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiQueue;
import sim.gui.elements.GuiElement.GraphicalStructure;

/**
 * A class representing a queue. Its visual component is the GuiQueue.
 */
public class Queue implements GraphicalStructure{
	private Vector<Object> data;
	private GuiQueue gui;
	public GuiElement getGuiElement(){
		return gui;
	}
	/**
	 * Constructor. 
	 * @param bounds The size of the graphical element.
	 */	
	public Queue(Rectangle bounds){
		data = new Vector<Object>();
		gui = new GuiQueue(bounds, data);
	}
	
	/**
	 * Adds an element to the end of the queue. 
	 * @param o The object to be added.
	 */
	public void add(Object o){
		data.add(o);
		gui.startAnimation();
		gui.setAdded((String)o);
		
		gui.repaint();
	}
	/**
	 * Removes an element from the front of the queue.
	 * @return The element at the front of the queue.
	 */
	public Object remove(){
		if(data.size()>0){
		Object o = data.remove(0);
		gui.startAnimation();
		gui.setRemoved((String)o);
		gui.repaint();
		return o;
		}
		return null;
	}
	
}
