package sim.structures;

import javax.swing.JComponent;

import sim.gui.elements.GuiVariable;

public class Variable {

	GuiVariable gui;
	String value;
	
	public Variable(String value){
		this.value = value;
		gui = new GuiVariable(value, false);
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
