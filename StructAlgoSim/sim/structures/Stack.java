package sim.structures;

import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiStack;
import sim.gui.elements.GuiElement.GraphicalStructure;
/**
 * A class representing a stack. Its visual component is the GuiStack.
 */
public class Stack implements GraphicalStructure{
	
	private Vector<Object> s = new Vector<Object>();
	
	private GuiStack gui; 
	public GuiElement getGuiElement(){
		return gui;
	}
	/**
	 * Constructor. 
	 * @param bounds The size of the graphical element.
	 */
	public Stack(Rectangle bounds){
		gui = new GuiStack(bounds,s);
	}
	/**
	 * Pushes an element to the top of the stack.
	 * @param o The object to be added.
	 */	
	public void push(Object o){
		s.add(o);
		gui.setAdded((String)o);
		gui.startAnimation();
		gui.repaint();
	}
	/**
	 * Pops an element from the top of the stack.
	 * @return The topmost element of the stack.
	 */	
	public Object pop(){
		if(isEmpty()) return null;
		Object obj = s.get(s.size()-1);
		gui.setRemoved((String)obj);
		s.remove(obj);
		gui.startAnimation();
		gui.repaint();
		return obj;
	}
	/**
	 * Checks whether the stack is empty or not.
	 * @return false if the stack has elements, true otherwise.
	 */	
	public boolean isEmpty(){
		if(s.size() == 0)
			return true;
		else
			return false;
	}
}
