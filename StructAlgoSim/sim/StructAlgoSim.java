package sim;

import java.awt.FlowLayout;
import java.awt.Point;

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
		Stack s = new Stack(100,100, 50, 100);
		Variable v = new Variable(50,50,100,100,"win", false);
		Pop pop = new Pop(100,200,100,50,s, v);
		Push push = new Push(200,200,100,50,s, v);
		
		frame.add(pop.getGuiElement());
		frame.add(push.getGuiElement());
		frame.add(v.getGuiElement());
		frame.add(s.getGuiElement());
		frame.validate();
		frame.setVisible(true);
	}

}
