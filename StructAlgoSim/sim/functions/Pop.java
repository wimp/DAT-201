package sim.functions;

import java.awt.event.ActionEvent;

import sim.structures.Stack;
import sim.structures.Variable;


public class Pop extends Function {
	
	Stack s;
	Variable v;
	
	public Pop(Stack s, Variable v) {
		super("Pop");
		this.s=s;
		this.v=v;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		v.setValue((String)s.pop());
	}

}
