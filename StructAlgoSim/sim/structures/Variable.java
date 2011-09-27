package sim.structures;

import java.awt.Point;

import javax.swing.JComponent;

import sim.gui.elements.GuiVariable;

public class Variable {

	GuiVariable gui;
	String value;
	
	public Variable(int x, int y, int w, int h, String value){
		this.value = value;
		gui = new GuiVariable(x,y,w,h,value, false);
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
