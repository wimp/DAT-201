package sim.functions;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;

import sim.gui.elements.GuiVariable;
import sim.structures.Stack;


public class Pop extends Function {
	
	Stack s;
	GuiVariable v;
	
	public Pop(Stack s, GuiVariable v) {
		super("Pop");
		this.s=s;
		this.v=v;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		v.setValue((String)s.pop());
	}
	@Override
	public JComponent getGuiElement() {
		return null;
	}
}
