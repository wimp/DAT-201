package sim.functions;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;

import sim.gui.elements.GuiVariable;
import sim.structures.Stack;

public class Push extends Function {
	
	
	Stack s;
	GuiVariable v;
	
	public Push(Stack s, GuiVariable v){
		super("Push");
		this.s = s;
		this.v = v;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		s.push((Object)v.getValue());
	}
	@Override
	public JComponent getGuiElement() {
		return null;
	}
}
