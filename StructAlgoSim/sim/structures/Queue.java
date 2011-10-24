package sim.structures;


import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiQueue;

public class Queue {
	Vector<Object> data;
	GuiQueue gui;
	
	public GuiElement getGuiElement(){
		return gui;
	}
	public Queue(Rectangle bounds){
		data = new Vector<Object>();
		gui = new GuiQueue(bounds, data);
	}
	
	public void add(Object o){
		data.add(o);
		gui.repaint();
	}
	public Object remove(){
		if(data.size()>0){
		Object o = data.remove(0);
		gui.repaint();
		return o;
		}
		return null;
	}
	
}
