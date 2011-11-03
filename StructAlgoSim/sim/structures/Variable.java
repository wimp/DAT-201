package sim.structures;

import java.awt.Rectangle;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiVariable;
import sim.gui.elements.GuiElement.GraphicalStructure;

/**
 * A class representing a variable. Its visual component is the GuiVariable.
 * This class is used to hold data, usually as the output/input or index to functions
 *  such as Add, Insert, Get etc. 
 */
public class Variable implements GraphicalStructure{

	private GuiVariable gui;
	
	public boolean isEditable;
	/**
	 * Constructor. 
	 * @param bounds The size of the graphical element.
	 * @param value The initial value of this variable.
	 * @param editable true will make this variable editable.
	 */	
	public Variable(Rectangle bounds, String value, boolean editable){
		gui = new GuiVariable(bounds,value, editable);
		this.isEditable = editable;
	}
	public GuiElement getGuiElement() {
		return gui;
	}
	/**
	 * Sets the value of this variable to the one provided.
	 * @param value the new value of this variable.
	 */
	public void setValue(String value){
		gui.setValue(value);
	}
	/**
	 * Gets the current value of this variable.
	 * @return value the value of this variable.
	 */
	public String getValue(){
		return gui.getValue();
	}
	
	public void setEditable(boolean editable){
		this.isEditable = editable;
	}
	
	public boolean getEditable(){
		return isEditable;
	}
}
