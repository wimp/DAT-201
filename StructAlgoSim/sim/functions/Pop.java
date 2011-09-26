package sim.functions;

import java.awt.event.ActionEvent;

import sim.gui.elements.GuiStack;
import sim.gui.elements.GuiVariable;

public class Pop extends Function {
	
	GuiStack s;
	GuiVariable v;
	
	public Pop(GuiStack s, GuiVariable v) {
		super("Pop");
		this.s=s;
		this.v=v;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		v.setValue((String)s.pop());
	}

}
