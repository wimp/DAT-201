package sim.structures;

import java.awt.Rectangle;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiVariable;

public class Variable {

	private GuiVariable gui;
	
	public boolean isEditable;
	
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
