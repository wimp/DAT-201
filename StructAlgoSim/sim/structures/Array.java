package sim.structures;

import java.awt.Rectangle;
import java.util.Vector;

import sim.gui.elements.GuiArray;

/**
 * A class to implement one- and two dimensional arrays in the simulation gui.
 *
 */
public class Array {
// Class variables //
	private Vector<Object> v;
	private GuiArray gui;
	private int size;
	private int sizeB;
	
// Getters and setters //
	public GuiArray getGuiElement(){
		return gui;
	}

// Class constructors //
	/**
	 * Create a new instance of Array() that is one dimensional with the size of @size
	 * @param bounds
	 * @param size
	 */
	public Array(Rectangle bounds, int size){
		v = new Vector<Object>();
		gui = new GuiArray(bounds,v);
		this.size = size;
	}
	
	/**
	 * Create a new instance of Array() that is two dimensional with the first array being of sizeA and the second array being of sizeB
	 * @param bounds
	 * @param sizeA
	 * @param sizeB
	 */
	public Array(Rectangle bounds, int sizeA, int sizeB){
		v = new Vector<Object>();
		for(int i = 0;i < sizeA;i++){
			v.add(i, new Vector<Object>(sizeB));
		}
		gui = new GuiArray(bounds,v);
		
	}
	
	public void addItem(Object item){
		if(v.size() < size){
			v.add(item);
			gui.setData(v);
			gui.validate();
			gui.repaint();
		}else return;
	}
	
	public void insertBefore(Object itemToAdd, Object item){
		if(v.size() < size){
			int index = v.indexOf(item);
			v.add(index,itemToAdd);
			gui.setData(v);
			gui.validate();
			gui.repaint();
		}else return;
	}
	
	public void insertBefore(Object itemToAdd, int index){
		if(v.size() < size){
			v.add(index, itemToAdd);
			gui.setData(v);
			gui.validate();
			gui.repaint();
		}else return;
	}
	
	public void insertAfter(Object itemToAdd, Object item){
		if(v.size() < size){
			int index = v.indexOf(item);
			index++;
			v.add(index,itemToAdd);
			gui.setData(v);
			gui.validate();
			gui.repaint();
		}else return;
	}
	
	public void insertAfter(Object itemToAdd, int index){
		if(v.size() < size){
			index++;
			v.add(index, itemToAdd);
			gui.setData(v);
			gui.validate();
			gui.repaint();
		}else return;
	}
	
	public Object removeItem(int index){
		try{
			Object val = v.remove(index);
			gui.setData(v);
			gui.validate();
			gui.repaint();
			return val;
		}catch(NullPointerException e){
			return null;
		}catch(IndexOutOfBoundsException e){
			return null;
		}
		
	}
}