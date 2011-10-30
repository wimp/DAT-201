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
	private  GuiArray gui;
	private Object[][] array;
	private int sizeX;
	private int sizeY;
	private static int MAX_SIZE = 100;
	
// Getters and setters //
	public GuiArray getGuiElement(){
		return gui;
	}
// Class constructors //
	/**
	 * Create a new instance of Array() that is one dimensional with the size of {@link size}
	 * @param bounds
	 * @param size
	 */
	public Array(Rectangle bounds, int sizeY){
		this(bounds,sizeY,1);
	}
	/**
	 * Create a new instance of Array() that is two dimensional with the first array being of sizeA and the second array being of sizeB
	 * @param bounds
	 * @param sizeA
	 * @param sizeB
	 */
	public Array(Rectangle bounds, int sizeY, int sizeX){
		if(sizeY < MAX_SIZE && sizeX < MAX_SIZE){
			array = new Object[sizeY][sizeX];
			this.sizeY = sizeY;
			this.sizeX = sizeX;
			gui = new GuiArray(bounds, array);
		}
	}
	/**
	 * Inserts the given Object in a one-dimensional array at indexY
	 * @param itemToAdd
	 * @param indexY
	 */
	public void insertAt(Object itemToAdd, int indexY){
		insertAt(itemToAdd, indexY, 0);
		gui.repaint();
	}
	/**
	 * Inserts the given Object in a two-dimensional array at indexX
	 * @param itemToAdd
	 * @param indexY
	 * @param indexX
	 */
	public void insertAt(Object itemToAdd, int indexY, int indexX){
		array[indexY][indexX] = itemToAdd;
		gui.repaint();
		/*
		boolean intelligent = true, stupid = false;
		
		if(!intelligent && stupid){
		// Alternative syntax: xD
			for(int i = 0;i < sizeY;i++){
				if(i == indexY){
					for(int j = 0;j < sizeX;j++){
						if(j == indexX){
							array[i][j] = itemToAdd;
							return;
						}
					}
				}
			}
		}else return;*/
	}
	/**
	 * Method to get the value of a one-dimensional array at indexY
	 * @param indexY
	 * @return The Object at indexY
	 */
	public Object valueAt(int indexY){
		return valueAt(indexY, 0);
	}
	/**
	 * Method to get the value of a two-dimensional array at indexY,indexX
	 * @param indexY
	 * @param indexX
	 * @return The Object at array[indexY][indexX]
	 */
	public Object valueAt(int indexY, int indexX){
		return array[indexY][indexX];
	}
	public Object removeItem(int indexY){
		return removeItem(indexY,0);
	}
	private Object removeItem(int indexY, int indexX) {
		String item = array[indexY][indexX].toString();
		array[indexY][indexX] = null;
		gui.repaint();
		return item;
	}
}