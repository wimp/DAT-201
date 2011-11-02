package sim.structures;

import java.awt.Rectangle;
import sim.gui.elements.GuiArray;
import sim.gui.elements.GuiElement.GraphicalStructure;

/**
 * A class to implement one- and two dimensional arrays in the simulation gui.
 */
public class Array implements GraphicalStructure{
// Class variables //
	private  GuiArray gui;
	private Object[][] array;
	private static int MAX_SIZE = 100;
	private int dimensions;
	
// Getters and setters //
	public GuiArray getGuiElement(){
		return gui;
	}
	/**
	 * The total number of dimensions of this array.
	 * @return int Total number of dimensions.
	 */
	public int getDimensions(){
		return dimensions;
	}
// Class constructors //
	/**
	 * Create a new instance of Array() that is one dimensional with the size of sizeY
	 * @param bounds
	 * @param sizeY the number of elements in this one-dimensional array.
	 */
	public Array(Rectangle bounds, int sizeY){
		this(bounds,sizeY,1);
	}
	/**
	 * Create a new instance of Array() that is two dimensional with the first array being of sizeA and the second array being of sizeB
	 * @param bounds
	 * @param sizeY - the number of rows.
	 * @param sizeX - the number of columns.
	 */
	public Array(Rectangle bounds, int sizeY, int sizeX){
		if(sizeY < MAX_SIZE && sizeX < MAX_SIZE){
			array = new Object[sizeY][sizeX];
			gui = new GuiArray(bounds, array);
		}
		
		dimensions = sizeX > 0 ? 2 : 1;
	}
	/**
	 * Method to get the value of a one-dimensional array at indexY
	 * @param indexY
	 * @return The Object at indexY
	 */
	public Object getValueAt(int indexY){
		return getValueAt(indexY, 0);
	}
	/**
	 * Method to get the value of a two-dimensional array at indexY,indexX
	 * @param indexY
	 * @param indexX
	 * @return The Object at array[indexY][indexX]
	 */
	public Object getValueAt(int indexY, int indexX){
		return array[indexY][indexX];
	}
	/**
	 * Method to set the value of a one-dimensional array at indexY
	 * @param indexY
	 */
	public void setValueAt(Object value,int indexY){
		setValueAt(value, indexY,0);
	}
	/**
	 * Method to set the value of a two-dimensional array at indexY,indexX
	 * @param indexY
	 * @param indexX
	 */
	public void setValueAt(Object value, int indexY, int indexX) {
		if(value != null){
			String item = (String)value;
			array[indexY][indexX] = item;
			gui.setChanged(array[indexY][indexX]);
			gui.startAnimation();
			gui.repaint();
		}
	}
}