package sim.functions;

import javax.swing.JButton;

import sim.gui.elements.Variable;
import sim.structures.Stack;

public class Push extends Function {
	JButton b;
	Push(Stack s, Variable v){
		b = new JButton("Push");

	}
}
