package sim;

import java.awt.Rectangle;

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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		Stack s = new Stack(new Rectangle(100,100, 50, 100));
		Variable v = new Variable(new Rectangle(50,50,100,100),"win");
		Pop pop = new Pop(new Rectangle(100,200,100,50),s, v);
		Push push = new Push(new Rectangle(200,200,100,50), v, s);
		
		frame.add(pop.getGuiElement());
		frame.add(push.getGuiElement());
		frame.add(v.getGuiElement());
		frame.add(s.getGuiElement());
		frame.validate();
		frame.setVisible(true);
	}

}
