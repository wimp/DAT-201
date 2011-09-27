package sim;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import sim.functions.Pop;
import sim.functions.Push;
import sim.structures.Stack;
import sim.structures.Variable;

public class StructAlgoSim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("StructAlgoSim 0.1");
		frame.setSize(700,600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		Stack s = new Stack();
		Variable v = new Variable("win");
		Pop pop = new Pop(s, v);
		Push push = new Push(s, v);
		frame.add(pop);
		frame.add(push);
		frame.add(v.getGuiElement());
		frame.add(s.getGuiElement());
		frame.validate();
	}

}
