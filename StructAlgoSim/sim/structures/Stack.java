package sim.structures;

import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiStack;

public class Stack {
	
	private Vector<Object> s = new Vector<Object>();
	
	GuiStack gui; 
	public GuiElement getGuiElement(){
		return gui;
	}
	public Stack(Rectangle bounds){
		gui = new GuiStack(bounds,s);
	}
	public void push(Object obj){
		s.add(obj);
		gui.setAdded((String)obj);
		gui.startAnimation();
		gui.repaint();
	}
	public Object pop(){
		if(isEmpty()) return null;
		Object obj = s.get(s.size()-1);
		gui.setRemoved((String)obj);
		gui.startAnimation();
		gui.repaint();
		return obj;
	}
	public boolean isEmpty(){
		if(s.size() == 0)
			return true;
		else
			return false;
	}
}
