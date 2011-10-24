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
	private  GuiArray gui;
	private int size;
	private int sizeB;
	private final int MAXSIZE = 100;
	
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
		if(size < MAXSIZE){
		v = new Vector<Object>();
		for(int x = 0;x < size;x++){
			v.add(x, "");
		}
		gui = new GuiArray(bounds,v);
		this.size = size;
		}
	}
	
	/**
	 * Create a new instance of Array() that is two dimensional with the first array being of sizeA and the second array being of sizeB
	 * @param bounds
	 * @param sizeA
	 * @param sizeB
	 */
	public Array(Rectangle bounds, int sizeA, int sizeB){
		if(sizeA < MAXSIZE && sizeB<MAXSIZE){
		v = new Vector<Object>();
		for(int i = 0;i < sizeA;i++){
			Vector<Object> v1 = new Vector<Object>(sizeB);
			for(int x = 0;x < sizeB;x++){
				v1.add(x, "");
			}
			v.add(i, new Vector<Object>(sizeB));
		}
			gui = new GuiArray(bounds,v);
		}
	}
	public void insertAt(Object itemToAdd, int index){
		if(v.size() < size){
			v.set(index,itemToAdd);
			gui.setData(v);
			gui.validate();
			gui.repaint();
		}else return;
	}
	public Object valueAt(int index){
			return v.get(index);
	}
	public Object removeItem(int index){
		try{
			Object val = v.set(index, "");
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