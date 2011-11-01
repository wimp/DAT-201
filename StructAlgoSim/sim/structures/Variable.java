package sim.structures;

import java.awt.Rectangle;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiVariable;

/**
 * A class representing a variable. Its visual component is the GuiVariable.
 * This class is used to hold data, usually as the output/input or index to functions
 *  such as Add, Insert, Get etc. 
 */
public class Variable {

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
	public void setValue(String value){
		gui.setValue(value);
	}
	public String getValue(){
		return gui.getValue();
	}
}
