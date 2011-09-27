package sim.structures;

import java.awt.Rectangle;

import javax.swing.JComponent;

import sim.gui.elements.GuiVariable;

public class Variable {

	GuiVariable gui;
	
	public Variable(Rectangle bounds, String value, boolean editable){
		gui = new GuiVariable(bounds,value, editable);
	}
	public JComponent getGuiElement() {
		return gui;
	}
	public void setValue(String value){
		gui.setValue(value);
	}
	public String getValue(){
		return gui.getValue();
	}
}
