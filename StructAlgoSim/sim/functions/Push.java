package sim.functions;

import java.awt.event.ActionEvent;

import sim.gui.elements.GuiStack;
import sim.gui.elements.GuiVariable;

public class Push extends Function {
	public Push(GuiStack s, GuiVariable v){
		super("Push");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("WINWINWIN!");
	}
}
