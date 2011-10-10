package sim.structures;

import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiArray;


public class Array {
//	private int MAX_SIZE = 50;
	private Vector<Object> v;
	private GuiArray gui;
	
	public GuiArray getGuiElement(){
		return gui;
	}
	
	public Array(Rectangle bounds, int size){
		v = new Vector<Object>();
//		v.setSize(size);
		gui = new GuiArray(bounds,v);
	}
	
	public Array(Rectangle bounds, int sizeA, int sizeB){
		v = new Vector<Object>();
		for(int i = 0;i < sizeA;i++){
			v.add(i, new Vector<Object>(sizeB));
		}
		gui = new GuiArray(bounds,v);
	}
	
	public void addItem(Object item){
		v.add(item);
		gui.setData(v);
		gui.validate();
		gui.repaint();
	}
	
	public void insertBefore(Object itemToAdd, Object item){
		int index = v.indexOf(item);
		v.add(index,itemToAdd);
		gui.setData(v);
		gui.validate();
		gui.repaint();
	}
	
	public void insertBefore(Object itemToAdd, int index){
		v.add(index, itemToAdd);
		gui.setData(v);
		gui.validate();
		gui.repaint();
	}
	
	public void insertAfter(Object itemToAdd, Object item){
		int index = v.indexOf(item);
		index++;
		v.add(index,itemToAdd);
		gui.setData(v);
		gui.validate();
		gui.repaint();
	}
	
	public void insertAfter(Object itemToAdd, int index){
		index++;
		v.add(index, itemToAdd);
		gui.setData(v);
		gui.validate();
		gui.repaint();
	}
	
	public Object removeItem(int index){
		Object val = v.remove(index);
		gui.setData(v);
		gui.validate();
		gui.repaint();
		return val;
	}
}