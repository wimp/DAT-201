package sim.structures;

import java.awt.Rectangle;

import javax.swing.JComponent;

import sim.gui.elements.GuiVariable;

public class Variable {

	GuiVariable gui;
	String value;
	
	public Variable(Rectangle bounds, String value){
		this.value = value;
		gui = new GuiVariable(bounds,value, false);
	}
	public JComponent getGuiElement() {
		return gui;
	}
	public void setValue(String value){
		this.value = value;
		gui.setValue(value);
	}
	public String getValue(){
		return value;
	}
}
