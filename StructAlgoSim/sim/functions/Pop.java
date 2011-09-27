package sim.functions;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sim.gui.elements.GuiElement;
import sim.gui.elements.GuiFunction;
import sim.structures.Stack;
import sim.structures.Variable;


public class Pop implements ActionListener {
	
	Stack s;
	Variable v;
	GuiFunction gui;
	public GuiElement getGuiElement(){
		return gui;
	}
	public Pop(int x, int y, int w, int h, Stack s, Variable v) {
		gui = new GuiFunction(x,y,w,h,"Pop");
		gui.getButton().addActionListener(this);
		this.s=s;
		this.v=v;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		v.setValue((String)s.pop());
	}
}
