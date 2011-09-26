package sim.functions;

import java.awt.event.ActionEvent;

import sim.gui.elements.GuiStack;
import sim.gui.elements.GuiVariable;

public class Push extends Function {
	
	
	GuiStack s;
	GuiVariable v;
	
	public Push(GuiStack s, GuiVariable v){
		super("Push");
		this.s = s;
		this.v = v;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		s.push
	}
}
