package sim;

import java.awt.Rectangle;

import javax.swing.JFrame;

import sim.functions.Pop;
import sim.functions.Push;
import sim.structures.SingleLinkedList;
import sim.structures.Stack;
import sim.structures.Variable;

public class StructAlgoSim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//RailwayPostfix rsim = new RailwayPostfix();
		
		JFrame frame = new JFrame();
		frame.setTitle("StructAlgoSim 0.1");
		frame.setSize(700,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);

		Stack s = new Stack(new Rectangle(100,300, 100, 100));
		Variable v = new Variable(new Rectangle(50,10,100,20),"win", true);
		Pop pop = new Pop(new Rectangle(100,200,100,25),s, v);
		Push push = new Push(new Rectangle(200,200,100,25), v, s);
		SingleLinkedList list = new SingleLinkedList(new Rectangle(100,100,100,100));
		frame.add(list.getGuiElement());
		frame.validate();
		frame.setVisible(true);
	}

}
