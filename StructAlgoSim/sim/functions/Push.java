package sim.functions;

import java.awt.event.ActionEvent;

import sim.structures.Stack;
import sim.structures.Variable;

public class Push extends Function {
	
	
	Stack s;
	Variable v;
	
	public Push(Stack s, Variable v){
		super("Push");
		this.s = s;
		this.v = v;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		s.push((Object)v.getValue());
	}
}
